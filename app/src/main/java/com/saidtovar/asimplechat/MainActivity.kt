package com.saidtovar.asimplechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.saidtovar.asimplechat.presentation.navigation.Navigation
import com.saidtovar.asimplechat.presentation.ui.theme.ASimpleChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ASimpleChatTheme {
                Navigation()
            }
        }
    }
}