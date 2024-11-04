package com.pretnders.domain.ports.`in`

import com.pretnders.domain.models.users.UserLoggedIn

interface LoginIn {
    fun login(identifier: String, password: String): UserLoggedIn
}