package com.example.bucher.screens

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.example.bucher.viewModel.PdfViewModel


@Composable
fun PdfViewer(uri: Uri,viewModel: PdfViewModel,onBack: () -> Unit) {

    val context = LocalContext.current
    val pages = remember { mutableStateListOf<Bitmap>() }

    LaunchedEffect(uri) {
        withContext(Dispatchers.IO) {
            val fd = context.contentResolver.openFileDescriptor(uri, "r") ?: return@withContext
            val render = PdfRenderer(fd)

            for (i in 0 until render.pageCount) {
                val page = render.openPage(i)

                val bitmap = createBitmap(page.width, page.height)

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                pages.add(bitmap)
                page.close()

            }
            render.close()
            fd.close()



        }
    }
    Column {
        Text(
            text = "← Back",
            modifier = Modifier
                .padding(12.dp)
                .clickable { viewModel.closePdf() },
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(pages) { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

    }
}