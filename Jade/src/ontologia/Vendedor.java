package ontologia;
 
import java.io.StringReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.PrintWriter;
 
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
 
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.content.onto.*;
 
 
public class Vendedor extends Agent {
 
    private Codec codec = new SLCodec();
    private Ontology ontologia = frutasOntology.getInstance();
 
 
    class EnviarMensajeBehaviour extends SimpleBehaviour {
 
      private boolean finished = false;
 
    public EnviarMensajeBehaviour(Agent a) {
        super(a);
    }
 
    public void action() {
        try
    {
            System.out.println("\nIntroduce el nombre del DESTINATARIO (el nombre dado al Agente al lanzar el -container): ");
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            String respuesta = buff.readLine();
            AID r = new AID();
            r.setLocalName(respuesta);
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setSender(getAID());
            msg.addReceiver(r);
            msg.setLanguage(codec.getName());
               msg.setOntology(ontologia.getName());
 
            System.out.println("\nIntroduce el NOMBRE de la fruta:");
            respuesta = buff.readLine();
            Fruta fru = new Fruta();
            fru.setNombre(respuesta);
            System.out.println("\nIntroduce el PRECIO:");
            respuesta = buff.readLine();
            fru.setPrecio(Integer.parseInt(respuesta));
 
            Oferta of = new Oferta();
            of.setFruta(fru);
 
            getContentManager().fillContent(msg, of);
 
            send(msg);
           }
           catch (java.io.IOException io)
            {System.out.println(io);
        }
        catch (jade.content.lang.Codec.CodecException ce) {
               System.out.println(ce);
        }
        catch (jade.content.onto.OntologyException oe) {
            System.out.println(oe);
        }
    catch (Exception e){
        System.out.println("\n\n... Terminando ...");
        finished=true;
    }
    }
 
    public boolean done() {
 
     return finished;
 
    }
    } // Fin de la clase EnviarMensajeBehaviour
 
    protected void setup() {
 
    /** Registrarse en el DF */
    DFAgentDescription dfd = new DFAgentDescription();
    ServiceDescription sd = new ServiceDescription();
    sd.setType("Vendedor");
    sd.setName(getName());
    sd.setOwnership("ARNOIA");
    dfd.setName(getAID());
    dfd.addServices(sd);
    try {
    DFService.register(this,dfd);
    } catch (FIPAException e) {
        System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
        doDelete();
    }
 
    getContentManager().registerLanguage(codec);
    getContentManager().registerOntology(ontologia);
 
    EnviarMensajeBehaviour EnviarBehaviour = new EnviarMensajeBehaviour(this);
    addBehaviour(EnviarBehaviour);
    }
 
   protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}
 
