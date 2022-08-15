package com.ikrima.practice.dicoding.githubuserappdicoding.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun toSimpleString(date : Date) : String {
        return SimpleDateFormat("EEE, dd MMM yyy").format(date)
    }

}