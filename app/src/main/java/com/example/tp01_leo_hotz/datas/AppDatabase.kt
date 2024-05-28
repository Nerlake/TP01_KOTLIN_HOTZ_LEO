package com.example.tp01_leo_hotz.datas

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.TypeConverters
import com.example.tp01_leo_hotz.Converters

@Database(entities = [Question::class, Score::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new "score" table
                database.execSQL("CREATE TABLE IF NOT EXISTS `score` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category` TEXT NOT NULL, `playerName` TEXT NOT NULL, `score` INTEGER NOT NULL)")
            }
        }
    }
}
