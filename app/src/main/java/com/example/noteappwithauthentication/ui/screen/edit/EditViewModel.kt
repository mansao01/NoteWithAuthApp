package com.example.noteappwithauthentication.ui.screen.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteappwithauthentication.NoteApplication
import com.example.noteappwithauthentication.data.NoteRepository
import com.example.noteappwithauthentication.ui.screen.add.AddViewModel

class EditViewModel(
    private val noteRepository: NoteRepository
):ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)
                val noteRepository = application.container.noteRepository
                EditViewModel(noteRepository = noteRepository)
            }
        }
    }
}