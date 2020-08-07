package de.smartsquare.smidle.pullrequest

import de.smartsquare.smidle.TestObjects.closedPullRequest
import de.smartsquare.smidle.TestObjects.closedPullRequest2
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import org.junit.jupiter.api.Assertions.assertEquals

@SpringBootTest
@ActiveProfiles("test")
class PullRequestRepositoryTest {

    @Autowired
    private lateinit var pullRequestRepository: PullRequestRepository

    @Test
    fun `returns empty list for no pull requests in database`() {
        val filteredPullRequests = pullRequestRepository.findAllLimited(1)

        assertEquals(filteredPullRequests, emptyList<PullRequest>())
    }

    @Test
    fun `finds limited number of latest pull requests`() {
        pullRequestRepository.save(closedPullRequest)
        pullRequestRepository.save(closedPullRequest2)

        val filteredPullRequests = pullRequestRepository.findAllLimited(1)

        assertEquals(filteredPullRequests.size, 1)
        assertEquals(filteredPullRequests[0].closedAt, Instant.parse("2020-07-28T12:48:28Z"))
    }
}
