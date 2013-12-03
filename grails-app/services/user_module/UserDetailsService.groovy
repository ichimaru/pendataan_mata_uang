package user_module
import org.grails.datastore.gorm.finders.FindByFinder;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.springframework.dao.DataIntegrityViolationException
import org.apache.catalina.mbeans.UserMBean;
import org.apache.commons.lang.RandomStringUtils
import org.codehaus.groovy.ast.stmt.BreakStatement;


import sun.security.util.BigInt;
import user_module.User
import user_module.UserDetails
import user_module.Groups
import user_module.GroupsUser
class UserDetailsService {
	
	def searchUser(def params){
		def searchUserId = params.userId
		def searchName = params.name
		def userDetailsCriteria=UserDetails.createCriteria()
		def searchResults=userDetailsCriteria.list(max:params.max, offset:params.offset,
			sort:params.sort, order:params.order){
			
			user {
				if(params?.userId){
					ilike("username","%${params.userId}%")
				}
			}
			or{
				if(params?.name){
					ilike("firstName","%${params.name}%")			
				}
				if(params?.name){
					ilike("lastName","%${params.name}%")
				}
			}
			}
			[searchResults: searchResults, searchResultSize: searchResults.getTotalCount()]
	}
	
	def checkAvailable (def cek, def available) {
		println 'params id nya dari service ' + cek
		println 'status availnya : ' + available
		
			if(User.findByUsernameLike(cek)) {
					available = false
				} 
			else{
					available = true
			}
			return available
    }
	
	def checkingSave(def params, def userInstance, def userDetailsInstance, def error){
		
		String charset = (('A'..'Z') + ('0'..'9')).join()
		Integer length = 9
		String randomString = RandomStringUtils.random(length, charset.toCharArray())
		println 'code berubah2nya : ' + randomString
		userInstance.password = randomString
		userDetailsInstance.firstName = params.firstName
		userDetailsInstance.lastName = params.lastName
		userDetailsInstance.userAlias = params.userAlias
		userDetailsInstance.email = params.email
		userDetailsInstance.mobilePhoneNo = params.mobilePhoneNo
		
		if(params.users){
			userInstance.username = params.users
			userDetailsInstance.user = userInstance
		}
		
		def user = User.createCriteria()
		def resultUser = user.get{
			if(userInstance?.username){
				ilike("username","${userInstance.username}")
			}
		}
		
		if(resultUser!=null){
			println 'search result tidak null, simbol sudah digunakan'
			throw new SecurityException("User ID "+resultUser.username+" telah digunakan, mohon gunakan User ID yang Lain")
		}
		else{
			userInstance.save(failOnError:true)
			userDetailsInstance.save(failOnError:true)
		}
	}
	
	def saveGroups (def params, def groupsInstance){
		println 'params user semuanya : '+params.user
		groupsInstance.description = params.description
		groupsInstance.name = params.name
		if(params.name==null||params.name==''){
			println 'search result tidak null, simbol sudah digunakan'
			throw new SecurityException("Group name has not input yet. Please try again")
		}
		def gr = Groups.createCriteria()
		def resultGroups = gr.get{
			if(groupsInstance?.name){
				ilike("name","${groupsInstance.name}")
				}
			}
		println 'result groups search : '+ resultGroups
		
		if(resultGroups!=null){
			println 'search result tidak null, simbol sudah digunakan'
			throw new SecurityException("Group Name : "+resultGroups.name+" was used by other Group, please enter others name")
		}
		else{
			println 'search result groups null, lanjut ke blok else untuk penyimpanan groups & membernya!'
			groupsInstance.save(FailOnError:true)
			def row1 = params.user.toList()
			def a = row1.size()
			println 'row 1 : ' + row1
			println 'a : '+a
			for (def i=0;i<a;i++){
				def g = row1.getAt(i)
				println "g : "+ g
				if(g==''){
					println 'g na null nih!'
				}
				else{
					println 'g na gk null!'
					User us =User.get(g)
					println 'us : '+us
					def groupsUserInstance = new GroupsUser ()
					groupsUserInstance.groups = groupsInstance
					groupsUserInstance.user = us
					groupsUserInstance.save(failOnError:true)
				}
			}
		}
	return
	}
	
	def searchGroups(def params){
		def searchName = params.name
		def groupsCriteria=Groups.createCriteria()
		def searchResults=groupsCriteria.list(max:params.max, offset:params.offset,
			sort:params.sort, order:params.order){
				if(params?.name){
					ilike("name","%${params.name}%")
				}
			}
			[searchResults: searchResults, searchResultSize: searchResults.getTotalCount()]
	}
	
	def deleteMember(def guInstance){
		println 'guInstance di tangkap di service ' + guInstance
		GroupsUser groupUserInstance = GroupsUser.get(guInstance.id)
		println 'nilai groupUserInstance : '+ groupUserInstance
		
		if (!groupUserInstance) {
			redirect(action: "list", id:id)
			return
		}
		try {
			groupUserInstance.delete(failOnError:true)
		}
		catch (DataIntegrityViolationException e) {
		}
	}
	
	def submitUser(def groupsInstance, def user){
		println 'nilai groupsInstance : ' + groupsInstance
		User us = User.get(user)
		println 'nilai user nya : '+ us
		def groupsUserInstance = new GroupsUser ()
		groupsUserInstance.groups = groupsInstance
		groupsUserInstance.user = us
		groupsUserInstance.save(failOnError:true)
	}
	
	def checkAvailable1(def cek,def available){
		println 'params id nya dari service ' + cek
		println 'status availnya : ' + available
		
			if( Groups.findByNameLike(cek)) {
					available = false
				}
			else{
					available = true
			}
			return available
			println 'hasil kondisi di service '+ available
	}
	
	def addUserUpdate(params, def gId){
		println 'param users yg ketangkep : ' + params.user
		println 'param gId ketangkep : ' + gId
		println 'param gId idnya ketangkep : ' + gId.id
		def groupsUserInstance = new GroupsUser ()
		def row1
		def a , b
		User us
		
		
		//tambahan if lagi di sini, apabila row bukan list maka a langsung aja... kalo list di size..lol
		row1 = params.user
		a = row1.size()
		b = row1.count(row1)
		println 'a : '+a
		println 'b : '+b
		
		if(b==1){
			println 'di b1 lagi'
			us =User.get(row1)
			groupsUserInstance.groups = gId
			groupsUserInstance.user = us
			groupsUserInstance.save(failOnError:true)
		}
		else{
			println 'nah ini baru di else'
			for (def i=0;i<a;i++ ){
				println 'i :' + i
				def g = row1.getAt(i)
				println "g : "+ g
					if(g==''){
						println 'g na null nih!'
					}
					else{
						println 'g na gk null!'
						us =User.get(g)
						println 'us : '+us
						def gui = new GroupsUser ()
						gui.groups = gId
						gui.user = us
						gui.save(failOnError:true)
						println 'kesave nih'
					}
					}
		}
		return
	}
}