package com.example.domain.use_cases.authorization.user_data

import com.example.common.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.common.DatastoreManager
import timber.log.Timber
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
){
    suspend operator fun invoke() {
        Timber.d("Progressing")
        datastoreManager.removeFromDataStore(DATASTORE_LOGGED_IN_EMAIL_KEY)
    }
}