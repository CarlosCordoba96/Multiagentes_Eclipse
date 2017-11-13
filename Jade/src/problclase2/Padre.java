package problclase2;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
 
import jade.proto.AchieveREResponder;
import jade.proto.ProposeInitiator;
//HIJO--FIPA QUERY--> PADRE --FIPA PROPOSE-->MADRE
public class Padre extends Agent {
 
    private boolean aceptado = false;
 
    protected void setup() {
 
    Object[] args = getArguments();
 
    if (args != null && args.length == 1) {
		//PROTOCOLO PROPOSE
        //Configurar y lanzar comportamiento de inicio de peticion
        ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE );
        mensaje.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
        mensaje.setContent("Puede salir el niño de fiesta?");
 
        mensaje.addReceiver(new AID((String) args[0], AID.ISLOCALNAME));
 
        this.addBehaviour(new PreguntarSiAceptado(this, mensaje));
 
        System.out.println(this.getLocalName() + ": Esperando peticion...");
 
		// PROTOCOLO FIPA-QUERY
        //Configurar y lanzar comportamiento de escucha
        MessageTemplate plantilla = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_QUERY);
        this.addBehaviour(new ComprobarResponder(this,plantilla));
 
        }
        else System.out.println("Debe escribir el nombre del tercer agente (pasado como parametro).");
    }
 
	// Respuesta al hijo
    class ComprobarResponder extends AchieveREResponder {
        public ComprobarResponder(Agent agente, MessageTemplate plantilla) {
            super(agente, plantilla);
        }
 
        protected ACLMessage handleRequest(ACLMessage request){
            System.out.printf("Recibida peticion de %s , le preguntare a la madre.\n", request.getSender().getLocalName());
 
            ACLMessage agree = request.createReply();
            agree.setPerformative(ACLMessage.AGREE ); //confirmamos recepcion del mensaje
            return agree;
        }
 
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response){
            System.out.printf("Comprobando si la madre le deja.\n");
 //LANZAR AQUI LO DEL PRINNCIPIO COMPORTAMIENTO DEL INITIATOR
            
            
            
            
            ACLMessage inform = request.createReply();
            inform.setPerformative(ACLMessage.INFORM ); // creamos el inform
 
			String retorno;
			if ( aceptado )
				retorno = "Vale.";
			else
				retorno = "No, mañana tienes que madrugar.";

			inform.setContent(retorno); // retornamos la decision

			System.out.printf("Mandando respuesta al hijo.\n");

			return inform;
        }
    }
 
	// Pregunta a la madre
    class PreguntarSiAceptado extends ProposeInitiator {
 
        public PreguntarSiAceptado(Agent agente, ACLMessage mensaje) {
            super(agente, mensaje);
        }
 
        //Manejar la respuesta en caso que acepte: ACCEPT_PROPOSAL
 
        protected void handleAcceptProposal(ACLMessage aceptacion) {
			System.out.printf("Le deja.\n");
            aceptado = true;
        }
 
        //Manejar la respuesta en caso que rechace: REJECT_PROPOSAL
 
        protected void handleRejectProposal(ACLMessage rechazo) {
			System.out.printf("No le deja.\n");
            aceptado = false;
        }
    }
}