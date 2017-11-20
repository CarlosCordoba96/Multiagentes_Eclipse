package seminario;
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
 
public class Alumno extends Agent {
 
    private Codec __________ = new SLCodec();
    private Ontology ontologia = seminariosOntology.__________();
 
    // Clase que describe el comportamiento que permite recibir un mensaje
    // y contestarlo
    class WaitPingAndReplyBehaviour extends SimpleBehaviour {
      private boolean finished = false;
 
      public WaitPingAndReplyBehaviour(Agent a) {
        super(a);
      }
 
      public void action() {
 
    System.out.println("\nDisfrutando de la buena vida....");
 
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
 
            ContentElement contentElement =
getContentManager().extractContent(msg);
            if (contentElement instanceof Trabajo){
                // Recibido un INFORM con contenido correcto
                Trabajo trabajo = (Trabajo) contentElement;
                Seminario seminario = trabajo.__________();
                System.out.println("Mensaje recibido:");
                System.out.println("Nombre: " + seminario.getTema());
                System.out.println("Precio: " + seminario.__________());
 
                //Realizarmos el seminario
                Realizar realizar = new Realizar();
                realizar.setSeminario(seminario);
                ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
                msg2.setLanguage(codec.getName());
                msg2.__________(ontologia.getName());
                msg2.setSender(getAID());
                msg2.addReceiver(msg.getSender());
                getContentManager().fillContent(msg2,realizar);
                send(msg2);
                System.out.println("Seminario hecho, a descansar...");
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
                reply.setContent("( (Unexpected-act
"+ACLMessage.getPerformative(msg.getPerformative())+")( expected (inform)))");
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
