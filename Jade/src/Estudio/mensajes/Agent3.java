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
		int result;
		AID sender;
		
		public void onStart() {
			sender=new AID();
			sender.setLocalName("Agente2");
			MessageTemplate filtroEmisor=MessageTemplate.MatchSender(sender);
			MessageTemplate filtroPerf=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt=MessageTemplate.and(filtroEmisor,filtroPerf);
			
			

		}

		public void action() {
			ACLMessage msg1=blockingReceive(mt);
			result=Integer.parseInt(msg1.getContent());
			System.out.println("3 recive de 2: "+result);
			result=result/2;
			
			ACLMessage resp=msg1.createReply();
			resp.setPerformative(ACLMessage.INFORM);
			resp.setSender(getAID());
			resp.setContent(""+result);
			System.out.println("De 3 a 2 envia: "+result);
			send(resp);
			

		}


		public boolean done() {



			return result==0;
		}

		public int onEnd() {


			return 0;
		}
	}
}
