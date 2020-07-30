package de.smartsquare.smidle.pullrequest

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class PullRequestControllerTest {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
        .findAndRegisterModules()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `sending action with invalid signature is unauthorized`() {
        val invalidSignature = "sha=invalid_signature"
        val post = post("/api/pull-request")
            .header("X-Hub-Signature", invalidSignature)
            .contentType(APPLICATION_JSON)
            .content("{ }")

        mockMvc
            .perform(post)
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `sending opened action with valid signature returns pull request action`() {
        val validSignature = "sha1=343a8f2a982c6b40331a16ab676b512270d0cc52"
        val openedAction = """
            {
              "action": "opened",
              "pull_request": {
                "id": 279147437,
                "title": "Pull Request Title",
                "url": "http://localhost",
                "created_at": "2020-07-28T11:48:28Z"
              }
            }
        """.trimIndent()

        val post = post("/api/pull-request")
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
            pullRequest = PullRequest(
                id = 279147437,
                title = "Pull Request Title",
                url = "http://localhost",
                createdAt = Instant.parse("2020-07-28T11:48:28Z")
            )
        )

        assertEquals(expectedResponse, action)
    }

    @Test
    fun `sending closed action with valid signature returns created pull request`() {
        val validSignature = "sha1=8cf5eb15be6d8bff5e4ab4590f259a3e61526fb4"
        val closedAction = """
            {
              "action": "closed",
              "pull_request": {
                "id": 279147438,
                "title": "Pull Request Title",
                "url": "http://localhost",
                "created_at": "2020-07-28T11:48:28Z",
                "closed_at": "2020-07-28T12:48:28Z"
              }
            }
        """.trimIndent()

        val post = post("/api/pull-request")
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

        val expectedResponse = PullRequest(
            id = 279147438,
            title = "Pull Request Title",
            url = "http://localhost",
            createdAt = Instant.parse("2020-07-28T11:48:28Z"),
            closedAt = Instant.parse("2020-07-28T12:48:28Z")
        )

        assertEquals(expectedResponse, pullRequest)
    }
}
