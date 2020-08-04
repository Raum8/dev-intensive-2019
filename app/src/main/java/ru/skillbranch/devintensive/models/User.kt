package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.format
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = Date(),
    val isOnline: Boolean = false
) {
    fun info(): String {
        return "$firstName $lastName $isOnline lastVisit: ${lastVisit?.format("HH:mm")}"
    }

    data class Builder(
        var id: String = "",
        var firstName: String? = null,
        var lastName: String? = null,
        var avatar: String? = null,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
    ){
        fun id(value: String) = apply { id = value }
        fun firstName(value: String? = null) = apply { firstName = value }
        fun lastName(value: String? = null) = apply { lastName = value }
        fun avatar(value: String? = null) = apply { avatar = value }
        fun rating(value: Int = 0) = apply { rating = value }
        fun respect(value: Int = 0) = apply { respect = value }
        fun lastVisit(value: Date = Date()) = apply { lastVisit = value }
        fun isOnline(value: Boolean = false) = apply { isOnline = value }

        fun build() = User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
    }
}