package com.group76.patient.entities.request

data class GetTokenByCpfAndPassword(
    val cpf: String,
    val password: String
)
