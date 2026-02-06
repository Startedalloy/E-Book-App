package com.example.bucher.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfBookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<PdfBookEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: PdfBookEntity)
}