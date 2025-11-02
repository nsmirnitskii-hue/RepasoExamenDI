package com.example.mixmusic.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mixmusic.R

// Estructura utilizada para almacenar las rutas de lass canciones y sus nombres.
// En el caso de las canciones cargadas desde el proyecto se utiliza resId y
// las cargadas desde el movil soundUri
data class SoundSlot(
    val name: String,
    val soundUri: Uri? = null,
    val resId: Int? = null,
    var player: MediaPlayer? = null // Objeto MediaPlayer que reproduce el sonido
)

// ViewModel que gestiona la lógica de reproducción de música.
class MusicViewModel : ViewModel() {

    // Lista de canciones fijas incluidas dentro del proyecto (res/raw)
    // Estas se crean con su nombre y el ID del recurso correspondiente.
    val fixedTracks = mutableListOf(
        SoundSlot("Tema 1", null, R.raw.stranger),
        SoundSlot("Tema 2", null, R.raw.uplifting)
    )

    // Lista dinámica que guarda las canciones cargadas por el usuario desde su móvil
    val userTracks = mutableListOf<SoundSlot>()

    // Función principal para reproducir o pausar una canción.
    // Si el MediaPlayer aún no existe, lo crea dependiendo de si:
    //   - el sonido proviene del móvil (soundUri != null)
    //   - o del proyecto (resId != null)
    // Luego alterna entre reproducir o pausar según el estado actual.
    fun togglePlay(soundSlot: SoundSlot, context: Context) {

        if (soundSlot.player == null) {
            soundSlot.player = if (soundSlot.soundUri != null) {
                // Canción cargada desde el móvil
                MediaPlayer.create(context, soundSlot.soundUri)
            } else if (soundSlot.resId != null) {
                // Canción del proyecto (res/raw)
                MediaPlayer.create(context, soundSlot.resId)
            } else {
                null
            }
        }

        if (soundSlot.player == null) return

        if (soundSlot.player!!.isPlaying) {
            soundSlot.player!!.pause()
        } else {
            soundSlot.player!!.start()
        }
    }

    // Añade una nueva canción seleccionada por el usuario desde su móvil.
    // Recibe el URI del archivo y opcionalmente un nombre personalizado.
    // Crea un nuevo SoundSlot y lo agrega a la lista de userTracks.
    fun addUserTrack(uri: Uri, name: String = "Canción del móvil") {
        val newSlot = SoundSlot(name, soundUri = uri)
        userTracks.add(newSlot)
    }

    // Detiene y libera todos los MediaPlayers activos (fijos y de usuario).
    // Es útil cuando se cierra la app o se quiere detener toda la música.
    // Evita fugas de memoria liberando los recursos del sistema.
    fun stopAll() {
        val allTracks = fixedTracks + userTracks
        for (track in allTracks) {
            track.player?.stop()
            track.player?.release()
            track.player = null
        }
    }
}