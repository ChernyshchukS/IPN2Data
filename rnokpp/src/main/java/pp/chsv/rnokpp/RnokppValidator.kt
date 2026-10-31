package pp.chsv.rnokpp

object RnokppValidator {

    private val weights = intArrayOf(
        -1, 5, 7, 9, 4, 6, 10, 5, 7
    )

    /**
     * Проверяет контрольную цифру РНОКПП.
     *
     * @return
     * -1 — контрольная цифра правильная;
     * 0..9 — ожидаемая контрольная цифра.
     *
     * @throws IllegalArgumentException
     * если номер не состоит ровно из 10 цифр.
     */
    fun validate(number: String): Int {

        require(
            number.length == 10 &&
                    number.all { it in '0'..'9' }
        ) {
            "РНОКПП должен содержать ровно 10 цифр"
        }

        val digits = number.map { it - '0' }

        val sum = weights.indices.sumOf { index ->
            digits[index] * weights[index]
        }

        val remainder = Math.floorMod(sum, 11)

        val expectedDigit =
            if (remainder == 10) 0 else remainder

        return if (digits[9] == expectedDigit) {
            -1
        } else {
            expectedDigit
        }
    }
}