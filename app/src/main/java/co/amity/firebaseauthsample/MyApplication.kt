package co.amity.firebaseauthsample

import android.app.Application
import com.amity.socialcloud.sdk.AmityCoreClient
import com.amity.socialcloud.sdk.AmityEndpoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AmityCoreClient.setup(
            apiKey = BuildConfig.AMITY_API_KEY,
            endpoint = AmityEndpoint.EU
        )
    }
}