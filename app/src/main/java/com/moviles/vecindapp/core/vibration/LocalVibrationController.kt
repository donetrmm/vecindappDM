package com.moviles.vecindapp.core.vibration

import androidx.compose.runtime.staticCompositionLocalOf

val LocalVibrationController = staticCompositionLocalOf<VibrationController> {
    error("No se ha proporcionado un vcontroller")
}
