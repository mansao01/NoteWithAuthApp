package com.example.noteappwithauthentication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteappwithauthentication.ui.navigation.Screen
import com.example.noteappwithauthentication.ui.screen.home.HomeScreen
import com.example.noteappwithauthentication.ui.screen.home.HomeViewModel
import com.example.noteappwithauthentication.ui.screen.login.LoginScreen
import com.example.noteappwithauthentication.ui.screen.login.LoginViewModel
import com.example.noteappwithauthentication.ui.screen.register.RegisterScreen
import com.example.noteappwithauthentication.ui.screen.register.RegisterViewModel

@Composable
fun NoteApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination:String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel =
                viewModel(factory = RegisterViewModel.Factory)
            RegisterScreen(
                uiState = registerViewModel.uiState,
                navigateToLogin = { navController.navigate(Screen.Login.route) })
        }

        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
            LoginScreen(
                uiState = loginViewModel.uiState,
                navigateToHome = { userId ->
                    navController.navigate(Screen.Home.createRoute(userId))
                },
                navigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Home.route, arguments = listOf(navArgument("userId"){
            type = NavType.IntType
        })){ data ->
            val userId =data.arguments?.getInt("userId") ?: -1
            val homeViewModel:HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(userId = userId, uiState = homeViewModel.uiState)
        }
    }
}