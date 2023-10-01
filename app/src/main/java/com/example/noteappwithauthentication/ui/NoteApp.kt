package com.example.noteappwithauthentication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteappwithauthentication.ui.navigation.Screen
import com.example.noteappwithauthentication.ui.screen.home.HomeScreen
import com.example.noteappwithauthentication.ui.screen.login.LoginScreen
import com.example.noteappwithauthentication.ui.screen.login.LoginViewModel
import com.example.noteappwithauthentication.ui.screen.register.RegisterScreen
import com.example.noteappwithauthentication.ui.screen.register.RegisterViewModel

@Composable
fun NoteApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Register.route,
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
                }
            )
        }

        composable(Screen.Home.route){
            HomeScreen()
        }
    }
}