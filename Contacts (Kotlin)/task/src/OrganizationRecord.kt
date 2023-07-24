package contacts

data class OrganizationRecord(
    var organizationName: String,
    var address: String,
    override var number: String = ""
    ) : ContactRecord(){

    override fun editContact(property: String, newValue: String) {
        when(property) {
            "name" -> organizationName = newValue
            "address" -> address = newValue
            "number" -> number = newValue
        }
        println("Saved")
        lastEditDate = java.time.LocalDateTime.now()
        printProperties()
    }

    override fun getEditableProperties() {
        println("Select a field (name, address, number):")
    }

    override fun getProperties(): String {
        return (organizationName + address + number).lowercase()
    }

    override fun getPropertyValue(property: String): String {
        return when(property) {
            "name" -> organizationName
            "address" -> address
            "number" -> number
            else -> {"Bad property!"}
        }
    }

    override fun printProperties() {
        print("Organization name: $organizationName\n" +
                "Address: $address\n" +
                "Number: $number\n" +
                "Time created: $creationDate\n" +
                "Time last edit: $lastEditDate\n")
    }

}