package com.example.noteappwithauthentication.ui.screen.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteappwithauthentication.R
import com.example.noteappwithauthentication.data.network.request.RegisterRequest
import com.example.noteappwithauthentication.ui.common.RegisterUiState
import com.example.noteappwithauthentication.ui.component.LoadingScreen

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory),
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    registerViewModel.getUiState()
    when (uiState) {
        is RegisterUiState.StandBy -> RegisterComponent(registerViewModel = registerViewModel)
        is RegisterUiState.Loading -> LoadingScreen()
        is RegisterUiState.Success -> {
            LaunchedEffect(Unit){
                mToast(context, uiState.registerResponse.msg)
                navigateToLogin()
            }
        }

        is RegisterUiState.Error -> {
            registerViewModel.getUiState()
            mToast(context, uiState.msg)
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterComponent(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var isNameEmpty by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var isEmailEmpty by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isPasswordEmpty by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisibility by remember { mutableStateOf(false) }


    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.register),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 48.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = stringResource(R.string.enter_your_name)) },
            placeholder = { Text(text = stringResource(R.string.name)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = isNameEmpty,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.enter_your_email)) },
            placeholder = { Text(text = stringResource(R.string.email)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isEmailValid(email)) {
                        keyboardController?.hide()
                    }
                }
            ),
            isError = isEmailEmpty,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.enter_your_password)) },
            placeholder = { Text(text = stringResource(R.string.password)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            isError = isPasswordEmpty,
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisibility) {
                            stringResource(R.string.hide_password)
                        } else {
                            stringResource(R.string.show_password)
                        }
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        Button(
            onClick = {
                when {
                    name.isEmpty() -> isNameEmpty = true
                    email.isEmpty() -> isEmailEmpty = true
                    password.isEmpty() -> isPasswordEmpty = true
                    else -> registerViewModel.register(
                        RegisterRequest(
                            name,
                            email,
                            password,
                        )
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 52.dp)
                .padding(top = 18.dp)
        ) {
            Text(text = "Register")
        }
    }
}

private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
