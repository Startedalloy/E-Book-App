package com.example.bucher.screens

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PdfThumbnails(uri: Uri) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uri) {
        // 1. Move to Dispatchers.IO so we don't freeze the UI
        withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
                    PdfRenderer(fd).use { renderer ->
                        if (renderer.pageCount > 0) {
                            renderer.openPage(0).use { page ->
                                // 2. Scale down the bitmap!
                                // Don't use full page width/height for a thumbnail.
                                val width = 300
                                val height = (width * (page.height.toFloat() / page.width)).toInt()

                                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                                page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                bitmap = bmp
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // Handle permission or file errors
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "Book Thumbnail",
            modifier = Modifier.fillMaxWidth().aspectRatio(0.75f),
            contentScale = ContentScale.Crop
        )
    } else {
        // Placeholder so the grid doesn't jump
        Box(Modifier.fillMaxWidth().aspectRatio(0.75f).background(Color.LightGray))
    }
}