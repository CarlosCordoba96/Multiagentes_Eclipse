package propose;
 
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ProposeInitiator;
 
public class ProposeIni extends Agent {
 
    protected void setup() {
        //Se crea un mensaje PROPOSE. Se quiere pedir permiso para salir de clase.
        ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE);
        mensaje.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
        mensaje.setContent("Puedo salir de clase?");
 
        //Se añade el destinatario.
        AID id = new AID();
        id.setLocalName("Puerta");
        mensaje.addReceiver(id);
 
        //Añadir el comportamiento
        this.addBehaviour(new SalirClase(this, mensaje));
    }
 
    private class SalirClase extends ProposeInitiator {
        public SalirClase(Agent agente, ACLMessage mensaje) {
            super(agente, mensaje);
        }
 
        //Manejar la respuesta en caso que acepte: ACCEPT_PROPOSAL
 
        protected void handleAcceptProposal(ACLMessage aceptacion) {
            System.out.printf("%s: Solicitud aceptada.\n", this.myAgent.getLocalName());
            System.out.printf("%s: Saliendo de clase...\n", this.myAgent.getLocalName());
        }
 
        //Manejar la respuesta en caso que rechace: REJECT_PROPOSAL
 
        protected void handleRejectProposal(ACLMessage rechazo) {
            System.out.printf("%s: Solicitud denegada.\n", this.myAgent.getLocalName());
            System.out.printf("%s: No salgo de clase.\n", this.myAgent.getLocalName());
        }
    }
}
