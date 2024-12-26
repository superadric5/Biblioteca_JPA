package Biblioteca.Vista;

import Biblioteca.Controlador.ControlInicioSesion;
import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Prestamo;
import Biblioteca.Modelo.Tipo;
import Biblioteca.Modelo.Usuario;

import java.util.Scanner;

public class MenuInicioSesion {

    private Scanner scanner;
    private ControlInicioSesion controlInicioSesion;

    public MenuInicioSesion() {
        scanner = new Scanner(System.in);
        controlInicioSesion = new ControlInicioSesion();
        iniciarSesion();
    }

    public void iniciarSesion() {
        DAOGenerico<Usuario, Integer> daousuario = new DAOGenerico<>(Usuario.class, Integer.class);
        DAOGenerico<Prestamo, Integer> daoPrestamos = new DAOGenerico<>(Prestamo.class, Integer.class);

        System.out.println("Inicio de sesion:");
        System.out.println("Indique su id");
        Integer id = scanner.nextInt();

        if (controlInicioSesion.verificarSesion(id) == true) {
            if (daousuario.getById(id).getTipo() == Tipo.administrador) {
                MenuAdministrador menuAdministrador = new MenuAdministrador();
                menuAdministrador.mostrarMenu();
                menuAdministrador.elegirOpcion();

            } else if (daousuario.getById(id).getTipo() == Tipo.normal) {
                Usuario usuario = daousuario.getById(id);
                System.out.println("Prestamos de " + usuario.getNombre());
                System.out.println(daoPrestamos.getPrestamosDeXUsuario(usuario));

            }
        }
        else {
            System.out.println("No se pudo iniciar sesion");
            iniciarSesion();
        }

    }
}
