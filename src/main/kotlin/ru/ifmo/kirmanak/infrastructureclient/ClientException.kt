package ru.ifmo.kirmanak.infrastructureclient

class ClientException(override val message: String) : Exception(message) {
    constructor(throwable: Throwable) : this(throwable.toString()) {
        addSuppressed(throwable)
    }
}