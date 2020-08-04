package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class PullRequest(
    @Id val id: Long,
    val title: String,
    val url: String,
    val createdAt: Instant,
    val closedAt: Instant,
    val mergedAt: Instant? = null
) {

    fun mapToObject(): PullRequestObject {
        return PullRequestObject(this.id, this.title, this.url, this.createdAt, this.closedAt, this.mergedAt)
    }
}
