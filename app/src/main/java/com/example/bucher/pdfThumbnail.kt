package com.example.bucher

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.createBitmap

@Composable
fun PdfThumbnails(uri: Uri) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uri) {
        val fileDescriptor =
            context.contentResolver.openFileDescriptor(uri, "r") ?: return@LaunchedEffect
        val render = PdfRenderer(fileDescriptor)
        val page = render.openPage(0)


        val bmp = createBitmap(page.width, page.height)


        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        bitmap = bmp

        page.close()
        render.close()
        fileDescriptor.close()

    }
    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Book Thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
        )
    }

}