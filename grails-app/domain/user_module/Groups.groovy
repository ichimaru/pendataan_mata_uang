package user_module

class Groups {
String name
String description
    static constraints = {
		name(blank:false)
		description(nullable:true)
    }
	
	String toString() {
		"${name}"
	  }
}
