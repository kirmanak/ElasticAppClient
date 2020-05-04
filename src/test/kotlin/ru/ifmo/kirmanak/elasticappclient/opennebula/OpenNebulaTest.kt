package ru.ifmo.kirmanak.elasticappclient.opennebula

import org.opennebula.client.Client
import ru.ifmo.kirmanak.elasticappclient.AppClientFactory.Companion.getClient
import ru.ifmo.kirmanak.elasticappclient.GenericTest

private const val ENV_LOGIN = "OPEN_NEBULA_LOGIN_TEST"
private const val ENV_PASS = "OPEN_NEBULA_PASSWORD_TEST"
private const val ENV_ADDR = "OPEN_NEBULA_ADDRESS_TEST"
private const val ENV_GROUP = "OPEN_NEBULA_GROUP_TEST"
private const val ENV_ROLE = "OPEN_NEBULA_ROLE_TEST"
private const val ENV_TEMPLATE = "OPEN_NEBULA_TEMPLATE_TEST"

class OpenNebulaTest : GenericTest() {
    init {
        val client = Client("${getStringEnv(ENV_LOGIN)}:${getStringEnv(ENV_PASS)}", getStringEnv(ENV_ADDR))
        appClient = getClient(client, getIntEnv(ENV_GROUP), getIntEnv(ENV_ROLE), getIntEnv(ENV_TEMPLATE))
    }
}