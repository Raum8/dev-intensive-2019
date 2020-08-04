package ru.skillbranch.devintensive.extensions

fun String.truncate(numSymbols: Int = 16): String {
    if (this.length <= numSymbols)
        return ""
    var numOfSpaces: Int = 0
    var tmpStr = this.removeRange(numSymbols, this.length)

    for (index in tmpStr.length - 1 downTo 0) {
        if (tmpStr[index] == ' ')
            ++numOfSpaces
        else break
    }

    tmpStr = if (tmpStr[tmpStr.length - 1] == ' ' && numOfSpaces == 1)
        tmpStr.replaceRange(tmpStr.length - 1 until tmpStr.length, "...")
    else if(tmpStr.trim() == this.trim()) {
        tmpStr
    }
    else {
        "$tmpStr..."
    }
    return tmpStr
}

fun String.stripHtml(): String {
    if(this == "" || this == " ")
        return ""
//    val newStr = Jsoup.parse(this).text().toString(); с помощью Jsoup изиии   in gradle implementation 'org.jsoup:jsoup:1.13.1'
    var indexFirst = this.indexOf('<')
    var indexLast = this.indexOf('>')
    var newStr = this.removeRange(indexFirst..indexLast)
    indexFirst = newStr.indexOf('<')
    indexLast = newStr.indexOf('>')
    newStr = newStr.removeRange(indexFirst..indexLast).trim()
    while("  " in newStr)
        newStr = newStr.replace("  ", " ")
    return newStr
}