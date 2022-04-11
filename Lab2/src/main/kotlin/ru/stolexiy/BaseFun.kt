package ru.stolexiy

import kotlin.math.*

internal const val absoluteTolerance = 1e-7

interface SingleArgMathFun {
    fun calc(x: Number): Double
}

open class CosFun: SingleArgMathFun {
    override fun calc(x: Number): Double {
        var _x = x.toDouble()
        if (_x.isInfinite() || _x.isNaN())
            return Double.NaN
        _x = normalizeRadians(_x)
        return generateSequence(0) { it + 1 }
            .takeWhile { i ->
                factorial(2 * i).isFinite()
            }
            .map { i ->
                (-1.0).pow(i) * _x.pow(2 * i) / factorial(2 * i)
            }
            .sum()
    }
}

internal fun normalizeRadians(x: Number): Double {
    var _x = x.toDouble()
    if (abs(_x) > PI) {
        var mult = (_x / PI).toInt()
        mult += mult % 2
        _x -= PI * mult
    }
    return _x
}

open class LnFun: SingleArgMathFun {
    override fun calc(x: Number) = calc(x.toDouble())
    
    private fun calc(x: Double): Double {
        if (x <= 0 || x.isNaN())
            return Double.NaN
        else if (x == Double.POSITIVE_INFINITY)
            return Double.POSITIVE_INFINITY
        var expDegree = 0
        var _x = x

        while (_x >= 1 || _x < 0.1) {
            if (x >= 1) {
                _x /= Math.E
                expDegree++
            } else {
                _x *= Math.E
                expDegree--
            }
        }
        return expDegree + 2 * generateSequence(1) { it + 2 }.takeWhile { i ->
            (_x + 1).pow(i).isFinite() && i <= 1000 && _x.pow(i) != 0.0
        }.fold(0.0) { acc, i ->
            return@fold (acc + (_x - 1).pow(i) / (i * (_x + 1).pow(i)))
        }
    }
}