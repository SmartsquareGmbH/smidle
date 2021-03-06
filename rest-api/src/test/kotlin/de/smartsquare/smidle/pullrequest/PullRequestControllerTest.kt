package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.databind.ObjectMapper
import de.smartsquare.smidle.FlywayTestExtension
import de.smartsquare.smidle.TestObjects.closedPullRequest
import de.smartsquare.smidle.TestObjects.closedPullRequest2
import de.smartsquare.smidle.TestObjects.openPullRequestObject
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(FlywayTestExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class PullRequestControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var pullRequestRepository: PullRequestRepository

    @Test
    fun `sending action with invalid signature is unauthorized`() {
        val invalidSignature = "sha=invalid_signature"
        val post = post("/api/pull-request/action")
                .header("X-Hub-Signature", invalidSignature)
                .contentType(APPLICATION_JSON)
                .content("{ }")

        mockMvc
                .perform(post)
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `sending opened action with valid signature returns pull request action`() {
        val validSignature = "sha1=d36fac798a2d276dbfa0587ed80c5ff4bf59009f"
        val openedAction = """
            {
              "action": "opened",
              "pull_request": {
                "id": 279147437,
                "title": "Pull Request Title",
                "html_url": "http://localhost",
                "created_at": "2020-07-28T11:48:28Z"
              }
            }
        """.trimIndent()

        val post = post("/api/pull-request/action")
                .header("X-Hub-Signature", validSignature)
                .contentType(APPLICATION_JSON)
                .content(openedAction)

        val response = mockMvc
                .perform(post)
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString

        val action = objectMapper.readValue(response, Action::class.java)

        val expectedResponse = Action(
                type = ActionType.OPENED,
                pullRequest = openPullRequestObject
        )

        assertEquals(expectedResponse, action)
    }

    @Test
    fun `sending closed action with valid signature returns created pull request`() {
        val validSignature = "sha1=5d7cbc423bc41026dcb75623b18cffe73d0b5143"
        val closedAction = """
            {
              "action": "closed",
              "pull_request": {
                "id": 279147438,
                "title": "Pull Request Title",
                "html_url": "http://localhost",
                "created_at": "2020-07-28T11:48:28Z",
                "closed_at": "2020-07-28T12:48:28Z"
              }
            }
        """.trimIndent()

        val post = post("/api/pull-request/action")
                .header("X-Hub-Signature", validSignature)
                .contentType(APPLICATION_JSON)
                .content(closedAction)

        val response = mockMvc
                .perform(post)
                .andExpect(status().isCreated)
                .andReturn()
                .response
                .contentAsString

        val pullRequest = objectMapper.readValue(response, PullRequest::class.java)
        val expectedResponse = closedPullRequest

        assertEquals(expectedResponse, pullRequest)
    }

    @Test
    fun `sending reopened action with valid signature removes pull request and returns action`() {
        pullRequestRepository.save(closedPullRequest)
        assertNotNull(pullRequestRepository.findByIdOrNull(closedPullRequest.id))

        val validSignature = "sha1=85406d5d57c5d7a7230707e6a04315ce0a6f9c47"
        val reopenedAction = """
            {
              "action": "reopened",
              "pull_request": {
                "id": 279147438,
                "title": "Pull Request Title",
                "html_url": "http://localhost",
                "created_at": "2020-07-28T11:48:28Z"
              }
            }
        """.trimIndent()

        val post = post("/api/pull-request/action")
                .header("X-Hub-Signature", validSignature)
                .contentType(APPLICATION_JSON)
                .content(reopenedAction)

        val response = mockMvc
                .perform(post)
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString

        assertNull(pullRequestRepository.findByIdOrNull(closedPullRequest.id))

        val action = objectMapper.readValue(response, Action::class.java)

        val expectedResponse = Action(
                type = ActionType.REOPENED,
                pullRequest = closedPullRequest.mapToObject().copy(closedAt = null)
        )

        assertEquals(expectedResponse, action)
    }

    @Test
    fun `returns emptyList if no pull request is in database`() {
        assertEquals(pullRequestRepository.findAll(), emptyList<PullRequest>())

        mockMvc
            .get("/api/pull-request")
            .andExpect {
                status { isOk }
                jsonPath("$.length()", equalTo(0))
            }
    }

    @Test
    fun `returns latest pull requests`() {
        pullRequestRepository.save(closedPullRequest)
        pullRequestRepository.save(closedPullRequest2)

        mockMvc
            .get("/api/pull-request")
            .andExpect {
                status { isOk }
                jsonPath("$.length()", equalTo(2))
                content { string(containsString("Pull Request Title")) }
            }
    }

    @Test
    fun `returns null if no closed pull request is in database`() {
        assertEquals(pullRequestRepository.findAll(), emptyList<PullRequest>())

        val get = get("/api/pull-request/lifetime/")

        val response = mockMvc
            .perform(get)
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        assertEquals(response, "")
    }

    @Test
    fun `returns average lifetime in minutes of all pull requests`() {
        pullRequestRepository.save(closedPullRequest)
        pullRequestRepository.save(closedPullRequest2)

        val get = get("/api/pull-request/lifetime/")

        val response = mockMvc
            .perform(get)
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        assertEquals(response, "90")
    }
}
