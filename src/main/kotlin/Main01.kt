import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

private fun main() {

    val lista = Path("src/main/resources/Day01Data.txt").readLines()
    var distanza = 0
    var similarity = 0

    val lista1 = mutableListOf<Int>()
    val lista2 = mutableListOf<Int>()

    lista.forEach() {
        val primo = it.substringBefore(" ").toInt()
        val secondo = it.substringAfterLast(" ").toInt()

        lista1.add(primo)
        lista2.add(secondo)
    }

    lista1.sort()
    lista2.sort()

    for (i in 0..999) {
        distanza += abs(lista1[i] - lista2[i])
    }
    println(distanza)

    lista1.forEach() { num1 ->
        similarity += num1 * lista2.count{it == num1}
    }
    println(similarity)
}