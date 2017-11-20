 package ontologia;
 
import jade.content.Predicate;
 
public class Oferta implements Predicate {
 
   private Fruta fruta;
 
   public Fruta getFruta() {
     return fruta;
   }
 
   public void setFruta(Fruta f) {
     fruta = f;
   }
}
