package com.templates.domain.utils

import kotlin.random.Random

object OtpGenerator {
    fun generateCode(): String {
        val numbers = "0123456789"
        var code = ""
        val random = Random
        for (i: Int in 0..5) {
            val number = numbers[random.nextInt(
                numbers.length
            )].toString()
            code += number
        }
        return code
    }
}