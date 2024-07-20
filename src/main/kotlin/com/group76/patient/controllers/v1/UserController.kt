package com.group76.patient.controllers.v1

import com.group76.patient.controllers.v1.mapping.UrlMapping
import com.group76.patient.entities.request.CreatePatientRequest
import com.group76.patient.entities.request.UpdatePatientRequest
import com.group76.patient.entities.response.GetPatientInformationResponse
import com.group76.patient.usecases.ICreatePatientUseCase
import com.group76.patient.usecases.IUpdatePatientUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(UrlMapping.Version.V1 + UrlMapping.Resource.USER)
class UserController(
    private val createDoctorUseCase: ICreatePatientUseCase,
    private val updateDoctorUseCase : IUpdatePatientUseCase
) {
    @PostMapping(
        name = "CreateDoctor"
    )
    @Operation(
        method = "CreateDoctor",
        description = "Create a doctor",
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
    fun createDoctor(
        @Valid @RequestBody request: CreatePatientRequest
    ): ResponseEntity<Any> {
        val response = createDoctorUseCase.execute(request)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }

    @PutMapping(
        name = "UpdateDoctor"
    )
    @Operation(
        method = "UpdateDoctor",
        description = "Update a doctor",
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
    fun updateDoctor(
        @Valid @RequestBody request: UpdatePatientRequest,
        @RequestHeader(value = "Authorization") auth: String
    ): ResponseEntity<Any> {
        val response = updateDoctorUseCase.execute(request, auth)

        return ResponseEntity(
            response.error ?: response.data,
            response.statusCodes
        )
    }
}