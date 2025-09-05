package com.saidtovar.asimplechat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saidtovar.asimplechat.presentation.chatscreen.ChatScreen
import com.saidtovar.asimplechat.presentation.homescreen.HomeScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {

        composable<HomeScreen>{
            HomeScreen(
                onChatClick = { chatId ->
                    navController.navigate(ChatScreen(chatId))
                }
            )
        }

        composable<ChatScreen> { backStackEntry ->
            ChatScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}