package com.pretnders.domain.ports.out

interface UpdatePretnderProfileOut {
    fun changeNickname(reference:String, newNickname: String)
    fun changeOrientation(reference:String, newOrientation:String)
}