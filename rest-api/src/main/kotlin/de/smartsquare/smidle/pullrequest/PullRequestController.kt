package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.smidle.pullrequest.ActionType.CLOSED
import de.smartsquare.smidle.secret.HashUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pull-request")
class PullRequestController(private val hashUtil: HashUtil, private val pullRequestRepository: PullRequestRepository) {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
        .findAndRegisterModules()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)

    @PostMapping
    fun pullRequestAction(
        @RequestHeader("X-Hub-Signature") signature: String,
        @RequestBody payload: String
    ): ResponseEntity<Any> {
        if (signatureIsInvalid(payload, signature)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature supplied.")
        }

        val action = objectMapper.readValue(payload, Action::class.java)

        return if (action.type == CLOSED) {
            val savedPullRequest = pullRequestRepository.save(action.pullRequest)

            ResponseEntity.status(HttpStatus.CREATED).body(savedPullRequest)
        } else {
            ResponseEntity.ok(action)
        }
    }

    private fun signatureIsInvalid(payload: String, signature: String) = !hashUtil.checkSignature(payload, signature)
}
