import kotlin.io.path.Path
import kotlin.io.path.readText

private fun main() {
    val testo = Path("src/main/resources/Day03Data.txt").readText()

    val regex = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
    val matchResults = regex.findAll(testo)

    var result = 0

    matchResults.forEach {
        val mul = it.value.substringAfter("(").substringBefore(")")
        //println(mul)
        val a = mul.substringBefore(",").toInt()
        val b = mul.substringAfter(",").toInt()

        result += a*b
    }
    println(result)

    var isFirstPart = true
    var result2 = 0

    testo.split("do").forEach() { part ->
        if(isFirstPart || part.startsWith("()")) {

            val matchResults2 = regex.findAll(part)
            matchResults2.forEach {
                val mul = it.value.substringAfter("(").substringBefore(")")
                //println(mul)
                val a = mul.substringBefore(",").toInt()
                val b = mul.substringAfter(",").toInt()

                result2 += a*b
            }
        }

        isFirstPart = false
    }
    println(result2)
}