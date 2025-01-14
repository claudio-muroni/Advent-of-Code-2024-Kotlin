import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

private fun main() {
    val n = 139

    val lines = Path("src/main/resources/Day04Data.txt").readLines()

    var result = 0

    for (i in 1..n-1) {
        for (j in 1..n-1) {
            if (lines[i][j] == 'A') {
                val d1 = lines[i-1][j-1].toString() + lines[i+1][j+1].toString()
                val d2 = lines[i-1][j+1].toString() + lines[i+1][j-1].toString()

                if ((d1 == "MS" || d1 == "SM") && (d2 == "MS" || d2 == "SM")) {
                    result++
                }
            }
        }
    }

    println(result)
}