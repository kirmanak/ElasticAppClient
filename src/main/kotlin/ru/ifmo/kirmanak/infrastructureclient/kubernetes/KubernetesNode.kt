package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.models.V1Pod
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode
import java.math.BigDecimal

internal class KubernetesNode(private val pod: V1Pod) : ClusterNode {
    override fun getCPULoad() = getResourceRequests("cpu")

    override fun getRAMLoad() = getResourceRequests("memory")

    private fun getResourceRequests(name: String): Double {
        val sum = pod.spec?.containers
            ?.map { it.resources?.requests?.get(name)?.number }
            ?.fold(BigDecimal.ZERO) { acc, it ->
                if (it !== null) {
                    acc.add(it)
                } else {
                    acc
                }
            }
            ?: BigDecimal.ZERO

        return sum.toDouble()
    }

    override fun toString() = pod.toString()
}