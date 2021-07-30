package com.pos.everlastingthoughts.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thought_table")
data class Thought(
    @PrimaryKey
    val uid: String,
    val thought: String,
    val date: String
)