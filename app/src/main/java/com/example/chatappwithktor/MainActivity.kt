package com.example.chatappwithktor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatappwithktor.persntaion.chat.Chatscreen
import com.example.chatappwithktor.persntaion.username.UserNameScreen
import com.example.chatappwithktor.ui.theme.ChatAppWithKtorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppWithKtorTheme {
                val navConteoler= rememberNavController()
                NavHost(navController = navConteoler, startDestination = "username_screen" ){

                    composable("username_screen"){
                        UserNameScreen(onNavgite = navConteoler::navigate)
                    }
                    composable(
                        route = "chat_screen/{userName}",
                        arguments = listOf(
                            navArgument(name="userName"){
                                type= NavType.StringType
                                nullable = true
                            }
                        )
                    ){
                        val userName= it.arguments?.getString("userName")
                        Chatscreen(userName)
                    }
                }

            }
        }
    }
}


