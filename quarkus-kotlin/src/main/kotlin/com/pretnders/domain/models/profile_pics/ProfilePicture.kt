package com.pretnders.domain.models.profile_pics

data class ProfilePicture(val reference: String, val url: String, val order:Short){
    override fun toString(): String {
        return "ProfilePicture(reference='$reference', url='$url', order=$order)"
    }
}
