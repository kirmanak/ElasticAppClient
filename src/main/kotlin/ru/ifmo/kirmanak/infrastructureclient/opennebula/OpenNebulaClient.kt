package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.opennebula.client.Client
import org.opennebula.client.vm.VirtualMachinePool
import org.opennebula.client.vmgroup.VMGroup
import org.w3c.dom.Node
import ru.ifmo.kirmanak.infrastructureclient.AppClient
import ru.ifmo.kirmanak.infrastructureclient.AppClientException
import ru.ifmo.kirmanak.infrastructureclient.AppNode

open class OpenNebulaClient(
    private val client: Client, private val groupId: Int, private val roleId: Int
) : AppClient {

    override fun getNodes(): Array<AppNode> {
        val pool = VirtualMachinePool(client)
        throwIfError(pool.info())
        val vmIDs = getVMIdentifiers()

        return pool.filter { vmIDs.contains(it.id()) }.map { OpenNebulaNode(it) }.toTypedArray()
    }

    override fun scaleNodes(count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getVMIdentifiers(): Set<Int> {
        val role = findRole() ?: throw AppClientException("Failed to find role with id $roleId in VM group $groupId")
        val vmList = getString(role, "VMS")
        return vmList.split(",").map { it.toInt() }.toSortedSet()
    }

    private fun findRole(): Node? {
        val group = VMGroup(groupId, client)

        val root = getRootElement(group.info())
        val roleList = getNodeList(root, "ROLES/ROLE")
        for (i in 0..roleList.length) {
            val item = roleList.item(i)
            val id = getNumber(item, "ID").toInt()
            if (id == roleId)
                return item
        }

        return null
    }
}
