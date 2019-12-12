package com.example.mynotes

import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter {
    companion object {
        fun getFormattedDate(date: Date): String {
            val pattern = "yyyy-MM-dd HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            return simpleDateFormat.format(date)
        }
    }
}