package com.group76.patient.services.impl

import com.group76.patient.entities.PatientEntity
import com.group76.patient.services.IPatientDbService
import org.springframework.stereotype.Component
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*

@Component
class PatientDbServiceImpl : IPatientDbService {
    private val tableName = "Doctor"
    override fun putItem(patientEntity: PatientEntity) : PutItemResponse {
        val dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_2)
            .build()

        val itemValues = mutableMapOf(
            "id" to AttributeValue.builder().s(patientEntity.id.toString()).build(),
            "password" to AttributeValue.builder().s(patientEntity.password).build(),
            "cpf" to AttributeValue.builder().s(patientEntity.cpf).build(),
            "email" to AttributeValue.builder().s(patientEntity.email).build(),
            "name" to AttributeValue.builder().s(patientEntity.name).build(),
            "address" to AttributeValue.builder().s(patientEntity.address).build(),
            "cep" to AttributeValue.builder().s(patientEntity.cep).build(),
            "state" to AttributeValue.builder().s(patientEntity.state).build(),
            "city" to AttributeValue.builder().s(patientEntity.city).build(),
            "phone" to AttributeValue.builder().s(patientEntity.phone).build(),
        )

        val putItemRequest = PutItemRequest.builder()
            .tableName(tableName)
            .item(itemValues)
            .build()

        val response = dynamoDbClient.putItem(putItemRequest)
        dynamoDbClient.close()
        return response
    }

    override fun verifyEmail(email: String, id: String?): Boolean {
        return scan("email", email, "id", id).count() > 0
    }

    override fun verifyCpf(cpf: String, id: String?): Boolean {
        return scan("cpf", cpf, "id", id).count() > 0
    }

    override fun getById(id: String): GetItemResponse {
        val key = mapOf("id" to AttributeValue.builder().s(id).build())
        val client = DynamoDbClient.builder()
            .region(Region.US_EAST_2)
            .build()

        val updateRequest = GetItemRequest.builder()
            .tableName(tableName)
            .key(key)
            .build()

        val item = client.getItem(updateRequest)
        client.close()
        return item
    }

    override fun getByEmail(email: String): ScanResponse {
        return scan("email", email)
    }

    override fun getByCpf(cpf: String): ScanResponse {
        return scan("cpf", cpf)
    }

    fun scan(
        attributeName: String,
        value: String,
        attributeNameDiff: String? = null,
        valueNotEqual: String? = null
    ): ScanResponse {
        val dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_2)
            .build()

        val scanRequest = if(attributeNameDiff == null || valueNotEqual == null)
            ScanRequest.builder()
            .tableName(tableName)
            .filterExpression("#attr = :value")
            .expressionAttributeNames(mapOf("#attr" to attributeName))
            .expressionAttributeValues(mapOf(":value" to AttributeValue.builder().s(value).build()))
            .build()
        else ScanRequest.builder()
            .tableName(tableName)
            .filterExpression("#attr = :value AND #notEqualAttr <> :notEqualValue")
            .expressionAttributeNames(mapOf(
                "#attr" to attributeName,
                "#notEqualAttr" to attributeNameDiff
            ))
            .expressionAttributeValues(mapOf(
                ":value" to AttributeValue.builder().s(value).build(),
                ":notEqualValue" to AttributeValue.builder().s(valueNotEqual).build()
            ))
            .build()

        val result = dynamoDbClient.scan(scanRequest)
        dynamoDbClient.close()
        return result
    }

    override fun updateItem(patientEntity: PatientEntity): UpdateItemResponse {
        val dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_2)
            .build()

        val updateExpression = "SET password = :password" +
                ", cpf = :cpf" +
                ", email = :email" +
                ", name = :name" +
                ", address = :address" +
                ", cep = :cep" +
                ", state = :state" +
                ", city = :city" +
                ", phone = :phone"

        val itemValues = mutableMapOf(
            ":password" to AttributeValue.builder().s(patientEntity.password).build(),
            ":cpf" to AttributeValue.builder().s(patientEntity.cpf).build(),
            ":email" to AttributeValue.builder().s(patientEntity.email).build(),
            ":name" to AttributeValue.builder().s(patientEntity.name).build(),
            ":address" to AttributeValue.builder().s(patientEntity.address).build(),
            ":cep" to AttributeValue.builder().s(patientEntity.cep).build(),
            ":state" to AttributeValue.builder().s(patientEntity.state).build(),
            ":city" to AttributeValue.builder().s(patientEntity.city).build(),
            ":phone" to AttributeValue.builder().s(patientEntity.phone).build(),
        )

        val updateItemRequest = UpdateItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("id" to AttributeValue.builder().s(patientEntity.id.toString()).build()))
            .updateExpression(updateExpression)
            .expressionAttributeValues(itemValues)
            .returnValues(ReturnValue.UPDATED_NEW)
            .build()

        val result = dynamoDbClient.updateItem(updateItemRequest)
        dynamoDbClient.close()
        return result
    }
}