package com.group76.patient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PatientApplication

fun main(args: Array<String>) {
	runApplication<PatientApplication>(*args)
}
