package pe.edu.upeu.dao;

import java.io.Console;

import org.fusesource.jansi.Ansi;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.UsuarioTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


public class UsuarioDao extends AppCrud{
    LeerArchivo lar;
    UsuarioTO usuTO;

    LeerTeclado lte=new LeerTeclado();
    UtilsX ut=new UtilsX();

    public void registarUsuario() {
        usuTO=new UsuarioTO();
        ut.pintarLine2('H', 30);
        System.out.print(ansi().render("@|green ✰  Registrando Nuevo Usuario ✰|@ \n"));
        ut.pintarLine2('H', 30);
        String usuario=lte.leer("", "Ingrese un usuario:");
        if(validarExisteUser(usuario)){
            usuTO.setUsuario(usuario);        
            lar=new LeerArchivo("Usuario.txt");
            usuTO.setIdUsuario(generarId(lar, 0, "U", 1));
            usuTO.setPerfil(
            lte.leer("", "Ingrese el Perfil de usuario (ADMIN, VENDEDOR): ").toUpperCase());        
            Console cons=System.console();
            System.out.println("Ingrese su clave:");
            char[] pws=cons.readPassword();
            try {
                usuTO.setClave(ut.md5(String.valueOf(pws))); 
            } catch (Exception e) {
            }
            lar=new LeerArchivo("Usuario.txt");
            agregarContenido(lar, usuTO);
        }else{
            System.out.println("El usuario ya existe....coloque otro usuario");
            registarUsuario();
        }
    }



    public Object[][] login(String usuario, String clave) {
        lar=new LeerArchivo("Usuario.txt");
        Object[][] datax=buscarContenido(lar, 1, usuario);
        if(datax!=null && datax.length>0 && datax[0][2].equals(clave)){
            return datax;
        }
        return null;
    }

    public boolean eliminarUsuario() {
        ut.clearConsole();
        lar=new LeerArchivo("Usuario.txt");
        imprimirLista(listarContenido(lar));
        lar=new LeerArchivo("Usuario.txt");
        System.out.println("");
        String idU=lte.leer("", ansi().fg(RED).a("Ingrese el U... del usuario que desea eliminar:").reset().toString()).toUpperCase();
        eliminarRegistros(lar, 0, idU);
        return false;
    }    

    public boolean validarExisteUser(String user) {
        lar=new LeerArchivo("Usuario.txt");
        Object[][] datax=buscarContenido(lar, 1, user);    
        if(datax==null || datax.length==0){
            return true;
        }
        return false;
    }
        
}
