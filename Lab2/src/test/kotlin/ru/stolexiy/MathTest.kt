package ru.stolexiy

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import kotlin.math.PI
import kotlin.math.abs
import kotlin.test.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*;
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import java.lang.Double.NaN

@ExtendWith(MockitoExtension::class)
internal class MathTest {

    private fun sec(x: Number) = SecFun().calc(x)

    private val baseFunMock: CosFun = mock {
        on { calc(Mockito.anyInt()) } doAnswer { invocation ->
            Math.cos(invocation.getArgument(0))
        }
    }

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


    @Test
    fun `simple tan test`() {
        val expected = abs(Math.tan(PI / 3))
        val actual = abs(TanFun().calc(PI / 3))
        assertEquals(expected, actual, absoluteTolerance)
    }

    /*@ParameterizedTest
    @ValueSource(classes = [SinFun::class, TanFun::class])
    fun <T: TrigonometricFun> `simple sin test`(testFunClass: Class<T>) {
        val testFun = testFunClass.constructors[1].newInstance(baseFun) as SingleArgMathFun
        val expected = abs(Math.sin(PI / 3))
        val actual = abs(testFun.calc(PI / 3))
        assertEquals(expected, actual, absoluteTolerance)
    }*/

    @ParameterizedTest
    @ValueSource(doubles = [1.0, 2.3, PI / 2])
    fun `simple sin test`(x: Number) {
        val expected = abs(Math.sin(x.toDouble()))
        val actual = abs(SinFun(baseFunMock).calc(x))
        assertEquals(expected, actual, absoluteTolerance)
    }
}