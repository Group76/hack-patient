package com.group76.patient.usecases.impl

import com.group76.patient.entities.request.GetTokenByCpfAndPassword
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetTokenResponse
import com.group76.patient.services.IPatientDbService
import com.group76.patient.services.IHashService
import com.group76.patient.services.IJwtService
import com.group76.patient.usecases.IGetTokenByCpfAndPasswordUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class GetTokenByCpfAndPasswordUseCaseImpl(
    private val dynamo: IPatientDbService,
    private val hashService: IHashService,
    private val jtwService: IJwtService
) : IGetTokenByCpfAndPasswordUseCase {
    override fun execute(payload: GetTokenByCpfAndPassword): BaseResponse<GetTokenResponse> {
        val scanResponse = dynamo
            .getByCpf(payload.cpf)

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