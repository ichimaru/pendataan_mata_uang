package pendataan_mata_uang

import static org.junit.Assert.*
import org.apache.jasper.compiler.Node.ParamsAction;
import org.junit.*
import groovy.util.GroovyTestCase
import pendataan_mata_uang.CurrencyService

class CurrencyControllerTests extends GroovyTestCase {
def CurrencyService
    void testDelete() {
        def cc = new CurrencyController()
		cc.metaClass.redirect = { Map args -> return args }
		def cur1 = new Currency(id:1, sym:'Rp', name:'Rupiah', isTransactional:'Ya', dateCreated:new Date())
		def cur2 = new Currency(id:2, sym:'AUS', name:'Australian Dollar', isTransactional:'Tidak', dateCreated:new Date())
		def cur3 = new Currency(id:3, sym:'USD', name:'Uncle Sam Dollar', isTransactional:'Tidak', dateCreated:new Date())
		def cur4 = new Currency(id:4, sym:'EUR', name:'Euro', isTransactional:'Ya', dateCreated:new Date())
		def cur5 = new Currency()
		def bl1 = new BankLimit(id:1, currency:cur1, dayLimit:0.0, alertLimit:0.0)
		cur1.save(flush:true);cur2.save(flush:true);cur3.save(flush:true);cur4.save(flush:true);bl1.save()
		assertFalse cur5.validate()
		def curr = Currency.list()
		assertEquals 4, curr.size()
		
		def banks = BankLimit.list()
		assertEquals 1, banks.size()
		assertEquals 'Rp', banks[0].currency.sym
		
		def a =Currency.get(1)
		cc.params.id=a.id
		cc.delete()
		assertEquals "Y",a.deleteFlag
		def b = Currency.list()
		assertEquals 4 , b.size()
    }
	
	void testSave(){
		def cc = new CurrencyController()
		cc.metaClass.redirect = { Map args -> return args }
		
		cc.params.sym='afa1'
		cc.params.name='afalan1'
		cc.params.isTransactional='Y'
		cc.save()
		
		def a = Currency.findBySym('afa1')
		assert 'afa1',a.sym
		
		def b = Currency.list()
		assertEquals 1,b.size()
		
		def c = BankLimit.findByCurrency(a)
		assertEquals 'afa1',c.currency.sym
	}
	
	void testShow(){
		def cc = new CurrencyController()
		cc.metaClass.redirect = { Map args -> return args }
		def cur1 = new Currency(id:1, sym:'Rp', name:'Rupiah', isTransactional:'Ya', dateCreated:new Date())
		def cur2 = new Currency(id:2, sym:'AUS', name:'Australian Dollar', isTransactional:'Tidak', dateCreated:new Date())
		cur1.save(flush:true);cur2.save(flush:true)
		def a = Currency.findBySym('Rp')
		
		assertEquals 'Rp',cc.show(a.id).currencyInstance.sym
	}
	//test check available belom bisa
	void testcheckAvailable(){
		def cc = new CurrencyController()
		cc.metaClass.redirect = { Map args -> return args }
		
		cc.params.sym='xoxox'
		cc.params.name='xoxox'
		cc.params.isTransactional=''
	}
	
	void testEdit(){
		def cc = new CurrencyController()
		cc.metaClass.redirect = { Map args -> return args }
		def cur1 = new Currency(id:1, sym:'Rp', name:'Rupiah', isTransactional:'Ya', dateCreated:new Date())
		def cur2 = new Currency(id:2, sym:'AUS', name:'Aus', isTransactional:'Tidak', dateCreated:new Date())
		cur1.save(flush:true);cur2.save(flush:true)
		def a = Currency.findBySym('AUS')
		
		assertEquals 'Aus',cc.edit(a.id).currencyInstance.name
	}
}
