package pe.edu.upeu.util;

import java.net.URL;
import javax.swing.table.TableModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class UtilsX {

    public int fibonaciRecur(final int numero) {
    if (numero < 2) {
    return numero;
    } else {
    return fibonaciRecur(numero - 1) + fibonaciRecur(numero - 2);
    }
    }

    public URL getFile(String ruta){
    return this.getClass().getResource("/"+ruta);
    }

    public TableModel reporData() {
    return null;
    }

    public  String md5(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(text.getBytes());
        return Hex.encodeHexString(bytes);
    }


    public  void pintarLine(char horient, int sizen) {
        for (int i = 0; i <= (sizen); i++) {
            if (horient=='H') {
                if(i==sizen){
                    System.out.print(ansi().fg(CYAN).a("═").reset());
                }else{System.out.print(ansi().fg(CYAN).a("═").reset());}
                
            } else {
                System.out.print(ansi().fg(BLUE).a("╟").reset());
            }
        }
        if (horient=='H') {
            System.out.print("\n");
        }
    }
    public  void pintarLine2(char horient, int sizen) {
        for (int i = 0; i <= (sizen); i++) {
            if (horient=='H') {
                if(i==sizen){
                    System.out.print(ansi().fg(CYAN).a("-").reset());
                }else{
                    System.out.print(ansi().fg(CYAN).a("-").reset());}
                
            } else {
                System.out.print(ansi().fg(CYAN).a(" | ").reset());
            }
        }
        if (horient=='H') {
            System.out.print("\n");
        }
    }

    public final void clearConsole(){
        try{            
            final String os = System.getProperty("os.name");    
            if (os.contains("Windows")){
               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        }
        catch (final Exception e){
           System.out.println("Error: "+e.getMessage());
        }
       System.out.println(" "); 
    } 

    public void pintarTextHeadBody(char type, int sizen, String content) {  
        AnsiConsole.systemInstall();      
        int sizeX=sizen>=2?4*sizen:4;
        System.out.print(ansi().fg(CYAN).a("║").reset());
        String[] data=content.split(",");        
        for (int j = 1; j <= data.length; j++) {
            data[j-1]=data[j-1].length()>sizeX?data[j-1].substring(0,sizeX):data[j-1];            
            int contentSize=data[j-1].length();
            System.out.print(data[j-1]);         
            if(sizeX-contentSize>=4 && (double)(sizeX-contentSize)/4>=1){
                int y=(int)((sizeX-contentSize)/4);
                //System.out.print("y*"+y); 
                for (int i = 0; i <y; i++) {
                    System.out.print("    ");     
                }
                int x=((sizeX-contentSize)-(y*4));                
                //System.out.print("z*"+x); 
                for (int i = 0; i < x; i++) {
                    System.out.print(" "); 
                }                 
            }else{
                int x=((sizeX-contentSize)-((int)((sizeX-contentSize)/4)*4));
                //System.out.print("x*"+x); 
                for (int i = 0; i < x; i++) {
                    System.out.print(" "); 
                }
            }           
            System.out.print(ansi().fg(CYAN).a("║").reset());
        }
        if(type!='H'){
            System.out.println("");
        }
        
    }

	/*
	    pintarLine('H', 28);
        pintarTextHeadBody('H', 3, "Nombre,Apellidos,dni,celular");
        System.out.println("");
        pintarLine('H', 28);
        pintarTextHeadBody('B', 3, "David,M,43631917,951782520");
	*/	

}