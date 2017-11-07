package problclase2;

import javax.swing.JOptionPane;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
 
public class Madre extends Agent {
 
    protected void setup() {
        System.out.printf("Esperando peticiones...\n");
 
        MessageTemplate  plantilla = ProposeResponder.createMessageTemplate(FIPANames.InteractionProtocol. ......);
 
        this.addBehaviour(new ResponderPermiso(this, ......));
    }
 
    //Metodo que permite al usuario decidir si acepta la propuesta o si la rechaza.
    private boolean ......(String agente, String propuesta) {
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
 
                ACLMessage agree = propuesta. ......(); // crear la respuesta
                agree.setPerformative(ACLMessage. ......); // confirmamos
 
                return agree;
            } else {
 
                System.out.printf("No, mañana tiene que madrugar.\n");
 
                ACLMessage refuse = propuesta. ......(); // crear la respuesta
                refuse.setPerformative(ACLMessage. ......); //rechazamos
 
                return refuse;
            }
        }
    }
}