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

    /**
     * Scales application instances count by {@param count}.
     * Instances are added if {@param count} is positive and removed if it is negative.
     * If {@param count} is zero nothing will be done.
     */
    @Throws(AppClientException::class)
    fun scaleNodes(count: Int)
}