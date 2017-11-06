package examen6n;

import jade.core.Agent;
import jade.core.behaviours.*;


public class MiAgente extends Agent {
	protected void setup() {
		SequentialBehaviour sec= (new SequentialBehaviour(this){
			public int onEnd() {
				doDelete();
				return 0;
			}
		});
		
		ParallelBehaviour p1=(new ParallelBehaviour(this,0) {
			public int onEnd() {
				System.out.println("Finalizando paralelo1");
				return 0;
			}
		});
		
		int r1=(int) (Math.random() * 9)+1;
		p1.addSubBehaviour(new Comp1(r1,"1.1"));
		
		int r2=(int) (Math.random() * 9)+1;
		p1.addSubBehaviour(new Comp1(r2,"1.2"));

		int r3=(int) (Math.random() * 9)+1;
		p1.addSubBehaviour(new Comp1(r3,"1.3"));

		int ejec=(Math.random() <= 0.5) ? 1 : 2;
		System.out.println("Ha salido: "+ejec);
		ParallelBehaviour p2=(new ParallelBehaviour(this,ejec) {
			public int onEnd() {
				System.out.println("Finalizando paralelo2");
				return 0;
			}
		});
		
		p2.addSubBehaviour(new Comp2("2.1"));
		p2.addSubBehaviour(new Comp2("2.2"));
		sec.addSubBehaviour(p1);
		sec.addSubBehaviour(p2);
		
		
		
		addBehaviour(sec);
	}
	
	protected void takeDown() {
		
		System.out.println("Liberando recursos");
	}
	
	private class Comp1 extends Behaviour{
		private int n;
		private int ejec=0;
		private String step;
		public Comp1(int n,String step) {
			this.n=n;
			this.step=step;
		}
		public void action() {
			ejec++;
			System.out.println("Soy el comportamiento "+ step +" y estoy en mi ejecucion "+ejec+" de "+n);
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return ejec==n;
		}
	}
	
	private class Comp2 extends OneShotBehaviour{
		private String name;
		public Comp2(String name) {
			this.name=name;
		}
		
		public void action() {
			System.out.println("Soy el comportamiento "+name+" y estoy en mi ejecucion 1 de 1");
			block();
			restart();
			//CON BLOCK(1999
		}
		
	}
	
}
