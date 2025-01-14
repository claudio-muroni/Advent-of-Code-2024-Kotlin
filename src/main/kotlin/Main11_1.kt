import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.time.TimeSource

private fun main() {
    val line = Path("src/main/resources/Day11Data.txt").readLines()[0]
    //val line = "125 17"

    val stoneList = line.split(" ").toMutableList()
    val stoneList2 = stoneList.toMutableList()

    val ts = TimeSource.Monotonic

    val markP1I = ts.markNow()
    for (rep in 1..25) {

        var i = 0

        while (true) {

            if (i == stoneList.size) {
                break
            }

            val stone = stoneList[i]

            // RULE 1
            if (stone == "0") {
                stoneList[i] = "1"
                i += 1
                continue
            }

            // RULE 2
            if (stone.length % 2 == 0) {
                val stoneLeft = stone.substring(0..(stone.length/2)-1).toLong()
                val stoneRight = stone.substring(stone.length/2).toLong()

                stoneList.removeAt(i)
                stoneList.add(i, stoneRight.toString())
                stoneList.add(i, stoneLeft.toString())

                i += 2
                continue
            }

            // RULE ELSE
            stoneList[i] = (stone.toLong()*2024).toString()
            i++

        }

        println(rep)

    }
    val markP1F = ts.markNow()

    println("result: ${stoneList.size} - time: ${markP1F-markP1I}")
}