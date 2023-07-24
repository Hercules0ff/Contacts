package contacts

class PhoneBook(private var filePath: String? = null) {
    private var contactList = mutableListOf<ContactRecord?>()

    init {
        while (true) {
            print("\n[menu] Enter action (add, list, search, count, exit):")
            val action = readln()
            when(action.lowercase()) {
                "add" -> newContact()
                "list" -> if (contactInfo()) break
                "search" -> {
                    if (search()) {
                        break
                    }
                }
                "count" -> countOfContacts()
                "exit" -> break
            }
        }
    }

    private fun newContact() {
        while (true) {
            print("Enter the type (person, organization):")
            when (readln()) {
                "person" -> newPersonContact()
                "organization" -> newOrganizationContact()
            }
            return
        }
    }

    private fun newOrganizationContact() {
        print("Enter the organization name:")
        val organizationName = readln()
        print("Enter the address:")
        val address = readln()
        print("Enter the number:")
        val telephoneNumber = isNumberCorrect(readln())
        val newContactRecord = OrganizationRecord(
            organizationName = organizationName,
            address = address,
            number = telephoneNumber)
        contactList.add(newContactRecord)
        println("The record added.\n")
    }

    private fun newPersonContact() {
        print("Enter the name:")
        val name = readln()
        print("Enter the surname:")
        val surname = readln()
        print("Enter the birth date:")
        val birthDate = isBirthDateCorrect(readln())
        print("Enter the gender (M, F):")
        val gender = isGenderCorrect(readln())
        print("Enter the number:")
        val telephoneNumber = isNumberCorrect(readln())
        val newContactRecord = PersonRecord(name =  name,
            surname = surname,
            birthDate = birthDate,
            gender = gender,
            number = telephoneNumber)
        contactList.add(newContactRecord)
        println("The record added.\n")
    }

    private fun info() {
        mappedInfo(contactList)
    }

    private fun contactInfo(): Boolean {
        info()
        while (true) {
            print("\n[list] Enter action ([number], back):")
            when(val action = readln()) {
                "back" -> return false
                else -> {
                    try {
                        val contact = contactList[action.toInt() - 1]
                        contact?.printProperties()
                        if (record(contact)) return false
                    } catch (e: Exception) {
                        return true
                    }
                }
            }
        }
    }

    private fun removeContact(contact: ContactRecord?) {
        contactList.remove(contact)
    }

    private fun countOfContacts() {
        println("The Phone Book has ${contactList.size} records.")
    }

    private fun isNumberCorrect(telephoneNumber: String): String {
        val phoneNumberRegex = Regex("""^\+?\w*(?:[- ]?(?:\(\w{2,}\)|\w{2,}))?(?:[- ]\w{2,})*$""")
        return if (phoneNumberRegex.matches(telephoneNumber)) {
            telephoneNumber
        } else {
            println("Wrong number format!")
            "[no number]"
        }
    }

    private fun isGenderCorrect(gender: String): String {
        return if (gender.isNotEmpty() && (gender == "M" || gender == "F")) {
            gender
        } else {
            println("Bad gender!")
            "[no data]"
        }
    }

    private fun isBirthDateCorrect(birthDate: String): String {
        return try {
            java.time.LocalDate.parse(birthDate)
            birthDate
        } catch (e: RuntimeException) {
            println("Bad birth date!")
            "[no data]"
        }
    }

    private fun mappedInfo(list: List<ContactRecord?>) {
        list.mapIndexed { index, contactRecord ->
            if (contactRecord is PersonRecord) {
                println("${index + 1}. ${contactRecord.name} ${contactRecord.surname}")
            } else {
                contactRecord as OrganizationRecord
                println("${index + 1}. ${contactRecord.organizationName}")
            }
        }
    }

    private fun search():Boolean {
        print("Enter search query:")
        val search = readln()
        val allInfo = contactList.filter { contactRecord -> contactRecord?.getProperties()?.contains(".*$search.*".toRegex())
            ?: false }
        println("Found ${allInfo.size} results:")
//        if (allInfo.isEmpty()) {
//            return
//        }
        mappedInfo(allInfo)
        while (true) {
            print("\n[search] Enter action ([number], back, again):")
            when(val action = readln()) {
                "again" -> search()
                "back" -> return false
                else -> {
                    try {
                        val contact = allInfo[action.toInt() - 1]
                        contact?.printProperties()
                        record(contact)
                    } catch (e: Exception) {
                        return true
                    }
                }
            }
        }
    }

    private fun record(contact: ContactRecord?):Boolean {
        while (true) {
            print("\n[record] Enter action (edit, delete, menu):")
            when(readln()) {
                "edit" -> {
                    contact?.getEditableProperties()
                    val property = readln()
                    print("Enter $property:")
                    contact?.editContact(property, readln())
                }
                "delete" -> removeContact(contact)
                "menu" -> return true
            }
        }
    }
}