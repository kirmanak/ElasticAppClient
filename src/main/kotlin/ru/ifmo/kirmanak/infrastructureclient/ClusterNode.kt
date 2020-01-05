package ru.ifmo.kirmanak.infrastructureclient

interface ClusterNode {
    fun getCPULoad(): Double

    fun getRAMLoad(): Double
}