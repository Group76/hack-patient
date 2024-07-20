package com.group76.patient.entities

import java.util.UUID

data class PatientEntity(
    val id: UUID,
    val password: String,
    val email: String,
    val cpf: String,
    val name: String,
    val address: String,
    val cep: String,
    val state: String,
    val city: String,
    val phone: String
)
