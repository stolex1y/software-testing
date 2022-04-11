package ru.stolexiy

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.ln
import kotlin.test.assertEquals

internal class IntegrationTest {

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

    val sysOfFyncWithMocks = MySystemOfFunctions(
        cos = cosMock, ln = lnMock
    )

    val sysOfFunc = MySystemOfFunctions()


    @ParameterizedTest
    @CsvFileSource(resources = ["/system-tests.csv"], delimiter = ';')
    fun `system of func with base func mocks test`(x: Double, expected: Double) {
        assertEquals(expected, sysOfFyncWithMocks.calc(x), absoluteTolerance)
    }

    @Test
    fun `system of func breakpoints with base func mocks test`() {
        MatcherAssert.assertThat(abs(sysOfFyncWithMocks.calc(-PI / 2)), Matchers.greaterThan(1E14))
        MatcherAssert.assertThat(abs(sysOfFyncWithMocks.calc(-5 * PI / 2)), Matchers.greaterThan(1E14))
        MatcherAssert.assertThat(sysOfFyncWithMocks.calc(0), Matchers.equalTo(Double.NaN))
        MatcherAssert.assertThat(sysOfFyncWithMocks.calc(-0), Matchers.equalTo(Double.NaN))
    }

    @Test
    fun `system of func NaN argument with base func mocks test`() {
        assertEquals(Double.NaN, sysOfFyncWithMocks.calc(Double.NaN))
    }

    @Test
    fun `system of func infinity argument with base func mocks test`() {
        assertEquals(Double.NaN, sysOfFyncWithMocks.calc(Double.POSITIVE_INFINITY))
        assertEquals(Double.NaN, sysOfFyncWithMocks.calc(Double.NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.2, -1.1, -1.0, -0.9, -0.5, -0.2])
    fun `system of func periodic with base func mocks test`(x: Double) {
        val period = 2 * PI
        val expected = sysOfFyncWithMocks.calc(x)
        assertEquals(expected, sysOfFyncWithMocks.calc(x - period), absoluteTolerance)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/system-tests.csv"], delimiter = ';')
    fun `system of func full integration test`(x: Double, expected: Double) {
        assertEquals(expected, sysOfFunc.calc(x), absoluteTolerance)
    }

    @Test
    fun `system of func breakpoints full integration test`() {
        MatcherAssert.assertThat(abs(sysOfFunc.calc(-PI / 2)), Matchers.greaterThan(1E14))
        MatcherAssert.assertThat(abs(sysOfFunc.calc(-5 * PI / 2)), Matchers.greaterThan(1E14))
        MatcherAssert.assertThat(sysOfFunc.calc(0), Matchers.equalTo(Double.NaN))
        MatcherAssert.assertThat(sysOfFunc.calc(-0), Matchers.equalTo(Double.NaN))
    }

    @Test
    fun `system of func NaN argument full integration test`() {
        assertEquals(Double.NaN, sysOfFunc.calc(Double.NaN))
    }

    @Test
    fun `system of func infinity argument full integration test`() {
        assertEquals(Double.NaN, sysOfFunc.calc(Double.POSITIVE_INFINITY))
        assertEquals(Double.NaN, sysOfFunc.calc(Double.NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.2, -1.1, -1.0, -0.9, -0.5, -0.2])
    fun `system of func periodic full integration test`(x: Double) {
        val period = 2 * PI
        val expected = sysOfFunc.calc(x)
        assertEquals(expected, sysOfFunc.calc(x - period), absoluteTolerance)
    }
}