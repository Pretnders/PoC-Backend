package com.templates.domain.ports.out

import com.templates.domain.models.users.User

interface FindClientsOut {
    fun findByIdentifier(identifier: String) : User
    fun findByPasswordVerificationCode(passwordVerificationCode: String) : User

}