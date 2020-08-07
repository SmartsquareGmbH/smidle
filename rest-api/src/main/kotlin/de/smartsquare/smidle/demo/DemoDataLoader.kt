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
            closedAt = Instant.parse("2020-07-16T13:01:59Z"),
            mergedAt = Instant.parse("2020-07-16T13:01:59Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147431,
            title = "Another feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-15T10:34:19Z"),
            closedAt = Instant.parse("2020-07-16T15:57:30Z"),
            mergedAt = Instant.parse("2020-07-16T15:57:30Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147430,
            title = "Nice feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-14T11:23:47Z"),
            closedAt = Instant.parse("2020-07-16T18:06:19Z"),
            mergedAt = Instant.parse("2020-07-16T18:06:19Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147429,
            title = "Useful feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-14T10:48:27Z"),
            closedAt = Instant.parse("2020-07-15T09:06:13Z"),
            mergedAt = Instant.parse("2020-07-15T09:06:13Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147428,
            title = "Boring feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-14T10:02:40Z"),
            closedAt = Instant.parse("2020-07-14T11:36:22Z"),
            mergedAt = Instant.parse("2020-07-14T11:36:22Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147427,
            title = "One more feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-10T15:11:28Z"),
            closedAt = Instant.parse("2020-07-14T08:48:14Z"),
            mergedAt = Instant.parse("2020-07-14T08:48:14Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147426,
            title = "A feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-12T13:30:17Z"),
            closedAt = Instant.parse("2020-07-13T16:21:19Z"),
            mergedAt = Instant.parse("2020-07-13T16:21:19Z")
        ))

        pullRequestRepository.save(PullRequest(
            id = 279147425,
            title = "THE feature",
            url = "https://github.com/",
            createdAt = Instant.parse("2020-07-13T09:19:56Z"),
            closedAt = Instant.parse("2020-07-13T10:56:43Z"),
            mergedAt = Instant.parse("2020-07-13T10:56:43Z")
        ))
    }
}
