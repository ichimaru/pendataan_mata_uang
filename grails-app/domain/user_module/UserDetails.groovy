package user_module

class UserDetails {
	String firstName
	String lastName
	User user
	String userAlias
	String email
	String mobilePhoneNo
	
    static constraints = {
		firstName(blank:false)
		lastName(blank:false)
		user(blank:false)
		userAlias(blank:false)
		email(email:true)
		mobilePhoneNo(blank:false)
    }
	
	String toString() {
		"${firstName} - ${lastName} - ${user}"
	  }
}
