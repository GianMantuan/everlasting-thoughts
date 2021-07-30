package com.pos.everlastingthoughts.database

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pos.everlastingthoughts.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Thought::class], version = 1, exportSchema = false)
abstract class ThoughtDatabase: RoomDatabase() {
    abstract fun thoughtDao(): ThoughtDAO

    class Callback @Inject constructor(
        private val database: Provider<ThoughtDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().thoughtDao()
        }
    }
}