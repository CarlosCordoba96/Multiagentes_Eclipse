package ontologia;
 
import jade.content.Concept;
 
public class Fruta implements Concept {
 
   private String nombre;
   private int precio;
 
   public String getNombre() {
     return nombre;
   }
 
   public void setNombre(String n) {
     nombre = n;
   }
 
   public int getPrecio() {
     return precio;
   }
 
   public void setPrecio(int p) {
     precio = p;
   }
}
