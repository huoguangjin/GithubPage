package com.example.github_page.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class SettingsViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    companion object {
        private const val SP_NAME = "SP_SETTINGS"
        private const val SP_KEY_THEME = "SP_THEME"
    }

    private val sp: SharedPreferences by lazy {
        app.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    val theme = mutableStateOf(ThemeType.AUTO)

    val isDarkTheme: Boolean
        @Composable get() = when (theme.value) {
            ThemeType.LIGHT -> false
            ThemeType.DARK -> true
            else -> isSystemInDarkTheme()
        }

    init {
        readSettings()
    }

    private fun readSettings() {
        theme.value = sp.getString(SP_KEY_THEME, ThemeType.AUTO)!!
    }

    fun updateTheme(newTheme: String) {
        theme.value = newTheme
        sp.edit {
            putString(SP_KEY_THEME, newTheme)
        }
    }
}

object ThemeType {
    const val AUTO = "auto"
    const val LIGHT = "light"
    const val DARK = "dark"
}
