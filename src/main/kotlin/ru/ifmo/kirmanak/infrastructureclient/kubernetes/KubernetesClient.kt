package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.AppsV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1Deployment
import ru.ifmo.kirmanak.infrastructureclient.AppClient
import ru.ifmo.kirmanak.infrastructureclient.AppClientException
import ru.ifmo.kirmanak.infrastructureclient.AppNode

private const val DEPLOYMENT_SELECTOR = "app"

open class KubernetesClient(
    apiClient: ApiClient, private val namespace: String, private val deployment: String
) : AppClient {
    private val coreApi = CoreV1Api(apiClient)
    private val metricsApi = MetricsV1Beta1Api(apiClient)
    private val appsApi = AppsV1Api(apiClient)

    override fun getNodes(): Array<AppNode> {
        val dep = getDeployment()

        val selector = getDeploymentSelector(dep)
        val labelSelector = "$DEPLOYMENT_SELECTOR=$selector"

        val pods = getPods(labelSelector)

        return pods.items.map { KubernetesNode(it, this) }.toTypedArray()
    }

    override fun scaleNodes(count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal fun getMetricsPerPod() = metricsApi.getPodMetrics(namespace)

    private fun getDeployment() =
        try {
            appsApi.readNamespacedDeployment(deployment, namespace, null, null, null)
                ?: throw AppClientException("Deployment \"$deployment\" was not found in namespace \"$namespace\"")
        } catch (e: ApiException) {
            throw AppClientException(e)
        }


    private fun getPods(labelSelector: String) =
        try {
            coreApi.listNamespacedPod(namespace, null, null, null, null, labelSelector, null, null, null, null)
                ?: throw AppClientException("Pods list of \"$deployment\" was not found in namespace \"$namespace\"")
        } catch (e: ApiException) {
            throw AppClientException(e)
        }

    private fun getDeploymentSelector(dep: V1Deployment) = dep
        .spec
        ?.selector
        ?.matchLabels
        ?.get(DEPLOYMENT_SELECTOR)
        ?: throw AppClientException("Deployment \"$deployment\" has no \"$DEPLOYMENT_SELECTOR\" selector")
}