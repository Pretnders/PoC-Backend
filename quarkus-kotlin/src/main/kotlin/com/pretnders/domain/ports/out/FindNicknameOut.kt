package com.pretnders.domain.ports.out

interface FindNicknameOut {
    fun doNicknameExist(reference:String, newNickname: String):Boolean
}