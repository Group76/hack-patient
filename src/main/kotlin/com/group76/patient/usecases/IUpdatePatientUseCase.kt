package com.group76.patient.usecases

import com.group76.patient.entities.request.UpdatePatientRequest
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetPatientInformationResponse

interface IUpdatePatientUseCase {
    fun execute(
        payload: UpdatePatientRequest,
        token: String
    ): BaseResponse<GetPatientInformationResponse>
}