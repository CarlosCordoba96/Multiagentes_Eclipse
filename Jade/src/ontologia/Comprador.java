 package ontologia;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPANames;
import jade.domain.DFService;
import jade.domain.FIPAException;
 
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.content.onto.*;
 
public class Comprador extends Agent {
 
    private Codec codec = new SLCodec();
    private Ontology ontologia = frutasOntology.getInstance();
 
    // Clase que describe el comportamiento que permite recibir un mensaje
    // y contestarlo
    class WaitPingAndReplyBehaviour extends SimpleBehaviour {
      private boolean finished = false;
 
      public WaitPingAndReplyBehaviour(Agent a) {
        super(a);
      }
 
      public void action() {
 
    System.out.println("\nEsperando oferta del Vendedor....");
 
    MessageTemplate mt = MessageTemplate.and(
            MessageTemplate.MatchLanguage(codec.getName()),
            MessageTemplate.MatchOntology(ontologia.getName()));
        ACLMessage  msg = blockingReceive(mt);
 
        try {
 
        if(msg != null){
        if(msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD){
                System.out.println("Mensaje NOT UNDERSTOOD recibido");
            }
        else{
            if(msg.getPerformative()== ACLMessage.INFORM){
 
            ContentElement ce = getContentManager().extractContent(msg);
            if (ce instanceof Oferta){
                // Recibido un INFORM con contenido correcto
                Oferta of = (Oferta) ce;
                Fruta fru = of.getFruta();
                System.out.println("Mensaje recibido:");
                System.out.println("Nombre: " + fru.getNombre());
                System.out.println("Precio: " + fru.getPrecio());
 
                //Compramos
                Comprar compra = new Comprar();
                compra.setFruta(fru);
                ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
                msg2.setLanguage(codec.getName());
                msg2.setOntology(ontologia.getName());
                msg2.setSender(getAID());
                msg2.addReceiver(msg.getSender());
                getContentManager().fillContent(msg2,compra);
                send(msg2);
                System.out.println("Compra solicitada.");
            }
            else{
                // Recibido un INFORM con contenido incorrecto
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                reply.setContent("( UnexpectedContent (expected ping))");
                send(reply);
            }
            }
            else {
                // Recibida una performativa incorrecta
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+")( expected (inform)))");
                send(reply);
            }
        }
        }else{
        //System.out.println("No message received");
        }
 
         }
         catch (jade.content.lang.Codec.CodecException ce) {
               System.out.println(ce);
        }
        catch (jade.content.onto.OntologyException oe) {
            System.out.println(oe);
        }
     }
 
      public boolean done() {
        return finished;
      }
 
  } //Fin de la clase WaitPingAndReplyBehaviour
 
  protected void setup() {
 
    getContentManager().registerLanguage(codec);
    getContentManager().registerOntology(ontologia);
    WaitPingAndReplyBehaviour PingBehaviour;
    PingBehaviour = new  WaitPingAndReplyBehaviour(this);
    addBehaviour(PingBehaviour);
 }
}
