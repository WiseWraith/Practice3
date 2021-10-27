sealed class Result<T>
class Success<T>(val data: T) : Result<T>()
class Error<T>(val errorMessage: String) : Result<T>()

enum class Operation {
    SORT_ASC { override fun <T : Comparable<T>> invoke(List: List<T>) = List.sorted() },
    SORT_DESC { override fun <T : Comparable<T>> invoke(List: List<T>) = List.sortedDescending() },
    SHUFFLE { override fun <T : Comparable<T>> invoke(List: List<T>) = List.shuffled() };
    abstract operator fun <T : Comparable<T>> invoke(list: List<T>): List<T>
}

fun <T : Comparable<T>> List<T>.operate(operation: Operation) =
    if (this.isNotEmpty()) { when (operation) {
            Operation.SORT_ASC -> Success(Operation.SORT_ASC(this))
            Operation.SORT_DESC -> Success(Operation.SORT_DESC(this))
            Operation.SHUFFLE -> Success(Operation.SHUFFLE(this))
        }
    } else Error("List is empty")

fun generateStrings(stringLength: Int, length: Int): List<String> {
    val allowedChars = ('A'..'Z') + ('a'..'z')
    val randStringList = mutableListOf<String>()
    for (i in 1..length) randStringList.add((1..stringLength)
        .map { allowedChars.random() }
        .joinToString(""))
    return randStringList
}

fun generateInt(length: Int) = (-1000..1000).shuffled().take(length)

fun <T : Comparable<T>> Result<List<T>>.draw() {
    if (this is Error) println(this.errorMessage)
    else if (this is Success) println(this.data)
}

fun main() {
    generateStrings(6, 8).operate(Operation.SORT_ASC).draw()
    generateStrings(6, 8).operate(Operation.SORT_DESC).draw()
    generateStrings(6, 8).operate(Operation.SHUFFLE).draw()
    generateInt(10).operate(Operation.SORT_ASC).draw()
    generateInt(10).operate(Operation.SORT_DESC).draw()
    generateInt(10).operate(Operation.SHUFFLE).draw()
}