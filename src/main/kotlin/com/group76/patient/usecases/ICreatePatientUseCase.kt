package com.group76.patient.usecases

import com.group76.patient.entities.request.CreatePatientRequest
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetPatientInformationResponse

interface ICreatePatientUseCase {
    fun execute(
        payload: CreatePatientRequest
    ): BaseResponse<GetPatientInformationResponse>
}