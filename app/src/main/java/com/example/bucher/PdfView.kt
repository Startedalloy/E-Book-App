package com.example.bucher

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

@Composable
fun PdfViews(viewModel: PdfViewModel) {

    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    // File picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        pdfUri = uri
    }

    Scaffold(
        topBar = { Text("Bucher", fontWeight = FontWeight.ExtraBold) },
        floatingActionButton = {
            FloatingActionButton(onClick = { launcher.launch(arrayOf("application/pdf")) }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }) { innerPadding ->
        // Your main content goes here
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.books.size) { index ->
                    val book = viewModel.books[index]
                    PdfThumbnailItem(book)
                }

            }
        }
    }
}

@Composable
fun  PdfThumbnailItem(book: PdfBook) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        PdfThumbnails(book.uri)
    }
}

