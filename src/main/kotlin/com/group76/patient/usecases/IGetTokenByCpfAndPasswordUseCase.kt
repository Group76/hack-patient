package com.group76.patient.usecases

import com.group76.patient.entities.request.GetTokenByCpfAndPassword
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetTokenResponse

interface IGetTokenByCpfAndPasswordUseCase {
    fun execute(
     payload: GetTokenByCpfAndPassword
    ): BaseResponse<GetTokenResponse>
}