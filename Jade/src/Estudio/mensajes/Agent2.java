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
		int result;
		AID sender1;
		AID sender3;
		MessageTemplate mt1;
		MessageTemplate mt3;

		public void onStart() {
			sender3=new AID();
			sender3.setLocalName("Agente3");
			sender1=new AID();
			sender1.setLocalName("Agente1");
			MessageTemplate filtroEmisor=MessageTemplate.MatchSender(sender1);
			MessageTemplate filtroPerfor=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			mt1=MessageTemplate.and(filtroPerfor, filtroEmisor);
			
			MessageTemplate filtroEmisor2=MessageTemplate.MatchSender(sender3);
			MessageTemplate filtroPerfor2=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			mt3=MessageTemplate.and(filtroPerfor2, filtroEmisor2);

		}

		public void action() {
			ACLMessage resp1=blockingReceive(mt1);
			result=Integer.parseInt(resp1.getContent());
			System.out.println("2 recive de 1 :"+result);
			result=result/2;
			
			
			ACLMessage msg3= new ACLMessage(ACLMessage.REQUEST);
			msg3.setSender(getAID());
			msg3.addReceiver(sender3);
			System.out.println("2 manda a 3 : "+result);
			msg3.setContent(""+result);
					
			send(msg3);
			
			ACLMessage resp3=blockingReceive(mt3);
			result=Integer.parseInt(resp3.getContent());
			System.out.println("2 recive de 3 :"+result);
			result=result/2;
			ACLMessage response=resp1.createReply();
			response.setSender(getAID());
			response.setContent(""+result);
			response.setPerformative(ACLMessage.INFORM);
			System.out.println("2 envia a 1 : "+result);
			send(response);
			

		}


		public boolean done() {



			return result==0;
		}

		public int onEnd() {


			return 0;
		}
	}
}
