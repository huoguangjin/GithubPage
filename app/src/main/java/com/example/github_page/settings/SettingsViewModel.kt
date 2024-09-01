package com.example.github_page.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.example.github_page.R
import java.util.Locale

class SettingsViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    companion object {
        const val SP_NAME = "SP_SETTINGS"
        const val SP_KEY_THEME = "SP_THEME"
        const val SP_KEY_LANGUAGE = "SP_LANGUAGE"
    }

    private val sp: SharedPreferences by lazy {
        app.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    val theme = mutableStateOf(ThemeType.AUTO)
    val language = mutableStateOf(LanguageType.LOCAL)

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
        language.value = sp.getString(SP_KEY_LANGUAGE, null)
    }

    fun updateTheme(newTheme: String) {
        theme.value = newTheme
        sp.edit {
            putString(SP_KEY_THEME, newTheme)
        }
    }

    fun updateLanguage(newLanguage: String?) {
        language.value = newLanguage
        sp.edit {
            if (newLanguage != null) {
                putString(SP_KEY_LANGUAGE, newLanguage)
            } else {
                remove(SP_KEY_LANGUAGE)
            }
        }
    }
}

object ThemeType {
    const val AUTO = "auto"
    const val LIGHT = "light"
    const val DARK = "dark"

    val ALL = arrayOf(
        AUTO,
        LIGHT,
        DARK,
    )

    @StringRes
    fun toStringResId(lang: String?): Int = when (lang) {
        LIGHT -> R.string.theme_light
        DARK -> R.string.theme_dark
        else -> R.string.theme_auto
    }
}

object LanguageType {
    val LOCAL: String? = null
    val CHINESE: String = Locale.CHINESE.toLanguageTag()
    val ENGLISH: String = Locale.ENGLISH.toLanguageTag()

    val ALL = arrayOf(
        LOCAL,
        CHINESE,
        ENGLISH,
    )

    @StringRes
    fun toStringResId(lang: String?): Int = when (lang) {
        CHINESE -> R.string.language_chinese
        ENGLISH -> R.string.language_english
        else -> R.string.language_local
    }
}
