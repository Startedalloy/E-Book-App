package com.example.bucher.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PdfBookEntity::class],
    version = 1
)
abstract class PdfDatabase : RoomDatabase() {
    abstract fun bookDao(): PdfBookDao
}
