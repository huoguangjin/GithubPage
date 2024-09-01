package com.example.github_page.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Locale

@Composable
fun SettingsHelper(
    settingsViewModel: SettingsViewModel = hiltViewModel(LocalContext.current as AppCompatActivity)
) {
    SettingsLanguage(settingsViewModel)
}

@Composable
fun SettingsLanguage(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val language by settingsViewModel.language
    DisposableEffect(language) {
        val locale = language?.let(Locale::forLanguageTag) ?: Locale.getDefault()
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
        onDispose {
        }
    }
}
