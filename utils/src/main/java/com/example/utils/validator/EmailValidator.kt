package com.example.utils.validator

object EmailValidator {
    @JvmStatic
    fun emailValidation(email: String): Boolean {
        val emailMatcher = Config.emailPattern.matcher(email)
        return emailMatcher.matches()
    }
}