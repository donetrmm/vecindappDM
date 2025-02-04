package com.moviles.vecindapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moviles.vecindapp.login.presentation.LoginScreen
import com.moviles.vecindapp.neighborhoods.presentation.NeighborhoodsScreen
import com.moviles.vecindapp.register.presentation.RegisterScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val NEIGHBORHOODS = "neighborhoods"
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.REGISTER) { RegisterScreen(navController) }
        composable(Routes.NEIGHBORHOODS) { NeighborhoodsScreen(navController) }
    }
}