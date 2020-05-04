package ru.ifmo.kirmanak.elasticappclient.kubernetes

import io.kubernetes.client.util.ClientBuilder
import ru.ifmo.kirmanak.elasticappclient.AppClientFactory
import ru.ifmo.kirmanak.elasticappclient.GenericTest

private const val ENV_NAMESPACE = "KUBERNETES_TEST_NAMESPACE"
private const val ENV_DEPLOYMENT = "KUBERNETES_TEST_DEPLOYMENT"

class KubernetesTest : GenericTest() {
    init {
        val apiClient = ClientBuilder.defaultClient()
        appClient = AppClientFactory.getClient(apiClient, getStringEnv(ENV_NAMESPACE), getStringEnv(ENV_DEPLOYMENT))
    }
}
