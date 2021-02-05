package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.response.UsersResult

interface PicPayRepository {
    fun getUsers(callback: (result: UsersResult) -> Unit)
}