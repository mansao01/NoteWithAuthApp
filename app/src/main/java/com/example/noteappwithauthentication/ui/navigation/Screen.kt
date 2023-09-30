package com.example.noteappwithauthentication.ui.navigation

sealed class Screen(val route:String){

    object Login:Screen("login")
    object Register:Screen("register")
    object Home:Screen("login/{userId}"){
        fun createRoute(userId:Int) ="login/$userId"
    }
}