package com.example.firebasetest

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.firebasetest.screens.Screen
import com.example.firebasetest.ui.theme.FireBaseTestTheme
import com.example.firebasetest.utils.Injection
import com.example.firebasetest.utils.NavigationGraph

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authRepository = Injection.provideAuthRepository()
        val startDestination = if (authRepository.isLoggedIn()) {
            Screen.ChatRoomsScreen.route
        } else {
            Screen.LoginScreen.route
        }

        setContent {
            val navController= rememberNavController()
            FireBaseTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavigationGraph(navController, modifier=Modifier.padding(innerPadding),
                            authRepository=authRepository, startDestination=startDestination)
                }
            }
        }
    }
}



