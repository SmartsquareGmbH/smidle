package de.smartsquare.smidle.pullrequest

import de.smartsquare.smidle.util.asPullRequestAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class PullRequestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val webhook = """
        {
          "action": "opened",
          "number": 1,
          "pull_request": {
            "id": 279147437
          }
        }
    """.trimIndent()

    @Test
    fun `sending webhook with invalid signature is unauthorized`() {
        val invalidSignature = "sha=invalid_signature"
        val post = post("/api/pull-request")
            .header("X-Hub-Signature", invalidSignature)
            .contentType(APPLICATION_JSON)
            .content(webhook)

        mockMvc
            .perform(post)
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `sending webhook with valid signature is successfully`() {
        val validSignature = "sha1=5b26f73cad8cb135126e464c618710b115da3225"
        val post = post("/api/pull-request")
            .header("X-Hub-Signature", validSignature)
            .contentType(APPLICATION_JSON)
            .content(webhook)

        mockMvc
            .perform(post)
            .andExpect(status().isOk)
    }

    @Test
    fun `sending webhook returns pull request action`() {
        val validSignature = "sha1=5b26f73cad8cb135126e464c618710b115da3225"
        val post = post("/api/pull-request")
            .header("X-Hub-Signature", validSignature)
            .contentType(APPLICATION_JSON)
            .content(webhook)

        val response = mockMvc
            .perform(post)
            .andReturn()
            .response
            .contentAsString
            .asPullRequestAction()

        assertEquals(response.action, ActionType.OPENED)
        assertEquals(response.number, 1)
    }
}
