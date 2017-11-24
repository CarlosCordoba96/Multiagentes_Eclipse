package noviembre20;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Agent1 extends Agent {
	private int naleatorio;
	private int i=0;
	protected void setup() {
		
		
		addBehaviour(new Comp());
		
	}
	
	private class Comp extends Behaviour{
		MessageTemplate plantilla = null;
		private boolean fin=false;
		private String msg;
		
		
		public void onStart() {
			 	AID emisor = new AID();
	            emisor.setLocalName("Agent2");
	            MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
	            MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	            plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
	            
	            int naleatorio = ((int) (Math.random() * 99999)+1);

	            System.out.println("Se enviara el numero: "+naleatorio);
	            System.out.println(getLocalName() +": Preparandose para enviar un mensaje a Agente 2");
	            AID id = new AID();
	            id.setLocalName("Agent2");
	 
	        // Creación del objeto ACLMessage
	            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
	 
	        //Rellenar los campos necesarios del mensaje
	            mensaje.setSender(getAID());
	            mensaje.addReceiver(id);
	            //msg=Integer.toString(naleatorio);
	            msg=" hola ";
	            mensaje.setContent(msg);
	 
	       //Envia el mensaje a los destinatarios
	            send(mensaje);
	            System.out.println(getLocalName() +": Enviando "+  msg +" a Agente2");
	            
	            
		}
		public void action() {
			
 
            
           
       //Espera la respuesta
            ACLMessage mensaje2 =blockingReceive(plantilla);
           
                System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje: ");
                System.out.println(mensaje2.getContent());
                
            
			
		}

		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	/*
	 * send en onstart
	 * 
	 * blocking receive AG2
	 * 
	 * done
	 * msg==0
	 */
}

