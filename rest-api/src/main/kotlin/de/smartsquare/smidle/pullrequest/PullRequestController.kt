package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.smartsquare.smidle.pullrequest.ActionType.CLOSED
import de.smartsquare.smidle.pullrequest.ActionType.REOPENED
import de.smartsquare.smidle.secret.HashUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

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

        return when (action.type) {
            CLOSED -> {
                val pullRequest = action.pullRequest.mapToEntity()
                        ?: return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body("Closed_at field has no value.")

                val savedPullRequest: PullRequest = pullRequestRepository.save(pullRequest)

                ResponseEntity.status(HttpStatus.CREATED).body(savedPullRequest)
            }
            REOPENED -> {
                pullRequestRepository.deleteById(action.pullRequest.id)

                ResponseEntity.ok(action)
            }
            else -> {
                ResponseEntity.ok(action)
            }
        }
    }

    @GetMapping("/lifetime/{id}")
    fun lifeTimeOfPullRequest(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        val pullRequest = pullRequestRepository.findByIdOrNull(id)
                ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Pull Request with id $id does not exist."
                )

        return ResponseEntity.ok().body(pullRequest.getLifeTimeInSeconds())
    }

    private fun signatureIsInvalid(payload: String, signature: String) = !hashUtil.checkSignature(payload, signature)

    private fun PullRequest.getLifeTimeInSeconds(): Long = Duration.between(this.createdAt, this.closedAt).toSeconds()
}
