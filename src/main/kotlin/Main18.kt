import kotlin.io.path.Path
import kotlin.io.path.readLines

private operator fun Pair<Int, Int>.minus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first-p2.first, this.second-p2.second))
}

private operator fun Pair<Int, Int>.plus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first+p2.first, this.second+p2.second))
}

private val byteSet = mutableSetOf<Pair<Int, Int>>()
private var stepMin = -1

private val xMax = 70
private val yMax = 70

private val cache = hashMapOf<Pair<Int,Int>,Int>()

private var found = false
private val cache2 = mutableSetOf<Pair<Int,Int>>()

private fun main() {

    val lines = Path("src/main/resources/Day18Data.txt").readLines()

    byteSet.clear()
    for ((i, line) in lines.withIndex()) {
        if (i == 1024) break
        val byte = line.substringAfter(",").toInt() to line.substringBefore(",").toInt()
        byteSet.add(byte)
    }

    stepMin = -1
    cache.clear()
    var path = mutableSetOf<Pair<Int, Int>>()
    recursive(0 to 0, path)

    println("result 1: $stepMin")

    byteSet.clear()
    for ((i, line) in lines.withIndex()) {

        val byte = line.substringAfter(",").toInt() to line.substringBefore(",").toInt()
        byteSet.add(byte)

        if (i >= 1024) {
            stepMin=-1
            cache2.clear()
            found=false
            recursive2(0 to 0)
            if (stepMin == -1) {
                println("result 2: ${byte.second},${byte.first}")
                break
            }
        }
    }
}

private fun recursive(pos: Pair<Int,Int>, path: MutableSet<Pair<Int, Int>>) {

    if (pos in byteSet || pos in path || pos.first !in 0..yMax || pos.second !in 0..xMax) return

    if (cache.contains(pos) && path.size >= cache.getValue(pos)) return else cache[pos]=path.size

    val newPath = path.toMutableSet()
    newPath.add(pos)

    if (stepMin >= 0 && path.size >= stepMin) return

    if (pos == 70 to 70) {
        //println(path.size)
        stepMin = path.size
        return
    }

    recursive(pos.first+0 to pos.second+1, newPath)
    recursive(pos.first+1 to pos.second+0, newPath)
    recursive(pos.first+0 to pos.second-1, newPath)
    recursive(pos.first-1 to pos.second+0, newPath)
}

private fun recursive2(pos: Pair<Int,Int>,) {

    if (found) return

    if (pos in byteSet || pos.first !in 0..yMax || pos.second !in 0..xMax) return

    if (cache2.contains(pos)) return else cache2.add(pos)

    if (pos == 70 to 70) {
        //println(path.size)
        stepMin = 1
        found = true
        return
    }

    recursive2(pos.first+0 to pos.second+1)
    recursive2(pos.first+1 to pos.second+0)
    recursive2(pos.first+0 to pos.second-1)
    recursive2(pos.first-1 to pos.second+0)
}