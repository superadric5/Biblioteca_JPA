package Biblioteca.Controlador;

import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Ejemplar;
import Biblioteca.Modelo.Libro;

import java.util.Scanner;

public class PeticionesLibros {
    private final Scanner scanner;
    private final DAOGenerico<Libro, String> daoLibro;

    public PeticionesLibros() {
        scanner = new Scanner(System.in);
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
    }

    public Libro creacionLibro(){
        System.out.println("Introduce su ISBN");
        String isbn = scanner.next();
        System.out.println("Introduce su Titulo");
        String titulo = scanner.next();
        System.out.println("Introduce su Autor");
        String autor = scanner.next();
        Libro libro = new Libro(isbn, titulo, autor);
        return libro;
    }

    public Libro modificarLibro() {
        System.out.println("Indique el isbn del libro que desea actualizar");
        String isbn = scanner.next();
        Libro libro = daoLibro.getById(isbn);
        return  libro;
    }

    public Libro eliminarLibro(){
        System.out.println("Indique el isbn del libro que desea eliminar");
        String isbn = scanner.next();
        Libro libro = daoLibro.getById(isbn);
        return  libro;
    }
}

