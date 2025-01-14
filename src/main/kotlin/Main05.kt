import kotlin.io.path.Path
import kotlin.io.path.readLines

private fun main() {
    val rules = Path("src/main/resources/Day05Data_Rules.txt").readLines()
    val updates = Path("src/main/resources/Day05Data_Updates.txt").readLines()

    var result = 0
    var result2 = 0

    //Per ogni update
    for (updateStr in updates) {
        val updateArr = updateStr.split(",")
        var isCorrect = true

        //per ogni page eccetto l'ultima
        majorLoop@
        for (i in 0..updateArr.size-2) {
            val pageToCheck = updateArr[i]

            //per ogni regola
            for (ruleStr in rules) {
                val ruleArr = ruleStr.split("|")
                if (ruleArr[1] == pageToCheck) {

                    for (j in i+1..updateArr.size-1) {
                        if (updateArr[j] == ruleArr[0]) {
                            isCorrect = false
                            //println("$updateArr non corretto, page $pageToCheck, rule $ruleArr")

                            //Parte 2?

                            break@majorLoop
                        }
                    }
                }
            }
        }

        if (isCorrect) {
            val posizioneCentrale = ((updateArr.size-1)/2)
            result += updateArr[posizioneCentrale].toInt()
            //println("OK $updateArr, elemento centrale ${updateArr[posizioneCentrale]}")
        }
    }

    println(result)
}