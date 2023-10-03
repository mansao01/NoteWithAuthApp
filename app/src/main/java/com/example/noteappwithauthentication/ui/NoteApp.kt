package com.example.noteappwithauthentication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteappwithauthentication.ui.navigation.Screen
import com.example.noteappwithauthentication.ui.screen.add.AddScreen
import com.example.noteappwithauthentication.ui.screen.add.AddViewModel
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
    startDestination: String
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
                navigateToHome = {
                    navController.navigate(Screen.Home.route)
                },
                navigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                uiState = homeViewModel.uiState,
                navigateToAdd = {
                    navController.navigate(Screen.Add.route)
                })
        }

        composable(Screen.Add.route) {
            val addViewModel: AddViewModel = viewModel(factory = AddViewModel.Factory)
            AddScreen(
                uiState = addViewModel.uiState,
                addViewModel = addViewModel,
                navigateToHome = {
                    navController.navigate(Screen.Home.route)
                })
        }
    }
}