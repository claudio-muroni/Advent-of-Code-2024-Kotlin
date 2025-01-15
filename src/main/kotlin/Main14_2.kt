import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val lines = Path("src/main/resources/Day14Data.txt").readLines()

    val nx = 101
    val ny = 103
    File("src/main/resources/Day14Result.txt").writeText("")

    val vList = mutableListOf<Pair<Int,Int>>()
    val pList = mutableListOf<Pair<Int,Int>>()

    for (line in lines) {
        val vx = line.substringAfter("v=").substringBefore(",").toInt()
        val vy = line.substringAfterLast(",").toInt()

        vList.add(Pair(vx,vy))

        val px = line.substringAfter("p=").substringBefore(",").toInt()
        val py = line.substringAfter(",").substringBefore(" ").toInt()

        pList.add(Pair(px,py))
    }

    for (n in 1..10000) {
        val robotMap = Array(103) { Array<Char>(101) { ' ' } }

        for (i in 0..pList.size-1) {

            var px2 = pList[i].first + vList[i].first
            var py2 = pList[i].second + vList[i].second

            if (px2>nx-1) px2 -= nx
            if (px2<0) px2 += nx
            if (py2>ny-1) py2 -= ny
            if (py2<0) py2 += ny

            val newPos = Pair(px2, py2)
            pList[i] = newPos

            robotMap[py2][px2] = '#'
        }

        var mapText = ""
        for (j in 0..ny-1) {
            mapText += robotMap[j].contentToString()+"\n"
        }

        if (mapText.contains("#, #, #, #, #")) {
            File("src/main/resources/Day14Result.txt").appendText("*** $n ***\n")
            File("src/main/resources/Day14Result.txt").appendText(mapText+"\n")
        }
    }

    println("check src/main/resources/Day14Result.txt and look for the first christmas tree appearance")
}