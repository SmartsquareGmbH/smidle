package de.smartsquare.smidle.testutil

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.smidle.pullrequest.PullRequest

private val objectMapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules()

fun String.asPullRequest(): PullRequest = objectMapper.readValue(this, PullRequest::class.java)
