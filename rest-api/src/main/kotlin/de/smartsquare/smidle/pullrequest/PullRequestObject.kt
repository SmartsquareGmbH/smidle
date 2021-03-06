package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Duration
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class PullRequestObject(
    val id: Long,
    val title: String,
    @JsonProperty("html_url") val url: String,
    @JsonProperty("created_at") val createdAt: Instant,
    @JsonProperty("closed_at") val closedAt: Instant? = null,
    @JsonProperty("merged_at") val mergedAt: Instant? = null
) {

    fun mapToEntity(): PullRequest? {
        if (this.closedAt == null) {
            return null
        }

        return PullRequest(
                this.id,
                this.title,
                this.url,
                this.createdAt,
                this.closedAt,
                Duration.between(this.createdAt, this.closedAt).toMinutes(),
                this.mergedAt
        )
    }
}
