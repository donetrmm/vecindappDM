package com.moviles.vecindapp.neighborhoods.presentation

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodResponse
import com.moviles.vecindapp.ui.theme.VecindAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeighborhoodsScreen(navController: NavHostController) {
    val viewModel: NeighborhoodsViewModel = viewModel()
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getNeighborhoods()
    }

    val neighborhoodsList by viewModel.neighborhoodsList
    val errorMessage by viewModel.errorMessage
    var showAddDialog by remember { mutableStateOf(false) }

    VecindAppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Vecindarios",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = { showLogoutDialog = true },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = "Cerrar sesión",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showAddDialog = true }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Agregar Vecindario"
                            )
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (neighborhoodsList.isEmpty()) {
                    EmptyStateView(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(neighborhoodsList) { neighborhood ->
                            NeighborhoodItem(neighborhood = neighborhood)
                        }
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
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        action = {
                            TextButton(onClick = { viewModel.errorMessage.value = "" }) {
                                Text(
                                    "Cerrar",
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    ) {
                        Text(errorMessage)
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddNeighborhoodDialog(
            onDismiss = { showAddDialog = false },
            onAdd = {
                viewModel.onAddNeighborhood()
                showAddDialog = false
            },
            viewModel = viewModel
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Estás seguro que deseas salir de la aplicación?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                ) {
                    Text("Confirmar", color = Color(0xFF00497D))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancelar", color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}

@Composable
fun NeighborhoodItem(neighborhood: NeighborhoodResponse) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = neighborhood.nombre,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                icon = Icons.Default.LocationOn,
                text = neighborhood.direccion,
                iconColor = MaterialTheme.colorScheme.secondary
            )
            InfoRow(
                icon = Icons.Default.Home,
                text = "Colonia: ${neighborhood.colonia}",
                iconColor = MaterialTheme.colorScheme.secondary
            )
            InfoRow(
                icon = Icons.Default.LocationCity,
                text = "Estado: ${neighborhood.estado}",
                iconColor = MaterialTheme.colorScheme.secondary
            )
            InfoRow(
                icon = Icons.Default.People,
                text = "Casas: ${neighborhood.numeroCasas}",
                iconColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, text: String, iconColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
        )
    }
}

@Composable
private fun EmptyStateView(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(32.dp)
    ) {
        Icon(
            Icons.Default.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "No hay vecindarios registrados",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Presiona el botón + para agregar uno nuevo",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        )
    }
}

@Composable
private fun AddNeighborhoodDialog(
    onDismiss: () -> Unit,
    onAdd: () -> Unit,
    viewModel: NeighborhoodsViewModel
) {
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.obtenerUbicacion()
        }
    }

    Dialog(
        onDismissRequest = {
            viewModel.showError.value = false
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Nuevo Vecindario",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00497D)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    value = viewModel.nombre.value,
                    onValueChange = { viewModel.nombre.value = it },
                    label = "Nombre",
                    icon = Icons.Default.DriveFileRenameOutline,
                    isError = viewModel.errorFields.value.contains("nombre"),
                    errorMessage = "Nombre requerido"
                )

                Button(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            viewModel.obtenerUbicacion()
                        } else {
                            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = Color(0xFF096AAF),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text("Usar mi ubicación")
                }

                InputField(
                    value = viewModel.direccion.value,
                    onValueChange = { viewModel.direccion.value = it },
                    label = "Dirección",
                    icon = Icons.Default.Place,
                    isError = viewModel.errorFields.value.contains("direccion"),
                    errorMessage = "Dirección requerida"
                )

                InputField(
                    value = viewModel.colonia.value,
                    onValueChange = { viewModel.colonia.value = it },
                    label = "Colonia",
                    icon = Icons.Default.HomeWork,
                    isError = viewModel.errorFields.value.contains("colonia"),
                    errorMessage = "Colonia requerida"
                )

                InputField(
                    value = viewModel.estado.value,
                    onValueChange = { viewModel.estado.value = it },
                    label = "Estado",
                    icon = Icons.Default.Map,
                    isError = viewModel.errorFields.value.contains("estado"),
                    errorMessage = "Estado requerido"
                )

                InputField(
                    value = viewModel.numeroCasas.value,
                    onValueChange = { viewModel.numeroCasas.value = it },
                    label = "Número de Casas",
                    icon = Icons.Default.Numbers,
                    keyboardType = KeyboardType.Number,
                    isError = viewModel.errorFields.value.contains("numeroCasas"),
                    errorMessage = "Número de casas requerido"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            viewModel.showError.value = false
                            onDismiss()
                        },
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Gray
                        )
                    ) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (viewModel.validateInputs()) {
                                onAdd()
                            }
                        },
                        colors = ButtonColors(
                            containerColor = Color(0xFF096AAF),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Black,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text("Agregar", )
                    }
                }
            }
        }
    }
}

@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isError) MaterialTheme.colorScheme.error
                    else Color(0xFF096AAF)

                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error
                else Color(0xFF096AAF),
                unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                else MaterialTheme.colorScheme.outline,
                focusedLabelColor = if (isError) MaterialTheme.colorScheme.error
                else Color(0xFF096AAF),
                errorCursorColor = MaterialTheme.colorScheme.error,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            singleLine = true
        )
    }
}
