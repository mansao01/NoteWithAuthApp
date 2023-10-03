package com.example.noteappwithauthentication.ui.screen.add

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappwithauthentication.ui.common.AddUiState

@Composable
fun AddScreen(
    uiState: AddUiState,
    modifier: Modifier = Modifier,
    addViewModel: AddViewModel = viewModel(factory = AddViewModel.Factory),
    navigateToHome:() -> Unit,

) {


}