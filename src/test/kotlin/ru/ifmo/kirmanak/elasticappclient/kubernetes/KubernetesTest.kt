package ru.ifmo.kirmanak.elasticappclient.kubernetes

import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.junit.Test
import ru.ifmo.kirmanak.elasticappclient.AppClient
import ru.ifmo.kirmanak.elasticappclient.AppClientFactory
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
        kubeClient.getAppInstances().forEach { println(it) }
    }

    @Test
    fun printCPU() {
        kubeClient.getAppInstances().forEach { println(it.getCPULoad()) }
    }

    @Test
    fun printRAM() {
        kubeClient.getAppInstances().forEach { println(it.getRAMLoad()) }
    }


    /*@Test
    fun addInstance() {
        kubeClient.scaleInstances(1)
    }*/

    @Test
    fun removeInstance() {
        kubeClient.scaleInstances(-1)
    }
}
