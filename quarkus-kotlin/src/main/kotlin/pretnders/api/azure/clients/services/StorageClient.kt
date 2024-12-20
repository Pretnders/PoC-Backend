package pretnders.api.azure.clients.services

import com.azure.identity.ClientSecretCredentialBuilder
import com.azure.storage.blob.BlobClient
import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobServiceClient
import com.azure.storage.blob.BlobServiceClientBuilder
import com.azure.storage.blob.models.PublicAccessType
import io.quarkus.logging.Log
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.resteasy.reactive.multipart.FileUpload
import pretnders.api.admins.domain.UpdateAdminsOut
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum


@ApplicationScoped
class StorageClient (
    private var updateAdminsOut: UpdateAdminsOut
): StorageClientOut {
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

    override fun updateAdminProfilePicture(phoneNumber: String, file: FileUpload): String {
        val containerName = String.format(ADMIN_FORMATTER, phoneNumber)
        val fileName = file.name()
        try {
            val containerClient: BlobContainerClient = blobServiceClient!!.getBlobContainerClient(containerName)
            containerClient.listBlobs().forEach { blob ->
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

    override fun deleteBlobFromContainer(phoneNumber: String, fileName: String) {
        val containerName = String.format(FORMATTER, phoneNumber)
        val containerClient: BlobContainerClient = blobServiceClient!!.getBlobContainerClient(containerName)
        Log.debug("Deleting file $fileName from container $containerName")
        containerClient.getBlobClient(fileName).deleteIfExists()
    }

    override fun addPretnderProfilePicture(phoneNumber: String, file: FileUpload): String {
        val containerName = String.format(FORMATTER, phoneNumber)
        Log.info(containerName)
        val fileName = file.fileName()
        try {
            val containerClient: BlobContainerClient = blobServiceClient!!.getBlobContainerClient(containerName)
            containerClient.getBlobClient(fileName).deleteIfExists()
            containerClient.getBlobClient(fileName).uploadFromFile(
                file.filePath().toString()
            )
            val profilePictureUrl = containerClient.getBlobClient(fileName).blobUrl
            Log.info("Profile picture uploaded : $profilePictureUrl")
            return profilePictureUrl
        } catch (e: Exception) {
            Log.info(e.toString())
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