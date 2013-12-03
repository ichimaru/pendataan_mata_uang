package pendataan_mata_uang

class BankLimit {
Currency currency
BigDecimal dayLimit
BigDecimal alertLimit
    static constraints = {
		currency()
		dayLimit(min:0.0)
    }
}
