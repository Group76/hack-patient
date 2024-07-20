package com.group76.patient.usecases.impl

import com.group76.patient.entities.PatientEntity
import com.group76.patient.entities.request.UpdatePatientRequest
import com.group76.patient.entities.response.BaseResponse
import com.group76.patient.entities.response.GetPatientInformationResponse
import com.group76.patient.services.IPatientDbService
import com.group76.patient.services.IHashService
import com.group76.patient.services.IJwtService
import com.group76.patient.usecases.IUpdatePatientUseCase
import com.group76.patient.utils.Helper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdatePatientUseCaseImpl(
    private val patientDb: IPatientDbService,
    private val hashService: IHashService,
    private val jwtService: IJwtService
) : IUpdatePatientUseCase {
    override fun execute(payload: UpdatePatientRequest, token: String): BaseResponse<GetPatientInformationResponse> {
        val error = payload.getError()

        if(error != null)
            return BaseResponse(data = null,
                error = BaseResponse.BaseResponseError(error),
                statusCodes = HttpStatus.BAD_REQUEST
            )

        val id = jwtService.extractId(token)

        if(id == null
            || jwtService.isExpired(token)) {
            return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Unauthorized"),
                HttpStatus.UNAUTHORIZED
            )
        }

        val patient = PatientEntity(
            name = payload.name,
            id = UUID.fromString(id),
            email = payload.email,
            phone = payload.phone,
            password = hashService.hash(payload.password),
            address = payload.address,
            city = payload.city,
            cep = payload.cep,
            cpf = Helper.removeSpecialCharactersAndSpaces(payload.cpf),
            state = payload.state
        )

        if (!patientDb.verifyEmail(patient.email)) {
            return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("E-mail already exists."),
                statusCodes = HttpStatus.BAD_REQUEST
            )
        }

        if (!patientDb.verifyCpf(patient.cpf)) {
            return BaseResponse(
                data = null,
                error = BaseResponse.BaseResponseError("Cpf already exists."),
                statusCodes = HttpStatus.BAD_REQUEST
            )
        }

        val response = patientDb.updateItem(patient)

        if(!response.sdkHttpResponse().isSuccessful){
            return BaseResponse(data = null,
                error = BaseResponse.BaseResponseError("Error while updating client."),
                statusCodes = HttpStatus.INTERNAL_SERVER_ERROR
            )
        }

        return BaseResponse(
            data = GetPatientInformationResponse(
                email = patient.email,
                phone = patient.phone,
                name = patient.name,
                address = patient.address,
                id = patient.id,
                token = jwtService.generateToken(patient.id.toString()),
                city = patient.city,
                cep = patient.cep,
                cpf = patient.cpf,
                state = patient.state
            ),
            error = null
        )
    }
}