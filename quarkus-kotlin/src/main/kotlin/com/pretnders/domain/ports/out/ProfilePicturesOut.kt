package com.pretnders.domain.ports.out

interface ProfilePicturesOut {
    fun getNextPicOrder(pretnderId:Long):Long
    fun addPic(pretnderReference:String, pictureReference:String, profilePicUrl:String)
    fun swapPicturesOrder(swapperReference:String, swapperOrder:Long, swappedReference:String, swappedOrder:Long)
    fun deletePicture(pictureReference:String)
    fun updatePictureUrl(pictureReference:String, newUrl:String)

}