package EjerComp;
import jade.core.*;
import jade.core.behaviours.*;
public class Fsm extends Agent{
	private static final String INICIAL="INICIAL";
	private static final String ESTADO_1="ESTADO_1";
	private static final String ESTADO_2="ESTADO_2";
	private static final String FINAL="FINAL";
	public void setup() {
		FSMBehaviour fsm=(new FSMBehaviour() {
			public int onEnd() {
				System.out.println("Agente terminado");
				doDelete();
				return 0;
			}
		});
		
		fsm.registerFirstState(new Behav(),INICIAL);
		fsm.registerState(new random(4), ESTADO_1);
		fsm.registerState(new Betwo(), ESTADO_2);
		fsm.registerLastState(new Behav(), FINAL);
		
		fsm.registerDefaultTransition(INICIAL, ESTADO_1);
		fsm.registerTransition(ESTADO_1, ESTADO_1, 0);
		fsm.registerTransition(ESTADO_1, ESTADO_1, 1);
		fsm.registerTransition(ESTADO_1, ESTADO_1, 2);
		fsm.registerTransition(ESTADO_1, ESTADO_2, 3);
		fsm.registerDefaultTransition(ESTADO_2, FINAL);
		
		addBehaviour(fsm);



	}
	public void takeDown() {
System.out.println("LIBERANDO RECURSOS");
	}

	private class Behav extends OneShotBehaviour{


		@Override
		public void action() {
			System.out.println("Ejecuntando: "+getBehaviourName());

		}
	}
	private class Betwo extends Behaviour{

		@Override
		public void action() {
			System.out.println("Ejecuntando: "+getBehaviourName());

		}

		@Override
		public boolean done() {
			return true;
		}

	}

	private class random extends Behav{
		private int MaxValue;
		private int exit;

		public random(int num) {
			MaxValue=num;
		}
		public void action() {
			System.out.println("Ejecuntando: "+getBehaviourName());
			exit=(int)(Math.random()*MaxValue);
			System.out.println("Valor de salida: "+exit);
		}
		public int onEnd() {
			return exit;
		}
	}
}
