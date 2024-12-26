package Biblioteca.Vista;

import java.util.Scanner;

public class MenuAdministrador {

    private Scanner scanner;

    public MenuAdministrador() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("Menu de Administrador");
        System.out.println("1. Gestion de usuarios");
        System.out.println("2. Gestion de ejemplares");
        System.out.println("3. Gestion de libros");
        System.out.println("4. Gestion de prestamos");
        System.out.println("5. Salir");
    }

    public void elegirOpcion() {
        Integer opc = scanner.nextInt();
        switch (opc) {
            case 1: MenuUsuarios menuUsuario = new MenuUsuarios(); break;
            case 2: MenuEjemplares menuEjemplar = new MenuEjemplares(); break;
            case 3: MenuLibros menuLibro = new MenuLibros(); break;
            case 4: MenuPrestamos menuPrestamo = new MenuPrestamos(); break;
            default:System.out.println("Saliendo...");
        }
    }
}
