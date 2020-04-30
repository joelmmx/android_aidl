// IServicioMusica.aidl
package com.example.servicioremoto;

// Declare any non-default types here with import statements

interface IServicioMusica {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String reproduce(in String mensaje);
    void setPosicion(int ms);
    int getPosicion();
}
