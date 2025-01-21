import kotlin.io.path.Path
import kotlin.io.path.readLines

private operator fun Pair<Int, Int>.minus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first-p2.first, this.second-p2.second))
}

private operator fun Pair<Int, Int>.plus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first+p2.first, this.second+p2.second))
}

private var minPoints = 0L
private val lines = Path("src/main/resources/Day16Data.txt").readLines()
private val end = Pair(1, lines[1].length-2)

private val cache2 = hashMapOf<Pair<Pair<Int,Int>,Pair<Int,Int>>, Long>()
private val cache = hashMapOf<Pair<Int,Int>, Long>()
private val bestTiles = mutableSetOf<Pair<Int, Int>>()

fun main() {

    val actualPos = Pair(lines.size-2, 1)
    val actualDir = 0 to 1

    minPoints = 0L
    cache.clear()
    cache2.clear()
    val path = mutableSetOf<Pair<Int, Int>>()
    recursive(actualPos, actualDir, 0L, path)

    println("Result 1: $minPoints")
    println("Result 2: ${bestTiles.size}")

}

private fun recursive(pos: Pair<Int,Int>, dir: Pair<Int,Int>, points: Long, path: MutableSet<Pair<Int, Int>>) {

    val newPath = path.toMutableSet()
    newPath.add(pos)

    if (pos == end) {
        if (minPoints==0L || points<minPoints) {
            bestTiles.clear()
            bestTiles.addAll(newPath)
            minPoints=points
        }
        if (points == minPoints) {
            bestTiles.addAll(newPath)
        }
        return
    }

    if (cache2.contains(pos to dir) && cache2.getValue(pos to dir) < points) return else cache2[pos to dir] = points

    if (cache.contains(pos) && cache.getValue(pos) < points) {
        if (cache.getValue(pos) < points-1000)
            return
    } else cache[pos] = points

    var newPoints = points + 1
    if (minPoints != 0L && newPoints > minPoints) return

    var nextPos = pos + dir
    if ( lines[nextPos.first][nextPos.second] != '#') recursive(pos + dir, dir, newPoints, newPath)

    newPoints = points + 1000 + 1
    if (minPoints != 0L && newPoints > minPoints) return

    var newDir: Pair<Int, Int>

    if (dir == 0 to 1 || dir == 0 to -1) {
        newDir = 1 to 0
        nextPos = pos + newDir
        if (lines[nextPos.first][nextPos.second] != '#') recursive(nextPos, newDir, newPoints, newPath)

        newDir = -1 to 0
        nextPos = pos + newDir
        if (lines[nextPos.first][nextPos.second] != '#') recursive(nextPos, newDir, newPoints, newPath)
    }

    if (dir == 1 to 0 || dir == -1 to 0) {
        newDir = 0 to 1
        nextPos = pos + newDir
        if (lines[nextPos.first][nextPos.second] != '#') recursive(nextPos, newDir, newPoints, newPath)

        newDir = 0 to -1
        nextPos = pos + newDir
        if (lines[nextPos.first][nextPos.second] != '#') recursive(nextPos, newDir, newPoints, newPath)
    }
}