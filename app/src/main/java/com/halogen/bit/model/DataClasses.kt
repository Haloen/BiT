package com.halogen.bit.model

import java.lang.IllegalArgumentException
import java.time.LocalDate

/**
 * This data class represents a plan
 * @property name     Name of plan
 * @property duration Length of focus
 */
data class Plan (
    val name: String,
    val duration: Duration,
) {
    override fun toString(): String {
        return "$name,$duration"
    }

    companion object {
        /**
         * This plan represents an infinitely running plan
         * For those who prefer a stopwatch instead of a timer
         */
        val MARATHON = Plan("Marathon", Duration.ZERO)
    }
}

/**
 * This is a class to store the time for a plan.
 * The java one is not good and kotlin is experimental.
 * Thus I made my own, which will help with debugging errors in time.
 * @property secs seconds of duration, should be between 0 and 59 inclusive
 * @property mins minutes of duration, should be between 0 and 59 inclusive
 * @property hours hours of duration, can be any positive number
 */
data class Duration(
    var hours: Int = 0,
    var mins: Int = 0,
    var secs: Int = 0,
) {
    init {

        //Too many seconds
        if (secs >= 60) {
            mins += secs
            secs %= 60
        }

        //Too many minutes
        if (mins >= 60) {
            hours += mins
            mins %= 60
        }

        //If still negative
        if (secs < 0 || mins < 0 || hours < 0) throw IllegalArgumentException("Negative Duration")

    }

    operator fun dec() = Duration(
        if (mins == 0 && secs == 0) hours - 1 else hours,
        if (secs == 0) (mins - 1 + 60) % 60 else mins,
        (secs - 1 + 60) % 60
    )

    override fun toString(): String {
        return String.format("%02d:%02d:%02d", hours, mins, secs)
    }

    companion object {
        val ZERO = Duration(0, 0, 0)
    }
}