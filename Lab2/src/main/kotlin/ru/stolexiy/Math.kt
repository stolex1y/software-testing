package ru.stolexiy

import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

abstract class TrigonometricFun(
    protected val baseFun: CosFun = CosFun()
): SingleArgMathFun

open class CotFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    private val sin = SinFun(baseFun)
    override fun calc(x: Number) = baseFun.calc(x) / sin.calc(x)
}

open class TanFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    private val sin = SinFun(baseFun)
    override fun calc(x: Number) = sin.calc(x) / baseFun.calc(x)
}

open class SinFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    override fun calc(x: Number): Double {
        val _x = normalizeRadians(x)
        return sign(_x) * sqrt(1.0 - baseFun.calc(_x).pow(2))
    }
}

abstract class LogarithmicFun(
    protected val baseFun: LnFun = LnFun()
): SingleArgMathFun

//fun log(base: Int, x: Number) = ln(x) / ln(base)
open class LogFun(
    val base: Number = 10.0,
    baseFun: LnFun = LnFun()
): LogarithmicFun(baseFun) {
    init {
        if (base.toDouble() <= 0 || base.toDouble() == 1.0)
            throw IllegalArgumentException()
    }

    override fun calc(x: Number): Double {
        val _x = x.toDouble()
        if (_x < 0)
            return Double.NaN
        return baseFun.calc(_x) / baseFun.calc(base.toDouble())
    }
}

internal fun factorial(x: Int): Double =
    (1..x).fold(1.0) { acc, i -> acc * i.toLong() }