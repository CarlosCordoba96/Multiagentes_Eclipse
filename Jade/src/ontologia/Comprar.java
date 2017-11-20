package ontologia;
 
import jade.content.AgentAction;
 
public class Comprar implements AgentAction {
 
   private Fruta fruta;
 
   public Fruta getFruta() {
     return fruta;
   }
 
   public void setFruta(Fruta f) {
     fruta = f;
   }
}
