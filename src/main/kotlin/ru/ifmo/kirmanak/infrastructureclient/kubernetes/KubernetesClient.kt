package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.ApiClient
import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1PodList
import ru.ifmo.kirmanak.infrastructureclient.ClientException
import ru.ifmo.kirmanak.infrastructureclient.ClusterClient
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode

class KubernetesClient(apiClient: ApiClient, private val nameSpace: String) : ClusterClient {
    private val api = CoreV1Api(apiClient)

    override fun getNodes(): Array<ClusterNode> {
        val nodes: V1PodList

        try {
            nodes = api.listNamespacedPod(nameSpace, true, null, null, null, null, null, null, null, null)
        } catch (e: ApiException) {
            throw ClientException(e)
        }

        return nodes.items.map { KubernetesNode(it) }.toTypedArray()
    }

}