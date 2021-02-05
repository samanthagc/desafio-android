package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.response.ApiService
import com.picpay.desafio.android.data.response.UsersResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PicPayDataSource : PicPayRepository {

    override fun getUsers(callback: (result: UsersResult) -> Unit) {
        ApiService.service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(UsersResult.ServerError)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                callback(UsersResult.Success(response.body()!!))
            }
        })
    }

}