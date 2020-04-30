package com.example.servicioremoto

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class ServicioRemoto : Service() {

    var reproductor : MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        reproductor = MediaPlayer.create(this,R.raw.audio)
    }

    private val binder = object : IServicioMusica.Stub(){
        override fun reproduce(mensaje: String?): String {
            reproductor?.start()
            return if(mensaje!=null)mensaje else ""
        }

        override fun getPosicion(): Int {
            return if(reproductor!=null) reproductor!!.currentPosition else 0
        }

        override fun setPosicion(ms: Int) {
            reproductor?.seekTo(ms)
        }

    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}
