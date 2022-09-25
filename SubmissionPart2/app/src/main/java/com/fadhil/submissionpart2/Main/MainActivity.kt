package com.fadhil.submissionpart2.Main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.view.Menu
import android.view.MenuItem

import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhil.submissionpart2.FavoriteUser
import com.fadhil.submissionpart2.R
import com.fadhil.submissionpart2.User.User
import com.fadhil.submissionpart2.User.UserActivityDetail
import com.fadhil.submissionpart2.User.UserAdapter
import com.fadhil.submissionpart2.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Search User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                intent = Intent(this@MainActivity, UserActivityDetail::class.java)
                with(intent) {
                    putExtra(UserActivityDetail.EXTRA_USERNAME, data.login)
                }
                startActivity(intent)
            }

        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.apply {
            rvUser?.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser?.setHasFixedSize(true)
            rvUser?.adapter = adapter


            SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (p0 != null) {
                        viewModel.setSearch(p0)
                        searching()
                        showLoading(true)
                    }



                    return true
                }


                override fun onQueryTextChange(p0: String?): Boolean {


                    return false
                }

            })


        }


    }

    private fun searching() {
        binding.apply {
            val searchText = SearchView.query
            if (searchText!!.isEmpty()) return
            //  showLoading(true)
            viewModel.setSearch(searchText.toString())


            viewModel.get().observe(this@MainActivity) {
                if (it != null) {
                    adapter.setList(it)
                    adapter.notifyDataSetChanged()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading!!.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.loading!!.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.link_favorite -> {
                val mIntent = Intent(this,FavoriteUser::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }




}