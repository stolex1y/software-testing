package ru.stolexiy

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.tan

fun cos(_x: Number): Double {
    var x: Double = _x.toDouble()
    if (abs(x) > PI) {
        var mult = (x / PI).toInt()
        mult += mult % 2
        x -= PI * mult
    }
    return generateSequence(0) { it + 1 }
            .take(10)
            .map { k ->
                (-1.0).pow(k) * x.pow(2 * k) / factorial(2 * k)
            }
            .sum()
}

fun sec(x: Number) = 1 / cos(x)

private fun factorial(x: Int): Long =
    (1..x).fold(1L) { acc, i -> acc * i.toLong() }

