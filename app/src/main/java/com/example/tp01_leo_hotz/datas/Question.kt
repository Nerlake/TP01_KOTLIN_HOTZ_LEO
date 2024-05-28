package com.example.tp01_leo_hotz.datas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val reponse: List<String>,
    val resultat: Int,
    val category: String
)
