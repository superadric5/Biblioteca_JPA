package Biblioteca.Controlador;

import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Ejemplar;
import Biblioteca.Modelo.Estado;
import Biblioteca.Modelo.Libro;

import java.util.List;

public class ControladorLibros {
    private final DAOGenerico<Libro, String> daoLibro;
    public ControladorLibros() {
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
    }

    public boolean verificarCreacionDeEjemplar(Libro libro) {

        boolean libroExiste = false;
        boolean isbnCorrecto = false;

        libroExiste = comprobarLibroExiste(libro);
        isbnCorrecto = comprobarISBN(libro);

        if (libroExiste == false && isbnCorrecto == true) {
            return true;
        }
        return false;
    }

    public boolean comprobarLibroExiste(Libro libro) {
        List<Libro> libros = daoLibro.getAll();
        for (Libro l : libros) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarISBN(Libro libro) {
        String isbn = libro.getIsbn();
        if (isbn.equals("^(\\d{13})$")) {
            return true;
        }
        return false;
    }
}
