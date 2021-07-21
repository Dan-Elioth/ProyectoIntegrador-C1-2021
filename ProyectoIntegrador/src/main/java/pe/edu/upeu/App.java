package pe.edu.upeu;

import java.io.Console;

import pe.edu.upeu.dao.CategoriaDao;
import pe.edu.upeu.dao.ProductoDao;
import pe.edu.upeu.dao.UsuarioDao;
import pe.edu.upeu.dao.VentaDao;
import pe.edu.upeu.gui.*;
import pe.edu.upeu.modelo.CategoriaTO;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
/**
 * Hello world!
 *
 */
public class App {

    static UtilsX ut=new UtilsX();


    public static void registrarCategoria(CategoriaTO categ) {
        System.out.println("--------Registro Categoria----------");
        System.out.println("IdCategoria: "+categ.getIdCateg()+" \tnombre: "+categ.getNombre());
    }
    
    public static void registrarProducto(ProductoTO prod) {
        System.out.println("--------Registro Producto----------");
        System.out.println("IdCategoria: "+prod.getIdCateg()+" \tnombre: "+prod.getNombre());
    }    

    public static void menuMainADMIN() {
        AnsiConsole.systemInstall();
        int opcionAlg=0;
        ut.pintarLine('H', 43);
        ut.pintarLine('V', 0);
        System.out.print(ansi().render("@|yellow Seleccione el algortimo que desea probar: ➘➘✍|@   \n"));
        ut.pintarLine('H', 43);

        String mensaje= ansi().fg(BLUE).a(
        "\n\t\t1=Resgistrar Categoría de un producto"+
        "\t\t\t4=Realizar venta"+
        "\n\t\t12=Reporte Categoria de productos"+
        "\t\t\t5=Reporte de Ventas entre rango de fechas"+
        "\n\t\t2=Registrar Producto"+
        "\t\t\t\t\t6=Actualizar Stock"+
        "\n\t\t21=Reporte de Stock"+
        "\t\t\t\t\t7=Cerrar Sesion"+
        "\n\t\t3=Registrar Nuevo usuario"+
        "\n\t\t31=Eliminar Usuario"+
        "\n\t\t0=Salir del Programa").reset().toString()
        ;
        CategoriaDao daoCat;
        UsuarioDao uDao;
        ProductoDao proDao;
        VentaDao venDao;
        UtilsX ut=new UtilsX();
        LeerTeclado lt=new LeerTeclado();
        opcionAlg=lt.leer(0, mensaje);
        ut.clearConsole();
        do {                        
            switch (opcionAlg) {

                case 1: daoCat=new CategoriaDao(); 
                daoCat.registrarCategoria(); break;
                case 12: ut.clearConsole(); daoCat=new CategoriaDao(); 
                daoCat.reportarCategoria(); break;
                case 2: proDao=new ProductoDao(); proDao.registrarProducto(); break;
                case 21: proDao=new ProductoDao(); proDao.reporteProductosT(); break;
                case 3:uDao=new UsuarioDao(); uDao.registarUsuario(); break;
                case 31:uDao=new UsuarioDao(); uDao.eliminarUsuario(); break;
                case 4: venDao=new VentaDao(); venDao.registroVentaGeneral();  break;
                case 5: venDao=new VentaDao(); venDao.reporteVentasPorFechas();  break;
                case 6: venDao=new VentaDao(); venDao.actualizarproducto2();  break;
                case 7: validarAccesoSistema(); break;

                default: System.out.println("Opcion no existe!"); break;    
            }             
            if (opcionAlg!=0) {  
                String dato=lt.leer("", "Desea seguir probando algoritmos?: SI/NO");              
                if(dato.toUpperCase().equals("SI")){
                    ut.pintarLine('H', 42);
                    ut.pintarLine('V', 0);
                    System.out.print(ansi().render("@|red Seleccione el algortimo que desea probar: ➘➘✍|@ \n"));
                    ut.pintarLine('H', 42);
                    opcionAlg=lt.leer(0, mensaje);      
                }else{
                    opcionAlg=0;
                }                
            }
        } while (opcionAlg!=0);        
    } 
    public static void menuMainVendedor() {
        int opcionAlg=0;
        ut.pintarLine('H', 42);
        ut.pintarLine('V', 0);
        System.out.print(ansi().render("@|red Seleccione el algortimo que desea probar: ➘➘✍|@ \n"));
        ut.pintarLine('H', 42);
        String mensaje=
        "\n\t\t1=Reporte Categoria de productos"+
        "\n\t\t2=Reporte de Stock"+
        "\n\t\t3=Realizar una venta venta"+
        "\n\t\t4=Cerrar Sesion"+
        "\n\t\t0=Salir del Programa";

        CategoriaDao daoCat;
        UsuarioDao uDao;
        ProductoDao proDao;
        VentaDao venDao;
        UtilsX ut=new UtilsX();
        LeerTeclado lt=new LeerTeclado();
        opcionAlg=lt.leer(0, mensaje);
        ut.clearConsole();
        do {                        
            switch (opcionAlg) {
                case 1: ut.clearConsole(); daoCat=new CategoriaDao(); 
                daoCat.reportarCategoria(); break;
                case 2: proDao=new ProductoDao(); proDao.reporteProductosT(); break;
                case 3: venDao=new VentaDao(); venDao.registroVentaGeneral();  break;
                case 4: validarAccesoSistema(); break;
                default: System.out.println("Opcion no existe!"); break;    
            }             
            if (opcionAlg!=0) {  
                String dato=lt.leer("", "Desea seguir probando algoritmos?: SI/NO");              
                if(dato.toUpperCase().equals("SI")){
                    ut.pintarLine('H', 42);
                    ut.pintarLine('V', 0);
                    System.out.print(ansi().render("@|red Seleccione el algoritmo que desea probar: ➘➘✍|@ \n"));
                    ut.pintarLine('H', 42);
                    opcionAlg=lt.leer(0, mensaje);      
                }else{
                    opcionAlg=0;
                }                
            }
        } while (opcionAlg!=0);        
    } 
    
    
    
    public static void validarAccesoSistema() {
        LeerTeclado lt=new LeerTeclado();
        System.out.println("\n"+(ansi().bgBrightCyan().fg(BLACK).a("****************Acceso al Sistema***********").reset()));  
        String usuario=lt.leer("","\nIngrese su Usuario:");
        Console cons=System.console();
        System.out.println("Ingrese su clave:");
        char[] clave=cons.readPassword();
        UsuarioDao uDao=new UsuarioDao();

        try {
            if(uDao.login(usuario, ut.md5(String.valueOf(clave)))!=null){
                if(uDao.login(usuario, ut.md5(String.valueOf(clave)))[0][3].toString().equals("ADMIN")){  menuMainADMIN();
                }else{
                    menuMainVendedor();
                }}else{
                System.out.println(ansi().bgBrightRed().fg(YELLOW).
                a("☢ Error ☢...Intente Nuevamente!!").reset());
                validarAccesoSistema();
            }
            
        } catch (Exception e) {

        }
    }


    public static void main( String[] args ){
        //menuMainADMIN();
        AnsiConsole.systemInstall();
        ut.clearConsole();
        System.out.println( ansi().bgBrightGreen().fg(RED).a("\t\t\t\t Bienvenido al módulo de ventas de la").fg(BLACK).a(" Pasteleria ™El Caserito™  ").reset() );
        validarAccesoSistema();
    }
}