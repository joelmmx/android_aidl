package com.example.servicioremoto

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var servicio : IServicioMusica? = null

    private val conexion = object : ServiceConnection {
        val TAG = "ServiceConnection"
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG,"onServiceDisconnected()")
            servicio = null
            Toast.makeText(this@MainActivity,"Se ha perdido la conexion con el servicio",Toast.LENGTH_SHORT).show()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG,"onServiceConnected()")
            servicio = IServicioMusica.Stub.asInterface(service)
            Toast.makeText(this@MainActivity,"Conectado servicio",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boton_arrancar.setOnClickListener {
            this@MainActivity.bindService(Intent(this@MainActivity,ServicioRemoto::class.java),conexion,
                Context.BIND_AUTO_CREATE)
        }
        boton_reproducir.setOnClickListener {
            try{
                servicio?.reproduce("titulo")
            }catch (e : Exception){
                e.printStackTrace()
                Toast.makeText(this@MainActivity,e.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        boton_avanzar.setOnClickListener {
            try {
                servicio?.setPosicion(servicio?.posicion!!.plus(1000))
            }catch (e:Exception){
                Toast.makeText(this@MainActivity,e.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        boton_detener.setOnClickListener {
            try {
                this@MainActivity.unbindService(conexion)
            }catch (e:java.lang.Exception){
                Toast.makeText(this@MainActivity,e.toString(),Toast.LENGTH_SHORT).show()
            }
            servicio = null
        }
    }
}
