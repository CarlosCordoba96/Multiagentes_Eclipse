package Estudio.mensajes;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.AID;
import jade.core.Agent;

public class Agent3 extends Agent {

	public void setup() {
		addBehaviour(new beha());
	}

	class beha extends Behaviour{
		MessageTemplate mt;
		public void onStart() {
			//recivir de AG1
			AID id= new AID();
			id.setLocalName("Agente2");
			MessageTemplate filtroemisor=MessageTemplate.MatchSender(id);
			MessageTemplate filtroPerfor=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt=MessageTemplate.and(filtroPerfor, filtroemisor);
			

		}

		public void action() {
			System.out.println("Esperando Mensaje de AG2");
			ACLMessage msg=blockingReceive(mt);
			ACLMessage respu=msg.createReply();
			int resp2=Integer.parseInt(msg.getContent());
			System.out.println("AGENTE3 RECIVE: "+resp2);
			respu.setPerformative(ACLMessage.INFORM);
			respu.setContent(""+resp2/2);
			
			System.out.println("Enviando mensaje a 2 de 3 :"+resp2/2);
			send(respu);


		}


		public boolean done() {



			return false;
		}

		public int onEnd() {


			return 0;
		}
	}
}
