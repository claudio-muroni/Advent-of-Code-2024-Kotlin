import java.math.BigInteger
import kotlin.io.path.Path
import kotlin.io.path.readLines

private var res07 = -1

private fun main() {
    val lines = Path("src/main/resources/Day07Data.txt").readLines()

    var result1 = BigInteger.ZERO

    for (line in lines) {
        val testResult = line.substringBefore(":").toBigInteger()
        val values = line.substringAfter(": ").split(" ").map { it.toBigInteger() }.toMutableList()

        res07 = 0
        var global = 0
        recursive(values.removeFirst(), testResult, values)

        if (res07 > 0) {
            result1+=testResult
        }
    }

    println("$result1")
}

private fun recursive(midResult: BigInteger, testResult: BigInteger, values: MutableList<BigInteger>) {

    if (values.size == 0) {
        if (midResult == testResult) {
            res07++
        }
    } else {
        val nextVal = values.removeFirst()
        val v1 = values.toMutableList()
        val v2 = values.toMutableList()

        recursive(midResult + nextVal, testResult, v1)
        recursive(midResult * nextVal, testResult, v2)

        //PARTE 2
        val v3 = values.toMutableList()
        recursive((midResult.toString() + nextVal.toString()).toBigInteger(), testResult, v3)
    }
}