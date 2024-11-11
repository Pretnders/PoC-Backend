package com.pretnders.domain.services

import com.azure.identity.ClientSecretCredentialBuilder
import com.azure.storage.blob.BlobClient
import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobServiceClient
import com.azure.storage.blob.BlobServiceClientBuilder
import com.azure.storage.blob.models.PublicAccessType
import com.pretnders.domain.errors.ApplicationException
import com.pretnders.domain.errors.ApplicationExceptionsEnum
import com.pretnders.domain.ports.`in`.AzureStorageIn
import com.pretnders.domain.ports.`in`.ProfilePicturesIn
import com.pretnders.domain.ports.out.UpdateAdminsOut
import com.pretnders.domain.ports.out.UpdatePretndersOut
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.resteasy.reactive.multipart.FileUpload
import io.quarkus.logging.Log;


@ApplicationScoped
class AzureStorage : AzureStorageIn {
    companion object {
        const val FORMATTER: String = "pretender-%s"
        const val ADMIN_FORMATTER: String = "admin-%s"

    }


    @field:ConfigProperty(name = "azure.client-id")
    private lateinit var clientId: String

    @field:ConfigProperty(name = "azure.tenant-id")
    private lateinit var tenantId: String

    @field:ConfigProperty(name = "azure.tenant.token")
    private lateinit var clientSecret: String

    @field:ConfigProperty(name = "azure.store.endpoint")
    private lateinit var endpoint: String

    @Inject
    @field:Default
    private lateinit var updatePretndersOut: UpdatePretndersOut

    @Inject
    @field:Default
    private lateinit var updateAdminsOut: UpdateAdminsOut

    @Inject
    @field:Default
    private lateinit var profilePicturesIn: ProfilePicturesIn

    var blobServiceClient: BlobServiceClient? = null

    @PostConstruct
    fun init() {
        blobServiceClient = BlobServiceClientBuilder()
            .endpoint(endpoint)
            .credential(
                ClientSecretCredentialBuilder()
                    .tenantId(tenantId)
                    .clientId(clientId)
                    .clientSecret(clientSecret).build()
            )
            .buildClient()
    }

    override fun updateAdminProfilePicture(phoneNumber:String, file: FileUpload): String {
        val containerName = String.format(ADMIN_FORMATTER, phoneNumber)
        val fileName = file.name()
        try {
            val containerClient: BlobContainerClient = blobServiceClient!!.getBlobContainerClient(containerName)
            containerClient.listBlobs().forEach{ blob ->
                containerClient.getBlobClient(blob.name).delete()
            }
            val client: BlobClient = containerClient.getBlobClient(fileName)
            client.uploadFromFile(
                file.filePath().toString()
            )
            val profilePictureUrl = client.blobUrl
            updateAdminsOut.updateProfilePicture(phoneNumber, profilePictureUrl)
            Log.info("Profile picture updated : $profilePictureUrl")
            return profilePictureUrl
        } catch (e: Exception) {
            Log.debug(e.toString())
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
    }

    override fun addPretnderProfilePicture(reference:String, phoneNumber: String, file: FileUpload): String {
        val containerName = String.format(FORMATTER, phoneNumber)
        val fileName = file.name()
        try {
            val containerClient: BlobContainerClient = blobServiceClient!!.getBlobContainerClient(containerName)
           containerClient.getBlobClient(fileName).uploadFromFile(
                file.filePath().toString()
            )
            val profilePictureUrl = containerClient.getBlobClient(fileName).blobUrl
            profilePicturesIn.addProfilePicture(reference, profilePictureUrl)
            Log.info("Profile picture updated : $profilePictureUrl")
            return profilePictureUrl
        } catch (e: Exception) {
            Log.debug(e.toString())
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
    }

    override fun createContainerForPretnder(phoneNumber: String) {
        val containerName = String.format(FORMATTER, phoneNumber)
        createContainerWithPublicAccessPolicy(containerName, phoneNumber)
    }

    override fun createContainerForAdmin(phoneNumber: String) {
        val containerName = String.format(ADMIN_FORMATTER, phoneNumber)
        createContainerWithPublicAccessPolicy(containerName, phoneNumber)
    }

    private fun createContainerWithPublicAccessPolicy(containerName: String, phoneNumber: String) {
        try {
            blobServiceClient!!.createBlobContainer(containerName)
            val blobServiceCLient = blobServiceClient!!.getBlobContainerClient(containerName)
            blobServiceCLient.setAccessPolicy(PublicAccessType.BLOB, null)
            Log.debug("Container created for user with phone number $phoneNumber")
        } catch (e: Exception) {
            Log.debug(e.message)
            throw ApplicationException(ApplicationExceptionsEnum.ERROR)
        }
    }

}