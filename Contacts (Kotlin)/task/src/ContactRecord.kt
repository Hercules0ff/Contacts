package contacts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
abstract class ContactRecord(
    open var number: String = "",
    val creationDate: java.time.LocalDateTime = java.time.LocalDateTime.now(),
    var lastEditDate: java.time.LocalDateTime = java.time.LocalDateTime.now()
) {
    abstract fun editContact(property: String, newValue: String)

    abstract fun getEditableProperties()

    abstract fun getProperties(): String

    abstract fun getPropertyValue(property: String): String

    abstract fun printProperties()
}
