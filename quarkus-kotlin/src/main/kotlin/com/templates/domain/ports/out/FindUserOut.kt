package com.templates.domain.ports.out

import com.templates.domain.models.users.User

interface FindUserOut {
    fun findByIdentifier(identifier: String) : User

}