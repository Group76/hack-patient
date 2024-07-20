package com.group76.patient.services.impl

import com.group76.patient.services.IJwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtServiceImpl : IJwtService {
    private val secretKey = "d8e45755249ca02070d6a9786ddb3c5521e172ed167b8c31cba588368ed3348b"
    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
    override fun generateToken(id: String): String? {
        val now = Date()
        val validity = Date(now.time + 3600000) // 1 hour validity

        return Jwts.builder()
            .expiration(validity)
            .issuedAt(now)
            .subject(id)
            .claim("id", id)
            .claim("role", "Patient")
            .signWith(key)
            .compact()
    }


    override fun extractId(token: String): String? =
        getAllClaims(token)
            ?.subject
    override fun isExpired(token: String): Boolean =
        getAllClaims(token)
            ?.expiration
            ?.before(Date(System.currentTimeMillis())) ?: true
    private fun getAllClaims(token: String): Claims? {
        try {
            val parser = Jwts.parser()
                .verifyWith(key)
                .build()

            return parser
                .parseSignedClaims(token)
                .payload
        }
        catch (ex: ExpiredJwtException){
            return null
        }

    }
}