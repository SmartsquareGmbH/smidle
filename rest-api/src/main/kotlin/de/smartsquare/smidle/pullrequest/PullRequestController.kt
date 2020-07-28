package de.smartsquare.smidle.pullrequest

import de.smartsquare.smidle.secret.HashUtil
import de.smartsquare.smidle.util.asPullRequestAction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pull-request")
class PullRequestController(private val hashUtil: HashUtil) {

    @PostMapping
    fun pullRequestAction(
        @RequestHeader("X-Hub-Signature") signature: String,
        @RequestBody payload: String
    ): ResponseEntity<Any> {
        if (!hashUtil.checkSignature(payload, signature)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature supplied.")
        }

        return ResponseEntity.ok(payload.asPullRequestAction())
    }
}
