package de.smartsquare.smidle

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration {

    @Bean
    fun profile(@Value("\${spring.profiles.active:[]}") profiles: List<String>): FlywayConfigurationCustomizer {
        val locations = if (profiles.contains("demo")) {
            listOf("classpath:/db/migration", "classpath:/db/demo")
        } else {
            listOf("classpath:/db/migration")
        }

        return FlywayConfigurationCustomizer { conf -> conf.locations(*locations.toTypedArray()) }
    }
}