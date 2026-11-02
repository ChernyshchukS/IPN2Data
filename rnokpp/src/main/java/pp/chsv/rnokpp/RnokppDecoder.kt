package pp.chsv.rnokpp

import java.time.LocalDate

internal object RnokppDecoder {


    /**
     * Извлечение даты рождения.
     */
    fun getBirthDate(number: String): LocalDate {

        val days = number.substring(0, 5).toLong()

        return Rnokpp.BASE_DATE.plusDays(days)
    }

    /**
     * Извлечение трёхзначной порядковой части.
     */
    fun getSequence(number: String): Int {

        return number.substring(5, 8).toInt()
    }

    /**
     * Извлечение четырёхзначной порядковой части.
     */
    fun getSequenceGender(number: String): Int {

        return number.substring(5, 9).toInt()
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