package ru.ifmo.kirmanak.infrastructureclient

import io.kubernetes.client.ApiClient
import org.opennebula.client.Client
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.KubernetesClient
import ru.ifmo.kirmanak.infrastructureclient.opennebula.OpenNebulaClient

open class ClientFactory {
    companion object {
        fun getClient(kubeClient: ApiClient, nameSpace: String): ClusterClient {
            return KubernetesClient(kubeClient, nameSpace)
        }

        fun getClient(openNebulaClient: Client): ClusterClient {
            return OpenNebulaClient(openNebulaClient)
        }
    }
}