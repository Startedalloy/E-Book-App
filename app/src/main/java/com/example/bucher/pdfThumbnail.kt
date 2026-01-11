package com.example.bucher

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap

@Composable
fun PdfThumbnail(uri: Uri) {

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uri) {
        val fileDescriptor =
            context.contentResolver.openFileDescriptor(uri, "r") ?: return@LaunchedEffect

        val pdfRenderer = PdfRenderer(fileDescriptor)
        val page = pdfRenderer.openPage(0)

        val bmp = createBitmap(page.width, page.height)

        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        bitmap = bmp

        page.close()
        pdfRenderer.close()
        fileDescriptor.close()
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "PDF Preview",
            modifier = Modifier
                .size(200.dp)
                .clickable {
                    openPdf(context, uri)
                }
        )
    }
}


