package com.example.bucher

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PdfViewModel : ViewModel() {

    private val _books = mutableStateListOf<PdfBook>()
    val books: List<PdfBook> = _books


    fun addBook(uri: Uri) {
        _books.add(PdfBook(uri))
    }

}