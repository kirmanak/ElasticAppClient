package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.opennebula.client.vm.VirtualMachine
import ru.ifmo.kirmanak.infrastructureclient.AppClientException
import ru.ifmo.kirmanak.infrastructureclient.AppNode

internal class OpenNebulaNode(vm: VirtualMachine) : AppNode {
    private val name: String = vm.name ?: throw AppClientException("Unknown vm name")
    private val cpuLoad = getUsage("CPU", vm)
    private val memoryLoad = getUsage("MEMORY", vm) * 1024

    override fun getCPULoad() = cpuLoad

    override fun getRAMLoad() = memoryLoad

    override fun getName() = name

    private fun getUsage(resource: String, vm: VirtualMachine): Double {
        val root = getRootElement(vm.info())
        return getNumber(root, "MONITORING/$resource")
    }

    override fun toString(): String {
        return "OpenNebulaNode(name='$name', cpuLoad=$cpuLoad, memoryLoad=$memoryLoad)"
    }
}
