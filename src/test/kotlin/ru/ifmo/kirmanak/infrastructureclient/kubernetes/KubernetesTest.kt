package ru.ifmo.kirmanak.infrastructureclient.kubernetes

import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.junit.Test
import ru.ifmo.kirmanak.infrastructureclient.AppClient
import ru.ifmo.kirmanak.infrastructureclient.AppClientFactory
import java.nio.file.Files
import java.nio.file.Paths

class KubernetesTest {
    private val kubeClient: AppClient

    init {
        val home = System.getenv("HOME")
        val configPath = Paths.get(home, ".kube", "config")
        val config = KubeConfig.loadKubeConfig(Files.newBufferedReader(configPath))
        val apiClient = ClientBuilder.kubeconfig(config).build()
        kubeClient = AppClientFactory.getClient(apiClient, "default", "nginx-deployment")
    }

    @Test
    fun getNodesTest() {
        kubeClient.getNodes().forEach { println(it) }
    }

    @Test
    fun printCPU() {
        kubeClient.getNodes().forEach { println(it.getCPULoad()) }
    }

    @Test
    fun printRAM() {
        kubeClient.getNodes().forEach { println(it.getRAMLoad()) }
    }
}
