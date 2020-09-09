package ru.skillbranch.devintensive.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

@ExperimentalStdlibApi
@RequiresApi(Build.VERSION_CODES.N)
fun User.toUserView(): UserView {
    val nickName = Utils.transliterations("$firstName $lastName")
    val initials = Utils.toInitials(firstName,lastName)
    val status = if(lastVisit == null) "Еще ни разу не был" else if(isOnline) "online" else "Последний раз был ${lastVisit?.humanizeDiff(Date())}"
    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        avatar = avatar,
        status = status,
        initials = initials
    )
}


