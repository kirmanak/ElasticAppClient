package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.opennebula.client.vm.VirtualMachine
import ru.ifmo.kirmanak.infrastructureclient.AppNode

internal class OpenNebulaNode(private val vm: VirtualMachine) : AppNode {
    override fun getCPULoad(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRAMLoad(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String = vm.name
}
