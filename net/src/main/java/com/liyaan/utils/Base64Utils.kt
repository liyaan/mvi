package com.liyaan.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64

object Base64Utils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun encodeToString(originalString:String):String = Base64.getEncoder().encodeToString(originalString.toByteArray())
    @RequiresApi(Build.VERSION_CODES.O)
    fun decode(encodedString:String):String = String(Base64.getDecoder().decode(encodedString))
}