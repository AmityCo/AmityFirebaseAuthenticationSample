package co.amity.firebaseauthsample.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.amity.firebaseauthsample.R
import co.amity.firebaseauthsample.ui.home.HomeScreen
import co.amity.firebaseauthsample.ui.login.LoginScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainNavigation(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val userBannedText = stringResource(id = R.string.login_user_banned)
    val userErrorText = stringResource(id = R.string.login_user_error)

    LaunchedEffect(lifecycleOwner) {
        viewModel.uiState.collect { state ->
            when (state) {
                MainUiState.Banned -> showSnackbar(scope, snackbarHostState, userBannedText)
                is MainUiState.Error -> showSnackbar(scope, snackbarHostState, userErrorText)
                MainUiState.Loading -> { /* no-op */
                }

                MainUiState.LoggedIn -> navController.navigate(Route.Home.route) { popUpTo(0) }
                MainUiState.LoggedOut -> navController.navigate(Route.Login.route) { popUpTo(0) }
            }
        }
    }

    NavHost(navController = navController, startDestination = Route.Loading.route) {
        composable(Route.Loading.route) { LoadingScreen() }
        composable(Route.Home.route) { HomeScreen(modifier) }
        composable(Route.Login.route) {
            LoginScreen(
                modifier = modifier,
                navigateToUsers = { navController.navigate(Route.Home.route) { popUpTo(0) } })
        }
    }
}

private fun showSnackbar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    text: String
) {
    scope.launch { snackbarHostState.showSnackbar(text) }
}