package com.example.gesturaai

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.gesturaai.ui.theme.GesturaAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GesturaAITheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember { mutableStateOf(AppScreen.Login) }
                    var selectedMainTab by remember { mutableStateOf(MainTab.Home) }

                    when (currentScreen) {
                        AppScreen.Login -> {
                            LoginScreen(
                                onLoginClick = { email, _ ->
                                    Toast.makeText(
                                        this,
                                        getString(R.string.login_toast, email),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = AppScreen.Main
                                },
                                onSignUpClick = {
                                    currentScreen = AppScreen.SignUp
                                }
                            )
                        }

                        AppScreen.SignUp -> {
                            SignUpScreen(
                                onRegisterClick = { fullName, email, _, _ ->
                                    Toast.makeText(
                                        this,
                                        getString(R.string.register_toast, fullName.ifBlank { email }),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    currentScreen = AppScreen.Main
                                },
                                onBackToLoginClick = {
                                    currentScreen = AppScreen.Login
                                }
                            )
                        }

                        AppScreen.Main -> {
                            MainPageScreen(
                                selectedTab = selectedMainTab,
                                onTabSelected = { selectedMainTab = it },
                                onStartTranslatingClick = {
                                    selectedMainTab = MainTab.Translate
                                },
                                onLogoutClick = {
                                    currentScreen = AppScreen.Login
                                    // Reset tab for next time
                                    selectedMainTab = MainTab.Home
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private enum class AppScreen {
    Login,
    SignUp,
    Main
}
