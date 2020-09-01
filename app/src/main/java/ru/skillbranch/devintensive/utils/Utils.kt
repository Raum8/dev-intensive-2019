package ru.skillbranch.devintensive.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.equals("") || fullName.equals(" ") || fullName.equals(null))
            return null to null
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    fun transliterations(payload: String, divider: String = "_"): String {
        val dictionAnglRus = mapOf(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e",
            'ж' to "zh", 'з' to "z", 'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l", 'м' to "m",
            'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t", 'у' to "u",
            'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh", 'щ' to "sh'", 'ъ' to "",
            'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya"
        )
        val firstName: String = parseFullName(payload).first.toString().toLowerCase(Locale.ROOT)
        val lastName: String = parseFullName(payload).second.toString().toLowerCase(Locale.ROOT)

        var newFirstName: String = ""
        var newLastName: String = ""

        for (char in firstName.indices) {
            newFirstName += dictionAnglRus.getOrDefault(firstName[char], "")
        }
        for (char in lastName.indices) {
            newLastName += dictionAnglRus.getOrDefault(lastName[char], "")
        }

        return when {
            newFirstName == "" && newLastName == "" -> "${firstName.capitalize()}$divider${lastName.capitalize()}"
            newFirstName == "" -> "${firstName.capitalize()}$divider${newLastName.capitalize()}"
            newLastName == "" -> "${newFirstName.capitalize()}$divider${lastName.capitalize()}"
            else -> "${newFirstName.capitalize()}$divider${newLastName.capitalize()}"
        }

    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (!firstName.equals(null) || !lastName.equals(null)) {
            val firstInitial: Char? = when (firstName) {
                "" -> null
                " " -> null
                null -> null
                else -> {
                    firstName[0].toUpperCase()
                }
            }
            val lastInitial: Char? = when (lastName) {
                "" -> null
                " " -> null
                null -> null
                else -> {
                    lastName[0].toUpperCase()
                }
            }
            return if (firstInitial != null && lastInitial != null)
                "$firstInitial$lastInitial"
            else {
                if (firstInitial != null)
                    "$firstInitial"
                else
                    "$lastInitial"
            }
        } else
            return null
    }
}