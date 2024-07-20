package com.group76.patient.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "system")
class SystemProperties {
    var idService: String = ""
    val collection: CollectionConfiguration = CollectionConfiguration()

    class CollectionConfiguration{
        var patient: String = ""
    }
}

