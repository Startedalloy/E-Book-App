package com.example.bucher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.bucher.data.PdfDatabase
import com.example.bucher.navigation.NavGraph

import com.example.bucher.viewModel.PdfViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            PdfDatabase::class.java,
            "pdf_db"
        ).build()

        val dao = database.bookDao()
        val factory = PdfViewModelFactory(dao)
        enableEdgeToEdge()
        setContent {
            val viewModel: PdfViewModel = viewModel(factory = factory)
            NavGraph(viewModel)
        }
    }
}

