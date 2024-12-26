package Biblioteca.Vista;

import Biblioteca.Controlador.ControladorLibros;
import Biblioteca.Controlador.PeticionesLibros;
import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Ejemplar;
import Biblioteca.Modelo.Estado;
import Biblioteca.Modelo.Libro;

import java.util.List;
import java.util.Scanner;

public class MenuLibros {
    private final DAOGenerico<Libro, String> daoLibro;
    private final Scanner scanner;
    private final PeticionesLibros pLi;
    private final ControladorLibros cLi;

    public MenuLibros() {
        this.scanner = new Scanner(System.in);
        this.daoLibro = new DAOGenerico<>(Libro.class, String.class);
        pLi = new PeticionesLibros();
        cLi = new ControladorLibros();
        mostrarMenu();
        elegirOpcion();
    }

    public void mostrarMenu(){
        System.out.println("Gestion de Usuarios");
        System.out.println("1. Añadir libro");
        System.out.println("2. Modificar libro");
        System.out.println("3. Eliminar libro");
        System.out.println("4. Listar libros");
        System.out.println("5. Salir");
    }

    public void elegirOpcion(){
        Integer opcion = scanner.nextInt();
        switch (opcion){
            case 1: crearLibro();break;
            case 2: modificarLibro();break;
            case 3: eliminarLibro();break;
            case 4: listarLibros();break;
            default: System.out.println("Saliendo...");break;
        }
    }

    //Metodo para crear un Libro

    public void crearLibro(){
        System.out.println("Vamos a añadir un Libro a la biblioteca");
        Libro libro = pLi.creacionLibro();

        if (cLi.verificarCreacionDeEjemplar(libro) == true){
            daoLibro.add(libro);
            System.out.println("Libro añadido a la biblioteca con exito");
        }
        else {
            System.out.println("El libro no se pudo agregar. Vuelve a intentarlo");
            crearLibro();
        }
    }

    //Metodos para actualizar un Libro

    public void modificarLibro(){
        System.out.println("Vamos a modificar un Libro de la biblioteca");
        Libro libro  = pLi.creacionLibro();

        if (cLi.comprobarLibroExiste(libro) == true){
            System.out.println("Que desea modificar");
            System.out.println("1. Titulo");
            System.out.println("2. Autor");

            int opcion = scanner.nextInt();
            switch (opcion){
                case 1: modificarTitulo(libro); break;
                case 2: modificarAutor(libro); break;
                default: System.out.println("Opcion invalida");
            }
        }
        else {
            System.out.println("Libro no existe. Vuelve a intentarlo");
            modificarLibro();
        }

    }

    public void modificarTitulo(Libro libro){ //No hago un control porque un libro se puede llamar como quiera
        System.out.println("Indique el nuevo titulo que desea darle a el libro con isbn: "+libro.getIsbn());
        String newTitulo = scanner.next();
        libro.setTitulo(newTitulo);
        daoLibro.update(libro);
    }

    public void modificarAutor(Libro libro){ //No hago controlador porque un autor se puede llamar de todas las formas
        System.out.println("Indique el nuevo titulo que desea darle a el libro con isbn: "+libro.getIsbn());
        String newAutor = scanner.next();
        libro.setTitulo(newAutor);
        daoLibro.update(libro);
    }



    //Metodo para eliminar libros

    public void eliminarLibro(){
        System.out.println("Vamos a eliminar un Libro");
        Libro libro = daoLibro.getById(pLi.eliminarLibro().getIsbn());
        if (cLi.comprobarLibroExiste(libro) == true){
            daoLibro.delete(libro);
            System.out.println("Libro eliminado con exito");
        }
        else {
            System.out.println("El libro no existe. Vuelve a intentarlo");
            eliminarLibro();
        }

    }
    //Metodo para listar Libros

    public void listarLibros(){
        List<Libro> libros = daoLibro.getAll();
        System.out.println(libros);
    }
}
