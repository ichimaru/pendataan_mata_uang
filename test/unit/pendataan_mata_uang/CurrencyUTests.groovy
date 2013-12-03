package pendataan_mata_uang



import grails.test.GrailsUnitTestCase
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Currency)
class CurrencyUTests {

    void testConstraints() {
       def existingCurrency = new Currency(
		   sym:"USD", name:"United State Dollar", isTransactional:"Ya")
    
	   mockForConstraintsTests(Currency, [existingCurrency])
	   
	   //Currency butuh simbol & nama
	   def currencyInstance = new Currency()
	   assertFalse currencyInstance.validate()
	   
	   //simbol currency maksimal 5 karakter
	   currencyInstance = new Currency(
		   sym:'abcdef',
		   name:'abjad',
		   isTransactional:'Tidak')
	   assertFalse currencyInstance.validate()
	   
	   //nama currency maksimal 100 karakter
	   currencyInstance = new Currency(
		   sym:'RP',
		   name:'12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901',
		   isTransactional:'Ya'
		   )
	   assertFalse currencyInstance.validate()
	   
	   //description boleh dikosongkan, currency benar
	   currencyInstance=new Currency(
		   sym:'asd',
		   name:'as',
		   isTransactional:"Ya",
		   description:''
		   )
	   assertTrue currencyInstance.validate()  
	}
}
