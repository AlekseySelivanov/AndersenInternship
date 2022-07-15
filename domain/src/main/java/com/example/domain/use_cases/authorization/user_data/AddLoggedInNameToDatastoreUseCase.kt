package com.example.domain.use_cases.authorization.user_data

import com.example.common.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.common.DatastoreManager
import timber.log.Timber
import javax.inject.Inject

open class AddLoggedInNameToDatastoreUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
) {
   open suspend operator fun invoke(name: String) {
        Timber.d("invoke: $name")
        datastoreManager.addToDatastore(DATASTORE_LOGGED_IN_EMAIL_KEY, name)
    }
}