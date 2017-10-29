package fuentescomposite;
import jade.core.Agent;
import jade.core.behaviours.*;

public class ejercicio extends Agent{
	private Micomportamiento c1;
	private Micomportamiento2 c2;
	private int n=5;
	protected void setup(){
		c1= new Micomportamiento();
		c2= new Micomportamiento2();
		addBehaviour(c1);
		addBehaviour(c2);
	}
	protected void takeDown(){
        System.out.println("Liberando REcursos");
    }
	private class Micomportamiento extends Behaviour{
		private int NumeroEjecuciones;
		private int ejecuciones;

		public void onStart(){
			NumeroEjecuciones=(int) (Math.random() * 100);
			ejecuciones=0;
			System.out.println("NumeroEjecuciones de 1 es: "+NumeroEjecuciones);

		}
		public void action(){
			ejecuciones++;
			System.out.println("Executing ejecucion "+ejecuciones+ " de Behaviour 1 ");
			if(ejecuciones==NumeroEjecuciones/2){
				System.out.println("BLOQUEANDO");
				c2.block();
				System.out.println("El comportamiento 1 ha bloqueado al comportamiento 2");
			}
			
		}
		public final boolean done(){
			return ejecuciones==NumeroEjecuciones;
		}
		public int onEnd(){
			System.out.println("EL N ES; "+n);
			n--;
			if(n==0){
			System.out.println("Terminado");
           	doDelete();
			}else{
				removeBehaviour(c2);
				reset();
				c2.reset();
				addBehaviour(c1);
				addBehaviour(c2);
			}
           
            return 0;
        }

	}
	private class Micomportamiento2 extends Behaviour{
		private int NumeroEjecuciones;
		private int ejecuciones;
		
		public void onStart(){
			NumeroEjecuciones=(int) (Math.random() * 100);
			ejecuciones=0;
			System.out.println("NumeroEjecuciones de 2 es: "+NumeroEjecuciones);

		}
		public void action(){
			ejecuciones++;
			System.out.println("Executing ejecucion "+ejecuciones+ " de Behaviour 2");
			if(ejecuciones==NumeroEjecuciones/2){
				System.out.println("BLOQUEANDO");
				c1.block();
				System.out.println("El comportamiento 2 ha bloqueado al comportamiento 1");
			}
			
		}
		public final boolean done(){
			return ejecuciones==NumeroEjecuciones;
		}
		public int onEnd(){
			System.out.println("EL N ES; "+n);
			n--;
			if(n==0){
			System.out.println("Terminado");
           	doDelete();
			}else{
				removeBehaviour(c1);
				reset();
				c1.reset();
				addBehaviour(c1);
				addBehaviour(c2);
			}
           
            return 0;
        }

	}



//si no queremos que se ejecute el onstart solo addbehaviour si queremos que se ejecute reset+addbehaviour

}