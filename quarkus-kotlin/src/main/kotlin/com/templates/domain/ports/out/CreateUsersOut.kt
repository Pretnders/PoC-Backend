package com.templates.domain.ports.out

import com.templates.domain.models.commands.users.CreateUserCommand

interface CreateUsersOut {
    fun addClient(user: CreateUserCommand)
    fun addAdmin(user: CreateUserCommand)
}