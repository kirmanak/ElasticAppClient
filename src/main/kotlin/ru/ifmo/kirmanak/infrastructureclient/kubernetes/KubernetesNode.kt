package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.openapi.models.V1Pod
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode

internal class KubernetesNode(private val pod: V1Pod) : ClusterNode {
    override fun getCPULoad(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRAMLoad(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString() = pod.toString()
}