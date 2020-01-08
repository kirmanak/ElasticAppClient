package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.models.V1Pod
import ru.ifmo.kirmanak.infrastructureclient.ClientException
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.models.MetricsV1Beta1PodMetrics
import java.math.BigDecimal

internal class KubernetesNode(private val pod: V1Pod, private val client: KubernetesClient) : ClusterNode {
    override fun getCPULoad() = getResourceRequests("cpu")

    override fun getRAMLoad() = getResourceRequests("memory")

    private fun getResourceRequests(name: String): Double {
        val podName = pod.metadata?.name
            ?: throw ClientException("Current node has no name or no metadata at all!")

        val podNameSpace = pod.metadata?.namespace
            ?: throw ClientException("Node $podName has no namespace!")

        val allMetrics = client.getPodMetrics(podNameSpace).items
            ?: throw ClientException("Metrics were not found for namespace $podNameSpace")

        val podMetrics = findMetrics(podName, allMetrics)
            ?: throw ClientException("Node $podName from namespace $podNameSpace metrics were not received!")

        val podContainers = podMetrics.containers
            ?: throw ClientException("Node $podName containers were not found!")

        return podContainers.fold(BigDecimal.ZERO) { acc, container ->
            val usage = container.usage?.get(name)?.number
            if (usage === null) {
                acc
            } else {
                acc.add(usage)
            }
        }.toDouble()
    }

    private fun findMetrics(name: String, items: List<MetricsV1Beta1PodMetrics?>): MetricsV1Beta1PodMetrics? {
        for (item in items)
            if (item?.metadata?.name == name)
                return item

        return null
    }

    override fun toString() = pod.metadata?.name ?: "Unknown pod"
}