package Biblioteca.Vista;

import Biblioteca.Controlador.ControladorEjemplares;
import Biblioteca.Controlador.PeticionesEjemplar;
import Biblioteca.Modelo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuEjemplares {
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private final DAOGenerico<Libro, String> daoLibro;
    private final Scanner scanner;
    private final PeticionesEjemplar pEj;
    private final ControladorEjemplares cEj;


    public MenuEjemplares() {
        this.scanner = new Scanner(System.in);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        this.daoLibro = new DAOGenerico<>(Libro.class, String.class);
        this.cEj = new ControladorEjemplares();
        this.pEj = new PeticionesEjemplar();
        mostrarMenu();
        elegirOpcion();
    }

    public void mostrarMenu(){
        System.out.println("Gestion de Usuarios");
        System.out.println("1. Añadir Ejemplar");
        System.out.println("2. Modificar Ejemplar");
        System.out.println("3. Eliminar Ejemplar");
        System.out.println("4. Listar Ejemplares");
        System.out.println("5. Salir");
    }

    public void elegirOpcion(){
        Integer opcion = scanner.nextInt();
        switch (opcion){
            case 1: crearEjemplar();break;
            case 2: modificarEjemplar();break;
            case 3: eliminarEjemplar();break;
            case 4: listarEjemplares();break;
            default: System.out.println("Saliendo...");break;

        }
    }

    //Metodo para crear un Ejemplar

    public void crearEjemplar(){
        System.out.println("Vamos a añadir un Ejemplar a la biblioteca");
        Ejemplar ej = pEj.creacionEjemplar();
        if (cEj.verificarCreacionDeEjemplar(ej) == true){
            daoEjemplar.add(ej);
            System.out.println("Ejemplar creado con exito");
        }
        else {
            System.out.println("No se pudo crear el ejemplar. Vuelve a intentarlo");
            crearEjemplar();
        }
    }

    //Metodos para actualizar un ejemplar

    public void modificarEjemplar(){
        System.out.println("Vamos a modificar un Ejemplar de la biblioteca");
        Ejemplar ej  = pEj.modificarEjemplar();
        if (cEj.comprobarEjemplarExiste(ej) == true){
            System.out.println("Que desea modificar");
            System.out.println("1. ISBN");
            System.out.println("2. Estado");

            int opcion = scanner.nextInt();
            switch (opcion){
                case 1: modificarLibro(ej); break;
                case 2: modificarEstado(ej); break;
                default: System.out.println("Opcion invalida");
            }
        }
        else {
            System.out.println("No se pudo modificar el ejemplar. Vuelve a intentarlo");
            modificarEjemplar();
        }


    }

    public void modificarLibro(Ejemplar ej){
        System.out.println("Indique el nuevo ISBN que desea darle a el ejemplar con id "+ej.getId());
        String newISBN = scanner.next();
        if (cEj.comprobarISBN(newISBN)) {
            Libro libro = daoLibro.getById(newISBN);
            ej.setIsbn(libro);
            daoEjemplar.update(ej);
            System.out.println("Ejemplar modificado con exito");
        }
        else {
            System.out.println("No se pudo modificar. Vuelve a intentarlo");
            modificarLibro(ej);
        }

    }

    public void modificarEstado(Ejemplar ej){
        System.out.println("Indique el nuevo estado que desea darle a el ejemplar con id "+ej.getId());
        Estado estado = Estado.valueOf(scanner.next());
        if (cEj.comprobarEstado(estado)) {
            ej.setEstado(estado);
            daoEjemplar.update(ej);
            System.out.println("Ejemplar modificado con exito");
        }
        else {
            System.out.println("No se pudo modificar. Vuelve a intentarlo");
            modificarEstado(ej);
        }
    }



    //Metodo para eliminar ejemplares

    public void eliminarEjemplar(){
        System.out.println("Vamos a eliminar un Ejemplar");
        Ejemplar ej = daoEjemplar.getById(pEj.eliminarEjemplar().getId());
        if (cEj.comprobarEjemplarExiste(ej) == true){
            daoEjemplar.delete(ej);
            System.out.println("Ejemplar eliminado con exito");
        }
        else {
            System.out.println("No se pudo eliminar. Vuelve a intentarlo");
            eliminarEjemplar();
        }
    }


    //Metodo para listar Ejemplares
    public void listarEjemplares(){
        List<Ejemplar> ejemplares = daoEjemplar.getAll();
        System.out.println(ejemplares);
    }
}
