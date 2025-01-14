import kotlin.io.path.Path
import kotlin.io.path.readLines

private val lines = Path("src/main/resources/Day12Data.txt").readLines()
private val yMax = lines.size-1
private val xMax = lines[0].length-1

private val listaPos = mutableListOf<Pair<Int, Int>>()

fun main() {
    listaPos.clear()
    var result = 0
    var result2 = 0

    for (y in 0..yMax) {
        for (x in 0..xMax) {

            var actualPos = y to x

            if (actualPos in listaPos) {
                continue
            }

            val c = lines[y][x]
            val areaPerim = recursive(y, x, c)
            result += areaPerim.first*areaPerim.second

            // SECONDA PARTE
            result2 += areaPerim.first*areaPerim.third
        }
    }

    println(result)
    println(result2)
}

private fun recursive(y: Int, x:Int, c:Char): Triple<Int, Int, Int> {

    listaPos.add(y to x)

    var a = 1
    var p = 0
    var angoli = 0

    val directions = arrayOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

    for (d in directions) {
        val y2 = y + d.first
        val x2 = x + d.second
        if (y2 !in 0..yMax || x2 !in 0..xMax || lines[y2][x2]!=c) {
            p++
        }
    }

    // SECONDA PARTE
    when (p) {
        4 -> angoli = 4
        3 -> angoli = 2
        else -> {
            if (p == 2) {
                if (!((x-1>=0 && x+1<=xMax && lines[y][x-1]==c && lines[y][x+1]==c) || (y-1>=0 && y+1<=yMax && lines[y-1][x]==c && lines[y+1][x]==c))) {
                    angoli = 1
                }
            }

            val diagonals = arrayOf(1 to 1, 1 to -1, -1 to -1, -1 to 1)
            for (d in diagonals) {
                val y2 = y + d.first
                val x2 = x + d.second
                if (y2 in 0..yMax && x2 in 0..xMax && lines[y2][x]==c && lines[y][x2]==c && lines[y2][x2]!=c) {
                    angoli++
                }
            }
        }
    }

    for (d in directions) {
        val y2 = y + d.first
        val x2 = x + d.second
        if (!(y2 !in 0..yMax || x2 !in 0..xMax || lines[y2][x2]!=c) && Pair(y2, x2) !in listaPos) {
            val areaPerim = recursive(y2, x2, c)
            a += areaPerim.first
            p += areaPerim.second

            // SECONDA PARTE
            angoli += areaPerim.third
        }
    }

    return Triple(a, p, angoli)
}