package com.application.triviaapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trivia_game")
class TriviaGame {

    @PrimaryKey
    var playedAt: Long ? = null
    var playedName: String ? = null
    var answerOne: String ? = null
    var answerTwo: String ? = null

}