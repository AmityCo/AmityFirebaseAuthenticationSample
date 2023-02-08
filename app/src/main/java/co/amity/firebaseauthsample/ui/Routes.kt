package co.amity.firebaseauthsample.ui

sealed class Route(val route: String) {
    object Login : Route("login")
    object Home : Route("home")
    object Loading : Route("loading")
}