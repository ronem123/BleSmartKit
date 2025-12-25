package com.ram.mandal.blesmartkit.util

object ValidationUtil {

    fun checkIfValidArgNews(str: String?): Boolean {
        return !(str.isNullOrEmpty() || str == "{country}" || str == "{language}" || str == "{source}")
    }

}