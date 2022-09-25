package com.fadhil.submissionpart2.Follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadhil.submissionpart2.API.ApiConfig
import com.fadhil.submissionpart2.User.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username : String){
        ApiConfig.getApiService()
            .getUserfollowing(username)
            .enqueue(object : Callback<java.util.ArrayList<User>> {
                override fun onResponse(
                    call: Call<java.util.ArrayList<User>>,
                    response: Response<java.util.ArrayList<User>>
                ) {
                    if(response.isSuccessful){
                        listFollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<java.util.ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun getFollower(): LiveData<ArrayList<User>>{
        return listFollower
    }
}