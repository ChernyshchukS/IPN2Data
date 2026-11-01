package pp.chsv.rnokpp

import java.time.LocalDate

class Rnokpp private constructor(
    val number: String
) {

    val birthDate: LocalDate =
        RnokppDecoder.getBirthDate(number)

    val sequence: Int =
        RnokppDecoder.getSequence(number)

    val gender: Gender =
        RnokppDecoder.getGender(number)

    val controlDigit: Int =
        number.last().digitToInt()

    val expectedControlDigit: Int =
        RnokppValidator.calculateControlDigit(number)

    val isValid: Boolean =
        controlDigit == expectedControlDigit

    companion object {

        fun parse(number: String): Rnokpp {

            require(number.all { it in '0'..'9' }) {
                "Номер должен содержать только цифры"
            }

            require(number.length == 10) {
                "Номер должен содержать ровно из 10 цифр"
            }

            return Rnokpp(number)
        }
    }

    override fun toString(): String = number
}