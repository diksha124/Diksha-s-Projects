package com.application.triviaapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameData {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(game: TriviaGame)

    @Query("SELECT * FROM trivia_game")
    fun getCards(): List<TriviaGame>

}