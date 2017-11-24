package noviembre20;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Agent2 extends Agent {
	private int i=0;
	protected void setup() {
		addBehaviour(new Comp());
	}
	
	
	private class Comp extends Behaviour{
		MessageTemplate plantilla = null;
		 
		 public void onStart() {
			 AID emisor = new AID();
	            emisor.setLocalName("Agent1");
	            MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
	            MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	            plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
		 }
		
		public void action() {
			
            
            ACLMessage mensaje = blockingReceive(plantilla);
            
            	System.out.println(getLocalName() + ": ha recibido el siguiente mensaje: "+mensaje.getContent());
                //System.out.println(mensaje.getContent());
            	String respuesta=mensaje.getContent();

                ACLMessage resp=mensaje.createReply();
                resp.setPerformative( ACLMessage.INFORM );
                resp.setSender(getAID());
                resp.setContent( "Adios" );
                send(resp);
                //Enviar a Ag3
           
            
            
            
            
		}

		@Override
		public boolean done() {
			
			return false;
		}
		
	}
}

/*
*blockingreceive ag1
*send ag3
*
*separar en 2 comportamientos
*
*blockingreceive ag3
*
*send ag1
*
*
*done msg==0
*
*onend
*dodelete
*
*
*/