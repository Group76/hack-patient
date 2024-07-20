package com.group76.patient.utils

object Helper {

    fun removeSpecialCharactersAndSpaces(input: String): String {
        return input.filter { it.isLetterOrDigit() }
    }
}