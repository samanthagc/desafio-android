package com.picpay.desafio.android.data.service

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.response.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}