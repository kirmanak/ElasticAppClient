package ru.ifmo.kirmanak.infrastructureclient

import io.kubernetes.client.openapi.ApiClient
import org.opennebula.client.Client
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.KubernetesClient
import ru.ifmo.kirmanak.infrastructureclient.opennebula.OpenNebulaClient

open class ClientFactory {
    companion object {
        fun getClient(kubeClient: ApiClient, namespace: String, deployment: String): ClusterClient {
            return KubernetesClient(kubeClient, namespace, deployment)
        }

        fun getClient(openNebulaClient: Client): ClusterClient {
            return OpenNebulaClient(openNebulaClient)
        }
    }
}