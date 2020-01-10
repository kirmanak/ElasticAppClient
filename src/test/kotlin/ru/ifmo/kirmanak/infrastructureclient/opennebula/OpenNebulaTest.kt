package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.junit.Test
import org.opennebula.client.Client
import ru.ifmo.kirmanak.infrastructureclient.AppClient
import ru.ifmo.kirmanak.infrastructureclient.AppClientFactory

class OpenNebulaTest {
    private val oneClient: AppClient

    init {
        val client = Client("oneadmin:ConUsUlAtim3", null)
        oneClient = AppClientFactory.getClient(client, 0, "ubuntu")
    }

    @Test
    fun connects() {
        oneClient.getNodes().forEach { print(it) }
    }
}