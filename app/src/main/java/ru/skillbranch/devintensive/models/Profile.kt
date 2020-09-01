package ru.skillbranch.devintensive.models

import android.os.Build
import androidx.annotation.RequiresApi
import ru.skillbranch.devintensive.utils.Utils

data class Profile(
    val firstName: String,
    val lastName: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {
    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    val nickName = Utils.transliterations("$firstName $lastName")
    private val rank: String = "Junior Android Developer"
    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.N)
    fun toMap(): Map<String, Any> = mapOf(
        "nickName" to nickName,
        "rank" to rank,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )
}