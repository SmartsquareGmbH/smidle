package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class PullRequestAction(
    val action: ActionType,
    val number: Int,
    val timestamp: Instant = Instant.now()
)
