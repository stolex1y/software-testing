package ru.stolexiy

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.cos
import kotlin.math.pow

private fun factorial(x: Int): BigInteger =
    (1..x).fold(BigInteger.valueOf(1L)) { acc, i -> acc * BigInteger.valueOf(i.toLong()) }

fun cos(x: Number): Double {
    val cosX: Double = x.toDouble().let { x ->
        generateSequence(0) { it + 1 }
            .take(800)
            .map { k ->
                println("${k}) " + BigDecimal.valueOf((-1.0).pow(k)) * BigDecimal.valueOf(x).pow(2 * k) / factorial(2 * k).toBigDecimal())
                println("fact(${2 * k}) = ${factorial(2 * k)}")
                println("x.pow(${2 * k}) = ${x.pow(2 * k)}")
                println("-1.pow(${k}) = ${(-1.0).pow(k)}")
                BigDecimal.valueOf((-1.0).pow(k)) * BigDecimal.valueOf(x).pow(2 * k) / factorial(2 * k).toBigDecimal()
            }
            .sumOf { it }
            .toDouble()
    }
    println("cos(${x}) = $cosX")
    return cosX
}

fun sec(x: Number) = 1 / cos(x)

