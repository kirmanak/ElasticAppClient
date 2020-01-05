package ru.ifmo.kirmanak.infrastructureclient

interface ClusterClient {
    @Throws(ClientException::class)
    fun getNodes(): Array<ClusterNode>
}