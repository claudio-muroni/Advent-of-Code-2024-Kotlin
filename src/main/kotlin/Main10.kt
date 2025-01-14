import kotlin.io.path.Path
import kotlin.io.path.readLines

private val lines10 = Path("src/main/resources/Day10Data.txt").readLines()
private val yMax10 = lines10.size-1
private val xMax10 = lines10[0].length-1

private val res10List = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
private var res10p1 = -1
private var res10p2 = -1

private fun main() {

    res10p1 = 0
    res10p2 = 0
    res10List.clear()

    val zeroList = mutableListOf<Pair<Int, Int>>()

    for ((y, line) in lines10.withIndex()) {
        for ((x, pos) in line.withIndex()) {
            if (pos == '0') {
                zeroList.add(Pair(y, x))
            }
        }
    }

    //println(zeroList)

    for (startPos in zeroList) {
        recursive(0, startPos.first, startPos.second, startPos)

    }

    println(res10p1)
    println(res10p2)

}

private fun recursive(midPos: Int, y: Int, x: Int, startPos: Pair<Int, Int>) {

    if (midPos == 9) {
        val path = Pair(startPos, Pair(y, x))
        res10p2++
        if (path !in res10List) {
            res10List.add(path)
            res10p1++
        }
        return
    }

    // Right
    if (x+1<=xMax10 && lines10[y][x+1].toString() == (midPos+1).toString()) {
        recursive(midPos+1, y, x+1, startPos)
    }

    // Down
    if (y+1<=yMax10 && lines10[y+1][x].toString() == (midPos+1).toString()) {
        recursive(midPos+1, y+1, x, startPos)
    }

    // Left
    if (x-1>=0 && lines10[y][x-1].toString() == (midPos+1).toString()) {
        recursive(midPos+1, y, x-1, startPos)
    }

    // Up
    if (y-1>=0 && lines10[y-1][x].toString() == (midPos+1).toString()) {
        recursive(midPos+1, y-1, x, startPos)
    }

}