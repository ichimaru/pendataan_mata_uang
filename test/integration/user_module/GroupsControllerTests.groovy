package user_module

import static org.junit.Assert.*
import org.junit.*

import groovy.mock.interceptor.MockFor;
import groovy.util.GroovyTestCase
import user_module.UserDetailsService
import org.apache.jasper.compiler.Node.ParamsAction;

class GroupsControllerTests extends GroovyTestCase{
	def UserDetailsService
    
    void testSave() {
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'budi'; udc.params.lastName = 'john'; udc.params.users ='b01'
		udc.params.userAlias='bujon'; udc.params.email='bujon@gmail.com'; udc.params.mobilePhoneNo= '12345678'
		udc.save()
		
		udc.params.firstName = 'jean'; udc.params.lastName = 'lenong'; udc.params.users ='jenong'
		udc.params.userAlias='nong'; udc.params.email='jenong@gmail.com'; udc.params.mobilePhoneNo= '32123132'
		udc.save()
		
		def a = UserDetails.findByUserAlias('bujon')
		assertEquals 'budi',a.firstName
		
		def b = UserDetails.list()
		assertEquals 2, b.size()
		
		def c = UserDetails.findByUserAlias('nong')
		assertEquals 'lenong', c.lastName
		
		def d = [a, c]
		assertEquals 2, d.size()
		
		def gc = new GroupsController()
		gc.metaClass.redirect = { Map args -> return args }
		gc.params.name = 'best group'; gc.params.description = 'only for InTest'; gc.params.user = a.id
		gc.save()
		
		gc.params.name = 'groups brother'; gc.params.description = 'only for InTest 2'; gc.params.user = d 
		gc.save()
		
		def e = Groups.list()
		assertEquals 2, e.size()
		
		def f = Groups.get(1)
		assertEquals 'best group',f.name
	}
	
	void testDelete(){
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'budi'; udc.params.lastName = 'john'; udc.params.users ='b01'
		udc.params.userAlias='bujon'; udc.params.email='bujon@gmail.com'; udc.params.mobilePhoneNo= '12345678'
		udc.save()
		
		udc.params.firstName = 'jean'; udc.params.lastName = 'lenong'; udc.params.users ='jenong'
		udc.params.userAlias='nong'; udc.params.email='jenong@gmail.com'; udc.params.mobilePhoneNo= '32123132'
		udc.save()
		
		def a = UserDetails.findByUserAlias('bujon')
		assertEquals 'budi',a.firstName
		
		def b = UserDetails.list()
		assertEquals 2, b.size()
		
		def c = UserDetails.findByUserAlias('nong')
		assertEquals 'lenong', c.lastName
		
		def d = [a, c]
		assertEquals 2, d.size()
		
		def gc = new GroupsController()
		gc.metaClass.redirect = { Map args -> return args }
		gc.params.name = 'best group'; gc.params.description = 'only for InTest'; gc.params.user = a.id
		gc.save()
		
		gc.params.name = 'groups brother'; gc.params.description = 'only for InTest 2'; gc.params.user = d
		gc.save()
		
		def e = Groups.list()
		assertEquals 2, e.size()
		
		def f = Groups.findByDescription('only for InTest 2')
		assertEquals 'groups brother',f.name
		
		def g = Groups.findByDescription('only for InTest')
		gc.params.id = g.id
		gc.delete()
		
		def h =Groups.list()
		assertEquals 1, h.size()
	}
	
	void testShow(){
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'budi'; udc.params.lastName = 'john'; udc.params.users ='b01'
		udc.params.userAlias='bujon'; udc.params.email='bujon@gmail.com'; udc.params.mobilePhoneNo= '12345678'
		udc.save()
		
		def a = UserDetails.findByUserAlias('bujon')
		assertEquals 'budi', a.firstName
		
		def gc = new GroupsController()
		gc.metaClass.redirect = { Map args -> return args }
		gc.params.name = 'the groupy'; gc.params.description = 'only for InTest'; gc.params.user = a.id
		gc.save()		
		
		def b = Groups.findByDescription('only for InTest')
		assertEquals 'the groupy', b.name
		
		assertEquals 'the groupy',gc.show(b.id).groupsInstance.name
	}
	
	void testEditGroup(){
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'budi'; udc.params.lastName = 'john'; udc.params.users ='b01'
		udc.params.userAlias='bujon'; udc.params.email='bujon@gmail.com'; udc.params.mobilePhoneNo ='12345678'
		udc.save()
		
		def a = UserDetails.findByUserAlias('bujon')
		assertEquals '12345678', a.mobilePhoneNo
		
		def gc = new GroupsController()
		gc.metaClass.redirect = { Map args -> return args }
		gc.params.name = 'the groupy'; gc.params.description = 'only for InTest'; gc.params.user = a.id
		gc.save()
		
		assertEquals 'only for InTest',gc.editGroup(a.id).groupsInstance.description
		assertEquals 6,gc.editGroup(a.id).groupsInstance.id
	}
}
