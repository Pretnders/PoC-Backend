package com.pretnders.shared.utils.shared_models

data class UserBasicInformations( val reference: String, val jwToken:String, val
accountVerifiedStatus:Boolean){
    override fun toString(): String {
        return "UserBasicInformations(reference='$reference', jwToken='$jwToken', accountVerifiedStatus=$accountVerifiedStatus)"
    }
}
