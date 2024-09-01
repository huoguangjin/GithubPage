package com.example.github_page.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SettingsViewModelTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var app: Application
    private lateinit var sp: SharedPreferences
    private lateinit var settingsViewModel: SettingsViewModel

    @Before
    fun setup() {
        app = ApplicationProvider.getApplicationContext()
        sp = app.getSharedPreferences(SettingsViewModel.SP_NAME, Context.MODE_PRIVATE)
        sp.edit { clear() }
        settingsViewModel = SettingsViewModel(app)
    }

    @Test
    fun test_updateTheme() {
        val theme1 = sp.getString(SettingsViewModel.SP_KEY_THEME, null)
        Assert.assertEquals(null, theme1)

        val themeType = ThemeType.DARK
        settingsViewModel.updateTheme(themeType)

        val theme2 = sp.getString(SettingsViewModel.SP_KEY_THEME, null)
        Assert.assertEquals(themeType, theme2)

        Assert.assertEquals(themeType, settingsViewModel.theme.value)
    }

    @Test
    fun test_updateLanguage() {
        val language1 = sp.getString(SettingsViewModel.SP_KEY_LANGUAGE, null)
        Assert.assertEquals(null, language1)

        val languageType = LanguageType.ENGLISH
        settingsViewModel.updateLanguage(languageType)

        val language2 = sp.getString(SettingsViewModel.SP_KEY_LANGUAGE, null)
        Assert.assertEquals(languageType, language2)

        Assert.assertEquals(languageType, settingsViewModel.language.value)
    }

    @Test
    fun test_isDarkTheme() {
        composeTestRule.setContent {
            settingsViewModel.updateTheme(ThemeType.DARK)
            Assert.assertTrue(settingsViewModel.isDarkTheme)

            settingsViewModel.updateTheme(ThemeType.LIGHT)
            Assert.assertTrue(!settingsViewModel.isDarkTheme)

            settingsViewModel.updateTheme(ThemeType.AUTO)
            Assert.assertEquals(isSystemInDarkTheme(), settingsViewModel.isDarkTheme)
        }
    }
}
