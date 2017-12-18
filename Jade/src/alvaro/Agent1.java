package alvaro;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;

public class Agent1 extends Agent {
	
	private AID id = new AID();
	private ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
	private int random;
	private MessageTemplate plantilla;
	private String msg;

	public void setup() {
		id.setLocalName("ag2");
		plantilla = MessageTemplate.MatchSender(id);
		mensaje.setSender(getAID());
		mensaje.addReceiver(id);
		addBehaviour (new Comportamiento1());
	}

	class Comportamiento1 extends SimpleBehaviour {
		int random;
		public void onStart() {
			random = (int) (Math.random() *100000);
			msg = ""+random;
			mensaje.setContent(msg);
			System.out.println("1: Manda " + random + " a 2");
			send(mensaje);
		}
		public void action() {
			mensaje = blockingReceive(plantilla);
			random = Integer.parseInt(mensaje.getContent());
			System.out.println("1: Recibe " + random + " de 2");
			random = random/2;
			mensaje.setContent(""+random);
			System.out.println("1: Manda " + random + " a 2");
			send(mensaje);
			if (random != 0)
				send(mensaje);
		}
		public boolean done() {
			return (random==0);
		}
	}
}



















