package user_module

class User {
	String username
	String password
	
    static constraints = {
		username(blank:false)
		password(blank:false)
    }
	
	String toString() {
		"${username}"
	  }
}
