package problclase2;
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;
 
 
public class Hijo extends Agent {
 
    protected void setup()
    {
        Object[] args = getArguments();
 
        if (args != null && args.length == 1) {
            System.out.println("Fiesta!!, preguntare a mi padre.");
 
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
            msg.addReceiver(new AID((String) args[0], AID.ISLOCALNAME));
            msg.setProtocol(FIPANames.InteractionProtocol. ......); // protocolo a utilizar
            msg.setContent("¿Puedo salir de fiesta?");;
 
            addBehaviour(new ManejadorInitiator(this,msg));
 
        }
        else System.out.println("Debe escribir el nombre del agente intermedio (pasado como parametro).");
    }
 
    class ManejadorInitiator extends AchieveREInitiator
    {
        public ManejadorInitiator(Agent a,ACLMessage msg) {
            super(a,msg);
        }
 
		// El padre confirma la recepcion del mensaje
        protected void ......(ACLMessage agree)
        {
            System.out.println("Mi padre " + agree.getSender().getName()
            + " dice que le preguntara a mi madre.");
        }
 
		// El padre ha respondido que no puede
        protected void ......(ACLMessage refuse)
        {
            System.out.println("Mi padre " + refuse.getSender().getName()
            + " dice que no, que tengo que madrugar.");
        }
 
		// El padre no entendio el mensaje
        protected void ......(ACLMessage notUnderstood)
        {
            System.out.println("Mi padre " + notUnderstood.getSender().getName()
            + " no esta en casa.");
        }
 
		// El padre informa de la decision
        protected void ......(ACLMessage inform)
        {
            System.out.println("Mi padre " + inform.getSender().getName()
            + " dice que: " + inform.getContent() + ".");
        }
 
		// No encuentra al padre (error)
        protected void ......(ACLMessage fallo)
        {
            if (fallo.getSender().equals(myAgent.getAMS())) {
                System.out.println("Soy huerfano....");
            }
            else
            {
                System.out.println("Mi padre " + fallo.getSender().getName()
                + ": " + fallo.getContent().substring(1, fallo.getContent().length()-1)+".");
            }
        }
    }
}