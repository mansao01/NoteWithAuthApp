package com.example.noteappwithauthentication.ui.screen.edit

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappwithauthentication.R
import com.example.noteappwithauthentication.data.network.request.UpdateNoteRequest
import com.example.noteappwithauthentication.ui.common.EditUiState
import com.example.noteappwithauthentication.ui.component.LoadingScreen
import com.example.noteappwithauthentication.ui.component.MToast

@Composable
fun EditScreen(
    uiState:EditUiState,
    noteId:Int,
    editViewModel: EditViewModel = viewModel(factory = EditViewModel.Factory),
    navigateToHome:() ->Unit
) {
    Log.d("EditScreen", noteId.toString())
    val context = LocalContext.current
    LaunchedEffect(Unit){
        editViewModel.getDetailNote(noteId)
    }
    when(uiState){
        is EditUiState.StandBy -> {
            val title = uiState.getDetailNote.data.title
            val description = uiState.getDetailNote.data.description
            EditScreenContent(
                navigateToHome = { navigateToHome() },
                noteId = noteId,
                editViewModel = editViewModel,
                titleValue =title,
                descriptionValue =description
            )
        }
        is EditUiState.Loading -> LoadingScreen()
        is EditUiState.Success ->{
            LaunchedEffect(Unit ){
                navigateToHome()
            }
        }
        is EditUiState.Error -> MToast(context = context, message = uiState.message)
        is EditUiState.ErrorGetNoteDetail -> MToast(context = context, message = uiState.message)
    }
}

@Composable
fun EditScreenContent(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    noteId: Int,
    editViewModel: EditViewModel,
    titleValue:String,
    descriptionValue:String
) {
    Scaffold(
        topBar = {
            EditTopBar(
                navigateToHome = { navigateToHome() })
        }
    ) { paddingValue ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(modifier.fillMaxSize()) {
                var title by remember { mutableStateOf(titleValue) }
                var isTitleEmpty by remember { mutableStateOf(false) }

                var description by remember { mutableStateOf(descriptionValue) }
                var isDescriptionEmpty by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Left
                    ),
                    label = { Text(text = stringResource(R.string.enter_title)) },
                    placeholder = { Text(text = stringResource(R.string.title)) },
                    isError = isTitleEmpty,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Left
                    ),
                    label = { Text(text = stringResource(R.string.enter_description)) },
                    placeholder = { Text(text = stringResource(R.string.description)) },
                    isError = isDescriptionEmpty,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )


                Button(
                    onClick = {
                        when {
                            title.isEmpty() -> isTitleEmpty = true
                            description.isEmpty() -> isDescriptionEmpty = true
                            else -> editViewModel.updateNote(
                                id = noteId,
                                updateNoteRequest = UpdateNoteRequest(
                                    title,
                                    description,
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 52.dp)
                        .padding(top = 18.dp)
                ) {
                    Text(text = stringResource(R.string.edit))
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopBar(
    navigateToHome: () -> Unit
) {
    LargeTopAppBar(
        title = { Text(text = "Edit") },
        navigationIcon = {
            IconButton(onClick = { navigateToHome() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        })
}