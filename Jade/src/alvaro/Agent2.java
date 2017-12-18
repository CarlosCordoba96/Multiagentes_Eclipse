package alvaro;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;

public class Agent2 extends Agent {

	private AID id1 = new AID();
	private ACLMessage mensaje1 = new ACLMessage(ACLMessage.REQUEST);
	private AID id3 = new AID();
	private ACLMessage mensaje3 = new ACLMessage(ACLMessage.REQUEST);
	private int random;
	private MessageTemplate plantilla1;
	private MessageTemplate plantilla3;
	private String msg1;
	private String msg3;

	public void setup() {
		id1.setLocalName("ag1");
		id3.setLocalName("ag3");
		plantilla1 = MessageTemplate.MatchSender(id1);
		plantilla3 = MessageTemplate.MatchSender(id3);
		mensaje1.setSender(getAID());
		mensaje1.addReceiver(id1);
		mensaje3.setSender(getAID());
		mensaje3.addReceiver(id3);
		addBehaviour (new Comportamiento2());
	}

	class Comportamiento2 extends SimpleBehaviour {
		int random;

		public void action() {
			//Interaccion con agente1
			mensaje1 = blockingReceive(plantilla1);
			random = Integer.parseInt(mensaje1.getContent());
			System.out.println("2: Recibe " + random + " de 1");
			random = random/2;
			msg1 = ""+random;
			mensaje1.setContent(msg1);
			System.out.println("2: Manda " + random + " a 1");
			send(mensaje1);
			if (random != 0)
				send(mensaje1);
			//Interaccion con agente3
			mensaje3 = blockingReceive(plantilla3);
			random = Integer.parseInt(mensaje3.getContent());
			System.out.println("2: Recibe " + random + " de 3");
			random = random/2;
			msg3 = ""+random;
			mensaje3.setContent(msg3);
			System.out.println("2: Manda " + random + " a 3");
			send(mensaje3);
			if (random != 0)
				send(mensaje3);
		}
		public boolean done() {
			return (random==0);
		}
	}

}
