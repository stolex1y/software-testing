package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.PI
import kotlin.test.assertEquals

internal class FunctionsTests {

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-tests.csv"], delimiter = ';')
    fun `sec(x) test`(x: Double, result: Double) {
        assertEquals(result, sec(x), 0.00001)
    }

    @Test
    fun `sec(x) periodic`() {
        val x = 1.1
        val result = sec(x)
        val period = 2 * PI
        assertEquals(result, sec(x + period), 1e-7)
        assertEquals(result, sec(x - period), 1e-7)
    }

    @Test
    fun `sec(x) parity`() {
        val x = 0.5
        assertEquals(sec(x), sec(-x), 1e-7)
    }


}