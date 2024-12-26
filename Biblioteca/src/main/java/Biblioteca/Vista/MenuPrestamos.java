package Biblioteca.Vista;

import Biblioteca.Controlador.ControladorPrestamos;
import Biblioteca.Controlador.PeticionesPrestamo;
import Biblioteca.Modelo.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class MenuPrestamos {
    private final DAOGenerico<Prestamo, Integer> daoPrestamo;
    private final DAOGenerico<Usuario, Integer> daoUsuario;
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;
    private final Scanner scanner;
    private final PeticionesPrestamo pPr;
    private final ControladorPrestamos cPr;

    public MenuPrestamos() {
        this.scanner = new Scanner(System.in);
        this.daoPrestamo = new DAOGenerico<>(Prestamo.class, Integer.class);
        this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
        this.cPr = new ControladorPrestamos();
        this.pPr = new PeticionesPrestamo();
        mostrarMenu();
        elegirOpcion();
    }

    public void mostrarMenu(){
        System.out.println("Gestion de Prestamo");
        System.out.println("1. Crear Prestamo");
        System.out.println("2. Modificar Prestamo");
        System.out.println("3. Eliminar Prestamo");
        System.out.println("4. Registrar Devolucion");
        System.out.println("5. Listar Prestamos");
        System.out.println("6. Salir");
    }

    public void elegirOpcion(){
        Integer opcion = scanner.nextInt();
        switch (opcion){
            case 1: crearPrestamo();break;
            case 2: modificarPrestamo();break;
            case 3: eliminarPrestamo();break;
            case 4: devolucion();break;
            case 5: listarPrestamos();break;
            default: System.out.println("Saliendo..."); break;
        }
    }

    //Metodo de crear prestamo

    public void crearPrestamo(){
        System.out.println("Vamos a registrar un prestamo");
        Prestamo prestamo = pPr.creacionPrestamo();
        if (cPr.verificarCreacionPrestamo(prestamo) == true){
            daoPrestamo.add(prestamo);
            prestamo.getEjemplar().setEstado(Estado.Prestado);
            daoEjemplar.update(prestamo.getEjemplar());
            System.out.println("Prestamo creado con exito");
        }
        else {
            System.out.println("No se pudo crear el prestamo. Vuelve a intentarlo.");
            crearPrestamo();
        }
    }

    //Metodos de modificar prestamos

    public void modificarPrestamo(){
        System.out.println("Vamos modificar un prestamo");
        Prestamo prestamo = pPr.modificarPrestamo();
        if (cPr.comprobarPrestamoExiste(prestamo)){
            System.out.println("Que desea modificar?");
            System.out.println("1. Usuario");
            System.out.println("2. Ejemplar");
            System.out.println("3. Salir");
            int opcion = scanner.nextInt();
            switch (opcion){
                case 1: modificarUsuario(prestamo);break;
                case 2: modificarEjemplar(prestamo);break;
                default: System.out.println("Saliendo..."); break;
            }
        }
        else {
            System.out.println("El prestamo no existe pruebe otra vez.");
            modificarPrestamo();
        }
    }

    public void modificarUsuario(Prestamo prestamo){
        System.out.println("Vamos modificar el usuario del prestamo con id " + prestamo.getId());
        System.out.println("Indique el nuevo propietario del prestamo");
        Integer usuarioId = scanner.nextInt();
        Usuario us = daoUsuario.getById(usuarioId);
        if (cPr.comprobarUsuario(us) == true){
            prestamo.setUsuario(us);
            prestamo.setFechaInicio(LocalDate.now());
            prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
            daoPrestamo.update(prestamo);
            System.out.println("Usuario del prestamo "+ prestamo.getId() +" modificado con exito");
        }
        else {
            System.out.println("No se pudo modificar el usuario correctamente. Vuelve a intentarlo");
            modificarUsuario(prestamo);
        }

    }

    public void modificarEjemplar(Prestamo prestamo){
        System.out.println("Vamos modificar el ejemplar del prestamo con id " + prestamo.getId());
        System.out.println("Indique el nuevo ejemplar del prestamo");
        Integer ejemplarId = scanner.nextInt();
        Ejemplar ejemplar = daoEjemplar.getById(ejemplarId);
        if (cPr.comprobarEjemplar(ejemplar) == true){
            prestamo.setEjemplar(ejemplar);
            prestamo.setFechaInicio(LocalDate.now());
            prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
            daoPrestamo.update(prestamo);
            System.out.println("Ejemplar del prestamo "+ prestamo.getId() +" modificado con exito");
        }
        else {
            System.out.println("No se pudo modificar el ejemplar correctamente. Vuelve a intentarlo");
            modificarUsuario(prestamo);
        }

    }

    //Metodo de eliminar Prestamos

    public void eliminarPrestamo(){
        System.out.println("Vamos a eliminar un Prestamo");
        Prestamo prestamo = pPr.eliminarPrestamo();
        if (cPr.comprobarPrestamoExiste(prestamo) == true){
            Prestamo p = daoPrestamo.getById(prestamo.getId());
            daoPrestamo.delete(p);
            System.out.println("Prestamo eliminado con exito");
        }
        else {
            System.out.println("No se pudo eliminar el prestamo con exito. Pruebe otra vez");
            eliminarPrestamo();
        }

    }

    //Metodo de devolucion de ejemplares

    public void devolucion(){
        System.out.println("Devolucion de prestamo");
        System.out.println("Que usuario desea devolucion?");
        Integer usuarioId = scanner.nextInt();
        Usuario us = daoUsuario.getById(usuarioId);
        System.out.println("Que ejemplar va ha devolver?");
        Integer ejemplarId = scanner.nextInt();
        Ejemplar ejemplar = daoEjemplar.getById(ejemplarId);
        if (cPr.comprobarPrestamoDeXusuarioXejemplarExiste(us, ejemplar)){
            Prestamo prestamo = daoPrestamo.getPrestamoDeXUsuarioDeXEjemplar(us, ejemplar);
            ejemplar.setEstado(Estado.Disponible);
            daoEjemplar.update(ejemplar);
            cPr.comprobarDevolucionCorrecta(prestamo); //Este metodo es para ver si la devolucion esta realizada dentro del plazo y si no es asi penaliza al usuario
            daoPrestamo.delete(prestamo); //Borramos el prestamo
            System.out.println("Devolucion de prestamo finalizado");

        }
        else {
            System.out.println("Este prestamo no existe. Pruebe otra vez");
            devolucion();
        }
    }

    //Metodo para listar Prestamos
    public void listarPrestamos(){
        List<Prestamo> prestamos = daoPrestamo.getAll();
        System.out.println(prestamos);
    }
}
