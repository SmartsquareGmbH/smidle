package de.smartsquare.smidle.util

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.smidle.pullrequest.PullRequestAction

private val objectMapper: ObjectMapper = jacksonObjectMapper()
    .findAndRegisterModules()
    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)

fun String.asPullRequestAction(): PullRequestAction = objectMapper.readValue(this, PullRequestAction::class.java)
