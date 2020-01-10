package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.junit.Test
import org.opennebula.client.Client
import ru.ifmo.kirmanak.infrastructureclient.AppClientFactory

class OpenNebulaTest {
    private val oneClient: Client = Client("oneadmin:ConUsUlAtim3", null)

    @Test
    fun connects() {
        AppClientFactory.getClient(oneClient).getNodes().forEach { print(it) }
    }
}