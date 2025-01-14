import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

private fun main() {
    val n = 139

    val lines = Path("src/main/resources/Day04Data.txt").readLines()
    var testo = Path("src/main/resources/Day04Data.txt").readText()

    var result = 0

    val regex1 = """XMAS""".toRegex()
    val regex2 = """SAMX""".toRegex()

    //ricerca orizzontale
    result += regex1.findAll(testo).toList().size
    result += regex2.findAll(testo).toList().size
    println(result)

    //ricerca verticale
    testo = ""
    for (i in 0..n)  {
        for (j in 0..n)  {
            testo += lines[j][i]
        }
        testo += "\n"
    }
    //println(testo)
    result += regex1.findAll(testo).toList().size
    result += regex2.findAll(testo).toList().size
    println(result)

    //ricerca diagonale sx->dx
    testo = ""
    for (i in n downTo 0) {
        var x=i
        var y=0
        while (x < n+1) {
            testo += lines[y][x]
            x++
            y++
        }
        testo += "\n"
    }
    for (i in 1..n) {
        var x=0
        var y=i
        while (y < n+1) {
            testo += lines[y][x]
            x++
            y++
        }
        testo += "\n"
    }
    //println(testo)
    result += regex1.findAll(testo).toList().size
    result += regex2.findAll(testo).toList().size
    println(result)

    //ricerca diagonale dx->sx
    testo = ""
    for (i in 0..n) {
        var x=i
        var y=0
        while (x > -1) {
            testo += lines[y][x]
            x--
            y++
        }
        testo += "\n"
    }
    for (i in 1..n) {
        var x=n
        var y=i
        while (y < n+1) {
            testo += lines[y][x]
            x--
            y++
        }
        testo += "\n"
    }
    //println(testo)
    result += regex1.findAll(testo).toList().size
    result += regex2.findAll(testo).toList().size
    println(result)

}