package Biblioteca.Controlador;

import Biblioteca.Modelo.DAOGenerico;
import Biblioteca.Modelo.Usuario;

public class ControlInicioSesion {
    private final DAOGenerico<Usuario, Integer> daoUsuario;
    public ControlInicioSesion() {
        daoUsuario = new DAOGenerico<>(Usuario.class, Integer.class);
    }

    public boolean verificarSesion(Integer idUsuario) {
        if (daoUsuario.getById(idUsuario) == null) {
            return false;
        }
        return true;
    }
}
