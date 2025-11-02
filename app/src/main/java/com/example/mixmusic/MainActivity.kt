package com.example.mixmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.mixmusic.navigation.MainScreen
import com.example.mixmusic.ui.theme.MiPortafolioDeProyectosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPortafolioDeProyectosTheme (
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ){
                MainScreen()
                }
            }
        }
    }


