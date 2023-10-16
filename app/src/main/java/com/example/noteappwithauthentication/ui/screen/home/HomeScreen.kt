package com.example.noteappwithauthentication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
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
    navigateToLogin: () -> Unit,
    navigateToEdit:(Int) ->Unit

) {
    val context = LocalContext.current
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
                scrollBehavior = scrollBehavior,
                homeViewModel = homeViewModel,
                navigateToLogin = navigateToLogin,
                navigateToEdit = navigateToEdit
            )
            Log.d("HomeScreen", localToken)
        }

        is HomeUiState.Error -> {
            MToast(context = context, message = uiState.msg)
            navigateToLogin()
            homeViewModel.removeAccessToken()
            homeViewModel.saveIsLoginState(false)
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
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToLogin: () -> Unit,
    homeViewModel: HomeViewModel,
    navigateToEdit:(Int) ->Unit


) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            MyFAB(navigate = { navigateToAdd(profile.loggedInId) }, imageVector = Icons.Default.Add)
        },
        topBar = {
            HomeTopBar(
                scrollBehavior = scrollBehavior,
                navigateToLogin = { navigateToLogin() },
                homeViewModel = homeViewModel,
                profile  = profile
            )
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NoteList(noteList = noteList, navigateToEdit  = navigateToEdit)
        }
    }
}

@Composable
fun NoteList(
    noteList: List<NoteDataItem>,
    modifier: Modifier = Modifier,
    navigateToEdit:(Int) ->Unit
) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        items(noteList) { data ->
            NoteListItem(note = data, modifier = Modifier.clickable { navigateToEdit( data.id) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToLogin: () -> Unit,
    homeViewModel: HomeViewModel,
    profile:GetProfileResponse
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = "Welcome, ${profile.loggedInUserName}") },
        actions = {
            IconButton(onClick = {
                navigateToLogin()
                homeViewModel.removeAccessToken()
                homeViewModel.saveIsLoginState(false)
            }) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
            }
        }
    )
}