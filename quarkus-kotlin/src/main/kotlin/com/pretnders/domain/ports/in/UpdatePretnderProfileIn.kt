package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.pretnders_details.SexualOrientation

interface UpdatePretnderProfileIn {
    fun updateNickname(reference:String, nickname: String)
    fun updateOrientation(reference:String, orientation: SexualOrientation)

}