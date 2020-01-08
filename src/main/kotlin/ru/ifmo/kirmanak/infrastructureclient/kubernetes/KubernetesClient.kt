package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.AppsV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import ru.ifmo.kirmanak.infrastructureclient.ClientException
import ru.ifmo.kirmanak.infrastructureclient.ClusterClient
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode

private const val DEPLOYMENT_SELECTOR = "app"

open class KubernetesClient(
    apiClient: ApiClient, private val namespace: String, private val deployment: String
) : ClusterClient {
    private val coreApi = CoreV1Api(apiClient)
    private val metricsApi = MetricsV1Beta1Api(apiClient)
    private val appsApi = AppsV1Api(apiClient)

    override fun getNodes(): Array<ClusterNode> {
        val dep = getDeployment()

        val selector = dep.spec?.selector?.matchLabels?.get(DEPLOYMENT_SELECTOR)
            ?: throw ClientException("Deployment \"$deployment\" has no \"$DEPLOYMENT_SELECTOR\" selector")
        val labelSelector = "$DEPLOYMENT_SELECTOR=$selector"

        val pods = getPods(labelSelector)

        return pods.items.map { KubernetesNode(it, this) }.toTypedArray()
    }

    internal fun getMetricsPerPod() = metricsApi.getPodMetrics(namespace)

    private fun getDeployment() =
        try {
            appsApi.readNamespacedDeployment(deployment, namespace, null, null, null)
                ?: throw ClientException("Deployment \"$deployment\" was not found in namespace \"$namespace\"")
        } catch (e: ApiException) {
            throw ClientException(e)
        }


    private fun getPods(labelSelector: String) =
        try {
            coreApi.listNamespacedPod(namespace, null, null, null, null, labelSelector, null, null, null, null)
                ?: throw ClientException("Pods list of \"$deployment\" was not found in namespace \"$namespace\"")
        } catch (e: ApiException) {
            throw ClientException(e)
        }
}