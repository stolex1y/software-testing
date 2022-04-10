package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.math.PI
import kotlin.math.abs
import kotlin.test.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*;
import java.lang.Double.NaN

private const val absoluteTolerance = 1e-7

internal class MathTest {

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-tests.csv"], delimiter = ';')
    fun `sec(x) test`(x: Double, result: Double) {
        assertEquals(result, sec(x), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-tests.csv"], delimiter = ';')
    fun `sec(x) periodic test`(x: Double, result: Double) {
        val period = 2 * PI
        assertEquals(result, sec(x + period), absoluteTolerance)
        assertEquals(result, sec(x - period), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sec-tests.csv"], delimiter = ';')
    fun `sec(x) parity test`(x: Double, result: Double) {
        assertEquals(sec(x), sec(-x), absoluteTolerance)
    }

    @Test
    fun `sec(x) breakpoints test`() {
        assertThat(abs(sec(Math.PI / 2)), greaterThan(1E14))
        assertThat(abs(sec(- 3 * Math.PI / 2)), greaterThan(1E14))
    }

    @Test
    fun `sec(x) NaN argument test`() {
        assertEquals(sec(NaN), NaN)
    }

    @Test
    fun `sec(x) infinity argument test`() {
        assertEquals(sec(Double.POSITIVE_INFINITY), NaN)
        assertEquals(sec(Double.NEGATIVE_INFINITY), NaN)
    }


}