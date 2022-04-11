package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import java.lang.Double.NaN
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.test.assertEquals

internal class MathTest {
    private val cosMock: CosFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            cos(invocation.getArgument(0))
        }
    }

    private val lnMock: LnFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            ln(invocation.getArgument(0))
        }
    }

    private val tan = TanFun(cosMock)
    private val cot = CotFun(cosMock)
    private val sin = SinFun(cosMock)
    private val log10 = LogFun(base = 10, baseFun = lnMock)
    private val log3 = LogFun(base = 3, baseFun = lnMock)

    @ParameterizedTest
    @CsvFileSource(resources = ["/tan-tests.csv"], delimiter = ';')
    fun `tan(x) test`(x: Double, expected: Double) {
        assertEquals(expected, tan.calc(x), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/tan-tests.csv"], delimiter = ';')
    fun `tan(x) periodic test`(x: Double, expected: Double) {
        val period = 2 * PI
        assertEquals(expected, tan.calc(x + period), absoluteTolerance)
        assertEquals(expected, tan.calc(x - period), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/tan-tests.csv"], delimiter = ';')
    fun `tan(x) odd test`(x: Double, expected: Double) {
        assertEquals(tan.calc(x), -tan.calc(-x), absoluteTolerance)
    }

    @Test
    fun `tan(x) NaN argument test`() {
        assertEquals(NaN, tan.calc(NaN))
    }

    @Test
    fun `tan(x) infinity argument test`() {
        assertEquals(NaN, tan.calc(Double.POSITIVE_INFINITY))
        assertEquals(NaN, tan.calc(Double.NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/cot-tests.csv"], delimiter = ';')
    fun `cot(x) test`(x: Double, expected: Double) {
        assertEquals(expected, cot.calc(x), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/cot-tests.csv"], delimiter = ';')
    fun `cot(x) periodic test`(x: Double, expected: Double) {
        val period = 2 * PI
        assertEquals(expected, cot.calc(x + period), absoluteTolerance)
        assertEquals(expected, cot.calc(x - period), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/cot-tests.csv"], delimiter = ';')
    fun `cot(x) odd test`(x: Double, expected: Double) {
        assertEquals(cot.calc(x), -cot.calc(-x), absoluteTolerance)
    }

    @Test
    fun `cot(x) NaN argument test`() {
        assertEquals(NaN, cot.calc(NaN))
    }

    @Test
    fun `cot(x) infinity argument test`() {
        assertEquals(NaN, cot.calc(Double.POSITIVE_INFINITY))
        assertEquals(NaN, cot.calc(Double.NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sin-tests.csv"], delimiter = ';')
    fun `sin(x) test`(x: Double, expected: Double) {
        assertEquals(expected, sin.calc(x), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sin-tests.csv"], delimiter = ';')
    fun `sin(x) periodic test`(x: Double, expected: Double) {
        val period = 2 * PI
        assertEquals(expected, sin.calc(x + period), absoluteTolerance)
        assertEquals(expected, sin.calc(x - period), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sin-tests.csv"], delimiter = ';')
    fun `sin(x) odd test`(x: Double, expected: Double) {
        assertEquals(sin.calc(x), -sin.calc(-x), absoluteTolerance)
    }

    @Test
    fun `sin(x) NaN argument test`() {
        assertEquals(NaN, sin.calc(NaN))
    }

    @Test
    fun `sin(x) infinity argument test`() {
        assertEquals(NaN, sin.calc(Double.POSITIVE_INFINITY))
        assertEquals(NaN, sin.calc(Double.NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/log10-tests.csv"], delimiter = ';')
    fun `log10(x) test`(x: Double, expected: Double) {
        assertEquals(expected, log10.calc(x), absoluteTolerance)
    }

    @Test
    fun `log10(x) NaN argument test`() {
        assertEquals(NaN, log10.calc(NaN))
    }

    @Test
    fun `log10(x) infinity argument test`() {
        assertEquals(Double.POSITIVE_INFINITY, log10.calc(Double.POSITIVE_INFINITY))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1e-200, -1.0, -10.0, -23.0, Double.NEGATIVE_INFINITY])
    fun `log10(x) out of range test`(x: Double) {
        assertEquals(NaN, log10.calc(x))
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/log3-tests.csv"], delimiter = ';')
    fun `log3(x) test`(x: Double, expected: Double) {
        assertEquals(expected, log3.calc(x), absoluteTolerance)
    }

    @Test
    fun `log3(x) NaN argument test`() {
        assertEquals(NaN, log3.calc(NaN))
    }

    @Test
    fun `log3(x) infinity argument test`() {
        assertEquals(Double.POSITIVE_INFINITY, log3.calc(Double.POSITIVE_INFINITY))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1e-200, -1.0, -10.0, -23.0, Double.NEGATIVE_INFINITY])
    fun `log3(x) out of range test`(x: Double) {
        assertEquals(NaN, log3.calc(x))
    }

}