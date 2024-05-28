package com.example.tp01_leo_hotz.datas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {


    @Insert
    fun insert(vararg question: Question)
    @Query("SELECT * FROM question WHERE category == :categorie")
    fun getAllByCategorie(categorie: String): Array<Question>

    @Query("DELETE FROM question")
    fun deleteAllQuestions()
}