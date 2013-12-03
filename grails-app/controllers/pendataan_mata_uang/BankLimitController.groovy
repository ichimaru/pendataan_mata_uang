package pendataan_mata_uang


import org.springframework.dao.DataIntegrityViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BankLimitController {	
	static Logger logger = LoggerFactory.getLogger(CurrencyController.class)
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
			redirect(action: "show", params: params)
    }

    def show() {
		logger.error("SEKARANG ADA DI KELAS BANK LIMIT BAGIAN CONTROLLER")
		 params.max = Math.min(params.max ? params.int('max') : 30, 100)
		 logger.error("JUMLAH BANK LIMIT: {}", BankLimit.count())
		[bankLimitInstanceList: BankLimit.list(params), bankLimitInstanceTotal: BankLimit.count()]
    }

    def edit() {
//		 params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[bankLimitInstanceList: BankLimit.list(params), bankLimitInstanceTotal: BankLimit.count()]
    }
	
	def updateMulti = {
		def rows = params.bankLimit
		for (i in 0..rows.size()/3-1 ){
		   def bankParams = rows["r${i}"]
		   // ambil data bankLimit dari DB
		   def bankLimitInstance = BankLimit.get(bankParams.id)
		   // update bank limit
		   BigDecimal dl= bankParams.dayLimit.toBigDecimal()
		   bankLimitInstance.dayLimit = dl
		   BigDecimal al = dl * 0.8
		   bankLimitInstance.alertLimit = al
		   // simpen bank Limit
		   bankLimitInstance.save()
		   // arahin ke show
		   logger.error("BANK LIMIT  {}  BERHASIL DIUPDATE", bankLimitInstance)
		   }
		redirect(action: "show")
	}

}
