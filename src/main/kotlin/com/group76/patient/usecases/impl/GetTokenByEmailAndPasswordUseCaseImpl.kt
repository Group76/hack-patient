package com.group76.patient.usecases.impl

import com.group76.patient.entities.request.GetTokenByEmailAndPassword
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetTokenResponse
import com.group76.patient.services.IPatientDbService
import com.group76.patient.services.IHashService
import com.group76.patient.services.IJwtService
import com.group76.patient.usecases.IGetTokenByEmailAndPasswordUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class GetTokenByEmailAndPasswordUseCaseImpl(
    private val dynamo: IPatientDbService,
    private val hashService: IHashService,
    private val jtwService: IJwtService
) : IGetTokenByEmailAndPasswordUseCase {
    override fun execute(payload: GetTokenByEmailAndPassword): BaseResponse<GetTokenResponse> {
        val scanResponse = dynamo
            .getByEmail(payload.email)

        if (!scanResponse.hasItems())
            return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Patient not found."),
                statusCodes = HttpStatus.BAD_REQUEST
            )

        val item = scanResponse.items().first()
        val password = item["password"]?.s()
        val id = item["id"]?.s()

        if (password.isNullOrEmpty()
            || id.isNullOrEmpty()
        ) {
            return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Patient not found."),
                statusCodes = HttpStatus.BAD_REQUEST
            )
        }

        if (!hashService.checkPassword(payload.password, password)) {
            return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Patient not authorized."),
                statusCodes = HttpStatus.UNAUTHORIZED
            )
        }

        return BaseResponse(
            data = GetTokenResponse(
                token = jtwService.generateToken(id)!!,
                id = id
            ),
            error = null
        )
    }
}