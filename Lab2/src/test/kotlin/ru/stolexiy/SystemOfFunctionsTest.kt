package ru.stolexiy

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.NaN
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.*
import kotlin.test.assertEquals

internal class SystemOfFunctionsTest {

    private val cosMock: CosFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            cos(invocation.getArgument(0))
        }
    }

    private val sinMock: SinFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            sin(invocation.getArgument(0))
        }
    }

    private val tanMock: TanFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            tan(invocation.getArgument(0))
        }
    }

    private val cotMock: CotFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            1 / tan(invocation.getArgument(0))
        }
    }

    private val lnMock: LnFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            ln(invocation.getArgument(0))
        }
    }

    private val log3Mock: LogFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            log(invocation.getArgument(0), 3.0)
        }
    }

    private val log10Mock: LogFun = mock {
        on { calc(Mockito.anyDouble()) } doAnswer { invocation ->
            log10(invocation.getArgument(0))
        }
    }

    private val sysOfFunc = MySystemOfFunctions(
        cosMock, cotMock, tanMock, sinMock, lnMock, log3Mock, log10Mock
    )

    @ParameterizedTest
    @CsvFileSource(resources = ["/system-tests.csv"], delimiter = ';')
    fun `system of func module test`(x: Double, expected: Double) {
        assertEquals(expected, sysOfFunc.calc(x), absoluteTolerance)
    }

    @Test
    fun `system of func breakpoints module test`() {
        assertThat(abs(sysOfFunc.calc(-PI / 2)), greaterThan(1E14))
        assertThat(abs(sysOfFunc.calc(-5 * PI / 2)), greaterThan(1E14))
        assertThat(sysOfFunc.calc(0), equalTo(NaN))
        assertThat(sysOfFunc.calc(-0), equalTo(NaN))
    }

    @Test
    fun `system of func NaN argument module test`() {
        assertEquals(NaN, sysOfFunc.calc(NaN))
    }

    @Test
    fun `system of func infinity argument module test`() {
        assertEquals(NaN, sysOfFunc.calc(POSITIVE_INFINITY))
        assertEquals(NaN, sysOfFunc.calc(NEGATIVE_INFINITY))
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.2, -1.1, -1.0, -0.9, -0.5, -0.2])
    fun `system of func periodic module test`(x: Double) {
        val period = 2 * PI
        val expected = sysOfFunc.calc(x)
        assertEquals(expected, sysOfFunc.calc(x - period), absoluteTolerance)
    }
}