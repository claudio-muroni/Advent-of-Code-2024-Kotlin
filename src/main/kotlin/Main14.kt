import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val lines = Path("src/main/resources/Day14Data.txt").readLines()

    val nx = 101
    val ny = 103

    var q1 = 0L
    var q2 = 0L
    var q3 = 0L
    var q4 = 0L

    //val robotPositionList = mutableListOf<Pair<Int, Int>>()

    for (line in lines) {

        var px = line.substringAfter("p=").substringBefore(",").toInt()
        var py = line.substringAfter(",").substringBefore(" ").toInt()

        val vx = line.substringAfter("v=").substringBefore(",").toInt()
        val vy = line.substringAfterLast(",").toInt()

        for (s in 1..100) {

            px += vx
            py += vy

            if (px>nx-1) px -= nx
            if (px<0) px += nx
            if (py>ny-1) py -= ny
            if (py<0) py += ny

            //println("$px,$py")
        }

        val cx = nx/2
        val cy = ny/2

        when {
            px<cx && py<cy -> q1++
            px>cx && py<cy -> q2++
            px>cx && py>cy -> q3++
            px<cx && py>cy -> q4++
        }
    }

    //println("$q1 $q2 $q3 $q4")

    val result = q1*q2*q3*q4
    println(result)
}