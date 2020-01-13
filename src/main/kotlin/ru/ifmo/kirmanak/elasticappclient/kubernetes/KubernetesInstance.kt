package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.models.V1Pod
import ru.ifmo.kirmanak.infrastructureclient.AppClientException
import ru.ifmo.kirmanak.infrastructureclient.AppInstance
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.models.MetricsV1Beta1PodMetrics
import java.math.BigDecimal

internal open class KubernetesInstance(pod: V1Pod, client: KubernetesClient) : AppInstance {
    private val name = pod.metadata?.name ?: throw AppClientException("Node name or the whole metadata is unknown!")
    private val cpuLoad = getUsage("cpu", client)
    private val memoryLoad = getUsage("memory", client)

    private fun getUsage(metricName: String, client: KubernetesClient): Double {
        val podContainers = getPodMetrics(client).containers
            ?: throw AppClientException("Node \"$name\" containers were not found!")

        return podContainers.fold(BigDecimal.ZERO) { acc, container ->
            val usage = container.usage?.get(metricName)?.number
                ?: throw AppClientException("Usage of \"$metricName\" was not found for container \"${container.name}\"")
            acc.add(usage)
        }.toDouble()
    }

    private fun getPodMetrics(client: KubernetesClient): MetricsV1Beta1PodMetrics {
        val allMetrics = client.getMetricsPerPod().items
            ?: throw AppClientException("Metrics API response has no items")

        for (item in allMetrics) {
            val itemName = item.metadata?.name ?: throw AppClientException("Metrics received without name or metadata!")

            if (itemName == name)
                return item
        }

        throw AppClientException("Metrics for pod \"$name\" were not found")
    }

    override fun getCPULoad() = cpuLoad

    override fun getRAMLoad() = memoryLoad

    override fun getName() = name

    override fun toString(): String {
        return "KubernetesNode(name='$name', CPULoad=$cpuLoad, RAMLoad=$memoryLoad)"
    }
}