package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.models.V1Pod
import ru.ifmo.kirmanak.infrastructureclient.ClientException
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.models.MetricsV1Beta1PodMetrics
import java.math.BigDecimal

internal open class KubernetesNode(private val pod: V1Pod, private val client: KubernetesClient) : ClusterNode {
    private val name: String
        get() = pod.metadata?.name ?: throw ClientException("Node name or the whole metadata is unknown!")

    override fun getCPULoad() = getUsage("cpu")

    override fun getRAMLoad() = getUsage("memory")

    private fun getUsage(metricName: String): Double {
        val podContainers = getPodMetrics().containers
            ?: throw ClientException("Node \"$name\" containers were not found!")

        return podContainers.fold(BigDecimal.ZERO) { acc, container ->
            val usage = container.usage?.get(metricName)?.number
                ?: throw ClientException("Usage of \"$metricName\" was not found for container \"${container.name}\"")
            acc.add(usage)
        }.toDouble()
    }

    private fun getPodMetrics(): MetricsV1Beta1PodMetrics {
        val allMetrics = client.getMetricsPerPod().items
            ?: throw ClientException("Metrics API response has no items")

        for (item in allMetrics) {
            val itemName = item.metadata?.name ?: throw ClientException("Metrics received without name or metadata!")

            if (itemName == name)
                return item
        }

        throw ClientException("Metrics for pod \"$name\" were not found")
    }

    override fun toString() = name
}