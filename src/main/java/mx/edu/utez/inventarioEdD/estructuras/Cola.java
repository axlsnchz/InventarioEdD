package mx.edu.utez.inventarioEdD.estructuras;

import lombok.Getter;

public class Cola<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;
    @Getter
    private int tamano;

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (fin == null) {
            inicio = fin = nuevo;

        } else {

            fin.siguiente = nuevo;
            fin = nuevo;
        }

        tamano++;


    }

    public T desencolar() {

        if (inicio == null) {
            return null;
        }

        T dato = inicio.dato;
        inicio = inicio.siguiente;

        if (inicio == null) fin = null;
        tamano--;

        return dato;
    }

    public T peek() {
        return inicio == null ? null : inicio.dato;
    }
    public boolean isEmpty() {
        return inicio == null;
    }

}