package pp.chsv.rnokpp

object Rnokpp {

    /**
     * Проверяет контрольную цифру.
     *
     * -1 — номер корректен.
     * 0..9 — правильная контрольная цифра.
     */
    fun validate(number: String): Int {
        return RnokppValidator.validate(number)
    }
}