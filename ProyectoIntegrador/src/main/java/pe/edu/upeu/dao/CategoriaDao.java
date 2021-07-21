package pe.edu.upeu.dao;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.CategoriaTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class CategoriaDao extends AppCrud{
   
    LeerArchivo lar;
    CategoriaTO catTO;

    LeerTeclado lte=new LeerTeclado();
    UtilsX ut=new UtilsX();


    public Object[][] registrarCategoria() {  
        catTO=new CategoriaTO();
        lar=new LeerArchivo("Categoria.txt");
        catTO.setIdCateg(generarId(lar, 0, "C", 1));
        catTO.setNombre(lte.leer("", "Ingrese nombre categoria:"));        
        return agregarContenido(lar, catTO);
    }

    public void reportarCategoria() {
        lar=new LeerArchivo("Categoria.txt");  
        ut.pintarLine('H',30);
        ut.pintarLine2('V',0);
        System.out.print(ansi().fg(YELLOW).a("\tReporte de Categorias").reset());
        ut.pintarLine2('V',0);
        System.out.println(""); 
        ut.pintarLine('H',30);    
        imprimirLista(listarContenido(lar));
    }
    
}   