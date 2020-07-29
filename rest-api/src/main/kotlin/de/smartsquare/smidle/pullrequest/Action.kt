package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Action(
    @JsonProperty("action") val type: ActionType,
    @JsonProperty("pull_request") val pullRequest: PullRequest
)
