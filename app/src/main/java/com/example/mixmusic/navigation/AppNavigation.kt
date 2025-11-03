package com.example.mixmusic.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mixmusic.screen.AddScreen
import com.example.mixmusic.screen.MixScreen
import com.example.mixmusic.viewmodel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var navController = rememberNavController()
    val musicViewModel: MusicViewModel = viewModel()

    Scaffold(
        // barra superior
        topBar = { TopBar() },

        // barra inferior
        bottomBar = { BottomBar(navController) }

        // cuerpo central
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // definición de rutas de pantallas
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    MixScreen(navController, musicViewModel)
                }
                composable("add") {
                    AddScreen(navController, musicViewModel)
                }
            }
        }
    }
}

// Crea la barra superior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        title = {
            Text(
                "DI",
                fontSize = 30.sp,
            )
        },
        actions = {

            val context = LocalContext.current

            // Intent: enlace a Github
            IconButton(onClick = {
                val url = "https://github.com/nsmirnitskii-hue/RepasoExamenDI"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Github",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }

            // Intent: enviar email
            IconButton(onClick = {
                val recipient = "n.smirnitskii@ikasle.eus"
                val subject = "Prueba desde Jetpack Compose"
                val body = "Bienbenido, esto es un mensage de prueba"
                val uri = ("mailto:$recipient"
                        + "?subject=${Uri.encode(subject)}"
                        + "&body=${Uri.encode(body)}").toUri()
                val intent = Intent(Intent.ACTION_SENDTO).apply { data = uri }
                context.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }

            // Intent: compartir mensaje en whatssapp
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Examen de DI")
                }
                context.startActivity(Intent.createChooser(intent, "Compartir con"))

            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

// Crea la barra inferior
@Composable
fun BottomBar(navController: NavHostController) {

    NavigationBar(containerColor = Color.LightGray) {

        // Home
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = {
                Text(
                    "Home",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black,
                indicatorColor = Color.Transparent
            )
        )

        // Add

        NavigationBarItem(
            icon = { Icon(Icons.Default.Build, contentDescription = "add") },
            selected = navController.currentBackStackEntry?.destination?.route == "add",
            onClick = {
                navController.navigate("add") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            label = {
                Text(
                    "Add",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black,
                indicatorColor = Color.Transparent
            )
        )
    }
}