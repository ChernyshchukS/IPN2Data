package pp.chsv.rnokpp

import java.time.LocalDate

internal object RnokppDecoder {

    private val baseDate = LocalDate.of(1899, 12, 31)

    /**
     * Извлечение даты рождения.
     */
    fun getBirthDate(number: String): LocalDate {

        val days = number.substring(0, 5).toLong()

        return baseDate.plusDays(days)
    }

    /**
     * Извлечение трёхзначной порядковой части.
     */
    fun getSequence(number: String): Int {

        return number.substring(5, 8).toInt()
    }

    /**
     * Определение пола.
     *
     * Девятая цифра:
     * нечётная — мужчина;
     * чётная — женщина.
     */
    fun getGender(number: String): Gender {

        val digit = number[8].digitToInt()

        return if (digit % 2 == 0) {
            Gender.FEMALE
        } else {
            Gender.MALE
        }
    }
}