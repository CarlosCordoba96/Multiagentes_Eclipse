package seminario;
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
 
 
public class Profesor extends Agent {
 
    private Codec codec = new SLCodec();
    private Ontology __________ = seminariosOntology.getInstance();
 
 
    class EnviarMensajeBehaviour extends SimpleBehaviour {
 
      private boolean finished = false;
 
    public EnviarMensajeBehaviour(Agent a) {
        super(a);
    }
 
    public void action() {
        try
    {
            System.out.println("\nIntroduce el nombre del ALUMNO (el nombre dado
al Agente al lanzar el -container): ");
            BufferedReader buff = new BufferedReader(new
InputStreamReader(System.in));
            String respuesta = buff.readLine();
            AID r = new AID();
            r.setLocalName(__________);
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setSender(getAID());
            msg.addReceiver(r);
            msg.setLanguage(codec.getName());
               msg.setOntology(ontologia.getName());
 
            System.out.println("\nIntroduce el Tema del seminario:");
            respuesta = buff.readLine();
            Seminario seminario = new Seminario();
            seminario.setTema(respuesta);
            System.out.println("\nIntroduce la Nota:");
            respuesta = buff.readLine();
            seminario.__________(Integer.parseInt(respuesta));
 
            Trabajo trabajo = new Trabajo();
            trabajo.setSeminario(__________);
 
            getContentManager().fillContent(msg, trabajo);
 
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
    sd.setType("Profesor");
    sd.setName(getName());
    sd.setOwnership("NATIVIC");
    dfd.setName(getAID());
    dfd.addServices(sd);
    try {
    DFService.register(this,dfd);
    } catch (FIPAException e) {
        System.err.println(getLocalName()+" registration with DF unsucceeded.
Reason: "+e.getMessage());
        doDelete();
    }
 
    getContentManager().registerLanguage(__________);
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
