package com.moviles.vecindapp.core.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class DefaultVibrationController(private val context: Context) : VibrationController {
    override fun vibrate(duration: Long) {
        @Suppress("DEPRECATION")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
}
