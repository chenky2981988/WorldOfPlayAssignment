package com.chirag.worldofplayassignment.data.model

import com.chirag.worldofplayassignment.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUserView? = null,
        val error: Int? = null
)
