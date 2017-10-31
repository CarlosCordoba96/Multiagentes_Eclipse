package query;
 
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
 
public class Aeropuerto extends Agent {
 
    protected void setup() {
        System.out.println(this.getLocalName() + ": Abriendo centralita...");
 
        // Filtrado para recibir sólo mensajes del protocolo FIPA-Query.
 
        MessageTemplate plantilla = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_QUERY);
 
        this.addBehaviour(new ComprobarResponder(this, plantilla));
    }
 
    class ComprobarResponder extends AchieveREResponder {
        public ComprobarResponder(Agent agente, MessageTemplate plantilla) {
            super(agente, plantilla);
        }
 
        protected ACLMessage handleRequest(ACLMessage request)
                throws NotUnderstoodException, RefuseException {
            System.out.printf("Operadora: Hemos recibido una llamada de %s solicitando informacion sobre su reserva.\n", request.getSender().getLocalName());
 
            // Si el solicitante es válido se acepta su petición.
 
            if (comprobarSolicitante(request.getSender().getLocalName())) {
                System.out.println("Operadora: Espere un momento por favor...");
                ACLMessage agree = request.createReply();
                agree.setPerformative(ACLMessage.AGREE);
                return agree;
            } else {
                System.out.println(this.myAgent.getLocalName() + ": Todas las operadoras estan ocupadas.");
                throw new RefuseException("Por favor intentelo de nuevo mas tarde");
            }
        }
 
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
            ACLMessage inform = request.createReply();
            inform.setPerformative(ACLMessage.INFORM);
            String retorno = "No dispone de ninguna reserva";
 
            if (comprobarSolicitante(request.getSender().getName()))
                retorno = "Si que ha hecho alguna reserva";
 
            inform.setContent(retorno);
            return inform;
        }
 
        // Método simple de aceptación o rechazo de solicitudes.
        private boolean comprobarSolicitante(String nombre) {
            return (nombre.length() > 2);
        }
    }
}
