package com.example.noteappwithauthentication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappwithauthentication.data.network.response.GetProfileResponse
import com.example.noteappwithauthentication.data.network.response.NoteDataItem
import com.example.noteappwithauthentication.ui.common.HomeUiState
import com.example.noteappwithauthentication.ui.component.LoadingScreen
import com.example.noteappwithauthentication.ui.component.NoteListItem

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    userId: Int,
    uiState:HomeUiState
) {

    Text(text = userId.toString())
    when(uiState){
        is HomeUiState.Loading -> {
            homeViewModel.getNotesAndProfile(userId)
            LoadingScreen()
        }
        is HomeUiState.Success -> {
            val noteList = uiState.noteDataItem
            val localToken = uiState.localToken
            val profile = uiState.getProfileResponse
            HomeContent(noteList = noteList, profile = profile)
            Log.d("HomeScreen", localToken)
        }
        is HomeUiState.Error ->{}
    }
}


@Composable
fun HomeContent(
    noteList:List<NoteDataItem>,
    profile:GetProfileResponse
) {
    Column {
        Text(text = "Welcome ${profile.loggedInUserName}")
        NoteList(noteList = noteList)
    }
}
@Composable
fun NoteList(
    noteList: List<NoteDataItem>
) {
    LazyColumn{
        items(noteList){
            NoteListItem (note = it )
        }
    }

}