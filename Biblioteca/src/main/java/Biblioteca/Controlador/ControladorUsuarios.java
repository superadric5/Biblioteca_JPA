package Biblioteca.Controlador;

import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Prestamo;
import Biblioteca.Modelo.Tipo;
import Biblioteca.Modelo.Usuario;

import java.time.LocalDate;
import java.util.List;

public class ControladorUsuarios {
    private final DAOGenerico<Usuario, Integer> daoUsusario;
    private final DAOGenerico<Prestamo, Integer> daoPrestamo;

    public ControladorUsuarios() {
        daoUsusario = new DAOGenerico<>(Usuario.class, Integer.class);
        daoPrestamo = new DAOGenerico<>(Prestamo.class, Integer.class);
    }

    public boolean verificarCreacionDeUsuario(Usuario usuario) {
        boolean usuarioExiste = false;
        boolean dniCorrecto = false;
        boolean emailCorrecto = false;
        boolean passwordCorrecta = false;
        boolean tipoUsuarioCorrecto = false;

        usuarioExiste = comprobarUsuarioExiste(usuario);
        dniCorrecto = comprobarDNI(usuario);
        emailCorrecto = comprobarEmail(usuario);
        passwordCorrecta = comprobarPassword(usuario);
        tipoUsuarioCorrecto = comprobarTipoUsuario(usuario);

        if (usuarioExiste == false && dniCorrecto == true && emailCorrecto == true && passwordCorrecta == true && tipoUsuarioCorrecto == true) {
            return true;
        }
        return false;

    }

    public boolean comprobarUsuarioExiste(Usuario usuario) {
        List<Usuario> usuarios = daoUsusario.getAll();
        for (Usuario u : usuarios) {
            if (u.getId() == usuario.getId()) {
                return true;
            }
            else if (u.getDni().equals(usuario.getDni())) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarDNI(Usuario usuario) {
        String dni = usuario.getDni();
        if (dni.equals("^[0-9]{8}[A-Z]$")){
            return true;
        }
        return false;
    }

    public boolean comprobarDni(String dni) {
        if (dni.equals("^[0-9]{8}[A-Z]$")){
            return true;
        }
        return false;
    }

    public boolean comprobarEmail(Usuario usuario) {
        String email = usuario.getEmail();
        if (email.equals("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            return true;
        }
        return false;
    }

    public boolean comprobarEmail(String email) {
        if (email.equals("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            return true;
        }
        return false;
    }

    public boolean comprobarPassword(Usuario usuario) {
        String password = usuario.getPassword();
        if (password.equals("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d){8,}$")){
            return true;
        }
        return false;
    }

    public boolean comprobarPassword(String password) {
        if (password.equals("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d){8,}$")){
            return true;
        }
        return false;
    }

    public boolean comprobarTipoUsuario(Usuario usuario) {
        if (usuario.getTipo() == Tipo.normal || usuario.getTipo() == Tipo.administrador) {
            return true;
        }
        return false;
    }

    public boolean comprobarTipoUsuario(Tipo tipo) {
        if (tipo == Tipo.normal || tipo == Tipo.administrador) {
            return true;
        }
        return false;
    }

    public boolean comprobarUsuarioPenalizado(Usuario usuario) {
        if (usuario.getPenalizacionHasta() != null){
            if (usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean comprobarMaxPrestamosUsuario(Usuario usuario) { //Este controlador no sé muy bien si es mejor ponerlo en este controlador o en el controlador de Prestamos
        List<Prestamo> prestamos = daoPrestamo.getPrestamosDeXUsuario(usuario);
        if (prestamos.size() >= 3) {
            return true;
        }
        return false;
    }

}
