package com.picpay.desafio.android.data.response

import com.picpay.desafio.android.data.model.User

sealed class UsersResult {
    class Success(val users: List<User>) : UsersResult()
    object ServerError : UsersResult()
}