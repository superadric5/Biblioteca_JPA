package Biblioteca.Controlador;

import Biblioteca.Modelo.*;

import java.util.Scanner;

public class PeticionesEjemplar {

    private final Scanner scanner;
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private final DAOGenerico<Libro, String> daoLibro;

    public PeticionesEjemplar() {
        scanner = new Scanner(System.in);
        daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        daoLibro = new DAOGenerico<>(Libro.class, String.class);
    }

    public Ejemplar creacionEjemplar() {
        System.out.println("Introduce su ISBN");
        String isbn = scanner.next();
        Libro libro = daoLibro.getById(isbn);
        System.out.println("Introduce su Estado");
        Estado estado = Estado.valueOf(scanner.next());
        Ejemplar ej = new Ejemplar(libro, estado);
        return ej;
    }

    public Ejemplar modificarEjemplar() {
        System.out.println("Indique el id del ejemplar que desea actualizar");
        Integer id = scanner.nextInt();
        Ejemplar ej = daoEjemplar.getById(id);
        return  ej;
    }

    public Ejemplar eliminarEjemplar(){
        System.out.println("Indique el id del ejemplar que desea eliminar");
        Integer id = scanner.nextInt();
        Ejemplar ej = daoEjemplar.getById(id);
        return  ej;
    }


}
