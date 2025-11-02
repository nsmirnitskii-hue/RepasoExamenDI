package com.example.mixmusic.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mixmusic.viewmodel.MusicViewModel
import com.example.mixmusic.viewmodel.SoundSlot
import com.example.mixmusic.R
import androidx.compose.foundation.lazy.items


@Composable
fun MixScreen(
    navController: NavController,
    musicViewModel: MusicViewModel,
    context: Context = LocalContext.current
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                "Music Mix",
                fontSize = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Muestras",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        // Seccion de canciones fijas
        items(musicViewModel.fixedTracks) { track ->
            SongCard(soundSlot = track, viewModel = musicViewModel, context = context)
        }

        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                "Canciones importadas",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        // Canciones importadas desde el móvil
        if (!musicViewModel.userTracks.isEmpty()) {
            items(musicViewModel.userTracks) { track ->
                SongCard(soundSlot = track, viewModel = musicViewModel, context = context)
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Botón que detiene todas las canciones
            Button(
                onClick = { musicViewModel.stopAll() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "stop all",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(20.dp)
                    )

                    Text("Detener todo", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

// Crea un Card para un tema
@Composable
fun SongCard(soundSlot: SoundSlot, viewModel: MusicViewModel, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9))
    ) {
        // Añadir contenido de la tarjeta, cada cancion deberá ir en una tarjeta
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                soundSlot.name,
                fontSize = 15.sp,
                color = Color.Black
            )

            // Botón Play/Pausa individual
            Button(
                onClick = { viewModel.togglePlay(soundSlot, context) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "play",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp)
                )
                Text("Play", fontSize = 14.sp)
            }
        }
    }
}
