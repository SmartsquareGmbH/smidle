package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class PullRequest(
    @Id val id: Long,
    val title: String,
    val url: String,
    @JsonProperty("created_at") val createdAt: Instant,
    @JsonProperty("closed_at") val closedAt: Instant? = null,
    @JsonProperty("merged_at") val mergedAt: Instant? = null
)
