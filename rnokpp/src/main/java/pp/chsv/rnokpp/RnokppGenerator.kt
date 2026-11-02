package pp.chsv.rnokpp

import java.time.LocalDate
import java.time.temporal.ChronoUnit

internal object RnokppGenerator {

    /**
     * Генерирование номера
     */
    fun generate(
        date: LocalDate,
        sequenceNumber: Int,
        genderDigit: Int
    ): String {

        val days = ChronoUnit.DAYS.between(Rnokpp.BASE_DATE, date)

        require(days in 0..99999) {
            "date не может быть длиннее пяти цифр"
        }

        val firstNine = days.toString().padStart(5, '0') +
                sequenceNumber.toString().padStart(3, '0') +
                genderDigit
        return firstNine + RnokppValidator.calculateControlDigit(firstNine)
    }
}
