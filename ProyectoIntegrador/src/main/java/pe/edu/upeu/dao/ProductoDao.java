package pe.edu.upeu.dao;


import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.CategoriaTO;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;

public class ProductoDao extends AppCrud{
    LeerArchivo lar;

    CategoriaTO catTO;
    ProductoTO  proTO;

    LeerTeclado lte=new LeerTeclado();
    UtilsX ut=new UtilsX();

    public void crearProducto() {
        proTO=new ProductoTO();
        proTO.setNombre(lte.leer("", "Ingrese nombre de producto:"));
        mostrarCategoria();
        proTO.setIdCateg(lte.leer("", "Eliga el Id de categoria:"));
        lar=new LeerArchivo("Producto.txt");
        proTO.setIdProducto(generarId(lar, 0, "P", 1));
        proTO.setUnidadMed(lte.leer("", "Ingrrese Unidad de Medida:"));
        proTO.setPrecioUnit(lte.leer(0.0, "Ingrese el precio Unitario:"));
        proTO.setPorceUtil(lte.leer(0.0, "Ingrese el porcentaje de utilidad:"));
        proTO.setStock(lte.leer(0.0, "Ingrese el Stock:"));
        lar=new LeerArchivo("Producto.txt");
        agregarContenido(lar, proTO);
    }


    public void mostrarCategoria() {

        lar=new LeerArchivo("Categoria.txt");
        Object[][] datax=listarContenido(lar);
        for (int i = 0; i < datax.length; i++) {
            System.out.println(datax[i][0]+"="+datax[i][1]+",");
        }
        System.out.println("");
        
    }

    public void reporteProductos() {
        lar=new LeerArchivo("Producto.txt");
        imprimirLista(listarContenido(lar));
        
    }
    
    public void reportarProducto() {
        lar=new LeerArchivo("Producto.txt");
        Object[][] data=listarContenido(lar);
        String dataX="";
        ut.pintarLine('H',32);
        ut.pintarTextHeadBody('H', 2, "ID,Nombre,U.Med,IdCat,P.Unit,P.Util,Stock"); 
        System.out.println("");
        ut.pintarLine('H',32);       
       for (int i = 0; i < data.length; i++) {
           for (int j = 0; j < data[0].length; j++) {
               if(j==0){
                dataX+=""+data[i][j];
               }else{
                dataX+=","+data[i][j]; 
               }               
           }
           ut.pintarTextHeadBody('B', 2, dataX);  
           dataX="";
       }         
    }
    
}
