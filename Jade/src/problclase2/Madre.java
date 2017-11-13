package problclase2;

import javax.swing.JOptionPane;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
 
//HIJO--FIPA QUERY--> PADRE --FIPA PROPOSE-->MADRE
public class Madre extends Agent {
 
    protected void setup() {
        System.out.printf("Esperando peticiones...\n");
 
        MessageTemplate  plantilla = ProposeResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_PROPOSE);// tipo de protocolo
 
        this.addBehaviour(new ResponderPermiso(this,plantilla));
    }
 
    //Metodo que permite al usuario decidir si acepta la propuesta o si la rechaza.
    private boolean checkContent(String agente, String propuesta) {
        if (JOptionPane.showConfirmDialog(null, propuesta, agente + " dice:", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
 
    private class ResponderPermiso extends ProposeResponder {
 
        public ResponderPermiso (Agent agente, MessageTemplate plantilla) {
            super(agente, plantilla);
        }
 
        protected ACLMessage prepareResponse(ACLMessage propuesta){
            System.out.printf("Peticion de permiso recibida de %s.\n", propuesta.getSender().getLocalName());
 
            //Comprueba los datos de la propuesta
            if (Madre.this.checkContent(propuesta.getSender().getLocalName(), "Conceder permiso")) {
                System.out.printf("Vale.\n");
 
                ACLMessage agree = propuesta.createReply(); // crear la respuesta
                agree.setPerformative(ACLMessage.ACCEPT_PROPOSAL); // confirmamos
 
                return agree;
            } else {
 
                System.out.printf("No, mañana tiene que madrugar.\n");
 
                ACLMessage refuse = propuesta.createReply(); // crear la respuesta
                refuse.setPerformative(ACLMessage.REJECT_PROPOSAL ); //rechazamos
 
                return refuse;
            }
        }
    }
}