package Biblioteca.Controlador;

import Biblioteca.Modelo.*;

import java.util.List;

public class ControladorEjemplares {
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private final DAOGenerico<Libro, String> daoLibro;
    public ControladorEjemplares() {
        daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
    }

    public boolean verificarCreacionDeEjemplar(Ejemplar ej) {

        boolean isbnCorrecto = false;
        boolean estadoCorrecto = false;

        isbnCorrecto = comprobarISBN(ej);
        estadoCorrecto = comprobarEstado(ej);

        if (isbnCorrecto == true && estadoCorrecto == true) {
            return true;
        }
        return false;
    }

    public boolean comprobarEjemplarExiste(Ejemplar ej) {
        List<Ejemplar> ejemplares = daoEjemplar.getAll();
       for (Ejemplar e : ejemplares) {
           if (e.getId().equals(ej.getId())) {
               return true;
           }
       }
        return false;
    }

    public boolean comprobarISBN(Ejemplar ej) {
        Libro libro = ej.getIsbn();
        List<Libro> libros = daoLibro.getAll();
        for (Libro l : libros) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarISBN(String isbn) {
        Libro libro = daoLibro.getById(isbn);
        List<Libro> libros = daoLibro.getAll();
        for (Libro l : libros) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarEstado(Ejemplar ej) {
        if (ej.getEstado() == Estado.Dañado || ej.getEstado() == Estado.Prestado || ej.getEstado() == Estado.Disponible) {
            return true;
        }
        return false;
    }

    public boolean comprobarEstado(Estado estado) {
        if (estado == Estado.Dañado || estado == Estado.Prestado || estado == Estado.Disponible) {
            return true;
        }
        return false;
    }

    public boolean comprobarEstadoDisponible(Ejemplar ej) {
        if (ej.getEstado() == Estado.Disponible) {
            return true;
        }
        return false;
    }

}
