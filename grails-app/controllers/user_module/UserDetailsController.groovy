package user_module

import org.springframework.dao.DataIntegrityViolationException
import javax.persistence.IdClass;
import org.apache.log4j.or.RendererMap;
import user_module.UserDetailsService
import grails.validation.ValidationException
import org.junit.internal.runners.statements.FailOnTimeout;
import org.eclipse.jdt.internal.compiler.ast.WhileStatement;
import org.hibernate.validator.constraints.impl.MaxValidatorForNumber;

class UserDetailsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def UserDetailsService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		 if(!params.userId && !params.name) {
			 flash.message=""
			 [userId:"", name:"", userDetailsInstanceList: UserDetails.list(params), userDetailsInstanceTotal: UserDetails.count()]
		 }else{		
			def users = userDetailsService.searchUser(params)
			flash.message = message(code:'default.search.result.label', args: [users.searchResultSize])
			[userId:params.userId, name:params.name, searchUserId:params.userId, searchName:params.name, userDetailsInstanceList:users.searchResults, userDetailsInstanceTotal: users.searchResultSize]
		 }        
    }

    def create() {
        [userDetailsInstance: new UserDetails(params)]
    }

    def save() {
		boolean error=false
		def userInstance = new User()
		def userDetailsInstance = new UserDetails()
		try{
			println 'masuk blok try!'
			def check = userDetailsService.checkingSave(params, userInstance, userDetailsInstance, error)
		}
		catch(SecurityException se){
			error=true
			println 'berarti udah ke catch nihh..'
			println 'nilai se : '+ se
			flash.toUser = se.getMessage()
		}
		catch(ValidationException){
			error=true
			println 'ketangkep'
		}
		
		if(!userInstance.hasErrors() && !userDetailsInstance.hasErrors() && !error){
			println 'user & detailsnya valid'
			redirect(action:"list")
		}
		println 'user Instance : '+userInstance.username
		println 'user Instance : '+userInstance.password
		println 'user detail instance : '+userDetailsInstance.firstName
		println 'user detail instance : '+userDetailsInstance.lastName
		println 'user detail instance : '+userDetailsInstance.userAlias
		println 'user detail instance : '+userDetailsInstance.email
		println 'user detail instance : '+userDetailsInstance.mobilePhoneNo
		println 'user detail instance : '+userDetailsInstance.user
		
		userDetailsInstance.validate() && userInstance.validate()
		
		render(view: "create", model: [firstName:userDetailsInstance.firstName, lastName:userDetailsInstance.lastName, users:params.users, userAlias:userDetailsInstance.userAlias, email:userDetailsInstance.email, mobilePhoneNo:userDetailsInstance.mobilePhoneNo, userDetailsInstance:userDetailsInstance])
		return
		
    }

    def show(Long id){
        def userDetailsInstance = UserDetails.get(id)
        if (!userDetailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), id])
            redirect(action: "list")
            return
        }

        [userDetailsInstance: userDetailsInstance]
    }

    def edit(Long id) {
        def userDetailsInstance = UserDetails.get(id)
        if (!userDetailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), id])
            redirect(action: "list")
            return
        }

        [userDetailsInstance: userDetailsInstance]
    }

    def update(Long id, Long version) {
        def userDetailsInstance = UserDetails.get(id)
        if (!userDetailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userDetailsInstance.version > version) {
                userDetailsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'userDetails.label', default: 'UserDetails')] as Object[],
                          "Another user has updated this UserDetails while you were editing")
                render(view: "edit", model: [userDetailsInstance: userDetailsInstance])
                return
            }
        }

        userDetailsInstance.properties = params

        if (!userDetailsInstance.save(flush: true)) {
            render(view: "edit", model: [userDetailsInstance: userDetailsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), userDetailsInstance.id])
        redirect(action: "show", id: userDetailsInstance.id)
    }

    def delete(Long id) {
        def userDetailsInstance = UserDetails.get(id)
        if (!userDetailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), id])
            redirect(action: "list")
            return
        }
        try {
            userDetailsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def checkAvailable () {
		println 'params id nya adalah' + params.id
		def available = false
		def cek = params.id
		
		available = userDetailsService.checkAvailable(cek, available)
		
		println available
		println 'maka hasil di class : ' + available
		
		response.contentType = "application/json"
		render """{"available":${available}}"""
    }
}
