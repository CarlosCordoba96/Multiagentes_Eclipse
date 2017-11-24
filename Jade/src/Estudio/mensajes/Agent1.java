package Estudio.mensajes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class Agent1 extends Agent {


	public void setup() {
		addBehaviour(new beha());
	}

	class beha extends Behaviour{
		MessageTemplate mt;
		AID emisor;
		int naleatorio;
		public void onStart() {
			naleatorio=((int) (Math.random() * 99999)+1);
			emisor= new AID();
			emisor.setLocalName("Agente2");
			MessageTemplate filtroemisor=MessageTemplate.MatchSender(emisor);
			MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            mt = MessageTemplate.and(filtroInform,filtroemisor);
            
            ACLMessage mensaje= new ACLMessage(ACLMessage.REQUEST);
            mensaje.setSender(getAID());
            mensaje.addReceiver(emisor);
            mensaje.setContent(""+naleatorio);
            
            System.out.println("Enviando mensaje a AG2 : "+""+naleatorio);
            send(mensaje);

		}

		public void action() {
			System.out.println("Esperando mensaje de AG2...");
			ACLMessage msg1=blockingReceive(mt);
			String response=msg1.getContent();
			System.out.println(response);


		}


		public boolean done() {



			return false;
		}

		public int onEnd() {


			return 0;
		}


	}
}
