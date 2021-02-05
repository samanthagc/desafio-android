package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.PicPayDataSource
import com.picpay.desafio.android.data.response.UsersResult

class MainViewModel(private val dataSource: PicPayDataSource) : ViewModel() {

    private val userList: MutableLiveData<List<User>> = MutableLiveData()
    private val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val usersLiveData: LiveData<List<User>> = userList
    val errorLiveData: LiveData<Int> = errorMessage

    fun getUsers() {
        dataSource.getUsers { result: UsersResult ->
            when (result) {
                is UsersResult.Success ->
                    userList.value = result.users
                is UsersResult.ServerError ->
                    errorMessage.value = R.string.error
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