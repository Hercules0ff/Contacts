package contacts

data class PersonRecord(
    var name: String,
    var surname: String,
    var birthDate: String,
    var gender: String,
    override var number: String = ""
): ContactRecord() {

    override fun editContact(property: String, newValue: String) {
        when(property) {
            "name" -> name = newValue
            "surname" -> surname = newValue
            "birthDate" -> birthDate = newValue
            "gender" -> gender = newValue
            "number" -> number = newValue
        }
        println("Saved")
        lastEditDate = java.time.LocalDateTime.now()
        printProperties()
    }

    override fun getEditableProperties() {
        println("Select a field (name, surname, birthDate, gender, number):")
    }

    override fun getProperties(): String {
        return (name + surname + birthDate + gender + number).lowercase()
    }

    override fun getPropertyValue(property: String): String {
        return when(property) {
            "name" -> name
            "surname" -> surname
            "birth" -> birthDate
            "gender" -> gender
            "number" -> number
            else -> {"Bad property!"}
        }
    }

    override fun printProperties() {
        print("Name: $name\n" +
                "Surname: $surname\n" +
                "Birth date: $birthDate\n" +
                "Gender: $gender\n" +
                "Number: $number\n" +
                "Time created: $creationDate\n" +
                "Time last edit: $lastEditDate\n")
    }

}