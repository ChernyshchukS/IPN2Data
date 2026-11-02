package pp.chsv.rnokpp

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RnokppParseTest {

    @Test
    fun correctStartNumber() {
        val test = Rnokpp.parse("0000000000")
        assertEquals("1899-12-31", test.birthDate.toString())
        assertEquals(0, test.sequence)
        assertEquals(Gender.FEMALE, test.gender)
        assertEquals(0, test.controlDigit)
        assertEquals(0, test.expectedControlDigit)
    }

    @Test
    fun umber1111111111() {
        val test = Rnokpp.parse("1111111111")
        assertEquals("1930-06-03", test.birthDate.toString())
        assertEquals(111, test.sequence)
        assertEquals(Gender.MALE, test.gender)
        assertEquals(1, test.controlDigit)
        assertEquals(8, test.expectedControlDigit)
    }

    @Test
    fun umber2222222222() {
        val test = Rnokpp.parse("2222222222")
        assertEquals("1960-11-03", test.birthDate.toString())
        assertEquals(222, test.sequence)
        assertEquals(Gender.FEMALE, test.gender)
        assertEquals(2, test.controlDigit)
        assertEquals(5, test.expectedControlDigit)
    }

    @Test
    fun umber9999999999() {
        val test = Rnokpp.parse("9999999999")
        assertEquals("2173-10-14", test.birthDate.toString())
        assertEquals(999, test.sequence)
        assertEquals(Gender.MALE, test.gender)
        assertEquals(9, test.controlDigit)
        assertEquals(6, test.expectedControlDigit)
    }

    @Test
    fun correctNumber() {
        val test = Rnokpp.parse("1234567890")
        assertEquals(9, test.expectedControlDigit)
    }

    @Test
    fun incorrectChecksum() {
        val test = Rnokpp.parse("1234567899")
        assertEquals(test.expectedControlDigit, test.controlDigit)
    }

    @Test
    fun invalidLength() {

        assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("12345")
        }
    }

    @Test
    fun invalidCharacters() {

        assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("12345abc90")
        }
    }

    @Test
    fun `parse valid number`() {
        val number = "1234567890"

        val rnokpp = Rnokpp.parse(number)

        assertEquals(number, rnokpp.number)
    }

    @Test
    fun `parse number with leading zeros`() {
        val number = "0000012345"

        val rnokpp = Rnokpp.parse(number)

        assertEquals(number, rnokpp.number)
    }

    @Test
    fun `parse number containing letters`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("12345abc90")
        }

        assertEquals(
            "Номер должен содержать только цифры",
            exception.message
        )
    }

    @Test
    fun `parse number containing special characters`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("12345-7890")
        }

        assertEquals(
            "Номер должен содержать только цифры",
            exception.message
        )
    }

    @Test
    fun `parse number containing spaces`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("12345 7890")
        }

        assertEquals(
            "Номер должен содержать только цифры",
            exception.message
        )
    }

    @Test
    fun `parse number shorter than 10 digits`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("123456789")
        }

        assertEquals(
            "Номер должен содержать ровно из 10 цифр",
            exception.message
        )
    }

    @Test
    fun `parse number longer than 10 digits`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("12345678901")
        }

        assertEquals(
            "Номер должен содержать ровно из 10 цифр",
            exception.message
        )
    }

    @Test
    fun `parse empty string`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("")
        }

        assertEquals(
            "Номер должен содержать ровно из 10 цифр",
            exception.message
        )
    }

    @Test
    fun `parse unicode digits`() {
        // Арабские цифры Unicode вместо ASCII
        val exception = assertFailsWith<IllegalArgumentException> {
            Rnokpp.parse("١٢٣٤٥٦٧٨٩٠")
        }

        assertEquals(
            "Номер должен содержать только цифры",
            exception.message
        )
    }
}