package ru.ifmo.kirmanak.elasticappclient.opennebula

import org.junit.Test
import org.opennebula.client.Client
import ru.ifmo.kirmanak.elasticappclient.AppClient
import ru.ifmo.kirmanak.elasticappclient.AppClientFactory

private const val ENV_LOGIN = "OPEN_NEBULA_LOGIN_TEST"
private const val ENV_PASS = "OPEN_NEBULA_PASSWORD_TEST"
private const val ENV_ADDR = "OPEN_NEBULA_ADDRESS_TEST"

class OpenNebulaTest {
    private val oneClient: AppClient

    private companion object {
        fun getEnv(name: String) =
            System.getenv(name) ?: throw IllegalArgumentException("Set $name environmental variable")
    }

    init {
        val login = getEnv(ENV_LOGIN)
        val password = getEnv(ENV_PASS)
        val address = getEnv(ENV_ADDR)
        val client = Client("${login}:${password}", address)
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