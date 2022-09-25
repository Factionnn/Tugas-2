package com.fadhil.submissionpart2.User

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast


import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.AVATAR
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.COMPANY
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.FAVORITE
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.NAME
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.REPOSITORY
import com.fadhil.submissionpart2.DB.DatabaseContract.FavColumns.Companion.USERNAME
import com.fadhil.submissionpart2.DB.FavoriteHelper
import com.fadhil.submissionpart2.FavoriteUser
import com.fadhil.submissionpart2.Main.MainViewModel
import com.fadhil.submissionpart2.dll.PageAdapter
import com.fadhil.submissionpart2.R
import com.fadhil.submissionpart2.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserActivityDetail : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: MainViewModel

    companion object{
        const val EXTRA_USERNAME= "extra_username"
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        private val TAB_TITTLE = intArrayOf(
            R.string.follower,
            R.string.following


        )
        private var Fav = false
        private lateinit var gitHelper: FavoriteHelper
        private var favorite: Favorite? = null
        private lateinit var imgphoto : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()

        favorite = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorite != null) {
            setdataObject()
            Fav = true
            val checked: Int = R.drawable.ic_favorite
            binding.btnFavorite.setImageResource(checked)
        } else{
            setData()

        }
        viewpager()
        binding.btnFavorite.setOnClickListener(this)








    }

    private fun viewpager(){
        val bundle = Bundle()

        val folAdapter = PageAdapter(this,bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = folAdapter
        val tabs: TabLayout = findViewById(R.id.table)
        TabLayoutMediator(tabs,viewPager){tab,position -> tab.text = resources.getString(TAB_TITTLE[position])}.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setData(){
        val actionbar = supportActionBar
        actionbar?.title = "Detail User"
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        val username = intent.getStringExtra(EXTRA_USERNAME)?:"null"
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)
        viewModel.setDetail(username)
        Log.d("Detail","Username : $username")
        viewModel.getUserDetail().observe(this,{
            if (it != null) {
                binding.apply {
                    Glide.with(this@UserActivityDetail)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(circleImageView)

                    textname.text = it.name
                    usernamed.text = it.login
                    textcompany.text =
                        if (it.company != null) it.company else " Tidak Memiliki Perusahaan "
                    textlocation.text = if (it.location != null) it.location else " Tidak Ada Memiliki Lokasi"
                    detailfollower.text = "${it.followers}"
                    detailfollowing.text = "${it.following}"
                    textrepository.text =
                        if (it.bio != null) it.bio else " Tidak Memiliki Repository "


                }
            }
        })
    }

    private fun setdataObject() {
        val actionbar = supportActionBar
        actionbar?.title = "Detail User"
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        val username = intent.getStringExtra(EXTRA_NOTE)?:"null"
        val bundle = Bundle()
        bundle.putString(EXTRA_NOTE,username)
        viewModel.setDetail(username)
        Log.d("Detail","Username : $username")
        viewModel.getUserDetail().observe(this,{
            if (favorite != null) {
                binding.apply {
                    Glide.with(this@UserActivityDetail)
                        .load(favorite!!.avatar)
                        .circleCrop()
                        .into(circleImageView)

                    textname.text = favorite!!.name
                    usernamed.text = favorite!!.username
                    textcompany.text =
                        if (favorite!!.company != null) favorite!!.company else " Tidak Memiliki Perusahaan "
                    textlocation.text = if (favorite!!.location != null) favorite!!.location else " Tidak Ada Memiliki Lokasi"
                    detailfollower.text = "${favorite!!.followers}"
                    detailfollowing.text = "${favorite!!.following}"
                    textrepository.text =
                        if (favorite!!.repository != null) favorite!!.repository else " Tidak Memiliki Repository "
                    imgphoto = favorite?.avatar.toString()
                }
            }
        })
    }

    override fun onClick(view: View) {
        val checked: Int = R.drawable.ic_favorite
        val unChecked: Int = R.drawable.ic_baseline_favorite_border_24
        if (view.id == R.id.btn_favorite) {
            if (Fav) {
                gitHelper.deleteById(favorite?.username.toString())
                Toast.makeText(this, getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show()
               binding.btnFavorite.setImageResource(unChecked)
                Fav = false
            } else {
                val dataUsername = binding.usernamed.text.toString()
                val dataname = binding.nametext.text.toString()
                val dataAvatar = binding.circleImageView.toString()
                val datacompany = binding.textcompany.text.toString()
                val datalocation = binding.textlocation.text.toString()
                val datarepository = binding.textrepository.text.toString()
                val datafavorite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataname)
                values.put(AVATAR, dataAvatar)
                values.put (COMPANY, datacompany)
                values.put (REPOSITORY, datarepository)
                values.put(FAVORITE, datafavorite)

                Fav = true
                contentResolver.insert(CONTENT_URI,values)
                binding.btnFavorite.setImageResource(checked)


            }            }
        }

    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
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