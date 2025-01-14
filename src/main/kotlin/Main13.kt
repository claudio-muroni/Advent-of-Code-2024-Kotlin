import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val lines = Path("src/main/resources/Day13Data.txt").readLines()

    val costX = 3
    val costY = 1

    var result = 0
    var result2 = 0L

    for (i in 0..lines.size-1 step 4) {

        val a1 = lines[i].substringAfter("X+").substringBefore(",").toInt()
        val a2 = lines[i].substringAfter("Y+").toInt()

        val b1 = lines[i+1].substringAfter("X+").substringBefore(",").toInt()
        val b2 = lines[i+1].substringAfter("Y+").toInt()

        val c1 = lines[i+2].substringAfter("X=").substringBefore(",").toInt()
        val c2 = lines[i+2].substringAfter("Y=").toInt()


        outerLoop@
        for (x in 1..100) {
            for (y in 1..100) {

                if ((a1*x+b1*y==c1) && (a2*x+b2*y==c2)) {
                    result += x * costX + y * costY
                    break@outerLoop
                }

            }
        }

        val k = 10000000000000
        val c1L = c1 + k
        val c2L = c2 + k

        // CRAMER's RULE, to solve liner 2x2 systems
        val checkX = ((-b2*c1L+b1*c2L))%(a2*b1-a1*b2)==0L
        val checkY = ((-a2*c1L+a1*c2L))%(-a2*b1+a1*b2)==0L

        if (checkX && checkY) {
            val x = ((-b2*c1L+b1*c2L))/(a2*b1-a1*b2)
            val y = ((-a2*c1L+a1*c2L))/(-a2*b1+a1*b2)
            result2 += x * costX + y * costY
        }
    }

    println(result)
    println(result2)
}