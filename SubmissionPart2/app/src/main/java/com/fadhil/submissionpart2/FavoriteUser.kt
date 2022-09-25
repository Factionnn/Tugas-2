package com.fadhil.submissionpart2

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.fadhil.submissionpart2.databinding.ActivityFavoriteUserBinding
import com.fadhil.submissionpart2.dll.FavoriteAdapter
import com.fadhil.submissionpart2.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.fadhil.submissionpart2.User.Favorite


class FavoriteUser : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter
    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        val binding : ActivityFavoriteUserBinding? = null
        binding?.recycleFav?.layoutManager = LinearLayoutManager(this)
        binding?.recycleFav?.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        binding?.recycleFav?.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadNotesAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI,true,myObserver)
        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }



    }

    private fun loadNotesAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            val binding : ActivityFavoriteUserBinding? = null
            binding?.progressFav!!.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI,null,null,null,null)

                    MappingHelper.mapCursorToArrayList(cursor!!)

            }
            val favData = deferredNotes.await()
            binding.progressFav.visibility = View.INVISIBLE
            if (favData.size > 0) {
                adapter.listFavorite = favData
            } else {
                adapter.listFavorite = ArrayList()
                showmessage()
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE,adapter.listFavorite)
    }

   fun showmessage(){
       Toast.makeText(this,getString(R.string.data_kosong),Toast.LENGTH_SHORT).show()
   }

    override fun onResume() {
        super.onResume()
        loadNotesAsync()
    }
}