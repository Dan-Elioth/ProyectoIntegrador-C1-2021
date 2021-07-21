  
package pe.edu.upeu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.modelo.VentaDetalleTO;
import pe.edu.upeu.modelo.VentaTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;

import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class VentaDao extends AppCrud{
   LeerArchivo lar;
   LeerTeclado lte=new LeerTeclado();
   UtilsX ut=new UtilsX();
   ProductoTO prodTO;
   VentaTO ventTO;
   VentaDetalleTO vdTO;
   ProductoDao prodao; 
   SimpleDateFormat formato=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
   SimpleDateFormat formatoFecha=new SimpleDateFormat("dd-MM-yyyy");    

    public void registroVentaGeneral() {
        VentaTO vTO=crearVenta();
        double precioTotalX=0;
        String continuaV="SI";
        do {
            VentaDetalleTO toVD=carritoVenta(vTO);

            precioTotalX=precioTotalX+toVD.getPrecioTotal();
            
            continuaV=lte.leer("", "Desea comprar algo mas? (SI=S, NO=N):");
        } while (continuaV.charAt(0)=='S');
        vTO.setPrecioTotal(precioTotalX);
        vTO.setNetoTotal(precioTotalX/1.18);
        vTO.setIgv(vTO.getNetoTotal()*0.18);
        lar=new LeerArchivo("Venta.txt");
        editarRegistro(lar, 0, vTO.getIdVenta(), vTO);        
    }

    public VentaTO crearVenta() {
        lar=new LeerArchivo("Venta.txt");
        ventTO=new VentaTO();
        ventTO.setIdVenta(generarId(lar, 0, "V", 1));
        ventTO.setDniCliente(lte.leer("", "Ingrese el DNI del cliente:"));
        ventTO.setFechaVenta(formato.format(new Date()));
        ventTO.setIgv(0.0);
        ventTO.setNetoTotal(0.0);
        ventTO.setPrecioTotal(0.0);
        lar=new LeerArchivo("Venta.txt");
        agregarContenido(lar, ventTO);
        return ventTO;
    }

    public VentaDetalleTO carritoVenta(VentaTO vTO) {
        
        vdTO=new VentaDetalleTO();
        ut.clearConsole();
        System.out.println((ansi().bgBrightCyan().fg(BLACK).a("****************Agregar Productos a carrito de venta***********").reset()));
        MostrarProducto2();
        vdTO.setIdProducto(lte.leer("", "Ingrese el ID del Producto:").toUpperCase());
        vdTO.setIdVenta(vTO.getIdVenta());
        lar=new LeerArchivo("VentaDetalle.txt");
        vdTO.setIdVentaDetalle(generarId(lar, 0, "DV", 2));
        lar=new LeerArchivo("Producto.txt");
        Object[][] dataP=buscarContenido(lar, 0, vdTO.getIdProducto());
        double porcentUtil=Double.parseDouble(String.valueOf(dataP[0][5]));
        double punit=Double.parseDouble(String.valueOf(dataP[0][4]));
        vdTO.setPorcentUtil(porcentUtil);
        vdTO.setPrecioUnit(punit+punit*porcentUtil);
        vdTO.setCantidad(lte.leer(0.0, "Ingrese una cantidad:"));
        vdTO.setPrecioTotal(vdTO.getCantidad()*vdTO.getPrecioUnit());
        lar=new LeerArchivo("VentaDetalle.txt");
        agregarContenido(lar, vdTO);
        actualizarproducto1(dataP, vdTO.getCantidad());
        return vdTO;
    }

    public void actualizarproducto1(Object[][] prodTO, double canti){
        lar=new LeerArchivo("Producto.txt");
        ProductoTO p=new ProductoTO();
        p.setIdProducto(prodTO[0][0].toString());
        p.setStock(Double.parseDouble(prodTO[0][6].toString())-canti);
        editarRegistro(lar, 0, p.getIdProducto(), p); 
    }



    public void actualizarproducto2(){
        prodTO=new ProductoTO();
        prodao=new ProductoDao();
        System.out.println("************* Actualizar Stock *************");
        prodao.reporteProductosT();
        prodTO.setIdProducto(lte.leer("", "Ingrese el ID del Producto:"));
        lar=new LeerArchivo("Producto.txt");
        Object[][] proT=buscarContenido(lar, 0, prodTO.getIdProducto());
        double suma=lte.leer(0.0, "*************Cuanto desea aumentar en el Stock del producto ************* ");
        actualizarStock(proT, suma);
    }

    

    public void actualizarStock(Object[][] prodTO, double suma){
        lar=new LeerArchivo("Producto.txt");
        ProductoTO pro=new ProductoTO();
        pro.setIdProducto((prodTO[0][0].toString()));
        pro.setStock(Double.parseDouble(prodTO[0][6].toString())+suma);
        editarRegistro(lar, 0, pro.getIdProducto(), pro);
        
    }


    public void mostrarProductos() {
        lar=new LeerArchivo("Producto.txt");
        Object[][] dataP=listarContenido(lar);
        for (int i = 0; i < dataP.length; i++) {
            System.out.print(dataP[i][0]+"="+dataP[i][1]+
            "(P.Unit S/.:"+dataP[i][4]+", Stock:"+dataP[i][6]+"); ");
        }
        System.out.println("");
    }

    public void MostrarProducto2(){
        ut.clearConsole();
        lar=new LeerArchivo("Producto.txt");
        Object[][] data=listarContenido(lar);
	    ut.pintarLine('H', 83);
        ut.pintarTextHeadBody('H', 5, "ID,Nombre del producto,P.Unit S/.,Stock");
        System.out.println("");
        ut.pintarLine('H', 83);
        String dataB="";
        for (int i = 0; i < data.length; i++) {
            if(Double.parseDouble(data[i][6].toString())>=1){
                dataB=data[i][0]+","+data[i][1]+","+data[i][4]+","+data[i][6];
                ut.pintarTextHeadBody('B', 5, dataB);    
            }
        }  
        ut.pintarLine('H', 83);
    }

    public void reporteVentasPorFechas() {
        AnsiConsole.systemInstall();
        System.out.println("***************Reporte de Ventas por fechas*******************");
        String fechaInit=lte.leer("", "Ingrese fecha de inicio (dd-MM-yyyy):");
        String fechaFin=lte.leer("", "Ingrese fecha de Finalizacion (dd-MM-yyyy):");
        ut.clearConsole();
        lar=new LeerArchivo("Venta.txt");

        Object[][] dataV=listarContenido(lar);
        int cantRegVent=0;
        try {
            for (int i = 0; i < dataV.length; i++) {
                String[] fechaVent=dataV[i][2].toString().split(" ");
                if( (fechaVent[0].toString().equals(fechaInit) || formatoFecha.parse(fechaVent[0])
                .after(formatoFecha.parse(fechaInit)) ) && 
                (formatoFecha.parse(fechaVent[0]).before(formatoFecha.parse(fechaFin)) 
                || fechaVent[0].toString().equals(fechaFin) ) ){
                    cantRegVent++;
                }
            }
            Object[][] dataReal=new Object[cantRegVent][dataV[0].length];
            int indexFilaX=0;
            double netoTotalX=0, igvX=0, preciototalX=0;
            for (int i = 0; i < dataV.length; i++) {
                String[] fechaVent=dataV[i][2].toString().split(" ");
                if( (fechaVent[0].toString().equals(fechaInit) || formatoFecha.parse(fechaVent[0])
                .after(formatoFecha.parse(fechaInit)) ) && 
                (formatoFecha.parse(fechaVent[0]).before(formatoFecha.parse(fechaFin)) 
                || fechaVent[0].toString().equals(fechaFin))){
                   for (int j = 0; j < dataV[0].length; j++) {
                    dataReal[indexFilaX][j]=dataV[i][j];
                    if (j==3) { netoTotalX+=Double.parseDouble(String.valueOf(dataV[i][j])); }
                    if (j==4) { igvX+=Double.parseDouble(String.valueOf(dataV[i][j])); }
                    if (j==5) { preciototalX+=Double.parseDouble(String.valueOf(dataV[i][j])); }
                   }
                   indexFilaX++;
                }
            }            

            ut.clearConsole();
            ut.pintarLine2('H', 125);
            System.out.println(ansi().fg(YELLOW).a("\t\t\t\t\t\t\t\tReporte de ventes").reset());
            ut.pintarLine2('H', 125);
            System.out.println("\t\t\t\t\t\t\tEntre "+fechaInit+" a "+fechaFin);
            ut.pintarLine2('H', 125);
            ut.pintarTextHeadBody('B', 5, ansi().fg(RED).a("ID,DNI del Cliente,Fecha-Hora de Venta,Neto S/.,IGV,Precio Total S/.").reset().toString());
            ut.pintarLine('H', 125);            
            for (Object[] objects : dataReal) {
                String datacont=objects[0]+","+objects[1]+","+
                objects[2]+","+objects[3]+","+objects[4]+","+objects[5];
                ut.pintarTextHeadBody('B', 5, datacont);
            }
            ut.pintarLine('H', 125);        
         
            System.out.println(ansi().render("@|red Neto Total:S/. |@ @|green "+(Math.round(netoTotalX*100.0)/100.0)+
            "|@ | @|red IGV: S/.|@ @|green "+(Math.round(igvX*100.0)/100.0)+"|@  | @|red Monto total: S/. |@ @|green "+
            (Math.round(preciototalX*100.0)/100.0)+"|@"));
            
            ut.pintarLine('H', 125);
            
        } catch (Exception e) {      }
    }


}