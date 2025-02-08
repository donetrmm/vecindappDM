package com.moviles.vecindapp.login.presentation

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moviles.vecindapp.core.navigation.Routes
import com.moviles.vecindapp.ui.components.VecindAppLogo
import com.moviles.vecindapp.ui.theme.VecindAppTheme
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = remember { LoginViewModel() }
    val email by viewModel.email
    val password by viewModel.password
    val token by viewModel.token
    val errorMessage by viewModel.errorMessage
    val loginError by viewModel.loginError

    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = loginError) {
        if (loginError) {
            vibrarDispositivo(context)
            viewModel.loginError.value = false
        }
    }

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
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "VecindApp",
                        style = MaterialTheme.typography.headlineLarge,
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
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.onLogin()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = { navController.navigate(Routes.REGISTER) }) {
                        Text(text = "¿No tienes cuenta? Regístrate", color = MaterialTheme.colorScheme.primary)
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
                            TextButton(onClick = { viewModel.errorMessage.value = "" }) {
                                Text("Cerrar")
                            }
                        }
                    ) {
                        Text("Correo o contraseña incorrectos.")
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

    LaunchedEffect(token) {
        if (token.isNotEmpty()) {
            isLoading = false
            navController.navigate(Routes.NEIGHBORHOODS) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            isLoading = false
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            delay(3000)
            viewModel.errorMessage.value = ""
        }
    }
}

fun vibrarDispositivo(context: Context) {
    @Suppress("DEPRECATION")
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(500)
    }
}
