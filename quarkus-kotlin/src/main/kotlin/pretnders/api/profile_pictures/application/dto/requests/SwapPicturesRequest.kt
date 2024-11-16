package pretnders.api.profile_pictures.application.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class SwapPicturesRequest(val swapperReference:String, val swapperOrder:Long, val swappedReference:String,val swappedOrder:Long) {

}
