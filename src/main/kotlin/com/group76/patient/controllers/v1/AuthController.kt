package com.group76.patient.controllers.v1

import com.group76.patient.controllers.v1.mapping.UrlMapping
import com.group76.patient.entities.request.GetTokenByCpfAndPassword
import com.group76.patient.entities.request.GetTokenByEmailAndPassword
import com.group76.patient.entities.response.GetPatientInformationResponse
import com.group76.patient.usecases.IGetTokenByCpfAndPasswordUseCase
import com.group76.patient.usecases.IGetTokenByEmailAndPasswordUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(UrlMapping.Version.V1 + UrlMapping.Resource.AUTH)
class AuthController(
    private val getTokenByEmailAndPasswordUseCase: IGetTokenByEmailAndPasswordUseCase,
    private val getTokenByCpfAndPasswordUseCase: IGetTokenByCpfAndPasswordUseCase
) {
    @PostMapping(
        name = "GetTokenByEmailAndPassword",
        path = ["email"]
    )
    @Operation(
        method = "GetTokenByEmailAndPassword",
        description = "Get a token by Email and password",
        responses = [
            ApiResponse(
                description = "OK", responseCode = "200", content = [
                    Content(schema = Schema(implementation = GetPatientInformationResponse::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun getTokenByEmailAndPassword(
        @Valid @RequestBody request: GetTokenByEmailAndPassword
    ): ResponseEntity<Any> {
        val response = getTokenByEmailAndPasswordUseCase.execute(request)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }

    @PostMapping(
        name = "GetTokenByCpfAndPassword",
        path = ["cpf"]
    )
    @Operation(
        method = "GetTokenByCpgAndPassword",
        description = "Get a token by cpf and password",
        responses = [
            ApiResponse(
                description = "OK", responseCode = "200", content = [
                    Content(schema = Schema(implementation = GetPatientInformationResponse::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun getTokenByCpfAndPassword(
        @Valid @RequestBody request: GetTokenByCpfAndPassword
    ): ResponseEntity<Any> {
        val response = getTokenByCpfAndPasswordUseCase.execute(request)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }
}