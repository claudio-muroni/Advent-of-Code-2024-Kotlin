import kotlin.io.path.Path
import kotlin.io.path.readLines

private fun main() {
    val line = Path("src/main/resources/Day09Data.txt").readLines()[0]
    //val line = "2333133121414131402"

    val line2 = mutableListOf<String>()

    for ((i, c) in line.withIndex()) {

        for (rep in 1..c.toString().toInt()) {
            if (i % 2 == 0) {
                line2 += (i/2).toString()
            } else {
                line2 += "."
            }
        }

    }

    //println(line2)

    // COPIA PER LA SECONDA PARTE
    val line3 = line2.toMutableList()

    while (true) {
        val lastElement = line2.removeLast()

        if (lastElement != ".") {
            val index = line2.indexOf(".")

            if (index > -1) {
                line2.add(index, lastElement)
                line2.removeAt(index+1)
            } else {
                line2.add(lastElement)
                break
            }

        }
    }

   //println(line2)
    
    var result = 0L
    for ((i, id) in line2.withIndex()) {
        result += i.toLong()*id.toLong()
    }

    println(result)


    // SECONDA PARTE
    var value = line3.last().toInt()+1

    while (value != 1) {

        var countV = 0
        var countP = 0
        var posFrom = -1
        var posTo = -1

        outerFor@
        for (i in line3.size-1 downTo 1) {
            val e = line3[i]
            if (e != "." && e.toInt() < value) {
                value = e.toInt()
                countV = 1
                countP = 0

                var j = i-1
                while (line3[j] != "." && line3[j].toInt() == value) {
                    countV++
                    j--
                }

                for ((i2, e2) in line3.withIndex()) {

                    if (i2 > i-countV) {
                        break@outerFor
                    }

                    if (e2 == ".") {
                        countP++
                        if (countP == countV) {
                            posFrom = i
                            posTo = i2
                            break@outerFor
                        }

                    } else {
                        countP = 0
                    }
                }
            }
        }

        if (posFrom != -1 && posTo != -1) {
            for (k in 0..countV-1) {
                line3[posFrom-k] = "."
                line3[posTo-k] = value.toString()
            }
        }
    }

    //println(line3)

    var result2 = 0L
    for ((i, id) in line3.withIndex()) {
        if (id != ".") {
            result2 += i.toLong()*id.toLong()
        }
    }

    println(result2)
}