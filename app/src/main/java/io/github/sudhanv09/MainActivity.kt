package io.github.sudhanv09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.github.sudhanv09.presentation.navigation.AppNavigation
import io.github.sudhanv09.ui.theme.WaletTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaletTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}
