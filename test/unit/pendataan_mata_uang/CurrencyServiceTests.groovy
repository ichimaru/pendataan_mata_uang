package pendataan_mata_uang



import grails.test.mixin.*
import org.junit.*
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import grails.test.GrailsUnitTestCase
import pendataan_mata_uang.CurrencyService
import pendataan_mata_uang.Currency


/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

class CurrencyServiceTests extends GrailsUnitTestCase {
	
	void testSavingCurrency() {
		def testInstance=[]
		mockDomain(Currency, testInstance)
		def currencyService = new CurrencyService()
		def currencyInstance = new Currency(name:'Rupiah', sym:'Rp', dateCreated: new Date(), isTransactional:'Ya' )
		def saving = null
		def searchResult = null
		
		try{
			saving = currencyService.savingCurrency(currencyInstance, searchResult)
		}
		catch(RuntimeException e){
			println e
		}
		
		assert currencyInstance == Currency.get(1)
		assert 1 == Currency.count()
	}
}