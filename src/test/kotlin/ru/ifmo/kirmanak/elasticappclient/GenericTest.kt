package ru.ifmo.kirmanak.elasticappclient

import org.junit.Test

abstract class GenericTest {
    protected lateinit var appClient: AppClient

    protected companion object {
        fun getStringEnv(name: String) =
            System.getenv(name) ?: throw IllegalArgumentException("Set $name environmental variable")

        fun getIntEnv(name: String) = getStringEnv(name).toInt()
    }

    @Test
    fun getNodesTest() {
        appClient.getAppInstances().forEach { println(it) }
    }

    @Test
    fun printCPU() {
        appClient.getAppInstances().forEach { println(it.getCPULoad()) }
    }

    @Test
    fun printRAM() {
        appClient.getAppInstances().forEach { println(it.getRAMLoad()) }
    }

    @Test
    fun addInstance() {
        appClient.scaleInstances(1)
    }

    @Test
    fun removeInstance() {
        appClient.scaleInstances(-1)
    }
}