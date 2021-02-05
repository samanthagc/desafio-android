package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.PicPayDataSource
import com.picpay.desafio.android.data.response.UsersResult

class MainViewModel(private val dataSource: PicPayDataSource) : ViewModel() {

    val usersLiveData: MutableLiveData<List<User>> = MutableLiveData()
    val errorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getUsers() {
        dataSource.getUsers { result: UsersResult ->
            when (result) {
                is UsersResult.Success ->
                    usersLiveData.value = result.users
                is UsersResult.ServerError ->
                    errorLiveData.value = R.string.error
            }
        }
    }

    class MainViewModelFactory(private val dataSource: PicPayDataSource) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(dataSource) as T
        }
    }

}