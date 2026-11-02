package pp.chsv.rnokpp

import java.time.LocalDate

class Rnokpp private constructor(
    val number: String
) {

    val birthDate: LocalDate =
        RnokppDecoder.getBirthDate(number)

    val sequence: Int =
        RnokppDecoder.getSequence(number)

    val sequenceGender: Int =
        RnokppDecoder.getSequenceGender(number)

    val gender: Gender =
        RnokppDecoder.getGender(number)

    val controlDigit: Int =
        number.last().digitToInt()

    val expectedControlDigit: Int =
        RnokppValidator.calculateControlDigit(number)

    val isValid: Boolean =
        controlDigit == expectedControlDigit

    companion object {

        val BASE_DATE: LocalDate =
            LocalDate.of(1899, 12, 31)

        val MAX_DATE: LocalDate =
            LocalDate.of(2173, 10, 14)

        fun parse(number: String): Rnokpp {

            require(number.all { it in '0'..'9' }) {
                "Номер должен содержать только цифры"
            }

            require(number.length == 10) {
                "Номер должен содержать ровно из 10 цифр"
            }

            return Rnokpp(number)
        }

        fun tryParse(number: String): Rnokpp? {
            if (!number.all { it in '0'..'9' })
                return null
            if (number.length != 10)
                return null
            return Rnokpp(number)
        }

        fun generate(
            date: LocalDate,
            sequenceNumber: Int,
            genderDigit: Int
        ): Rnokpp {

            // Проверка граничных условий
            require(date in BASE_DATE..MAX_DATE) {
                "date должна быть в диапазоне от $BASE_DATE до $MAX_DATE"
            }

            require(sequenceNumber in 0..999) {
                "sequenceNumber должен быть от 0 до 999"
            }

            require(genderDigit in 0..9) {
                "genderDigit должен быть от 0 до 9"
            }

            // Реализация генерации
            val number = RnokppGenerator.generate(
                date = date,
                sequenceNumber = sequenceNumber,
                genderDigit = genderDigit
            )

            return parse(number)
        }

        fun tryGenerate(
            date: LocalDate,
            sequenceNumber: Int,
            genderDigit: Int
        ): Rnokpp? {

            // Проверка граничных условий
            if (date !in BASE_DATE..MAX_DATE) {
                return null
            }

            if (sequenceNumber !in 0..999) {
                return null
            }

            if (genderDigit !in 0..9) {
                return null
            }

            // Реализация генерации
            val number = RnokppGenerator.generate(
                date = date,
                sequenceNumber = sequenceNumber,
                genderDigit = genderDigit
            )

            return parse(number)
        }

    }

    override fun toString(): String = number
}