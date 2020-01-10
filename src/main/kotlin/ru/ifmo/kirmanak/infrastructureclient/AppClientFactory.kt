package ru.ifmo.kirmanak.infrastructureclient

import io.kubernetes.client.openapi.ApiClient
import org.opennebula.client.Client
import ru.ifmo.kirmanak.infrastructureclient.kubernetes.KubernetesClient
import ru.ifmo.kirmanak.infrastructureclient.opennebula.OpenNebulaClient

open class AppClientFactory {
    companion object {
        @JvmStatic
        fun getClient(kubeClient: ApiClient, namespace: String, deployment: String): AppClient {
            return KubernetesClient(kubeClient, namespace, deployment)
        }

        @JvmStatic
        fun getClient(openNebulaClient: Client, groupID: Int, roleName: String): AppClient {
            return OpenNebulaClient(openNebulaClient, groupID, roleName)
        }
    }
}