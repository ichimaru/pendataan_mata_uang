package pendataan_mata_uang

import static org.junit.Assert.*
import org.junit.*
//import grails.util.DomainBuilder
import groovy.util.GroovyTestCase

class CurrencyTests extends GroovyTestCase {

    @Before
    void setUp() {
        // Setup logic here
		super.setUp()
		def newCurrency1 = new Currency(sym:'Rp', name:'Rupiah', dateCreated:new Date(), isTransactional:'Ya')
			if(!newCurrency1.save(flush:true)){
				fail(newCurrency1.errors.allErrors[0].toString())
			}
		def newCurrency2 = new Currency(sym:'USD', name:'Uncle Sam Dollar', dateCreated:new Date(), isTransactional:'Ya')
			if(!newCurrency2.save(flush:true)){
				fail(newCurrency2.errors.allErrors[0].toString())
			}
    }

    @After
    void tearDown() {
        // Tear down logic here
		super.tearDown()
    }

    @Test
    void testCurrency() {
        def currency = Currency.list()
		assert currency.size() == 2
		assertEquals currency[0].sym, 'Rp'
		assertEquals currency[1].sym, 'USD'
		
		def curren = Currency.get(2)
		assert curren.name == "Uncle Sam Dollar"
    }
}
