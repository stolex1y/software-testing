package ru.stolexiy

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import java.lang.Double.NaN
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.abs
import kotlin.test.assertEquals

internal class BaseFunTest {
    
    private val cos = CosFun()
    private val ln = LnFun()

    @ParameterizedTest
    @CsvFileSource(resources = ["/cos-tests.csv"], delimiter = ';')
    fun `cos(x) test`(x: Double, expected: Double) {
        assertEquals(expected, cos.calc(x), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/cos-tests.csv"], delimiter = ';')
    fun `cos(x) periodic test`(x: Double, expected: Double) {
        val period = 2 * PI
        assertEquals(expected, cos.calc(x + period), absoluteTolerance)
        assertEquals(expected, cos.calc(x - period), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/cos-tests.csv"], delimiter = ';')
    fun `cos(x) even test`(x: Double, expected: Double) {
        assertEquals(cos.calc(x), cos.calc(-x), absoluteTolerance)
    }

    @Test
    fun `cos(x) NaN argument test`() {
        assertEquals(NaN, cos.calc(NaN))
    }

    @Test
    fun `cos(x) infinity argument test`() {
        assertEquals(NaN, cos.calc(Double.POSITIVE_INFINITY))
        assertEquals(NaN, cos.calc(Double.NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/ln-tests.csv"], delimiter = ';')
    fun `ln(x) test`(x: Double, expected: Double) {
        assertEquals(expected, ln.calc(x), absoluteTolerance)
    }

    @Test
    fun `ln(x) NaN argument test`() {
        assertEquals(NaN, ln.calc(NaN))
    }

    @Test
    fun `ln(x) infinity argument test`() {
        assertEquals(Double.POSITIVE_INFINITY, ln.calc(Double.POSITIVE_INFINITY))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1e-200, -1.0, -10.0, -23.0, Double.NEGATIVE_INFINITY])
    fun `ln(x) out of range test`(x: Double) {
        assertEquals(NaN, ln.calc(x))
    }

}
