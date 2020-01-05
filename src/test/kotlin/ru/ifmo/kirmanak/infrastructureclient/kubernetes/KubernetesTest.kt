package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.junit.Test
import ru.ifmo.kirmanak.infrastructureclient.ClientFactory
import ru.ifmo.kirmanak.infrastructureclient.ClusterClient
import java.nio.file.Files
import java.nio.file.Paths

class KubernetesTest {
    private val kubeClient: ClusterClient

    init {
        val home = System.getenv("HOME")
        val configPath = Paths.get(home, ".kube", "config")
        val config = KubeConfig.loadKubeConfig(Files.newBufferedReader(configPath))
        val apiClient = ClientBuilder.kubeconfig(config).build()
        kubeClient = ClientFactory.getClient(apiClient, "kube-system")
    }

    @Test
    fun getNodesTest() {
        kubeClient.getNodes().forEach { print(it) }
    }
}