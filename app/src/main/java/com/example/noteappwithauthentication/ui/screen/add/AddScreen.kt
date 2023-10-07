package com.example.noteappwithauthentication.ui.screen.add

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappwithauthentication.R
import com.example.noteappwithauthentication.data.network.request.CreateNoteRequest
import com.example.noteappwithauthentication.ui.common.AddUiState
import com.example.noteappwithauthentication.ui.component.LoadingScreen
import com.example.noteappwithauthentication.ui.component.MToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    uiState: AddUiState,
    modifier: Modifier = Modifier,
    addViewModel: AddViewModel = viewModel(factory = AddViewModel.Factory),
    navigateToHome: () -> Unit,
    userId: Int,
    scrollBehavior: TopAppBarScrollBehavior,

    ) {

    val context = LocalContext.current
    when (uiState) {
        is AddUiState.StandBy -> AddScreenContent(
            addViewModel = addViewModel,
            userId,
            modifier = modifier,
            navigateToHome = navigateToHome,
            scrollBehavior = scrollBehavior
        )

        is AddUiState.Loading -> LoadingScreen()
        is AddUiState.Success -> {
            LaunchedEffect(Unit) {
                navigateToHome()
            }
            MToast(context = context, message = uiState.createNoteResponse.msg)
        }

        is AddUiState.Error -> {
            MToast(context = context, message = uiState.msg)
            addViewModel.getUiState()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(
    addViewModel: AddViewModel,
    userId: Int,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToHome: () -> Unit,

    ) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AddTopBar(
                scrollBehavior = scrollBehavior,
                navigateToHome = { navigateToHome() })
        }
    ) { paddingValue ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(modifier.fillMaxSize()) {
                var title by remember { mutableStateOf("") }
                var isTitleEmpty by remember { mutableStateOf(false) }

                var description by remember { mutableStateOf("") }
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
                            else -> addViewModel.addNote(
                                CreateNoteRequest(
                                    title,
                                    description,
                                    userId
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 52.dp)
                        .padding(top = 18.dp)
                ) {
                    Text(text = stringResource(R.string.add))
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navigateToHome: () -> Unit
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = "Add") },
        navigationIcon = {
            IconButton(onClick = { navigateToHome() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddScreenPreview() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
AddTopBar(scrollBehavior = scrollBehavior) {
    
}
}