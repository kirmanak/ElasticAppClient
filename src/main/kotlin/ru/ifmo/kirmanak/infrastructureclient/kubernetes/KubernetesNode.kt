package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.models.V1Pod
import ru.ifmo.kirmanak.infrastructureclient.AppClientException
import ru.ifmo.kirmanak.infrastructureclient.AppNode
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.models.MetricsV1Beta1PodMetrics
import java.math.BigDecimal

internal open class KubernetesNode(private val pod: V1Pod, private val client: KubernetesClient) : AppNode {
    private val name: String
        get() = pod.metadata?.name ?: throw AppClientException("Node name or the whole metadata is unknown!")

    override fun getCPULoad() = getUsage("cpu")

    override fun getRAMLoad() = getUsage("memory")

    private fun getUsage(metricName: String): Double {
        val podContainers = getPodMetrics().containers
            ?: throw AppClientException("Node \"$name\" containers were not found!")

        return podContainers.fold(BigDecimal.ZERO) { acc, container ->
            val usage = container.usage?.get(metricName)?.number
                ?: throw AppClientException("Usage of \"$metricName\" was not found for container \"${container.name}\"")
            acc.add(usage)
        }.toDouble()
    }

    private fun getPodMetrics(): MetricsV1Beta1PodMetrics {
        val allMetrics = client.getMetricsPerPod().items
            ?: throw AppClientException("Metrics API response has no items")

        for (item in allMetrics) {
            val itemName = item.metadata?.name ?: throw AppClientException("Metrics received without name or metadata!")

            if (itemName == name)
                return item
        }

        throw AppClientException("Metrics for pod \"$name\" were not found")
    }

    override fun toString() = name
}