package de.smartsquare.smidle

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan
class SmidleApplication

fun main(args: Array<String>) {
    SpringApplication.run(SmidleApplication::class.java, *args)
}
