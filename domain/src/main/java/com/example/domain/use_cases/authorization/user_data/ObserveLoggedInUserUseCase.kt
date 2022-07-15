package com.example.domain.use_cases.authorization.user_data

import com.example.common.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.common.DatastoreManager
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveLoggedInUserUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
){

    operator fun invoke(): Flow<User?> {
        return datastoreManager.observeKeyValue(DATASTORE_LOGGED_IN_EMAIL_KEY).map {
            if(it!=null) {
                User(it)
            } else {
                null
            }
        }
    }
}