import kotlin.io.path.Path
import kotlin.io.path.readLines

private operator fun Pair<Int, Int>.minus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first-p2.first, this.second-p2.second))
}

private operator fun Pair<Int, Int>.plus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first+p2.first, this.second+p2.second))
}

fun main() {
    val lines = Path("src/main/resources/Day15Data.txt").readLines()

    val emptyLineIndex = lines.indexOf("")
    val map = lines.subList(0,emptyLineIndex).toMutableList().map { it.toCharArray() }.toMutableList()
    val moveList = lines.subList(emptyLineIndex+1, lines.size)

    var actualPos = Pair(-1,-1)
    outerLoop@
    for (y in 0..map.size-1) {
        for (x in 0..map[0].size-1) {
            if (map[y][x] == '@') {
                actualPos = Pair(y, x)
                map[y][x] = '.'
                break@outerLoop
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

            if (map[nextPos.first][nextPos.second] == '#') continue

            if (map[nextPos.first][nextPos.second] == '.') {
                actualPos = nextPos
                continue
            }

            if (map[nextPos.first][nextPos.second] == 'O') {
                while (true) {
                    nextPos += move

                    if (map[nextPos.first][nextPos.second] == '#') {
                        break
                    }

                    if (map[nextPos.first][nextPos.second] == '.') {
                        map[nextPos.first][nextPos.second] = 'O'
                        actualPos += move
                        map[actualPos.first][actualPos.second] = '.'
                        break
                    }
                }
            }
        }
    }

    var result = 0
    for (y in 0..map.size-1) {
        for (x in 0..map[0].size-1) {
            if (map[y][x] == 'O') {
                result += 100*y + x
            }
        }
    }
    println(result)
}