package com.example.github_page.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.github_page.R
import com.example.github_page.auth.AuthViewModel
import com.example.github_page.bean.GithubUser
import com.example.github_page.ui.LoadState
import com.example.github_page.ui.Routes
import kotlinx.coroutines.launch

@Composable
fun MainDrawer(
    navController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.HOME

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerState) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .width(dimensionResource(id = R.dimen.drawer_width))) {
                    Spacer(Modifier.height(12.dp))

                    DrawerHeader(
                        navController,
                        profileViewModel = profileViewModel,
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text(text = stringResource(id = R.string.page_home)) },
                        selected = currentRoute == Routes.HOME,
                        onClick = {
                            navController.navigate(Routes.HOME)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        label = { Text(text = stringResource(id = R.string.page_dashboard)) },
                        selected = currentRoute == Routes.DASHBOARD,
                        onClick = {
                            navController.navigate(Routes.DASHBOARD)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                        label = { Text(text = stringResource(id = R.string.menu_logout)) },
                        selected = false,
                        onClick = {
                            authViewModel.logout()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }) {
        content()
    }
}

@Composable
private fun DrawerHeader(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val viewState = profileViewModel.viewState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.drawer_header_height))
            .padding(dimensionResource(id = R.dimen.drawer_header_padding))
    ) {
        when (viewState.loading) {
            LoadState.Loading -> {
                CircularProgressIndicator()
            }

            is LoadState.Success -> {
                if (viewState.isLogin) {
                    LoginUserHeader(user = viewState.user!!)
                } else {
                    NotLoginUserHeader {
                        navController.navigate(Routes.LOGIN)
                    }
                }
            }

            is LoadState.Error -> {
                RetryGetUserProfileHeader(profileViewModel)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoginUserHeader(user: GithubUser) {
    Row {
        GlideImage(
            model = user.avatarUrl,
            contentDescription = stringResource(id = R.string.avatar),
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = user.name,
            modifier = Modifier.padding(12.dp),
            fontSize = 20.sp,
        )
    }
}

@Composable
fun NotLoginUserHeader(doLogin: () -> Unit) {
    Button(onClick = {
        doLogin()
    }) {
        Text(text = "login")
    }
}

@Composable
fun RetryGetUserProfileHeader(profileViewModel: ProfileViewModel) {
    Button(onClick = {
        profileViewModel.getCurrentUser()
    }) {
        Text(text = "retry to refresh user profile")
    }
}
