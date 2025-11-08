package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import android.content.Context

@Composable
fun MyNavigation(navController: NavHostController) {
    val startDestination = if (isUserLoggedIn(navController.context)) {
        Home.route
    } else {
        Onboarding.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Onboarding.route) {
            Onboarding(
                onRegisterSuccess = {
                    // Navigate to Home after successful registration
                    navController.navigate(Home.route) {
                        popUpTo(Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Home.route) {
            Home(
                onProfileClick = {
                    navController.navigate(Profile.route)
                }
            )
        }

        composable(Profile.route) {
            Profile(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    clearUserData(navController.context)
                    navController.navigate(Onboarding.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }
            )
        }

    }
}

private fun isUserLoggedIn(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("isLoggedIn", false)
}

private fun clearUserData(context: Context) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPref.edit().clear().apply()
}