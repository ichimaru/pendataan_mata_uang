package user_module

import org.springframework.dao.DataIntegrityViolationException

class GroupsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def UserDetailsService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		 if(!params.name) {
			 flash.message=""
			 [name:"", groupsInstanceList: Groups.list(params), groupsInstanceTotal: Groups.count()]
		 }else{		
			def groupsSearch = userDetailsService.searchGroups(params)
			
			flash.message = message(code:'default.search.result.label', args: [groupsSearch.searchResultSize])
			[name:params.name, searchName:params.name, groupsInstanceList:groupsSearch.searchResults, groupsInstanceTotal: groupsSearch.searchResultSize]
		 }    
    }

    def create() {
        [groupsInstance: new Groups(params)]
    }

    def save() {
		println 'lemparan params dari view : '+ params
		println 'params combo : '+ params.user
		
		def groupsInstance = new Groups ()
		boolean error = false
		try{
			println 'masuk ke blok try!'	
			def saveGroup=userDetailsService.saveGroups(params, groupsInstance)
			println 'nilai groups instance setelah dari service : '+groupsInstance
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
		if(!groupsInstance.hasErrors()&&!error){
			redirect(action:"list")
		}
		else{
			groupsInstance.validate()
			render(view: "create", model: [name: groupsInstance.name, description:groupsInstance.description])
		}
    }
	
	def show (Long id){
		def groupsInstance = Groups.get(id)
		println 'groupsInstance' + groupsInstance
		def guId = GroupsUser.findAllByGroups(groupsInstance)
		println 'groups user' + guId.user
		println 'groups user id' + guId.id
		int a=guId.size()
		def c =[]
		println 'a = ' + a
		for(int i=0;i<a;i++){
			def  b = User.get(guId.user.id[i])
			def ud = UserDetails.findByUser(b)
			println 'UD = '+ud
			c.add(ud)
		}
		println 'nilai C : '+c
		[groupsInstance: groupsInstance, guId:guId, c:c]
	}
	
	def editGroup (long id){
		def groupsInstance = Groups.get(id)
		def guId = GroupsUser.findAllByGroups(groupsInstance)
		if (!groupsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'groups.label', default: 'Groups'), id])
			redirect(action: "list")
			return
		}
		[groupsInstance: groupsInstance, guId: guId, userDetailsInstance:UserDetails.list(params),groupsI:id]
		
	}
	
	def updateGroup (long id,Long version){
		def groupsInstance = Groups.get(id)
		
		if (!groupsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'groups.label', default: 'Groups'), id])
			redirect(action: "list")
			return
			}

		if (version != null) {
			if (groupsInstance.version > version) {
					groupsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[message(code: 'groups.label', default: 'Groups')] as Object[],
							"Sudah ada User lain yang melakukan pembaruan, saat anda sedang memperbarui")
					render(view: "edit", model: [groupsInstance: groupsInstance])
			return
				}
			}
		groupsInstance.properties = params

		if (!groupsInstance.save(FailOnError:true)) {
			render(view: "edit", model: [groupsInstance: groupsInstance])
			return
		}

	flash.message = message(code: 'default.updated.message', args: [message(code: 'groups.label', default: 'Groups'), groupsInstance.id])
//	redirect(action: "list", id: groupsInstance.id)
		
	}	
	
//	def editMember (long id){
//		def groupsInstance = Groups.get(id)
//		def guId = GroupsUser.findAllByGroups(groupsInstance)
//		[guId: guId, userDetailsInstance:UserDetails.list(params), groupsInstance:id]
//	}
	
	def updateMulti () {
	Groups gId=Groups.get(params.id)
	println 'params.groupUser : ' + params.groupUser
	println 'params.deleteUser : ' + params.deleteUser
	updateGroup()
	
	def row1 = params.groupUser
	def row2 = params.deleteUser
		if(params.deleteUser!=null && params.groupUser!=null){
			for (i in 0..row1.size()/2-1 ){
				def guParams = row1["r${i}"]
				def duParams =row2["s${i}"]
				def guInstance = GroupsUser.get(guParams.id)
				println 'groupUser ke : '+ i +' adalah ' + guParams
				println 'deleteUser ke : '+ i +' adalah '+ duParams
				println 'guInstance ke : '+ i +' adalah '+ guInstance
					if(duParams == "Y"){
						println 'gu instance '+ guInstance+' pada blok delete nih'
						def deleteMember = userDetailsService.deleteMember(guInstance)
					}
			}
		}
		println 'params user : '+ params.user
		if(!params.user){
			redirect(action: "show", id:params.id)
			return
		}else{
		def addUser=userDetailsService.addUserUpdate(params, gId)
		}
		redirect(action: "show", id:params.id)
	}
	
//	def submitUser(){
//		println 'semua params : ' + params
//		println 'group user : '+ params.groupsInstance
//		def groupsInstance = Groups.get(params.groupsInstance)
//		println 'paramsnya nyangkut di sini : '+ groupsInstance
//		println 'paramsnya nyangkut di sini : '+ params.user
//		def user = params.user
//		def submit = userDetailsService.submitUser(groupsInstance, user)
//		redirect (action:"show", id:groupsInstance.id )
//	}
	
	def delete (Long id){
		def groupsInstance = Groups.get(id)
		println 'groupsInstance id: ' +groupsInstance
		def guId = GroupsUser.findAllByGroups(groupsInstance)
		println 'nilai guIdnya : ' + guId
		def row = guId.size()
		println 'nilai row : ' + row
		
		try {
			for(def i=0;i<row;i++){
				def gu = guId.id.getAt(i)
				println "gu : "+ gu
				GroupsUser groupsUserInstance = GroupsUser.get(gu)
				groupsUserInstance.delete(failOnError:true)
			}
			groupsInstance.delete(failOnError: true)
			
			}
		catch (DataIntegrityViolationException e) {
			
			}
		 redirect (action:"list")
	}
	
	def checkAvailable1 () {
		def available = false
		def cek = params.id
		println 'cek : ' +cek
		
		available = userDetailsService.checkAvailable1(cek, available)
		
		println available
		println 'maka hasil di class : ' + available
		
			//tampilin hasil pengecekan
			response.contentType = "application/json"
			render """{"available":${available}}"""
	}
}


