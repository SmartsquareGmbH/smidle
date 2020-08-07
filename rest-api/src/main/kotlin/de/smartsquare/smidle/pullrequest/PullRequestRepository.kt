package de.smartsquare.smidle.pullrequest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PullRequestRepository : JpaRepository<PullRequest, Long> {

    @Query(
        value = """
            SELECT * from PULL_REQUEST ORDER BY closed_at DESC LIMIT :number
        """,
        nativeQuery = true
    )
    fun findAllLimited(number: Int): List<PullRequest>
}
