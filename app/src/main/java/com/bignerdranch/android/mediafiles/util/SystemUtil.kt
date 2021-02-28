package com.bignerdranch.android.mediafiles.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object SystemUtil {
    fun timeToDate(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        return sdf.format(Date(time))
    }

    fun stringToDate(time: String): Long {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        try {
            val date = format.parse(time)
            date?.let {
                return it.time
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }
}
