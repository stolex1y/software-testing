package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.PI
import kotlin.test.assertEquals

internal class FunctionsTests {

    @Test
    fun cosXTest() {
        assertAll(
            { assertEquals(kotlin.math.cos(PI / 2), cos(PI / 2), 0.00001, "cos(0.1)") },
            { assertEquals(kotlin.math.cos(0.0), cos(0), 0.00001, "cos(0)") },
            { assertEquals(kotlin.math.cos(PI), cos(PI), 0.00001, "cos(PI)") },
        )
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-tests.csv"], delimiter = ';')
    fun `sec(x) test`(x: Double, result: Double) {
        assertEquals(result, sec(x), 0.0001)
    }

    @Test
    fun `sec(x) periodic`() {
        val x = 0.0
        val result = sec(x)
        val period = 2 * PI
        assertEquals(kotlin.math.cos(x - period), kotlin.math.cos(x))
//        assertEquals(result, sec(x + period), 0.0001)
        assertEquals(result, sec(x - period), 0.0001)
    }
}