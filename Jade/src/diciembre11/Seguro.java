package diciembre11;
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;


public class Seguro extends Agent {
 
    protected void setup()
    {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            System.out.println("Se me averió el vehículo...");
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
 	    for (int i = 0; i < args.length; ++i)
                msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
            
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            msg.setContent("Seat Alhambra 80");
 
            addBehaviour(new ManejadorInitiator(this,msg));
 
        }
        else System.out.println("Debe de escribir los nombres de los talleres asociados.");
    }
 
    class ManejadorInitiator extends AchieveREInitiator
    {
        public ManejadorInitiator(Agent a,ACLMessage msg) {
            super(a,msg);
        }
 
        protected void handleAgree(ACLMessage agree)
        {
            System.out.println("Taller " + agree.getSender().getName()
                    + " informa que: "+ agree.getContent() );
        }
        
        protected void handleNotUnderstood(ACLMessage notUnderstood) {
        	
        	System.out.println("Taller " + notUnderstood.getSender().getName()
                    + " informa que: "+ notUnderstood.getContent() );
        	
        }
        
        protected void handleRefuse(ACLMessage refuse) {
        	System.out.println("Taller " + refuse.getSender().getName()
                    + " informa que: "+ refuse.getContent() );
        }
        
        protected void handleFailure(ACLMessage failure) {
        	System.out.println("Taller " + failure.getSender().getName()
                    + " informa que: "+ failure.getContent() );
        }
        
        protected void  handleInform (ACLMessage inform) {
        	System.out.println("Taller " + inform.getSender().getName()
                    + " informa que: "+ inform.getContent() );
        }

// A partir de aquí el resto de manejadores
      
 
}
    
}
