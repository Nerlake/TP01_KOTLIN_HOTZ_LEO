package com.example.tp01_leo_hotz.datas

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {
    @Insert
    fun insert(vararg score: Score)

    @Query("SELECT * FROM score WHERE category = :categorie ORDER BY score DESC LIMIT 1")
    fun getBestScoreByCategory(categorie: String): Score?

    @Query("DELETE FROM score")
    fun deleteAllScores()
}
