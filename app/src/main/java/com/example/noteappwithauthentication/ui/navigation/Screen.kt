package com.example.noteappwithauthentication.ui.navigation

sealed class Screen(val route:String){

    object Login:Screen("login")
    object Register:Screen("register")
    object Home:Screen("home")
    object Add:Screen("home/add/{userId}"){
        fun createRoute(userId:Int) = "home/add/$userId"
    }

    object Edit:Screen("home/{noteId}"){
        fun createRoute( noteId:Int) = "home/$noteId"
    }

}