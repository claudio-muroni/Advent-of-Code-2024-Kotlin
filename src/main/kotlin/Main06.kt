import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.time.TimeSource

private fun main() {
    val ts = TimeSource.Monotonic

    val lines = Path("src/main/resources/Day06Data.txt").readLines()

    var actualPos = Pair(-1, -1)
    val positionList = mutableListOf<Pair<Int,Int>>()

    //trova la posizione iniziale
    outerLoop@
    for ((y, line) in lines.withIndex()) {
        for ((x, element) in line.withIndex()) {
            if (element == '^') {
                //println("posizione iniziale: $y, $x")
                actualPos = Pair(y, x)
                break@outerLoop
            }
        }
    }

    val yMax = lines.size-1
    val xMax = lines[0].length-1
    //println("dimensioni mappa: $yMax, $xMax")

    var direction = Pair(-1, 0)
    //-1 0 up
    //0 1 right
    //1 0 down
    //0 -1 left

    val mark1_1 = ts.markNow()
    //traccia percorso guardia
    outerLoop@
    while((actualPos.first in 0..yMax) && (actualPos.second in 0..xMax)) {

        positionList.add(actualPos)

        var nextPos = Pair(actualPos.first+direction.first, actualPos.second+direction.second)
        if (!((nextPos.first in 0..yMax) && (nextPos.second in 0..xMax))) {
            break@outerLoop
        }
        //println("nextPos: $nextPos")

        while (lines[nextPos.first][nextPos.second] == '#') {
            //aggiorna direzione
            when (direction) {
                Pair(-1, 0) -> direction=Pair(0, 1)
                Pair(0, 1) -> direction=Pair(1, 0)
                Pair(1, 0) -> direction=Pair(0, -1)
                Pair(0, -1) -> direction=Pair(-1, 0)
            }
            //aggiorna nextPos
            nextPos = Pair(actualPos.first+direction.first, actualPos.second+direction.second)

            if (!((nextPos.first in 0..yMax) && (nextPos.second in 0..xMax))) {
                break@outerLoop
            }
        }

        //fai un passo
        actualPos = nextPos
    }
    val mark1_2 = ts.markNow()

    //Risultato 1
    println(positionList.distinct().size)
    //Performance
    println("${mark1_2 - mark1_1}")

    // *** SECONDA PARTE ***
    val positionListLoop = mutableListOf<Pair<Int,Int>>()

    val mark2_1 = ts.markNow()
    for((i, pos) in positionList.withIndex()) {
        if(i==0){
            continue
        }

        val lines2 = lines.toMutableList().map{ it.toCharArray() }
        lines2[pos.first][pos.second] = '#'

        var actualPos2 = Triple(positionList[0].first, positionList[0].second, 'U')
        val positionList2 = mutableListOf<Triple<Int,Int,Char>>()

        var direction2 = Triple(-1, 0, 'U')

        //traccia percorso guardia
        outerLoop@
        while((actualPos2.first in 0..yMax) && (actualPos2.second in 0..xMax)) {

            //verifica loop
            if (actualPos2 !in positionList2) {
                positionList2.add(actualPos2)
            } else {
                //println("Trovato loop, pos: $pos")
                positionListLoop.add(pos)
                break@outerLoop
            }

            var nextPos2 = Triple(actualPos2.first+direction2.first, actualPos2.second+direction2.second, direction2.third)
            if (!((nextPos2.first in 0..yMax) && (nextPos2.second in 0..xMax))) {
                break@outerLoop
            }
            //println("nextPos: $nextPos")

            while (lines2[nextPos2.first][nextPos2.second] == '#') {
                //aggiorna direzione
                when (direction2) {
                    Triple(-1, 0, 'U') -> direction2=Triple(0, 1, 'R')
                    Triple(0, 1, 'R') -> direction2=Triple(1, 0, 'D')
                    Triple(1, 0, 'D') -> direction2=Triple(0, -1, 'L')
                    Triple(0, -1, 'L') -> direction2=Triple(-1, 0, 'U')
                }
                //aggiorna nextPos
                nextPos2 = Triple(actualPos2.first+direction2.first, actualPos2.second+direction2.second, direction2.third)

                if (!((nextPos2.first in 0..yMax) && (nextPos2.second in 0..xMax))) {
                    break@outerLoop
                }
            }

            //fai un passo
            actualPos2 = nextPos2
        }

    }
    val mark2_2 = ts.markNow()

    //Risultato 2
    println(positionListLoop.distinct().size)
    //Performance
    println("${mark2_2 - mark2_1}")

}