package user_module



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Groups)
class GroupsUTests {

     void testConstraints() {
       def existingGroups = new Groups(name:'Test Group',
		   							 	description:'only tester member')
	   
	   mockForConstraintsTests(Groups, [existingGroups])
	   
	   //groups harus memiliki attribut
	   def groups = new Groups()
	   assertFalse groups.validate()
	   
	   //groups harus memiliki nama
	   groups = new Groups(description:'test')
	   assertFalse groups.validate()
	   
	   //groups boleh tidak memiliki description
	   groups = new Groups(name:'beatable')
	   assertTrue groups.validate()
	}
}
