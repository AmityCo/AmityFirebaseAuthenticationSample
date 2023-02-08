package co.amity.firebaseauthsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.amity.firebaseauthsample.data.remote.AuthRepository
import com.amity.socialcloud.sdk.core.session.model.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = authRepository
        .amitySession.map {
            when (it) {
                SessionState.NotLoggedIn,
                SessionState.Establishing -> MainUiState.LoggedOut

                SessionState.Established,
                SessionState.TokenExpired -> MainUiState.LoggedIn

                is SessionState.Terminated -> MainUiState.Banned
            }
        }
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)

}

sealed interface MainUiState {
    object Loading : MainUiState
    object LoggedIn : MainUiState
    object LoggedOut : MainUiState
    object Banned : MainUiState
    data class Error(val throwable: Throwable?) : MainUiState
}
