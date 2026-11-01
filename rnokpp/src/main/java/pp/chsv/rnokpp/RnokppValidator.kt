package pp.chsv.rnokpp

internal object RnokppValidator {

    private val weights = intArrayOf(
        -1, 5, 7, 9, 4, 6, 10, 5, 7
    )

    /**
     * Вычисляет правильную контрольную цифру.
     *
     * Предполагается, что формат номера уже проверен.
     */
    fun calculateControlDigit(number: String): Int {

        val sum = weights.indices.sumOf { index ->
            number[index].digitToInt() * weights[index]
        }

        val remainder = Math.floorMod(sum, 11)

        return if (remainder == 10) 0 else remainder
    }
}