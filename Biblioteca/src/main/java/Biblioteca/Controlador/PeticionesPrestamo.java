package Biblioteca.Controlador;

import Biblioteca.Modelo.*;

import java.time.LocalDate;
import java.util.Scanner;

public class PeticionesPrestamo {
    private final Scanner scanner;
    private final DAOGenerico<Prestamo, Integer> daoPrestamo;
    private final DAOGenerico<Usuario, Integer> daoUsuario;
    private final DAOGenerico<Ejemplar, Integer> daoEjemplar;

    public PeticionesPrestamo() {
        scanner = new Scanner(System.in);
        this.daoPrestamo = new DAOGenerico<>(Prestamo.class, Integer.class);
        this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
        this.daoEjemplar = new DAOGenerico<>(Ejemplar.class, Integer.class);
    }

    public Prestamo creacionPrestamo(){
        System.out.println("Indica el id de usuario que va a llevarse un ejemplar");
        Integer usuarioId = scanner.nextInt();
        Usuario us = daoUsuario.getById(usuarioId);
        System.out.println("Indica el ejemplar que va ha ser alquilado");
        Integer ejemplarId = scanner.nextInt();
        Ejemplar ejemplar = daoEjemplar.getById(ejemplarId);
        Prestamo prestamo = new Prestamo(us, ejemplar, LocalDate.now(), LocalDate.now().plusDays(15));
        return prestamo;
    }

    public Prestamo modificarPrestamo() {
        System.out.println("Indique el id del prestamo que desea modificar");
        Integer id = scanner.nextInt();
        Prestamo prestamo = daoPrestamo.getById(id);
        return  prestamo;
    }

    public Prestamo eliminarPrestamo(){
        System.out.println("Indique el id del prestamo que desea eliminar");
        Integer id = scanner.nextInt();
        Prestamo prestamo = daoPrestamo.getById(id);
        return  prestamo;
    }
}
