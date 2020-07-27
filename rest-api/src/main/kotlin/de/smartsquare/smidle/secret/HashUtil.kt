package de.smartsquare.smidle.secret

import de.smartsquare.smidle.WebhooksProperties
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class HashUtil(val properties: WebhooksProperties) {

    fun checkSignature(payload: String, providedSignature: String): Boolean {
        val signature = "sha1=" + hmacSHA1(payload)

        return signature == providedSignature
    }

    private fun hmacSHA1(payload: String): String {
        val algorithm = "HmacSHA1"

        return Mac
            .getInstance(algorithm)
            .run {
                init(SecretKeySpec(properties.secret.toByteArray(), algorithm))
                doFinal(payload.toByteArray(StandardCharsets.UTF_8))
            }.hex()
    }

    private fun ByteArray.hex(): String {
        val result = StringBuilder(this.size * 2)
        val hexChars = "0123456789abcdef"

        this.map {
            result.append(hexChars[it.toInt() shr 4 and 0x0f])
            result.append(hexChars[it.toInt() and 0x0f])
        }

        return result.toString()
    }
}
