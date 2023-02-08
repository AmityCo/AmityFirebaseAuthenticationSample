package co.amity.firebaseauthsample.data.remote.di

import android.content.Context
import co.amity.firebaseauthsample.BuildConfig
import co.amity.firebaseauthsample.data.remote.AuthRepository
import co.amity.firebaseauthsample.data.remote.AuthRepositoryImp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        // Automatically sign in when exactly one credential is retrieved.
        .setAutoSelectEnabled(true)
        .build()

    private val signUpRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    @Singleton
    fun provideAuthRepository(@ApplicationContext appContext: Context): AuthRepository {
        return AuthRepositoryImp(
            Identity.getSignInClient(appContext),
            signInRequest,
            signUpRequest,
            Firebase.auth,
            Firebase.firestore
        )
    }
}