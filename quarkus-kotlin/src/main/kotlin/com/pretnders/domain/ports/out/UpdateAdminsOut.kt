package com.pretnders.domain.ports.out



interface UpdateAdminsOut {
    fun updateNickname(mail: String, newNickname: String)
}