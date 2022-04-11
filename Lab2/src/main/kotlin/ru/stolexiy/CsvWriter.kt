package ru.stolexiy

import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.lang.IllegalArgumentException
import java.util.*

class CsvWriter(
    var func: SingleArgMathFun,
    _start: Double,
    _end: Double,
    _delta: Double
) {
    var start: Double = _start
        set(value) {
            if (value >= end) throw IllegalArgumentException()
            else field = value
        }
    var end: Double = _end
        set(value) {
            if (value <= start) throw IllegalArgumentException()
            else field = value
        }
    var delta: Double = _delta
        set(value) {
            if (value > end - start) throw IllegalArgumentException()
            else field = value
        }

    fun writeToFile(fileName: String) {
        val file = File(fileName)
        if (!file.createNewFile() || !file.canWrite())
            throw IOException()
        PrintWriter(file).use {
            it.write(generateSequence(start) { it + delta }.takeWhile { it <= end }
                .map {
                    "${String.format("%.3f", it)};${String.format("%.7f", func.calc(it))}"
                }
                .joinToString("\n")
            )
        }
    }

}

fun main() {
    CsvWriter(CosFun(), -4.0, 4.0, 0.1).writeToFile("cos-values.csv")
    CsvWriter(LnFun(), 0.01, 4.0, 0.1).writeToFile("ln-values.csv")
    CsvWriter(TanFun(), -4.0, 4.0, 0.1).writeToFile("tan-values.csv")
    CsvWriter(CotFun(), -4.0, 4.0, 0.1).writeToFile("cot-values.csv")
    CsvWriter(LogFun(base = 3), 0.01, 4.0, 0.1).writeToFile("log3-values.csv")
    CsvWriter(MySystemOfFunctions(), -4.0, 4.0, 0.1).writeToFile("system-values.csv")
}