package com.group76.patient.entities.request

data class GetTokenByEmailAndPassword(
    val email: String,
    val password: String
)
