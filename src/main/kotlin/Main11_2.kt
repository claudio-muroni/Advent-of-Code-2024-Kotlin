import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.time.TimeSource

private var cache = hashMapOf<Pair<Long, Int>, Long>()
private var cacheUsage = -1L

fun main() {
    val line = Path("src/main/resources/Day11Data.txt").readLines()[0]
    val stoneList2 = line.split(" ").map { it.toLong() }.toLongArray()

    var result = 0L
    cache.clear()
    cacheUsage = 0

    val ts = TimeSource.Monotonic
    val markP2I = ts.markNow()
    for (stoneInit in stoneList2) {
        result += recursive(stoneInit, 75)
    }
    val markP2F = ts.markNow()

    println("\nRisultato: $result")
    println("\nTotal time: ${markP2F-markP2I}")
    println("dimensione cache: ${cache.size}")
    println("cache utilizzata $cacheUsage volte")
}

private fun recursive(stone: Long, s: Int): Long {

    if (s == 0) {
        return 1
    }

    if (Pair(stone, s) in cache) {
        cacheUsage++
        return cache.getValue(Pair(stone, s))
    }

    val str = stone.toString()
    val result: Long

    if (stone == 0L) {
        result = recursive(1L, s-1)
    }
    else if (str.length % 2 == 0) {

        val m = str.length/2
        val stoneLeft = str.substring(0..m-1).toLong()
        val stoneRight = str.substring(m).toLong()

        result = recursive(stoneLeft, s-1) + recursive(stoneRight, s-1)
    }
    else {
        result = recursive(stone*2024, s-1)
    }

    cache[Pair(stone, s)] = result
    return result
}