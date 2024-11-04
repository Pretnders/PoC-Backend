package com.templates.domain.ports.`in`

import com.templates.domain.models.users.UserLoggedIn

interface LoginIn {
    fun login(identifier: String, password: String): UserLoggedIn
}