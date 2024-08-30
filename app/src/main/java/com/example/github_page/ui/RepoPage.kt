package com.example.github_page.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun RepoPage(
    navController: NavHostController,
    user: String,
    name: String,
) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "$user/$name")
    }
}
