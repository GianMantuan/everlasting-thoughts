package com.pos.everlastingthoughts.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ThoughtDAO {
    @Insert
    suspend fun insert(thought: Thought)

    @Query("SELECT * FROM thought_table ORDER BY date DESC")
    fun getThoughts(): Flow<List<Thought>>
}