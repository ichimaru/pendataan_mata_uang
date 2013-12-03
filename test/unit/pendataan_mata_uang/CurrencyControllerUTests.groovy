package pendataan_mata_uang



import grails.test.mixin.*
import org.junit.*
import sun.security.util.BigInt;

import pendataan_mata_uang.CurrencyService

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CurrencyController)
@Mock(Currency)
class CurrencyControllerUTests {
	def currencyService
	
	def populateValidParams(params) {
		assert params != null
		// TODO: Populate valid properties like...
		//params["name"] = 'someValidName'
		params["sym"] = "USD"
		params["name"] = "moneyExam"
		params["isTransactional"]= "Ya"
		params["dateCreated"]= new Date ()
//		params["description"]="description"
//		params["lastUpdated"]=  new Date ('2008/01/01')
	}
	
	
    void testIndex() {
       controller.index()
	   assert "/currency/list" == response.redirectedUrl
    }
	
	void testSaveValidation(){
		def currencyInstance = new Currency(name:'test', sym:'test', isTransactional:'Ya')
		assertTrue('tset' != currencyInstance.name)
		assertFalse('test' !=currencyInstance.sym)
	}
	
	void testCreate(){
		def model = controller.create()
		assert model.currencyInstance !=null
	}
	
	void testList(){
		def model = controller.list()
		assert model.currencyInstanceList.size() == 0
		assert model.currencyInstanceTotal == 0
	}
	
	void testShow(){
		controller.show()
		
		assert flash.message !=null
		assert response.redirectedUrl == '/currency/list'
		
		populateValidParams(params)
		def currency = new Currency (params)
		assert currency.save() != null
		params.id = currency.id
		
		def model = controller.show()
		assert model.currencyInstance == currency
	}
	
	void testEdit(){
		controller.edit()
		
		assert flash.message !=null
		assert response.redirectedUrl == '/currency/list'
		
		populateValidParams(params)
		def currency = new Currency(params)
		assert currency.save() !=null
		params.id = currency.id
		
		def model = controller.edit()
		assert model.currencyInstance == currency
	}
	
	void testSave(){
		controller.save()
		response.reset()
		request.method = 'POST'
		controller.save()

		assert model.currencyInstance != null
		assert view == '/currency/create'
		
		response.reset()
		
		populateValidParams(params)
		controller.save()

		assert response.redirectedUrl == '/currency/list'
		assert controller.flash.message != null
		assert Currency.count() == 1
	}
}

