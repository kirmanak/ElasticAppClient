package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1PodList
import ru.ifmo.kirmanak.infrastructureclient.ClientException
import ru.ifmo.kirmanak.infrastructureclient.ClusterClient
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode

class KubernetesClient(apiClient: ApiClient, private val nameSpace: String) : ClusterClient {
    private val api = CoreV1Api(apiClient)
    private val metrics = MetricsV1Beta1Api(apiClient)

    override fun getNodes(): Array<ClusterNode> {
        val nodes: V1PodList

        try {
            nodes = api.listNamespacedPod(nameSpace, null, null, null, null, null, null, null, null, null)
        } catch (e: ApiException) {
            throw ClientException(e)
        }

        return nodes.items.map { KubernetesNode(it, this) }.toTypedArray()
    }

    internal fun getPodMetrics(nameSpace: String? = null) = metrics.getPodMetrics(nameSpace)
}