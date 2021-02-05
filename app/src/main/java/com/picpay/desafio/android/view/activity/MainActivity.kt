package com.picpay.desafio.android.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.repository.PicPayDataSource
import com.picpay.desafio.android.view.adapter.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainViewModel
import com.picpay.desafio.android.viewmodel.MainViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MainViewModel = MainViewModelFactory(PicPayDataSource())
            .create(MainViewModel::class.java)

        viewModel.usersLiveData.observe(this, Observer {
            user_list_progress_bar.visibility = View.GONE
            it?.let { users ->
                with(recyclerView) {
                    layoutManager =
                        LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = UserListAdapter(users)
                }
            }
        })

        viewModel.errorLiveData.observe(this, Observer { it ->
            user_list_progress_bar.visibility = View.GONE
            it?.let { message ->
                Toast.makeText(this@MainActivity, getString(message), Toast.LENGTH_SHORT)
                    .show()
            }
        })

        viewModel.getUsers()
    }
}
