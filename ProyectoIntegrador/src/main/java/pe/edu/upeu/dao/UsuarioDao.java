package pe.edu.upeu.dao;

import java.io.Console;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.UsuarioTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;

public class UsuarioDao extends AppCrud {
    LeerArchivo lar;
    UsuarioTO usuTO;

    LeerTeclado lte=new LeerTeclado();
    UtilsX ut=new UtilsX();
    
    public void registrarUsuario() {

        usuTO=new UsuarioTO();
        lar=new LeerArchivo("Usuario.txt");
        String usuario=lte.leer("", "Ingrese un usuario");

        if (validarExisteUser(usuario)) {
            usuTO.setUsuario(usuario);
            usuTO.setIdUsuario(idUsuario);
            usuTO.setPerfil(lte.leer("", "ingrese el perfil del usuario (ADMIN, VENDEDOR, CAJERO):").toUpperCase());
    
            usuTO.setUsuario(lte.leer("", "Ingrese un usuario"));
            Console cons=System.console();
            System.out.println("Ingrese su clave");
            char[] pws=cons.readPassword();
            usuTO.setClave(String.valueOf(pws));
            lar=new LeerArchivo("Usuario.txt");
            agregarContenido(lar, usuTO);
            
        }else{
            System.out.println("El usuario ya existe... coloque otro usuario");
            registrarUsuario();

        }


        
    }

    public  boolean login(String usuario, String clave) {
        lar=new LeerArchivo("Usuario.txt");
        Object[][] datax=buscarContenido(lar, 1, usuario);
        if (datax!=null && datax.length>0 && datax[0][2].equals(clave)) {
            return true;
        }
        return false;
        
    }

    public boolean validarExisteUser(String user) {

        lar=new LeerArchivo("Usuario.txt");
        Object[][] datax=buscarContenido(lar, 1, user);
        if (datax==null || datax.length==0) {
            return true;
        }
        return false;
        
    }
}
