package com.example.noteappwithauthentication.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteappwithauthentication.NoteApplication
import com.example.noteappwithauthentication.data.NoteRepository
import com.example.noteappwithauthentication.ui.screen.home.HomeViewModel

class AddViewModel(
    private val noteRepository: NoteRepository
):ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)
                val noteRepository = application.container.noteRepository
                AddViewModel(noteRepository = noteRepository)
            }
        }
    }
}