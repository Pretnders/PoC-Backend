package com.pretnders.domain.ports.`in`

import org.jboss.resteasy.reactive.multipart.FileUpload


interface AzureStorageIn {
    fun updateAdminProfilePicture( phoneNumber:String, file: FileUpload):String
    fun createContainerForUser(phoneNumber:String)

    fun createContainerForAdmin(phoneNumber:String)

}