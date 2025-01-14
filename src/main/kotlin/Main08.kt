import kotlin.io.path.Path
import kotlin.io.path.readLines

private operator fun Pair<Int, Int>.minus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first-p2.first, this.second-p2.second))
}

private operator fun Pair<Int, Int>.plus(p2: Pair<Int, Int>) : Pair<Int, Int> {
    return (Pair(this.first+p2.first, this.second+p2.second))
}

private fun main() {
    val lines = Path("src/main/resources/Day08Data.txt").readLines()

    val yMax = lines.size-1
    val xMax = lines[0].length-1

    val listaChar = mutableListOf<Char>()

    //elenco dei differenti char
    for (line in lines) {
        for (element in line) {
            if (element != '.' && element !in listaChar) {
                listaChar.add(element)
            }
        }
    }

    val listaAntiNodes = mutableListOf<Pair<Int, Int>>()

    for (c in listaChar) {

        val listaPos = mutableListOf<Pair<Int, Int>>()

        for ((y, line) in lines.withIndex()) {
            for ((x, element) in line.withIndex()) {
                if (element == c) {
                    listaPos.add(Pair(y, x))
                }
            }
        }

        //disposizione semplice
        for (p1 in listaPos) {
            for (p2 in listaPos) {

                if (p1 != p2) {

                    listaAntiNodes.add(p1)
                    listaAntiNodes.add(p2)

                    val distance = p2-p1
                    var antiNode = p2+distance

                    while(antiNode.first in 0..yMax && antiNode.second in 0..xMax) {
                        listaAntiNodes.add(antiNode)
                        antiNode += distance
                    }
                }

            }
        }
    }

    println(listaAntiNodes.distinct().size)

}