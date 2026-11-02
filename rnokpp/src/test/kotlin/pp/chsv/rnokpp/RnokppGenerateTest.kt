package pp.chsv.rnokpp

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RnokppGenerateTest {

    private val testDate = LocalDate.of(1990, 5, 15)

    // ---------------------------------------------------------
    // Корректная генерация
    // ---------------------------------------------------------

    @Test
    fun `generate valid number`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(10, rnokpp.number.length)
        assertTrue(rnokpp.number.all { it in '0'..'9' })
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate returns correct birth date`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(testDate, rnokpp.birthDate)
    }

    @Test
    fun `generate returns correct sequence number`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(123, rnokpp.sequence)
    }

    @Test
    fun `generate returns correct gender digit`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 7
        )

        assertEquals('7', rnokpp.number[8])
    }

    @Test
    fun `generate returns correct control digit`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(
            rnokpp.expectedControlDigit,
            rnokpp.controlDigit
        )

        assertTrue(rnokpp.isValid)
    }

    // ---------------------------------------------------------
    // Граничные значения даты
    // ---------------------------------------------------------

    @Test
    fun `generate with minimum date`() {

        val rnokpp = Rnokpp.generate(
            date = Rnokpp.BASE_DATE,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(Rnokpp.BASE_DATE, rnokpp.birthDate)
        assertEquals(10, rnokpp.number.length)
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate with maximum date`() {

        val rnokpp = Rnokpp.generate(
            date = Rnokpp.MAX_DATE,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(Rnokpp.MAX_DATE, rnokpp.birthDate)
        assertEquals(10, rnokpp.number.length)
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate with date before minimum`() {

        val exception = assertFailsWith<IllegalArgumentException> {

            Rnokpp.generate(
                date = Rnokpp.BASE_DATE.minusDays(1),
                sequenceNumber = 123,
                genderDigit = 1
            )
        }

        assertEquals(
            "date должна быть в диапазоне от " +
                    "${Rnokpp.BASE_DATE} до ${Rnokpp.MAX_DATE}",
            exception.message
        )
    }

    @Test
    fun `generate with date after maximum`() {

        val exception = assertFailsWith<IllegalArgumentException> {

            Rnokpp.generate(
                date = Rnokpp.MAX_DATE.plusDays(1),
                sequenceNumber = 123,
                genderDigit = 1
            )
        }

        assertEquals(
            "date должна быть в диапазоне от " +
                    "${Rnokpp.BASE_DATE} до ${Rnokpp.MAX_DATE}",
            exception.message
        )
    }

    // ---------------------------------------------------------
    // Граничные значения sequenceNumber
    // ---------------------------------------------------------

    @Test
    fun `generate with minimum sequence number`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 0,
            genderDigit = 1
        )

        assertEquals(0, rnokpp.sequence)
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate with maximum sequence number`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 999,
            genderDigit = 1
        )

        assertEquals(999, rnokpp.sequence)
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate with negative sequence number`() {

        val exception = assertFailsWith<IllegalArgumentException> {

            Rnokpp.generate(
                date = testDate,
                sequenceNumber = -1,
                genderDigit = 1
            )
        }

        assertEquals(
            "sequenceNumber должен быть от 0 до 999",
            exception.message
        )
    }

    @Test
    fun `generate with sequence number greater than 999`() {

        val exception = assertFailsWith<IllegalArgumentException> {

            Rnokpp.generate(
                date = testDate,
                sequenceNumber = 1000,
                genderDigit = 1
            )
        }

        assertEquals(
            "sequenceNumber должен быть от 0 до 999",
            exception.message
        )
    }

    // ---------------------------------------------------------
    // Граничные значения genderDigit
    // ---------------------------------------------------------

    @Test
    fun `generate with minimum gender digit`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 0
        )

        assertEquals('0', rnokpp.number[8])
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate with maximum gender digit`() {

        val rnokpp = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 9
        )

        assertEquals('9', rnokpp.number[8])
        assertTrue(rnokpp.isValid)
    }

    @Test
    fun `generate with negative gender digit`() {

        val exception = assertFailsWith<IllegalArgumentException> {

            Rnokpp.generate(
                date = testDate,
                sequenceNumber = 123,
                genderDigit = -1
            )
        }

        assertEquals(
            "genderDigit должен быть от 0 до 9",
            exception.message
        )
    }

    @Test
    fun `generate with gender digit greater than 9`() {

        val exception = assertFailsWith<IllegalArgumentException> {

            Rnokpp.generate(
                date = testDate,
                sequenceNumber = 123,
                genderDigit = 10
            )
        }

        assertEquals(
            "genderDigit должен быть от 0 до 9",
            exception.message
        )
    }

    // ---------------------------------------------------------
    // Дополнительные проверки
    // ---------------------------------------------------------

    @Test
    fun `generated number can be parsed`() {

        val generated = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        val parsed = Rnokpp.parse(generated.number)

        assertEquals(generated.number, parsed.number)
        assertEquals(generated.birthDate, parsed.birthDate)
        assertEquals(generated.sequence, parsed.sequence)
        assertEquals(generated.gender, parsed.gender)
        assertTrue(parsed.isValid)
    }

    @Test
    fun `generate same parameters produces same number`() {

        val first = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        val second = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        assertEquals(first.number, second.number)
    }

    @Test
    fun `generate different sequence produces different numbers`() {

        val first = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        val second = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 124,
            genderDigit = 1
        )

        assertTrue(first.number != second.number)
    }

    @Test
    fun `generate different gender digit produces different numbers`() {

        val first = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 1
        )

        val second = Rnokpp.generate(
            date = testDate,
            sequenceNumber = 123,
            genderDigit = 2
        )

        assertTrue(first.number != second.number)
    }

    @Test
    fun `generate and decode consistency`() {

        val dates = listOf(
            Rnokpp.BASE_DATE,
            LocalDate.of(1900, 1, 1),
            LocalDate.of(1990, 5, 15),
            LocalDate.of(2000, 2, 29),
            LocalDate.of(2024, 2, 29),
            Rnokpp.MAX_DATE
        )

        val sequences = listOf(0, 1, 12, 123, 999)

        val genderDigits = 0..9

        for (date in dates) {
            for (sequence in sequences) {
                for (genderDigit in genderDigits) {

                    val rnokpp = Rnokpp.generate(
                        date = date,
                        sequenceNumber = sequence,
                        genderDigit = genderDigit
                    )

                    assertEquals(date, rnokpp.birthDate)

                    assertEquals(sequence, rnokpp.sequence)

                    assertEquals(
                        genderDigit,
                        rnokpp.number[8].digitToInt()
                    )

                    assertTrue(rnokpp.isValid)
                }
            }
        }
    }
}