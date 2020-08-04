package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += units.value * value
    return this
}

enum class TimeUnits(val value:Long){
    SECOND(1000L),
    MINUTE(60 * SECOND.value),
    HOUR(60 * MINUTE.value),
    DAY(24 * HOUR.value);

    fun plural(value: Int): String{
        return "$value ${getPluralForm(value, this)}"
    }
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val difference = kotlin.math.abs(this.time - date.time)
    val isPast = this.time < date.time

    return when {
        difference <= TimeUnits.SECOND.value -> "только что"
        difference <= TimeUnits.SECOND.value * 45 ->  getTenseForm("несколько секунд", isPast)
        difference <= TimeUnits.SECOND.value * 75 -> getTenseForm("минуту", isPast)
        difference <= TimeUnits.MINUTE.value * 45 -> getTenseForm(TimeUnits.MINUTE.plural((difference / TimeUnits.MINUTE.value).toInt()), isPast)
        difference <= TimeUnits.MINUTE.value * 75 -> getTenseForm("час", isPast)
        difference <= TimeUnits.HOUR.value * 22 -> getTenseForm(TimeUnits.HOUR.plural((difference / TimeUnits.HOUR.value).toInt()), isPast)
        difference <= TimeUnits.HOUR.value * 26 -> getTenseForm("день", isPast)
        difference <= TimeUnits.DAY.value * 360 -> getTenseForm(TimeUnits.DAY.plural((difference / TimeUnits.DAY.value).toInt()), isPast)
        else -> if(isPast) "более года назад" else "более чем через год"
    }
}

fun getTenseForm(interval: String, isPast: Boolean): String {
    val prefix = if (isPast) "" else "через"
    val postfix = if (isPast) "назад" else ""
    return "$prefix $interval $postfix".trim()
}

fun getPluralForm(amount: Int, units: TimeUnits): String {
    return when(val posAmount = kotlin.math.abs(amount) % 100){
        1 -> Plurals.ONE.get(units)
        in 2..4 -> Plurals.FEW.get(units)
        0, in 5..19 -> Plurals.MANY.get(units)
        else -> getPluralForm(posAmount % 10, units)
    }
}

enum class Plurals(private val second: String, private val minute: String, private val hour: String, private val day: String){
    ONE("секунду", "минуту", "час", "день"),
    FEW("секунды", "минуты", "часа", "дня"),
    MANY("секунд","минут", "часов", "дней");

    fun get(unit: TimeUnits): String {
        return when(unit){
            TimeUnits.SECOND -> second
            TimeUnits.MINUTE -> minute
            TimeUnits.HOUR -> hour
            TimeUnits.DAY -> day
        }
    }
}