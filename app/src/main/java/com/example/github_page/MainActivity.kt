package com.example.github_page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.github_page.auth.LoginPage
import com.example.github_page.drawer.MainDrawer
import com.example.github_page.drawer.ProfileViewModel
import com.example.github_page.home.HomePage
import com.example.github_page.ui.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppScaffold()
            }
        }
    }
}

@Composable
fun AppScaffold(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.HOME

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val startDestination = Routes.HOME
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.HOME) {
            MainDrawer(navController, drawerState, profileViewModel = profileViewModel) {
                HomePage(
                    navController,
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                )
            }
        }
        composable(Routes.LOGIN) {
            LoginPage(navController)
        }
    }
}
