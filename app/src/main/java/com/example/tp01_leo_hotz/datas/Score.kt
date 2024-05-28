package com.example.tp01_leo_hotz.datas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val playerName: String,
    val score: Int
)