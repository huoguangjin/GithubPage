package com.example.github_page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.github_page.auth.LoginPage
import com.example.github_page.dashboard.DashboardPage
import com.example.github_page.drawer.MainDrawer
import com.example.github_page.drawer.ProfileViewModel
import com.example.github_page.home.HomePage
import com.example.github_page.issue.IssuePage
import com.example.github_page.search.SearchPage
import com.example.github_page.settings.SettingsPage
import com.example.github_page.ui.Routes
import com.example.github_page.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
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
        composable(Routes.SEARCH) {
            SearchPage()
        }
        composable(Routes.DASHBOARD) {
            MainDrawer(navController, drawerState, profileViewModel = profileViewModel) {
                DashboardPage(
                    navController,
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                )
            }
        }
        composable(
            Routes.ISSUE_ROUTE,
            arguments = listOf(
                navArgument(Routes.ISSUE_OWNER_ARG) { type = NavType.StringType; },
                navArgument(Routes.ISSUE_REPO_ARG) { type = NavType.StringType; },
            )
        ) {
            val owner = it.arguments?.getString(Routes.ISSUE_OWNER_ARG)!!
            val repo = it.arguments?.getString(Routes.ISSUE_REPO_ARG)!!
            IssuePage(navController, owner, repo)
        }
        composable(Routes.LOGIN) {
            LoginPage(navController)
        }
        composable(Routes.SETTINGS) {
            MainDrawer(navController, drawerState, profileViewModel = profileViewModel) {
                SettingsPage()
            }
        }
    }
}
