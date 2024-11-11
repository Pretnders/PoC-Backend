package com.pretnders.domain.ports.`in`

import org.jboss.resteasy.reactive.multipart.FileUpload


interface AzureStorageIn {
    fun updateAdminProfilePicture( phoneNumber:String, file: FileUpload):String
    fun addPretnderProfilePicture(phoneNumber:String, file: FileUpload):String

    fun createContainerForPretnder(phoneNumber:String)

    fun createContainerForAdmin(phoneNumber:String)

    fun deleteBlobFromContainer(phoneNumber: String, fileName: String)
}