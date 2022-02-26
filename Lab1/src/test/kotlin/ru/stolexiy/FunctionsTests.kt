package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.PI
import kotlin.test.assertEquals

private const val absoluteTolerance = 1e-7

internal class FunctionsTests {

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-tests.csv"], delimiter = ';')
    fun `sec(x) test`(x: Double, result: Double) {
        assertEquals(result, sec(x), absoluteTolerance)
    }

    @Test
    fun `sec(x) periodic`() {
        val x = 1.1
        val result = sec(x)
        val period = 2 * PI
        assertEquals(result, sec(x + period), absoluteTolerance)
        assertEquals(result, sec(x - period), absoluteTolerance)
    }

    @Test
    fun `sec(x) parity`() {
        val x = 0.5
        assertEquals(sec(x), sec(-x), absoluteTolerance)
    }


}