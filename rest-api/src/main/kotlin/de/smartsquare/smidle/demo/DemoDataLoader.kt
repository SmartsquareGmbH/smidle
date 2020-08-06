package de.smartsquare.smidle.demo

import de.smartsquare.smidle.pullrequest.PullRequest
import de.smartsquare.smidle.pullrequest.PullRequestRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@Lazy(false)
@Profile("demo")
class DemoDataLoader(private val pullRequestRepository: PullRequestRepository) : InitializingBean {
    private val log = LoggerFactory.getLogger(DemoDataLoader::class.java)

    override fun afterPropertiesSet() {
        log.info("Loading Demo Data")
        loadData()
    }

    fun loadData() {
        pullRequestRepository.save(PullRequest(
            id = 279147436,
            title = "New feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-26T11:48:28Z"),
            closedAt = Instant.parse("2020-07-26T13:03:11Z"),
            mergedAt = Instant.parse("2020-07-26T13:03:11Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147435,
            title = "Unnecessary feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-24T14:16:52Z"),
            closedAt = Instant.parse("2020-07-25T09:24:33Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147434,
            title = "Awesome feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-23T11:20:31Z"),
            closedAt = Instant.parse("2020-07-23T16:11:04Z"),
            mergedAt = Instant.parse("2020-07-23T16:11:04Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147433,
            title = "Cool feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-18T16:00:01Z"),
            closedAt = Instant.parse("2020-07-19T10:42:27Z"),
            mergedAt = Instant.parse("2020-07-19T10:42:27Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147432,
            title = "Fantastic feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-16T10:34:19Z"),
            closedAt = Instant.parse("2020-07-16T15:57:30Z"),
            mergedAt = Instant.parse("2020-07-16T15:57:30Z")
        ))
    }
}
