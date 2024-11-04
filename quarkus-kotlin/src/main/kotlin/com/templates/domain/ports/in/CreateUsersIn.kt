package com.templates.domain.ports.`in`

import com.templates.domain.models.commands.users.CreateUserCommand
import com.templates.domain.models.users.UserBasicInformations

interface CreateUsersIn {
    fun createUser(user:CreateUserCommand): UserBasicInformations
    fun createAdmin(user:CreateUserCommand, adminCode:String): UserBasicInformations

}