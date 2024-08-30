package com.example.github_page.settings

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.github_page.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    settingsViewModel: SettingsViewModel = hiltViewModel(LocalContext.current as AppCompatActivity),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.page_settings)) },
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { innerPadding ->
        val theme by settingsViewModel.theme

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalDivider()

            SettingsDropdownRow(
                key = stringResource(id = R.string.settings_theme),
                value = theme,
            ) { closeMenu ->
                arrayOf(ThemeType.AUTO, ThemeType.LIGHT, ThemeType.DARK).forEach {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = {
                            settingsViewModel.updateTheme(it)
                            closeMenu()
                        }
                    )
                }
            }

            HorizontalDivider()
        }
    }
}

@Composable
fun SettingsDropdownRow(
    key: String,
    value: String,
    content: @Composable ColumnScope.(() -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.weight(1.0f))

        Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    Icons.AutoMirrored.Default.KeyboardArrowRight,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = value
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                ) {
                    content { expanded = !expanded }
                }
            }
        }
    }
}
