package com.group76.patient.usecases

import com.group76.patient.entities.request.GetTokenByEmailAndPassword
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetTokenResponse

interface IGetTokenByEmailAndPasswordUseCase {
    fun execute(
     payload: GetTokenByEmailAndPassword
    ): BaseResponse<GetTokenResponse>
}