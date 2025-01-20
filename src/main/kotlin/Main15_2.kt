import kotlin.io.path.Path
import kotlin.io.path.readLines

private operator fun Pair<Int, Int>.minus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first-p2.first, this.second-p2.second))
}

private operator fun Pair<Int, Int>.plus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first+p2.first, this.second+p2.second))
}

private val map2 = mutableListOf<MutableList<Char>>()

private var boxList = mutableListOf<Pair<Int,Int>>()

fun main() {
    val lines = Path("src/main/resources/Day15Data.txt").readLines()

    val emptyLineIndex = lines.indexOf("")
    val map = lines.subList(0,emptyLineIndex).toMutableList().map { it.toCharArray() }.toMutableList()
    val moveList = lines.subList(emptyLineIndex+1, lines.size)

    map2.clear()
    for (i in 1..map.size) {
        map2.add(emptyList<Char>().toMutableList())
    }

    var actualPos = Pair(-1, -1)
    for (y in 0..map.size-1) {
        for (x in 0..map[0].size-1) {

            when (map[y][x]) {
                '#' -> {map2[y].add('#'); map2[y].add('#');}
                'O' -> {map2[y].add('['); map2[y].add(']');}
                '.' -> {map2[y].add('.'); map2[y].add('.');}
                '@' -> {
                    actualPos = Pair(y, map2[y].size)
                    map2[y].add('.'); map2[y].add('.');
                }
            }
        }
    }

    for (line in moveList) {
        for (c in line) {
            if (c=='\n') continue

            val move = when {
                c=='<' -> Pair(0, -1)
                c=='^' -> Pair(-1, 0)
                c=='>' -> Pair(0, 1)
                c=='v' -> Pair(1, 0)
                else -> Pair(0,0)
            }

            var nextPos = actualPos + move

            if (map2[nextPos.first][nextPos.second] == '#') continue

            if (map2[nextPos.first][nextPos.second] == '.') { actualPos = nextPos; continue; }

            if (c=='>' || c=='<') {
                while (true) {
                    nextPos += move

                    if (map2[nextPos.first][nextPos.second] == '#') break

                    if (map2[nextPos.first][nextPos.second] == '.') {

                        var par = if (c=='>') ']' else '['

                        while (nextPos != actualPos+move) {
                            map2[nextPos.first][nextPos.second] = par
                            nextPos -= move
                            par = if (par=='[') ']' else '['
                        }

                        actualPos = nextPos
                        map2[actualPos.first][actualPos.second] = '.'

                        break
                    }
                }
                continue
            }

            if (c=='^' || c=='v') {
                boxList.clear()
                if (pushVertical(nextPos, move)) {
                    var yMin = map2.size
                    var yMax = -1
                    for (pos in boxList) {
                        val y = pos.first; if (y<yMin) yMin=y; if (y>yMax) yMax=y;
                    }

                    val range = if (c=='^') yMin..yMax else yMax downTo yMin
                    val ordBoxList = mutableListOf<Pair<Int,Int>>()
                    for (i in range) {
                        for (pos in boxList) {
                            if (pos.first == i) ordBoxList.add(pos)
                        }
                    }

                    for (pos in ordBoxList) {
                        val next = pos + move
                        val actualChar = map2[pos.first][pos.second]
                        map2[next.first][next.second] = actualChar
                        map2[pos.first][pos.second] = '.'
                    }
                    actualPos = nextPos
                }
            }
        }
    }

    var result2 = 0L
    for (y in 0..map2.size-1) {
        for (x in 0..map2[0].size-1) {
            if (map2[y][x] == '[') {
                result2 += 100*y.toLong() + x.toLong()
            }
        }
    }
    println(result2)

}

fun pushVertical(actualPos: Pair<Int,Int>, move: Pair<Int,Int>): Boolean {

    val actualChar = map2[actualPos.first][actualPos.second]

    if (actualChar == '.') return true
    if (actualChar == '#') return false
    if (boxList.contains(actualPos)) return true

    val move2 = if (actualChar=='[') 0 to 1 else 0 to -1

    boxList.add(actualPos)
    boxList.add(actualPos + move2)

    val nextPos = actualPos + move

    return (pushVertical(nextPos, move) && pushVertical(nextPos + move2, move))
}