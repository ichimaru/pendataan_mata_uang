package pendataan_mata_uang

class Currency implements Serializable {
	String name
	String description = ""
	String sym
	String isTransactional = "N"
	String deleteFlag = "N"
	Date dateCreated
	Date lastUpdated
	
	
	
    static constraints = {
		name(blank: false, maxSize: 100)
		description(maxSize: 500)
		sym(blank: false, maxSize: 5)
		dateCreated(blank: false)
		lastUpdated()
    }
	
	String toString() {
		"${sym} - ${name} - ${isTransactional}"
	}

	static mapping = {
		cache true
	}
	
	def beforeInsert = {
		//        createdBy = springSecurityService.principal.username
				dateCreated = new Date()
			}
	def beforeUpdate = {
		//        updatedBy = springSecurityService.principal.username
				lastUpdated = new Date()
			}
}
