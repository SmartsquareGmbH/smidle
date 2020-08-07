package de.smartsquare.smidle

import de.smartsquare.smidle.pullrequest.PullRequest
import de.smartsquare.smidle.pullrequest.PullRequestObject
import java.time.Instant

object TestObjects {

    val openPullRequestObject = PullRequestObject(
        id = 279147437,
        title = "Pull Request Title",
        url = "http://localhost",
        createdAt = Instant.parse("2020-07-28T11:48:28Z")
    )

    val closedPullRequest = PullRequest(
        id = 279147438,
        title = "Pull Request Title",
        url = "http://localhost",
        createdAt = Instant.parse("2020-07-28T11:48:28Z"),
        closedAt = Instant.parse("2020-07-28T12:48:28Z")
    )

    val closedPullRequest2 = PullRequest(
        id = 279147439,
        title = "Pull Request Title",
        url = "http://localhost",
        createdAt = Instant.parse("2020-07-26T11:48:28Z"),
        closedAt = Instant.parse("2020-07-26T13:48:28Z")
    )
}
