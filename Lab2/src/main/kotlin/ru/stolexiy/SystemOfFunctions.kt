package ru.stolexiy

import kotlin.math.pow

/*
x <= 0 : (((((cos(x) - cot(x)) + (cos(x) * tan(x))) + cos(x)) * tan(x)) - ((cos(x) - sin(x)) + (tan(x) + sin(x))))
x > 0 : (((((log_3(x) / log_10(x)) ^ 2) ^ 2) + ln(x)) ^ 3)
 */
class MySystemOfFunctions(
    private val cos: CosFun = CosFun(),
    private val cot: CotFun = CotFun(cos),
    private val tan: TanFun = TanFun(cos),
    private val sin: SinFun = SinFun(cos),
    private val ln: LnFun = LnFun(),
    private val log_3: LogFun = LogFun( base = 3, baseFun = ln),
    private val log_10: LogFun = LogFun(base = 10, baseFun = ln)
): SingleArgMathFun {

    override fun calc(x: Number) = calc(x.toDouble())

    private fun calc(x: Double): Double {
        return if (x <= 0) {
            (cos.calc(x) - cot.calc(x) + cos.calc(x) * tan.calc(x) + cos.calc(x)) * tan.calc(x) -
                    (cos.calc(x) - sin.calc(x) + tan.calc(x) + sin.calc(x))
        } else {
            ((log_3.calc(x) / log_10.calc(x)).pow(2).pow(2) + ln.calc(x)).pow(3)
        }
    }
}
