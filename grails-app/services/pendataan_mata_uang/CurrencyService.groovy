package pendataan_mata_uang
import org.grails.datastore.gorm.finders.FindByFinder;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.springframework.dao.DataIntegrityViolationException

import pendataan_mata_uang.Currency
import pendataan_mata_uang.BankLimit
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CurrencyService {
	static Logger logger = LoggerFactory.getLogger(CurrencyController.class)
	//service berguna untuk menyimpan semua logic, jangan lupa niel pindahin logic yg dari controller, ke sini
	//untuk nilai alertLimit yg dari BankLimit perhitungan 80% di dapat dari alertLimit = 80%*dayLimit
	def searchCurrency(def params){
		logger.error("SEKARANG ADA DI CURRENCY SERVICE")
		def searchSym = params.sym
		def searchName = params.name
		def currencyCriteria=Currency.createCriteria()
		def searchResults=currencyCriteria.list(max:params.max, offset:params.offset,
			sort:params.sort, order:params.order){
				if(params?.sym){
					ilike("sym","%${params.sym}%")
					like("deleteFlag","N")
				}
				if(params?.name){
					ilike("name","%${params.name}%")
					like("deleteFlag","N")
				}
			}
			logger.error("JUMLAH HASIL PENCARIAN {}", searchResults.getTotalCount())
			[searchResults: searchResults, searchResultSize: searchResults.getTotalCount()]
	}
	
	
	def createBankLimit(def currency){
			def bl=BankLimit.findByCurrency(currency)
//			println 'nilai dari bl yg mau dibuat : '+bl
			logger.error("NILAI BANK LIMIT YANG AKAN DIBUAT ADALAH : {}", bl)
			if(bl==null){
				def bankLimitInstance = new BankLimit()
				bankLimitInstance.currency = currency
				bankLimitInstance.dayLimit = 0.0
				bankLimitInstance.alertLimit = 0.0
				bankLimitInstance.save(failOnError:true)
				}
			}
	
	def deleteBankLimit(def currency){
		def bl=BankLimit.findByCurrency(currency).id
		
//		println 'nilai dari bl yg diselect : '+bl
		logger.error("NILAI BANK LIMIT YANG DIPILIH UNTUK DIHAPUS ADALAH : {}", bl)
		if(bl){
			def bankLimitInstance = BankLimit.get(bl)
			println 'nilai bank limit'+ bankLimitInstance
			if (!bankLimitInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'bankLimit.label', default: 'BankLimit'), id])
				return
			}
	
			try {
				logger.error("BANK LIMIT  {}  BERHASIL DIHAPUS",bankLimitInstance)
				bankLimitInstance.delete()
				//				flash.message = message(code: 'default.deleted.message', args: [message(code: 'bankLimit.label', default: 'BankLimit'), id])

			}
			catch (DataIntegrityViolationException e) {
				logger.error("Bank limit gagal dihapus, akibat dari : {} ", e.getMessage())
//				flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bankLimit.label', default: 'BankLimit'), id])
			}
		}
	}
	
	def checkAvailable (def cek,def available) {
//		println 'params id nya dari service ' + cek
		logger.error("NILAI PARAMS ID : {} , NILAI AVAILNYA :{} ", cek, available)
//		println 'status availnya : ' + available
		int totalR = Currency.count()
//		println 'total record di tabel : '+ totalR
		logger.error("JUMLAH RECORD DI TABEL : {}", totalR)
		//ambil nilai record id terkecil
		def a = Currency.createCriteria().get{
			projections {
				min "id"
				}
			}as Integer
//		println 'id minimal : '+ a
		logger.error("NILAI ID TERKECIL : {}", a)
			if(a==null){
			available = true
			}
			else{
						++totalR
						println totalR
						println cek
						//looping pengecekan flag & Simbol
						for(int i=a;i<totalR;i++){
							println 'nilai a : '+ i +' nilai totalR '+ totalR
							def c = Currency.get(i)
							println 'tanda flagnya: '+c.deleteFlag
								
							if(c.deleteFlag=="Y" && c.sym==cek){
										available = true
										return  available
							}
							else{
										if(Currency.findBySym(cek)){
											println cek
											available = false
											println 'flag gagal'
											}
										else{
											available = true
											println 'flag berhasil'
										}
									}
						}
			}
		return available
	}
		
	def savingCurrency (def currencyInstance, def searchResult){
		
		println 'currency instance ke tangkep di service : ' + currencyInstance
		println 'search resultnya : ' + searchResult
		
		
		if(searchResult!=null){
//			println 'search result tidak null, simbol sudah digunakan'
			logger.error("DATA SEARCH RESULT TIDAK NULL, BERARTI SIMBOL CURRENCY TELAH TERPAKAI")
			throw new SecurityException("input simbol mata uang "+searchResult.sym+" telah digunakan, mohon lakukan perubahan terhadap simbol mata uang")
		}
		else{
			currencyInstance.save(FailOnError:true)
		}
//		return currencyInstance
	}
}

//example : 

//object.a == null throw new SecurityException("a cannot  be null")

