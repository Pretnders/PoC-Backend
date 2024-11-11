package com.pretnders.domain.ports.out

interface ProfilePicturesOut {
    fun getNextPicOrder(reference:String):Short
    fun addPic(pretnderReference:String, pictureReference:String, profilePicUrl:String, picOrder: Short)
}