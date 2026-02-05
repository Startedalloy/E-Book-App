package com.example.bucher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bucher.data.PdfBookDao
import com.example.bucher.viewModel.PdfViewModel

class PdfViewModelFactory(
    private val dao: PdfBookDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PdfViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PdfViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
