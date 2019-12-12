package com.example.mynotes

import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter {
    companion object {
        fun getFormattedDate(date: Date): String {
            val pattern = "dd/mm/yyyy HH:mm"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            return simpleDateFormat.format(date)
        }
    }
}