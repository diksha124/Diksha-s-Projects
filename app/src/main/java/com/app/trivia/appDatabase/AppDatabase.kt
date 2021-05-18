package com.app.trivia.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.application.triviaapp.database.GameData
import com.application.triviaapp.database.TriviaGame

@Database(entities = [TriviaGame::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameData

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "linkodesDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}