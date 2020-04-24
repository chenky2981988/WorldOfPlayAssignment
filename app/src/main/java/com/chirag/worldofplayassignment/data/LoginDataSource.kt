package com.chirag.worldofplayassignment.data

import com.chirag.worldofplayassignment.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val mockUser = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
            return Result.Success(mockUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

