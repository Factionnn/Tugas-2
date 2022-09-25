package com.fadhil.submissionpart2.dll

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhil.submissionpart2.CustomOnItemClickListener
import com.fadhil.submissionpart2.R
import com.fadhil.submissionpart2.User.Favorite
import com.fadhil.submissionpart2.User.User
import com.fadhil.submissionpart2.User.UserActivityDetail
import com.fadhil.submissionpart2.User.UserAdapter
import com.fadhil.submissionpart2.databinding.ItemUserBinding
import java.util.ArrayList

class FavoriteAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteAdapter.NoteViewHolder>() {
    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.NoteViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = this.listFavorite.size
    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val binding = ItemUserBinding.bind(itemView)
        fun bind (fav: Favorite) {
        binding.apply {
            Glide.with(itemView.context)
                .load(fav.avatar)
                .circleCrop()
                .into(imgItemPhoto)
            tvItemName.text = fav.username
            itemView.setOnClickListener(
                CustomOnItemClickListener (
                    adapterPosition,
                    object : CustomOnItemClickListener.OnItemClickCallback {
                        override fun onItemClicked(view: View,position: Int) {
                            val intent = Intent(activity,UserActivityDetail::class.java)

                        }
                    }
                        )
            )
        }
        }

    }
}