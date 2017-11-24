package Estudio.mensajes;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.AID;
import jade.core.Agent;

public class Agent2 extends Agent {
	public void setup() {
		addBehaviour(new beha());
	}

	class beha extends Behaviour{
		MessageTemplate mt;
		AID emisor;
		
		
		
		public void onStart() {
			
			//recivir de AG1
			AID id= new AID();
			id.setLocalName("Agente1");
			MessageTemplate filtroemisor=MessageTemplate.MatchSender(id);
			MessageTemplate filtroPerfor=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt=MessageTemplate.and(filtroPerfor, filtroemisor);
			
			//enviar a AG2

		}

		public void action() {
			System.out.println("Esperando mensaje de AG1...");
			ACLMessage msg1=blockingReceive(mt);
			System.out.println("Agente2 Mensaje: "+msg1.getContent());
			int resp=Integer.parseInt(msg1.getContent());
			
			
			MessageTemplate mt2;
			AID id2=new AID();
			id2.setLocalName("Agente3");
			ACLMessage mens=new ACLMessage(ACLMessage.REQUEST);
			mens.setSender(getAID());
			mens.setContent(""+resp/2);
			mens.addReceiver(id2);
			System.out.println("Enviando mensaje a AG3: "+resp/2);
			send(mens);
			
			//FILTRO RESPUESTA AGENTE3
			
			MessageTemplate filtroemisor=MessageTemplate.MatchSender(id2);
			MessageTemplate filtroreque=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			mt2=MessageTemplate.and(filtroemisor, filtroreque);
			
			
			System.out.println("Esperando mensaje de AG3..");
			ACLMessage msg2=blockingReceive(mt2);
			ACLMessage respu=msg1.createReply();
			int resp2=Integer.parseInt(msg2.getContent());
			System.out.println("Recivido de AGENTE3: "+resp2);
			respu.setPerformative(ACLMessage.INFORM);
			respu.setContent(""+resp2/2);
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
