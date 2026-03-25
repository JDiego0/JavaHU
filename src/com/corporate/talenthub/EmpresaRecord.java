package com.corporate.talenthub;

public record EmpresaRecord(String nombre, String nit, int anioFundacion) {

    /*
    Record vs Clase:

    - Record:
      * Menos código
      * Inmutable (no cambia después de crearse)
      * Genera getters automáticamente

    - Clase tradicional:
      * Más código
      * Mutable
    */

}
