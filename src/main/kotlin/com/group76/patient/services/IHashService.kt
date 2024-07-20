package com.group76.patient.services

interface IHashService {
    fun hash(value: String): String
    fun checkPassword(plainPassword: String, hashedPassword: String): Boolean
}
