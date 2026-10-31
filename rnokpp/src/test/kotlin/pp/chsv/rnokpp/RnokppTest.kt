package pp.chsv.rnokpp

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RnokppTest {

    @Test
    fun correctNumber() {
        assertEquals(9, Rnokpp.validate("1234567890"))
    }

    @Test
    fun incorrectChecksum() {
        assertEquals(9, Rnokpp.validate("1234567891"))
    }

    @Test
    fun invalidLength() {

        assertFailsWith<IllegalArgumentException> {
            Rnokpp.validate("12345")
        }
    }

    @Test
    fun invalidCharacters() {

        assertFailsWith<IllegalArgumentException> {
            Rnokpp.validate("12345abc90")
        }
    }
}