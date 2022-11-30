package com.ikrima.practice.dicoding.githubuserappdicoding.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse

@Database(entities = [DetailUserResponse::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {

    abstract fun favUserDao() : FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE : FavoriteUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context) : FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FavoriteUserRoomDatabase::class.java, "favorite_user_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }
    }

}