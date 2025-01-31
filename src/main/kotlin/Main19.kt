import kotlin.io.path.Path
import kotlin.io.path.readLines

private val lines = Path("src/main/resources/Day19Data.txt").readLines()
private val available = lines[0].split(", ")
private val patterns = mutableListOf<String>()
private var found = false
private var possible = 0

private val cache = hashSetOf<String>()
private val cache2 = hashMapOf<String, Long>()

private fun main() {

    patterns.clear()
    for (i in 2..<lines.size) {
        patterns.add(lines[i])
    }

    val possiblePat = mutableListOf<String>()
    possible = 0
    for (pattern in patterns) {
        //println(pattern)
        found = false
        cache.clear()
        recursive(pattern)

        if (found) possiblePat.add(pattern)
    }

    println("Result 1: $possible")

    var result = 0L
    cache2.clear()
    for (pattern in possiblePat) {

        val poss = recursive2(pattern)
        result += poss
    }

    println("Result 2: $result")
}

private fun recursive(pattern: String) {

    if (pattern in cache) return else cache.add(pattern)

    for (tow in available) {
        if (found) return

        if (tow.length <= pattern.length && pattern.substring(tow.indices) == tow) {

            if (tow.length == pattern.length) {
                possible++
                found = true
                break
            } else {
                recursive(pattern.substring(tow.length))
            }
        }
    }
}

private fun recursive2(pattern: String): Long {

    if (pattern in cache2) return cache2.getValue(pattern)

    var points = 0L
    for (tow in available) {

        if (tow.length <= pattern.length && pattern.substring(tow.indices) == tow) {

            if (tow.length == pattern.length) {
                points += 1
            } else {
                val progr = recursive2(pattern.substring(tow.length))
                points += progr
            }
        }
    }

    cache2[pattern] = points

    return points
}