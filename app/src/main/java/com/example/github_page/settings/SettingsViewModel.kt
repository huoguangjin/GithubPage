package com.example.github_page.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    val theme = mutableStateOf(ThemeType.AUTO)

    val isDarkTheme: Boolean
        @Composable get() = when (theme.value) {
            ThemeType.LIGHT -> false
            ThemeType.DARK -> true
            else -> isSystemInDarkTheme()
        }

    fun updateTheme(newTheme: String) {
        theme.value = newTheme
    }
}

object ThemeType {
    const val AUTO = "auto"
    const val LIGHT = "light"
    const val DARK = "dark"
}
