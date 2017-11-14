package hilos;
import jade.core.Agent;
import jade.core.behaviours.*;

public class sequential extends Agent {
	ThreadedBehaviourFactory tbf;
	Behaviour comp1,comp2,comp3;
	public void setup() {
		tbf = new ThreadedBehaviourFactory();
		comp1= new Comp(5,"1");	
		comp1=tbf.wrap(comp1);
		addBehaviour((comp1));
		/*
		
		addBehaviour(tbf.wrap(comp1));
		addBehaviour(tbf.wrap(comp2));
		addBehaviour(tbf.wrap(comp3));
		*/
		
	}	
	private class Comp extends Behaviour{
		int ejec;
		int current=0;
		String id;
		public Comp(int ejec,String id) {
			this.ejec=ejec;
			this.id=id;
		}
		public void action() {
			current++;
			System.out.println("Soy el comportamiento "+id +" y estoy en mi ejecucion "+current+" de "+ejec);
			
		}

		@Override
		public boolean done() {
			
			return (current==ejec);
		}
		public int onEnd() {
			comp2=new Comp2(10,"2");
			comp2=tbf.wrap(comp2);
			addBehaviour((comp2));
			return 0;
		}
	}
	private class Comp2 extends Behaviour{
		int ejec;
		int current=0;
		String id;
		public Comp2(int ejec,String id) {
			this.ejec=ejec;
			this.id=id;
		}
		public void action() {
			current++;
			System.out.println("Soy el comportamiento "+id +" y estoy en mi ejecucion "+current+" de "+ejec);
			
		}

		@Override
		public boolean done() {
			
			return (current==ejec);
		}
		public int onEnd() {
			comp3=new CompFinal(35,"3");
			comp3=tbf.wrap(comp3);
			addBehaviour((comp3));
			return 0;
		}
	}
	
	private class CompFinal extends Behaviour{
		int ejec;
		int current=0;
		String id;
		public CompFinal(int ejec,String id) {
			this.ejec=ejec;
			this.id=id;
		}
		public void action() {
			current++;
			System.out.println("Soy el comportamiento "+id +" y estoy en mi ejecucion "+current+" de "+ejec);
			
		}

		@Override
		public boolean done() {
			
			return (current==ejec);
		}
		
		public int onEnd() {
			java.lang.Thread td1 = tbf.getThread(comp1);
			td1.interrupt();
			java.lang.Thread td2 = tbf.getThread(comp2);
			td2.interrupt();
			java.lang.Thread td3 = tbf.getThread(comp3);
			td3.interrupt();
			doDelete();
			return 0;
		}
		
	}
	
	
	public void takeDown() {
		
		System.out.println("Liberando recursos");
		


        System.out.println("****Agente finalizado****");

	}

}
