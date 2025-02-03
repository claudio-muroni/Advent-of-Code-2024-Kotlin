import kotlin.io.path.Path
import kotlin.io.path.readLines

private operator fun Pair<Int, Int>.minus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first-p2.first, this.second-p2.second))
}

private operator fun Pair<Int, Int>.plus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first+p2.first, this.second+p2.second))
}

private val lines = Path("src/main/resources/Day20Data.txt").readLines()
private var end = -1 to -1

private val posHash = hashMapOf<Pair<Int,Int>,Int>()
private val cheatSet2 = mutableSetOf<Pair<Int,Int>>()
private val cheatMap2bis = mutableMapOf<Pair<Int,Int>,Int>()
private val cache2 = hashSetOf<Pair<Int,Int>>()
private val cache2bis = hashSetOf<Pair<Pair<Int,Int>,Int>>()

fun main() {

    var start = -1 to -1

    var standard = 0

    for (y in 0..<lines.size) {
        for (x in 0..<lines[0].length) {
            val c = lines[y][x]
            if (c == 'S') start = y to x
            if (c == 'E') end = y to x
            if (c != '#') standard++
        }
    }
    //println(standard)


    var i = 0

    var pos = start
    var precPos = start
    while (true) {
        posHash[pos] = i
        i++

        if (pos == end) break

        val posList = listOf(
            pos.first to pos.second+1,
            pos.first to pos.second-1,
            pos.first+1 to pos.second,
            pos.first-1 to pos.second
        )

        for (newPos in posList) {
            if (newPos != precPos && lines[newPos.first][newPos.second] != '#') {

                precPos = pos
                pos = newPos
                break
            }
        }
    }
    //println(posHash)

    pos = start
    precPos = start
    val cheatList = mutableListOf<Int>()
    var result2 = 0
    while (true) {
        if (pos == end) break

        val dirList = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0
        )

        val backPos = pos
        val backPrecPos = precPos

        for (dir in dirList) {
            var newPos = backPos + dir
            if (newPos != backPrecPos && lines[newPos.first][newPos.second] != '#') {
                precPos = pos
                pos = newPos
            }
            if (newPos != backPrecPos && newPos.first in 1..<lines.size-1 && newPos.second in 1..<lines[0].length-1 && lines[newPos.first][newPos.second] == '#') {
                newPos += dir
                if (lines[newPos.first][newPos.second] != '#') {
                    val cheat = posHash.getValue(newPos) - posHash.getValue(backPos)
                    if (cheat-2>=100) {
                        cheatList.add(cheat-2)
                    }
                }
            }
        }

        // PART 2
        cheatSet2.clear()
        //cache2.clear()
        cache2bis.clear()
        recursive(backPos, backPos, 0)
        result2 += cheatSet2.size
        //println(pos)
    }

    println("Result 1: ${cheatList.size}")
    println("Result 2: $result2")
}

private fun recursive(pos0: Pair<Int,Int>, pos: Pair<Int,Int>, step: Int) {

    if (step == 1 && lines[pos.first][pos.second] != '#') return

    if (step > 20 || pos.first !in 0..<lines.size || pos.second !in 0..<lines[0].length ) return

    //if (pos in cache2) return else cache2.add(pos)
    if (pos to step in cache2bis) return else cache2bis.add(pos to step)

    if (step != 0 && lines[pos.first][pos.second] != '#') {
        val cheat = posHash.getValue(pos) - posHash.getValue(pos0)
        if (cheat-step >= 72) {
            cheatSet2.add(pos)
            //println("$pos0 $pos")
        }
        return
    }

    val dirList = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0
    )

    for (dir in dirList) {
        val newPos = pos + dir
        recursive(pos0, newPos, step+1)
    }

}