package com.example.firebasetest.utils


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.firebasetest.data.repo.AuthRepository
import com.example.firebasetest.screens.ChatRoomListScreen
import com.example.firebasetest.screens.LoginScreen
import com.example.firebasetest.screens.Screen
import com.example.firebasetest.screens.SignUpScreen
import com.example.firebasetest.screens.ChatScreen
import com.example.firebasetest.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier,authRepository: AuthRepository,startDestination: String){

    NavHost(navController=navController, startDestination=startDestination){
        composable(Screen.LoginScreen.route){
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignupScreen.route)},
                onSignInSuccess = {
                    navController.navigate(Screen.ChatRoomsScreen.route)}
            )

        }
        composable(Screen.SignupScreen.route){
            SignUpScreen(onNavigateToLogin = {
                // Clear back stack when navigating
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.SignupScreen.route) {
                        inclusive = true // Remove sign-up screen from stack
                    }
                }
            })
        }
        composable(Screen.ChatRoomsScreen.route){
            val authViewModel: AuthViewModel = viewModel{
                AuthViewModel(authRepository)
            }
            ChatRoomListScreen(
                onJoinClicked = {roomId ->
                    try {
                        navController.navigate("${Screen.ChatScreen.route}/$roomId")
                    } catch (e:Exception){
                        println("Navigation Failed: ${e.message}")
                    }
                },
                onLogoutClicked = {

                    authViewModel.signOut() // Call your signOut function
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.ChatRoomsScreen.route) { inclusive = true }
                    }
                }

            )
        }

        composable(route = "${Screen.ChatScreen.route}/{roomId}",
            arguments = listOf(navArgument("roomId") { type = NavType.StringType }))
        { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)
        }
    }
}