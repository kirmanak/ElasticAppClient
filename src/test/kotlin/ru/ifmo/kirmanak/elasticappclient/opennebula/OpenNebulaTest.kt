package ru.ifmo.kirmanak.elasticappclient.opennebula

import org.junit.Test
import org.opennebula.client.Client
import ru.ifmo.kirmanak.elasticappclient.AppClient
import ru.ifmo.kirmanak.elasticappclient.AppClientFactory.Companion.getClient

private const val ENV_LOGIN = "OPEN_NEBULA_LOGIN_TEST"
private const val ENV_PASS = "OPEN_NEBULA_PASSWORD_TEST"
private const val ENV_ADDR = "OPEN_NEBULA_ADDRESS_TEST"
private const val ENV_GROUP = "OPEN_NEBULA_GROUP_TEST"
private const val ENV_ROLE = "OPEN_NEBULA_ROLE_TEST"
private const val ENV_TEMPLATE = "OPEN_NEBULA_TEMPLATE_TEST"

class OpenNebulaTest {
    private val oneClient: AppClient

    private companion object {
        fun getStringEnv(name: String) =
            System.getenv(name) ?: throw IllegalArgumentException("Set $name environmental variable")

        fun getIntEnv(name: String) = getStringEnv(name).toInt()
    }

    init {
        val client = Client("${getStringEnv(ENV_LOGIN)}:${getStringEnv(ENV_PASS)}", getStringEnv(ENV_ADDR))
        oneClient = getClient(client, getIntEnv(ENV_GROUP), getIntEnv(ENV_ROLE), getIntEnv(ENV_TEMPLATE))
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