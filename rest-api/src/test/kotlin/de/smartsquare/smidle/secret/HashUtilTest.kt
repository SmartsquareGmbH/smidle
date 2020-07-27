package de.smartsquare.smidle.secret

import de.smartsquare.smidle.WebhooksProperties
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class HashUtilTest {

    private val properties = WebhooksProperties(secret = "abc123")
    private val hashUtil = HashUtil(properties)

    @Test
    fun `checking valid signature returns true`() {
        val providedSignature = "sha1=bbe1912717905d61c4bc2217bba1fe52afb11159"
        val payload = "1 payload"

        val signatureMatches = hashUtil.checkSignature(payload, providedSignature)

        assertTrue(signatureMatches)
    }

    @Test
    fun `checking invalid signature returns false`() {
        val providedSignature = "sha1=invalid_signature"
        val payload = "1 payload"

        val signatureMatches = hashUtil.checkSignature(payload, providedSignature)

        assertFalse(signatureMatches)
    }
}
