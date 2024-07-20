package com.group76.patient.entities.response

import java.util.UUID

data class GetPatientInformationResponse(
    val id: UUID,
    val cpf: String,
    val email: String,
    val name: String,
    val address: String,
    val cep: String,
    val state: String,
    val city: String,
    val phone: String,
    val token: String?
)
