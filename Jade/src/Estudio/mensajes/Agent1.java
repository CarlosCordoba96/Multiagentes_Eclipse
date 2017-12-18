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
		int result;
		AID sender;
		public void onStart() {
			result=((int) (Math.random()*9999)+1);
			sender= new AID();
			sender.setLocalName("Agente2");
			MessageTemplate filtroemisor=MessageTemplate.MatchSender(sender);
			MessageTemplate filtroperformative=	MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			mt=MessageTemplate.and(filtroperformative, filtroemisor);
			
				

		}

		public void action() {
			ACLMessage msg1=new ACLMessage(ACLMessage.REQUEST);
			String mesg=""+result;
			msg1.setContent(mesg);
			msg1.addReceiver(sender);
			msg1.setSender(getAID());
			System.out.println("1 a 2 envia: "+mesg);
			send(msg1);
			
			ACLMessage resp;
			resp= blockingReceive(mt);
			result=Integer.parseInt(resp.getContent());
			
			if(result!=0) {
				result=result/2;
			}
		}


		public boolean done() {
			return result==0;
		}

		public int onEnd() {


			return 0;
		}


	}
}
