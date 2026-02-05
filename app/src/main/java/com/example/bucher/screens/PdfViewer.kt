package com.example.bucher.screens

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.graphics.asImageBitmap


@Composable
fun PdfViewer(uri: Uri, onBack: () -> Unit) {

    val context = LocalContext.current
    val pages = remember { mutableStateListOf<Bitmap>() }
    val scaleFactor = 2f
    LaunchedEffect(uri) {
        withContext(Dispatchers.IO) {
            val fd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext
            val render = PdfRenderer(fd)

            for (i in 0 until render.pageCount) {
                val page = render.openPage(i)
                val width = (page.width * scaleFactor).toInt()
                val height = (page.height * scaleFactor).toInt()

                val bitmap = createBitmap(width, height,Bitmap.Config.ARGB_8888)

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                pages.add(bitmap)
                page.close()

            }
            render.close()
            fd.close()


        }
    }
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "← Back",
            modifier = Modifier
                .padding(12.dp)
                .clickable { onBack() },
            fontWeight = FontWeight.Bold
        )
        LazyColumn(Modifier.fillMaxSize()) {
            items(pages) { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.FillWidth

                )
            }
        }

    }
}

