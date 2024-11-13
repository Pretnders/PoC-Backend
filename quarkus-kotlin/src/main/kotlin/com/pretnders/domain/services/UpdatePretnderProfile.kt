package com.pretnders.domain.services

import com.pretnders.domain.models.pretnders_details.SexualOrientation
import com.pretnders.domain.ports.`in`.UpdatePretnderProfileIn
import com.pretnders.domain.ports.out.UpdatePretnderProfileOut
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class UpdatePretnderProfile: UpdatePretnderProfileIn {

    @Inject
    private lateinit var updatePretenderProfileOut: UpdatePretnderProfileOut

    override fun updateNickname(reference: String, nickname: String) {
        updatePretenderProfileOut.changeNickname(reference, nickname)
    }

    override fun updateOrientation(reference: String, orientation: SexualOrientation) {
        updatePretenderProfileOut.changeOrientation(reference, orientation.name)
    }

}