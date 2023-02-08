package co.amity.firebaseauthsample.data.local

import androidx.room.PrimaryKey

data class User(
    @PrimaryKey(autoGenerate = false)
    var uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)