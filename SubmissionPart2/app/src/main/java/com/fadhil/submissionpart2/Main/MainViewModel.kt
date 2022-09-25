package com.fadhil.submissionpart2.Main

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadhil.submissionpart2.API.ApiConfig
import com.fadhil.submissionpart2.dll.DetailResponse
import com.fadhil.submissionpart2.User.User
import com.fadhil.submissionpart2.User.UserResponse


import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()
    val UserDetail = MutableLiveData<DetailResponse>()

    fun setSearch(username: String){

        ApiConfig.getApiService()
            .getUser(username)
            .enqueue(object : retrofit2.Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        listUsers.postValue(response.body()?.items)


                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun setDetail(username: String){

        ApiConfig.getApiService()
            .getUserDetails(username)
            .enqueue(object : retrofit2.Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if(response.isSuccessful){
                        UserDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
    fun getUserDetail(): LiveData<DetailResponse> {
        return UserDetail
    }

    fun get(): LiveData<ArrayList<User>>{
        return listUsers
    }





    }



