package ru.ifmo.kirmanak.infrastructureclient

/**
 * Interface to access elastic application working in a virtualized infrastructure.
 */
interface AppClient {
    /**
     * Requests information about currently working application instances from infrastructure provider.
     */
    @Throws(AppClientException::class)
    fun getNodes(): Array<AppNode>
}