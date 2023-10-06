package com.example.noteappwithauthentication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappwithauthentication.data.network.response.GetProfileResponse
import com.example.noteappwithauthentication.data.network.response.NoteDataItem
import com.example.noteappwithauthentication.ui.common.HomeUiState
import com.example.noteappwithauthentication.ui.component.LoadingScreen
import com.example.noteappwithauthentication.ui.component.MToast
import com.example.noteappwithauthentication.ui.component.MyFAB
import com.example.noteappwithauthentication.ui.component.NoteListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    uiState: HomeUiState,
    navigateToAdd: (Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToLogin:() ->Unit

) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.getNotesAndProfile()
    }
    when (uiState) {
        is HomeUiState.Loading -> {
            LoadingScreen()
        }

        is HomeUiState.Success -> {
            val noteList = uiState.noteDataItem
            val localToken = uiState.localToken
            val profile = uiState.getProfileResponse
            HomeContent(
                noteList = noteList,
                profile = profile,
                navigateToAdd = navigateToAdd,
                scrollBehavior = scrollBehavior
            )
            Log.d("HomeScreen", localToken)
        }

        is HomeUiState.Error -> {
            MToast(context = context, message = uiState.msg)
            navigateToLogin()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    noteList: List<NoteDataItem>,
    profile: GetProfileResponse,
    modifier: Modifier = Modifier,
    navigateToAdd: (Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior

) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            MyFAB(navigate = { navigateToAdd(profile.loggedInId) }, imageVector = Icons.Default.Add)
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                Text(
                    text = "Welcome, ${profile.loggedInUserName}",
                    modifier = modifier
                        .padding(top = 16.dp)
                        .padding(start = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
                NoteList(noteList = noteList)
            }
        }
    }
}

@Composable
fun NoteList(
    noteList: List<NoteDataItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        items(noteList) { data ->
            NoteListItem(note = data)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TODO()
}