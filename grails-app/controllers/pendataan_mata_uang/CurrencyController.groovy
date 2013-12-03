package pendataan_mata_uang

import groovy.sql.Sql.QueryCommand;
import javax.persistence.IdClass;
import grails.validation.ValidationException
import org.eclipse.jdt.internal.compiler.ast.WhileStatement;
import org.hibernate.validator.constraints.impl.MaxValidatorForNumber;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.springframework.dao.DataIntegrityViolationException
import pendataan_mata_uang.CurrencyService


import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CurrencyController {
	static Logger logger = LoggerFactory.getLogger(CurrencyController.class)
	
//	static Logger logger = LoggerFactory.getLogger(this) <= ini juga bisa dipakai kok
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def CurrencyService
	
    def index() {
		redirect(action: "list", params: params)
	}
	
	def list () {
		logger.error("SEKARANG ADA DI KELAS CURRENCY BAGIAN CONTROLLER")
		 params.max = Math.min(params.max ? params.int('max') : 10, 100)
		 
		 if(!params.sym && !params.name) {
			 def query = Currency.where {
				 deleteFlag == "N"
			 }
			 println query
			 def cur =query.list(offset:params.offset, max:params.max, sort:params.sort, order:params.order)
			 println cur
			 def currencyCount = query.count()
			 println currencyCount
			 logger.error("JUMLAH CURRENCY : {}", currencyCount)
			 flash.message=""
			 [simbol:"", nama:"", currencyInstanceList:cur, currencyInstanceTotal:currencyCount]
			 
		 }else{		
			def currencys = currencyService.searchCurrency(params)
			
			flash.message = message(code:'default.search.result.label', args: [currencys.searchResultSize])
			[simbol:params.sym, nama:params.name, searchSym:params.sym, searchName:params.name, currencyInstanceList:currencys.searchResults, currencyInstanceTotal: currencys.searchResultSize]
		 }
	}
	
	def create () {
		[currencyInstance: new Currency(params)]
	}
	
	def checkAvailable () {
		def available = false
		def cek = params.id
		
		available = currencyService.checkAvailable(cek, available)
		logger.error("STATUS CURRENCY : {}", available)
		println 'maka hasil di class : ' + available
		
			//tampilin hasil pengecekan
			response.contentType = "application/json"
			render """{"available":${available}}"""
	}
	
	def save (){
		def currencyInstance = new Currency(params)
			if(params.isTransactional=="Y"){
				currencyInstance.isTransactional="Ya"
			}
			else{
				currencyInstance.isTransactional="Tidak"
			}
		logger.error("SYM: {}, NAME:{}, TRANSACTIONAL: {}", currencyInstance.sym, currencyInstance.name, currencyInstance.isTransactional)
		boolean error=false
		
		def c = Currency.createCriteria()
		
		def searchResult = c.get{
			println 'masuk ke bagian search result'
			if(currencyInstance.sym!=null){
				ilike("sym","${currencyInstance.sym}")
				and{
					like("deleteFlag","N")
				}
			}
		}
			println 'hasil search : '+ searchResult
			try{
				println 'masuk blok try!'
				currencyInstance.validate()
				logger.error("status validasi : {}", currencyInstance.validate())
				def checkSym = currencyService.savingCurrency(currencyInstance, searchResult)
			}
			catch(SecurityException se){
				logger.error("CURRENCY DITANGKAP CATCH PERTAMA : {}", se.getMessage())
				error=true
				println 'berarti udah ke catch nihh..'
				println 'nilai se : '+ se
				flash.toUser = se.getMessage()
			}
			catch(ValidationException){
				error=true
				logger.error("CURRENCY DITANGKAP CATCH KE DUA : {}")
				println 'ketangkep'
			}
			if(!currencyInstance.hasErrors()&&!error){
				logger.error("CURRENCY VALID, LANJUT KE TAHAP SELANJUTNYA")
				println 'currency valid, step buat bank limit'
				if(currencyInstance.isTransactional=="Ya"){
					def idCur = currencyInstance
					def banks = currencyService.createBankLimit(currencyInstance)
				}
			redirect(action:"list")
			}
			currencyInstance.validate()
			render(view: "create", model: [simbol:currencyInstance.sym, nama:currencyInstance.name, currencyInstance: currencyInstance])
			return		
	}
	
	def show(Long id) {
		def currencyInstance = Currency.get(id)
		logger.error("CURRENCY DITAMPILKAN DENGAN ID: {}", id)
		if (!currencyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'currency.label', default: 'Currency'), id])
			redirect(action: "list")
			return
		}
		[currencyInstance: currencyInstance]
	}
	
	def edit(Long id) {
		def currencyInstance = Currency.get(id)
		logger.error("CURRENCY HENDAK DIPERBARUI DENGAN ID: {}", id)
		if (!currencyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'currency.label', default: 'Currency'), id])
			redirect(action: "list")
			return
		}
		[currencyInstance: currencyInstance]
	}
	
	def update(Long id, Long version) {
		def currencyInstance = Currency.get(id)			
			
			if (!currencyInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'currency.label', default: 'Currency'), id])
				redirect(action: "list")
				return
				}

			if (version != null) {
					if (currencyInstance.version > version) {
							currencyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
								[message(code: 'currency.label', default: 'Currency')] as Object[],
									"Sudah ada User lain yang melakukan pembaruan, saat anda sedang memperbarui")
							render(view: "edit", model: [currencyInstance: currencyInstance])
						return
					}
				}

		currencyInstance.properties = params
			if(params.isTransactional=="Ya"){
				currencyInstance.isTransactional="Ya"
			}else{
				currencyInstance.isTransactional="Tidak"
				}
			logger.error("STATUS TRANSAKSI SETELAH DI PERBARUI: {}", currencyInstance.isTransactional)
			if (!currencyInstance.save(FailOnError:true)) {
				render(view: "edit", model: [currencyInstance: currencyInstance])
				return
			}

		if(currencyInstance.isTransactional=="Ya"){
			def idCur = currencyInstance
			def banks = currencyService.createBankLimit(currencyInstance)
		}
		if(currencyInstance.isTransactional=="Tidak"){
			def banks = currencyService.deleteBankLimit(currencyInstance) 
		}
		redirect(action: "list", id: currencyInstance.id)
	}
	
	def delete(Long id) {
		def currencyInstance = Currency.get(id)
		logger.error("CURRENCY DIHAPUS DENGAN  ID : {}", id)
		if (!currencyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'currency.label', default: 'Currency'), id])
			redirect(action: "list")
			return
		}

		try {
			currencyInstance.deleteFlag="Y"
			if(currencyInstance.isTransactional=="Ya"){
				def banks = currencyService.deleteBankLimit(currencyInstance)
			}
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'currency.label', default: 'Currency'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			logger.error("CURRENCY GAGAL DIHAPUS AKIBAT : {}", e)
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'currency.label', default: 'Currency'), id])
			redirect(action: "show", id: id)
		}
	}
	
}
