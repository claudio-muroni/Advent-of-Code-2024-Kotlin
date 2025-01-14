import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

private fun main() {
    val lista = Path("src/main/resources/Day02Data.txt").readLines()
    var countSafe = 0

    lista.forEach() {
        val report = it.split(" ").toMutableList()
        if (isReportSafe(report)) {
            countSafe++
        } else {
            for (i in 0..report.size-1) {
                val report2 = report.toMutableList()
                report2.removeAt(i)
                if (isReportSafe(report2)) {
                    countSafe++
                    break
                }
            }
        }
    }

    println(countSafe)

}

fun isReportSafe(report: MutableList<String>) : Boolean {
    var isSafe = true

    val isCrescente = report[1].toInt() > report[0].toInt()
    for (i in 1..report.size-1) {
        if ((report[i].toInt() > report[i-1].toInt()) != isCrescente ) {
            isSafe = false
            break
        }

        val step = abs(report[i].toInt() - report[i-1].toInt())
        if (step !in 1..3) {
            isSafe = false
            break
        }
    }

    return isSafe
}