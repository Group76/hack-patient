package com.group76.patient.services

import com.group76.patient.entities.PatientEntity
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse
import software.amazon.awssdk.services.dynamodb.model.ScanResponse
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse

interface IPatientDbService {
    fun putItem(patientEntity: PatientEntity) : PutItemResponse
    fun verifyCpf(cpf: String, id: String? = null): Boolean
    fun verifyEmail(email: String, id: String? = null): Boolean
    fun getById(id: String): GetItemResponse
    fun getByCpf(cpf: String): ScanResponse
    fun getByEmail(email: String): ScanResponse
    fun updateItem(patientEntity: PatientEntity) : UpdateItemResponse
}