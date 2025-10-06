package com.example
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals

@MicronautTest
class Deserializer_issueTest @Inject constructor(
    private val objectMapper: io.micronaut.serde.ObjectMapper
) {

    @Serdeable
    data class TestMe(
        val testString: String? = "I AM DEFAULT"
    )

    @Test
    fun testDefaultValues() {
        val jsonWithNull = """{"testString": null}"""
        val jsonWithMissing = """{}"""

        // With nullIsSameAsDefault = false, a null in JSON overrides the default
        val fromNull = objectMapper.readValue(jsonWithNull, TestMe::class.java)
        assertEquals(null, fromNull.testString) //FAILS - default value set

        // A missing field still uses the default value
        val fromMissing = objectMapper.readValue(jsonWithMissing, TestMe::class.java)
        assertEquals("I AM DEFAULT", fromMissing.testString)
    }

}
