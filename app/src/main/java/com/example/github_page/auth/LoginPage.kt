package com.example.github_page.auth

import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.github_page.net.LoginApi
import com.example.github_page.ui.Routes

@Composable
fun LoginPage(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val isLogin by authViewModel.isLogin.observeAsState()

    LaunchedEffect(isLogin) {
        if (isLogin == true) {
            navController.navigate(Routes.HOME)
        }
    }

    var loading by remember { mutableStateOf(true) }

    Column {
        if (loading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

        DisposableEffect(Unit) {
            onDispose {

            }
        }

        DisposableEffect(Unit) {
            onDispose {
                CookieManager.getInstance().apply {
                    removeAllCookies {}
                    removeSessionCookies {}
                    flush()
                }
            }
        }

        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        loading = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        loading = false
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        val uri = request.url
                        if (uri.toString().contains(LoginApi.REDIRECT_URI)) {
                            val code = uri.getQueryParameter("code")
                            if (code != null) {
                                loading = true
                                authViewModel.getAccessToken(code)
                                return true
                            }
                        }

                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }

                val authUrl = LoginApi.AUTHORIZE_URL
                loadUrl(authUrl)
            }
        })
    }
}
