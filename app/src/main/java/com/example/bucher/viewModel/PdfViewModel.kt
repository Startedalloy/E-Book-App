package com.example.bucher.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bucher.data.PdfBookDao
import com.example.bucher.model.PdfBook
import kotlinx.coroutines.flow.map
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.example.bucher.data.PdfBookEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PdfViewModel (private val dao:PdfBookDao): ViewModel() {

    private val _books = mutableStateListOf<PdfBook>()

    val books = dao.getAllBooks().map { list ->
        list.map { PdfBook(it.uri.toUri()) }
    }.stateIn(scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList())

    var selectedBook by mutableStateOf<PdfBook?>(null)
        private set


    fun addBook(uri: Uri) {
        viewModelScope.launch { dao.insertBook(book = PdfBookEntity(uri.toString())) }
    }

    fun openPdf(book: PdfBook) {
        selectedBook = book
    }

    fun closePdf() {
        selectedBook = null
    }

}