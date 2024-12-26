package Biblioteca.Vista;

import Biblioteca.Controlador.ControladorUsuarios;
import Biblioteca.Controlador.PeticionesUsuario;
import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Tipo;
import Biblioteca.Modelo.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuUsuarios {
    private final DAOGenerico<Usuario, Integer> daoUsuario;
    private final PeticionesUsuario pUs;
    private final ControladorUsuarios cUs;
    private final Scanner scanner;

    public MenuUsuarios() {
      this.scanner = new Scanner(System.in);
      this.pUs = new PeticionesUsuario();
      this.cUs = new ControladorUsuarios();
      this.daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
      mostrarMenu();
      elegirOpcion();
    }

    public void mostrarMenu(){
        System.out.println("Gestion de Usuarios");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Modificar Usuario");
        System.out.println("3. Eliminar Usuario");
        System.out.println("4. Listar Usuarios");
        System.out.println("5. Salir");
    }

    public void elegirOpcion(){
        Integer opcion = scanner.nextInt();
        switch (opcion){
            case 1: crearUsuario(); break;
            case 2: modificarUsuario();break;
            case 3: eliminarUsuario();break;
            case 4: listarUsuarios();break;
            default: System.out.println("Saliendoo...");break;

        }
    }

    //Metodo para crear un usuario

    public void crearUsuario(){
        System.out.println("Vamos a añadir un usuario a la biblioteca");

        //Este metodo realiza preguntas para recoger los datos y terminar creando un usuario
        Usuario us = pUs.creacionUsuario();

        //Realizamos una comprobacion de si el usuario se ha creado correctamente
        if (cUs.verificarCreacionDeUsuario(us) == true){
            daoUsuario.add(us);
            System.out.println("Creacion realizada con exito");
        }
        else {
            System.out.println("No se pudo crear el usuario. Vuelve a intentarlo");
            crearUsuario();
        }
    }

    //Metodos para actualizar un usuario

    public void modificarUsuario(){
        System.out.println("Vamos a modificar un usuario de la biblioteca");
        Usuario us  = pUs.modificarUsuario();

        if (cUs.comprobarUsuarioExiste(us) == true){
            System.out.println("Que desea modificar");
            System.out.println("1. Dni");
            System.out.println("2. Nombre");
            System.out.println("3. Email");
            System.out.println("4. Password");
            System.out.println("5. Tipo");
            int opcion = scanner.nextInt();
            switch (opcion){
                case 1: modificarDNI(us); break;
                case 2: modificarNombre(us); break;
                case 3: modificarEmail(us); break;
                case 4: modificarPassword(us); break;
                case 5: modificarTipo(us); break;
                default: System.out.println("Opcion invalida");
            }
        }
        else {
            System.out.println("usuario no exite. Vuelva a intentarlo");
            modificarUsuario();
        }
    }

    public void modificarDNI(Usuario us){
        //Esta parte no se si se podria hacer con el controlador de peticiones de usuario y aunque lo haga no simplifica mucho
        System.out.println("Indique el nuevo dni que desea darle a el usuario con id "+us.getId());
        String newDNI = scanner.next();
        if (cUs.comprobarDni(newDNI) == true){ //Controlador para saber si el dni esta correctamente introducido
            us.setDni(newDNI);
            daoUsuario.update(us);
            System.out.println("Dni modificado con exito");
        }
        else {
            System.out.println("Dni invalido. Vuelve a intentarlo");
            modificarDNI(us);
        }
    }

    public void modificarNombre(Usuario us){
        //Esta parte no se si se podria hacer con el controlador de peticiones de usuario y aunque lo haga no simplifica mucho
        System.out.println("Indique el nuevo nombre que desea darle a el usuario con id "+us.getId());
        String newNombre = scanner.next();
        us.setNombre(newNombre);
        daoUsuario.update(us);
        System.out.println("Nombre modificado con exito");
    }

    public void modificarEmail(Usuario us){
        System.out.println("Indique el nuevo email que desea darle a el usuario con id "+us.getId());
        String newEmail = scanner.next();
        //Controlador para saber si el email esta bien introducido
        if (cUs.comprobarEmail(newEmail) == true){
            us.setEmail(newEmail);
            daoUsuario.update(us);
            System.out.println("Email modificado con exito");
        }
        else {
            System.out.println("Email invalido. Vuelve a intentarlo");
            modificarEmail(us);
        }
    }

    public void modificarPassword(Usuario us){
        System.out.println("Indique la nuevo contraseña que desea darle a el usuario con id "+us.getId());
        String newPassword = scanner.next();
        //Controlador para saber si la contraseña esta bien introducido
        if (cUs.comprobarPassword(newPassword) == true){
            us.setPassword(newPassword);
            daoUsuario.update(us);
            System.out.println("Contraseña modificada con exito");
        }
        else {
            System.out.println("Error al actualizar contraseña. Vuelve a intentarlo");
            modificarPassword(us);
        }
    }

    public void modificarTipo(Usuario us){
        System.out.println("Indique el nuevo tipo que desea darle a el usuario con id "+us.getId());
        Tipo newTipo = Tipo.valueOf(scanner.next());
        //Controlador para saber si el tipo esta bien introducido
        if (cUs.comprobarTipoUsuario(newTipo) == true){
            us.setTipo(newTipo);
            daoUsuario.update(us);
            System.out.println("Tipo modificado con exito");
        }
        else {
            System.out.println("Tipo invalido. Vuelve a intentarlo");
        }
    }


    //Metodo para eliminar usuarios
    public void eliminarUsuario(){
        System.out.println("Vamos a eliminar el usuario");
        Usuario us = daoUsuario.getById(pUs.eliminarUsuario().getId());
        if (cUs.comprobarUsuarioExiste(us) == true){
            daoUsuario.delete(us);
            System.out.println("Usuario eliminado con exito");
        }
        else {
            System.out.println("Usuario no existe. Vuelve a intentarlo");
            eliminarUsuario();
        }
    }


    //Metodo para listar Usuarios
    public void listarUsuarios(){
        List<Usuario> usuarios = daoUsuario.getAll();
        System.out.println(usuarios);
    }
}
