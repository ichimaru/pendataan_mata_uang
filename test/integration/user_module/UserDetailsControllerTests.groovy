package user_module

import static org.junit.Assert.*
import org.junit.*
import groovy.util.GroovyTestCase
import user_module.UserDetailsService
import org.apache.jasper.compiler.Node.ParamsAction;

class UserDetailsControllerTests extends GroovyTestCase {
def UserDetailsService
   
    void testDelete() {
       def udc = new UserDetailsController()
	   udc.metaClass.redirect = { Map args -> return args }
	   udc.params.firstName = 'budi'
	   udc.params.lastName = 'john'
	   udc.params.users ='b01'
	   udc.params.userAlias='bujon'
	   udc.params.email='bujon@gmail.com'
	   udc.params.mobilePhoneNo= '12345678'
	   udc.save()
	   	   
	   udc.params.firstName = 'daniel'
	   udc.params.lastName = 'sianipar'
	   udc.params.users ='uniks'
	   udc.params.userAlias='unnik'
	   udc.params.email='daniel@gmail.com'
	   udc.params.mobilePhoneNo= '87654321'
	   udc.save()
	   
	   def a = UserDetails.list()
	   def b = User.list()
	   assertEquals 2, a.size()
	   assertEquals 2, b.size()
	   
	   def c = User.findByUsername('uniks')
	   assertEquals 'uniks', c.username
	   
	   def d = UserDetails.findByFirstName('daniel')
	   udc.params.id=d.id
	   udc.delete()
	   
	   def e = UserDetails.list()
	   assertEquals 1, e.size()
	   
    }
	
	void testSave(){
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'budi'
		udc.params.lastName = 'john'
		udc.params.users ='b01'
		udc.params.userAlias='bujon'
		udc.params.email='bujon@gmail.com'
		udc.params.mobilePhoneNo= '12345678'
		udc.save()
		
		def a = User.findByUsername('b01')
		assertEquals 'budi',UserDetails.findByUser(a).firstName
		def b = User.list()
		assertEquals 1, b.size()
	}
	
	void testShow(){
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'Scarlet'; udc.params.lastName = 'johannsen'; udc.params.users ='scar'
		udc.params.userAlias='hann'; udc.params.email='scarhann@gmail.com'; udc.params.mobilePhoneNo= '1029384756'
		udc.save()
		
		udc.params.firstName = 'johny'; udc.params.lastName = 'deb'; udc.params.users ='jodeb'
		udc.params.userAlias='ohny'; udc.params.email='onndeb@gmail.com'; udc.params.mobilePhoneNo= '575757575'
		udc.save()
		
		udc.params.firstName = 'steven'; udc.params.lastName = 'segel'; udc.params.users ='stegel'
		udc.params.userAlias='tepen'; udc.params.email='segeel@gmail.com'; udc.params.mobilePhoneNo= '30303030'
		udc.save()
		
		
		def a =UserDetails.findByUserAlias('tepen')
		assertEquals 'segel', a.lastName
		
		def b = UserDetails.list()
		assertEquals 3, b.size()
		
		def c = User.list()
		assertEquals 3, c.size()
		
		def d = UserDetails.get(a.id)
		assertEquals 'steven', d.firstName
		assertEquals 'segel', d.lastName
		assertEquals 6, d.id
		
		
		assertEquals 'steven', udc.show(d.id).userDetailsInstance.firstName
		assertEquals 'stegel', udc.show(d.id).userDetailsInstance.user.toString()
		assertEquals 'tepen', udc.show(d.id).userDetailsInstance.userAlias
	}
	
	void testEdit(){
		def udc = new UserDetailsController()
		udc.metaClass.redirect = { Map args -> return args }
		udc.params.firstName = 'connie'; udc.params.lastName = 'line'; udc.params.users ='connie'
		udc.params.userAlias='line1'; udc.params.email='scarhann@gmail.com'; udc.params.mobilePhoneNo= '1029384756'
		udc.save()
		
		udc.params.firstName = 'brown'; udc.params.lastName = 'line'; udc.params.users ='brown'
		udc.params.userAlias='line2'; udc.params.email='onndeb@gmail.com'; udc.params.mobilePhoneNo= '575757575'
		udc.save()
		
		udc.params.firstName = 'john'; udc.params.lastName = 'ngkok'; udc.params.users ='johngkok'
		udc.params.userAlias='tepen'; udc.params.email='segeel@gmail.com'; udc.params.mobilePhoneNo= '30303030'
		udc.save()
		
		def a = UserDetails.findAllByLastName('line')
		assertEquals 2, a.size()
		
		def b = User.list()
		assertEquals 3, b.size()
		
		def c =UserDetails.get(8)
		assertEquals 'brown',c.firstName
		
		assertEquals 'brown', udc.edit(c.id).userDetailsInstance.firstName
		assertEquals 'line',udc.edit(c.id).userDetailsInstance.lastName
		
	}
}