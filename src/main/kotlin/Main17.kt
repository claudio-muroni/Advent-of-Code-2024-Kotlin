import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow

fun main() {
    val lines = Path("src/main/resources/Day17Data.txt").readLines()

    val a = lines[0].substringAfter(": ").toLong()
    val b = lines[1].substringAfter(": ").toLong()
    val c = lines[2].substringAfter(": ").toLong()
    val programValues = lines[4].substringAfter(": ").split(",")

    var output = exec(a, b, c, programValues)
    println("result 1: $output")

//    val min = 8.toDouble().pow(programValues.size-1).toLong()
//    val max = 8.toDouble().pow(programValues.size).toLong()
//
//    val programValuesStr = lines[4].substringAfter(": ")
//    var exp = 1
//    var a2 = min
//    while(a2 in min .. max) {
//        output = exec2(a2, b, c, programValues)
//        if (output == programValuesStr) {
//            println("result 2: $a2")
//            break
//        }
//
//        if ((output.length+1)/2 >= exp) {
//            //println(a2)
//            exp++
//            println("$a2 - $exp - $output")
//        }
//
//        //if (a2 % 100000000 == 0L) println("$a2 - $exp - $output")
//
//        a2 += 8.toDouble().pow(exp).toLong()
//    }
}

fun exec(a0: Long, b0: Long, c0: Long, programValues: List<String>): String {

    var a = a0
    var b = b0
    var c = c0

    var output = ""
    var pointer = 0

    while (pointer in programValues.indices) {

        val opcode = programValues[pointer].toInt()
        val literal = programValues[pointer+1].toInt()
        val combo = when(literal) {
            0, 1, 2, 3 -> literal
            4 -> a.toInt()
            5 -> b.toInt()
            6 -> c.toInt()
            7 -> -1
            else -> -1
        }

        when(opcode) {
            0 -> {
                a = (a / 2.toDouble().pow(combo)).toLong()
                //if (a0 == 117440L) println(a)
            }
            1 -> b = b.xor(literal.toLong())
            2 -> b = combo.toLong() % 8
            3 -> if (a != 0L) pointer = literal
            4 -> b = b.xor(c)
            5 -> {
                if (output != "") output += ","
                output += (combo % 8).toString()
            }
            6 -> b = (a / 2.toDouble().pow(combo)).toLong()
            7 -> c = (a / 2.toDouble().pow(combo)).toLong()
        }

        if (!(opcode == 3 && a != 0L)) pointer+=2
    }

    return output
}

fun exec2(a0: Long, b0: Long, c0: Long, programValues: List<String>): String {

    var a = a0
    var b = b0
    var c = c0

    var output = ""
    var pointer = 0

    var o = 0
    while (pointer in programValues.indices) {

        val opcode = programValues[pointer].toInt()
        val literal = programValues[pointer+1].toInt()
        val combo = when(literal) {
            0, 1, 2, 3 -> literal
            4 -> a.toInt()
            5 -> b.toInt()
            6 -> c.toInt()
            7 -> -1
            else -> -1
        }

        when(opcode) {
            0 -> a = (a / 2.toDouble().pow(combo)).toLong()
            1 -> b = b.xor(literal.toLong())
            2 -> b = combo.toLong() % 8
            3 -> if (a != 0L) pointer = literal
            4 -> b = b.xor(c)
            5 -> {
                if (programValues[o] != (combo % 8).toString()) {
                    break
                } else {
                    if (output != "") output += ","
                    output += (combo % 8).toString()
                    //println("$a0 - $output")
                    o++
                }
            }
            6 -> b = (a / 2.toDouble().pow(combo)).toLong()
            7 -> c = (a / 2.toDouble().pow(combo)).toLong()
        }

        if (!(opcode == 3 && a != 0L)) pointer+=2
    }

    return output
}