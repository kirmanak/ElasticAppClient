package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.opennebula.client.Client
import org.opennebula.client.OneResponse
import org.opennebula.client.vm.VirtualMachinePool
import ru.ifmo.kirmanak.infrastructureclient.ClientException
import ru.ifmo.kirmanak.infrastructureclient.ClusterClient
import ru.ifmo.kirmanak.infrastructureclient.ClusterNode

class OpenNebulaClient(private val client: Client) : ClusterClient {

    override fun getNodes(): Array<ClusterNode> {
        val pool = VirtualMachinePool(client)
        throwIfError(pool.info())

        return pool.map { OpenNebulaNode(it) }.toTypedArray()
    }
}

/* internal val Any.xpath by lazy {
    XPathFactory.newInstance().newXPath()
} */

@Throws(ClientException::class)
internal fun Any.throwIfError(response: OneResponse): OneResponse {
    if (response.isError)
        throw ClientException(response.errorMessage)
    else
        return response
}

/* internal fun Any.getXMLNode(response: OneResponse, nodeType: String): Node {
    val message = response.message
    val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val document = docBuilder.parse(ByteArrayInputStream(message.toByteArray()))
    val docRoot = document.documentElement
    return xpath.evaluate(nodeType, docRoot, XPathConstants.NODE) as Node
} */