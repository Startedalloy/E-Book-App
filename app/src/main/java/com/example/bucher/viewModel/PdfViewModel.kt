package com.example.bucher.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bucher.model.PdfBook

class PdfViewModel : ViewModel() {

    private val _books = mutableStateListOf<PdfBook>()
    val books: List<PdfBook> = _books

    var selectedBook by mutableStateOf<PdfBook?>(null)
        private set


    fun addBook(uri: Uri) {
        _books.add(PdfBook(uri))
    }

    fun openPdf(book: PdfBook) {
        selectedBook = book
    }

    fun closePdf() {
        selectedBook = null
    }

}