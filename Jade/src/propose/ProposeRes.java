package propose;
 
import javax.swing.JOptionPane;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
 
public class ProposeRes extends Agent {
 
    protected void setup() {
        System.out.printf("%s: Esperando propuestas...\n", this.getLocalName());
 
        //Creamos la plantilla a emplear, para solo recibir mensajes con el protocolo FIPA_PROPOSE y la performativa PROPOSE
        MessageTemplate plantilla = ProposeResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_PROPOSE);
 
        //Añadimos el comportamiento "responderSalirClase()"
        this.addBehaviour(new ResponderSalirClase(this, plantilla));
    }
 
    //Método que permite al usuario decidir si acepta la propuesta o si la rechaza.
    private boolean checkContent(String agente, String propuesta) {
        if (JOptionPane.showConfirmDialog(null, propuesta, agente + " dice:", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
 
    private class ResponderSalirClase extends ProposeResponder {
 
        public ResponderSalirClase(Agent agente, MessageTemplate plantilla) {
            super(agente, plantilla);
        }
 
        //Preparación de la respuesta. Recibe un mensaje PROPOSE y, según su contenido, acepta o no.
 
        protected ACLMessage prepareResponse(ACLMessage propuesta)
                throws NotUnderstoodException {
            System.out.printf("%s: Proposicion recibida de %s.\n", this.myAgent.getLocalName(), propuesta.getSender().getLocalName());
            System.out.printf("%s: La propuesta es: %s.\n", this.myAgent.getLocalName(), propuesta.getContent());
 
            //Comprueba los datos de la propuesta
            if (ProposeRes.this.checkContent(propuesta.getSender().getLocalName(), propuesta.getContent())) {
                //Aceptación de la propuesta.
                System.out.printf("%s: Dando permiso para salir de clase.\n", this.myAgent.getLocalName());
 
                //Se crea la respuesta al mensaje con la performativa ACCEPT_PROPOSAL, pues se acepta.
                ACLMessage agree = propuesta.createReply();
                agree.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
 
                return agree;
            } else {
 
                //Rechazo de la propuesta.
                System.out.printf("%s: Prohibiendo que se salga de clase.\n", this.myAgent.getLocalName());
 
                //Se crea la respuesta al mensaje con la performativa REJECT_PROPOSAL, pues se rechaza.
                ACLMessage refuse = propuesta.createReply();
                refuse.setPerformative(ACLMessage.REJECT_PROPOSAL);
 
                return refuse;
            }
        }
    }
}
