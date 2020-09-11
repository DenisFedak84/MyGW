package com.example.mygw.utils

import java.util.regex.Pattern

fun String.isEmailAddressValid(): Boolean {
    // 3
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    // 4
    return matcher.matches()
}