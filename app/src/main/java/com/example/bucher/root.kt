package com.example.bucher

import androidx.compose.runtime.Composable
import com.example.bucher.screens.PdfViewer
import com.example.bucher.screens.PdfViews
import com.example.bucher.viewModel.PdfViewModel

@Composable
fun AppRoot(viewModel: PdfViewModel) {

    if (viewModel.selectedBook == null) {
        PdfViews(viewModel)
    } else {
        PdfViewer(
            uri = viewModel.selectedBook!!.uri,
            viewModel, onBack = { viewModel.closePdf() }
        )
    }
}
