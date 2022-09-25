package com.fadhil.submissionpart2.helper

import android.database.Cursor
import com.fadhil.submissionpart2.DB.DatabaseContract
import com.fadhil.submissionpart2.User.Favorite
import okio.Buffer

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor): ArrayList<Favorite>{
        val favoriteList = ArrayList<Favorite>()

        notesCursor.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.AVATAR))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.REPOSITORY))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.COMPANY))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.FAVORITE))
                favoriteList.add(
                    Favorite(
                        username,
                        name,avatar, company, location, repository,favorite
                    )
                )
            }
        }
        return favoriteList
    }
}