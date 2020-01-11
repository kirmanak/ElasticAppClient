package ru.ifmo.kirmanak.infrastructureclient.opennebula

import org.junit.Test
import org.opennebula.client.Client
import ru.ifmo.kirmanak.infrastructureclient.AppClient
import ru.ifmo.kirmanak.infrastructureclient.AppClientFactory

class OpenNebulaTest {
    private val oneClient: AppClient

    init {
        val client = Client("oneadmin:ConUsUlAtim3", null)
        oneClient = AppClientFactory.getClient(client, 0, 0, 1)
    }

    @Test
    fun connects() {
        oneClient.getAppInstances().forEach { println(it) }
    }

    @Test
    fun printCPU() {
        oneClient.getAppInstances().forEach { println(it.getCPULoad()) }
    }

    @Test
    fun printRAM() {
        oneClient.getAppInstances().forEach { println(it.getRAMLoad()) }
    }

    @Test
    fun addInstance() {
        oneClient.scaleInstances(1)
    }

    @Test
    fun removeInstance() {
        oneClient.scaleInstances(-1)
    }
}