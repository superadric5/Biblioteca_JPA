package Biblioteca.Controlador;

import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Tipo;
import Biblioteca.Modelo.Usuario;

import java.time.LocalDate;
import java.util.Scanner;

public class PeticionesUsuario {
    private final Scanner scanner;
    private final DAOGenerico<Usuario, Integer> daoUsuario;

    public PeticionesUsuario() {
        scanner = new Scanner(System.in);
        this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
    }

    public Usuario creacionUsuario(){
        System.out.println("Ingrese su dni");
        String dni = scanner.next();
        System.out.println("Ingrese su nombre");
        String nombre = scanner.next();
        System.out.println("Ingrese su email");
        String email = scanner.next();
        System.out.println("Ingrese su contraseña");
        String password = scanner.next();
        System.out.println("Ingrese su tipo");
        Tipo tipo = Tipo.valueOf(scanner.next());
        LocalDate penalizacion = null;

        Usuario usuario = new Usuario(dni, nombre, email, password, tipo, penalizacion);
        return usuario;
    }

    public Usuario modificarUsuario(){
        System.out.println("Ingrese su id");
        Integer id = scanner.nextInt();
        Usuario us = daoUsuario.getById(id);
        return  us;
    }

    public Usuario eliminarUsuario(){
        System.out.println("Ingrese su id");
        Integer id = scanner.nextInt();
        Usuario us = daoUsuario.getById(id);
        return  us;
    }
}
