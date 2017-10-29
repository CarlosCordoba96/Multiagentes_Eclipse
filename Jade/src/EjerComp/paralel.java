package EjerComp;
import jade.core.*;
import jade.core.behaviours.*;

public class paralel extends Agent {
	public void setup() {
		ParallelBehaviour p=(new ParallelBehaviour(this,0) {
			public int onEnd() {
				System.out.println("Terminando agente paralelo");
				doDelete();
				return 0;
			}
		});
		Behaviour b1=new Comp1();
		p.addSubBehaviour(b1);
		Behaviour b2=new Comp2();
		p.addSubBehaviour(b2);
		Behaviour b3=new Comp3(this,5000);
		p.addSubBehaviour(b3);
		addBehaviour(p);
		
	}
	public void takeDown() {
		System.out.println("Liberando recursos");
	}
	private class Comp1 extends Behaviour{
		private int i;
		public void onStart() {
			i=0;
		}
		public void action() {
			i++;
			System.out.println("Soy el comportamiento 1 y estoy en la ejecucion: "+i);
		}

		
		public boolean done() {
			return i==3;
		}
		
		
	}
	private class Comp2 extends OneShotBehaviour{
		public void action() {
			System.out.println("Soy el comportamiento 2");
		}
	}
	
	private class Comp3 extends WakerBehaviour{

		public Comp3(Agent a, long timeout) {
			super(a, timeout);
			// TODO Auto-generated constructor stub
		}
		public void onWake() {
			System.out.println("Soy el 3er comportamiento y acabo de despertar");
		}
		
		
	}
}
