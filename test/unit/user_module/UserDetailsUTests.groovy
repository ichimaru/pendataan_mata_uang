package user_module



import grails.test.mixin.*
import groovy.mock.interceptor.MockFor;

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(UserDetails)
class UserDetailsUTests {

    void testConstraints() {
       def existingUserDetails = new UserDetails(firstName:'ichi',
		   										 lastName:'maru',
												 userAlias:'maru',
												 userId:23,
												 mobilePhoneNo:214224,
												 email:'ada@ada.com'
												)
	   
	   mockForConstraintsTests(UserDetails, [existingUserDetails])
	   
	   //user details harus diisi not blank
	   def userDetails = new UserDetails()
	   assertFalse userDetails.validate()
	   
	   //email address harus berformat email address
	   userDetails = new UserDetails(firstName:'adaada',
		   							 lastName:'dwdwd',
									 userAlias:'dwdw',
									 userId:24,
									 mobilePhoneNo:249124,
									 email:'adaemails'
									)
	   assertFalse userDetails.validate()
	   
	}
}
