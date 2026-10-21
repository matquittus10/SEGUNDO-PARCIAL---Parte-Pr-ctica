package com.example.segundoparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.segundoparcial.ui.home.HomeView
import com.example.segundoparcial.ui.theme.SegundoParcialTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SegundoParcialTheme {
                HomeView()
            }
        }
    }
}
