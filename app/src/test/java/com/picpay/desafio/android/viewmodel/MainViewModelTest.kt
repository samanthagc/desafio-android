package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.PicPayRepository
import com.picpay.desafio.android.data.response.UsersResult
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var usersLiveDataObserver: Observer<List<User>>

    @Mock
    private lateinit var errorLiveDataObserver: Observer<Int>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenSuccessGetBooksThenSetsUsersLiveData() {
        val users = listOf(User(
            "https://randomuser.me/api/portraits/man/9.jpg",
            "Samantha Costa",
            1001,
            "@samantha.costa"))

        val resultSucess = MockRepository(UsersResult.Success(users))
        viewModel = MainViewModel(resultSucess)
        viewModel.usersLiveData.observeForever(usersLiveDataObserver)

        viewModel.getUsers()

        verify(usersLiveDataObserver).onChanged(users)
    }

    @Test
    fun whenErrorGetBooksThenSetsUsersLiveData() {
        val resultError = MockRepository(UsersResult.ServerError)
        viewModel = MainViewModel(resultError)
        viewModel.errorLiveData.observeForever(errorLiveDataObserver)

        viewModel.getUsers()

        verify(errorLiveDataObserver).onChanged(R.string.error)
    }
}

class MockRepository(private val result: UsersResult) : PicPayRepository {
    override fun getUsers(callback: (result: UsersResult) -> Unit) {
        callback(result)
    }
}