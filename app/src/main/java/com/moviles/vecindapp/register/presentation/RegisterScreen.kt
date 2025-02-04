package com.moviles.vecindapp.register.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moviles.vecindapp.core.navigation.Routes
import com.moviles.vecindapp.ui.components.VecindAppLogo
import com.moviles.vecindapp.ui.theme.VecindAppTheme

@Composable
fun RegisterScreen(navController: NavHostController) {
    val viewModel = remember { RegisterViewModel() }
    val email by viewModel.email
    val password by viewModel.password
    var confirmPassword by remember { mutableStateOf("") }
    val registrationSuccess by viewModel.registrationSuccess
    val errorMessage by viewModel.errorMessage
    var isLoading by remember { mutableStateOf(false) }

    VecindAppTheme {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VecindAppLogo()
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Crear una cuenta",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { viewModel.email.value = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { viewModel.password.value = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                                viewModel.setErrorMessage("Llena todos los campos.")
                            } else if (password != confirmPassword) {
                                viewModel.setErrorMessage("Las contraseñas no coinciden")
                            } else {
                                isLoading = true
                                viewModel.onRegister()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "Registrarse", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = { navController.navigate(Routes.LOGIN) }) {
                        Text(text = "¿Ya tienes cuenta? Inicia sesión", color = MaterialTheme.colorScheme.primary)
                    }
                }

                AnimatedVisibility(
                    visible = errorMessage.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        action = {
                            TextButton(onClick = { viewModel.setErrorMessage("") }) {
                                Text("Cerrar")
                            }
                        }
                    ) {
                        Text(errorMessage)
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }

    LaunchedEffect(registrationSuccess) {
        if (registrationSuccess) {
            isLoading = false
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.REGISTER) { inclusive = true }
            }
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            isLoading = false
        }
    }
}

