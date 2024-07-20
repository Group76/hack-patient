package com.group76.patient.entities.request

import com.group76.patient.utils.BrazilStates
import com.group76.patient.utils.CepValidator

data class CreatePatientRequest(
    val password: String,
    val email: String,
    val cpf: String,
    val name: String,
    val address: String,
    val cep: String,
    val state: String,
    val city: String,
    val phone: String,
) {
    fun getError(): String? {
        if (password.length < 8)
            return "Password not long enough, should be at least 8 characters"

        if (cpf.isEmpty())
            return "CPF must be informed"

        if (name.isEmpty())
            return "Name must be informed"

        if (email.isEmpty())
            return "Email must be informed"

        if (address.isEmpty())
            return "Address must be informed"

        if (cep.isEmpty())
            return "Cep must be informed"

        if (state.isEmpty())
            return "State must be informed"

        if (city.isEmpty())
            return "City must be informed"

        if (phone.isEmpty())
            return "Phone must be informed"

        if (!BrazilStates.containState(state))
            return "State is invalid"

        if (!CepValidator.validateCep(cep))
            return "CEP is invalid"

        return null
    }
}