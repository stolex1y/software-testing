package ru.stolexiy

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

internal const val absoluteTolerance = 1e-7

interface SingleArgMathFun {
    fun calc(x: Number): Double
}

open class CosFun: SingleArgMathFun {
    override fun calc(x: Number): Double {
        /*var _x = x.toDouble()
        if (abs(_x) > PI) {
            var mult = (_x / PI).toInt()
            mult += mult % 2
            _x -= PI * mult
        }
        return generateSequence(0) { it + 1 }
            .take(10)
            .map { k ->
                (-1.0).pow(k) * _x.pow(2 * k) / factorial(2 * k)
            }
            .sum()*/
        return 0.0
    }
}

abstract class TrigonometricFun(
    protected val baseFun: CosFun = CosFun()
): SingleArgMathFun

class SecFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    override fun calc(x: Number) = 1 / baseFun.calc(x)
}

//fun cot(x: Number) = cos(x) / sin(x)
class CotFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    override fun calc(x: Number) = baseFun.calc(x) / sin(x)

    private fun sin(x: Number) = sqrt(1 - baseFun.calc(x).pow(2))
}

//fun tan(x: Number) = sin(x) / cos(x)
class TanFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    override fun calc(x: Number) = sin(x) / baseFun.calc(x)

    private fun sin(x: Number) = sqrt(1 - baseFun.calc(x).pow(2))
}

//fun sin(x: Number) = cos(PI / 2 - x.toDouble())
class SinFun(
    baseFun: CosFun = CosFun()
): TrigonometricFun(baseFun) {
    override fun calc(x: Number) = sqrt(1 - baseFun.calc(x).pow(2))
}

fun ln(x: Number): Double {
    return 0.0
}
open class LnFunc: SingleArgMathFun {

}

fun log(a: Number, x: Number): Double {
    return 0.0
}

private fun factorial(x: Int): Long =
    (1..x).fold(1L) { acc, i -> acc * i.toLong() }

fun main() {

}
