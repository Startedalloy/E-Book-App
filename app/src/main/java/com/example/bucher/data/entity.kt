package com.example.bucher.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class PdfBookEntity(
   @PrimaryKey val uri: String
)
