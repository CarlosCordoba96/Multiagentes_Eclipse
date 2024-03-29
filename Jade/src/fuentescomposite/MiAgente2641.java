package fuentescomposite;

import jade.core.Agent;
import jade.core.behaviours.*;
 
public class MiAgente2641 extends Agent{
 
    long tini;
 
    protected void setup(){
        tini = System.currentTimeMillis();
        addBehaviour(new miTicker(this, 1000));
    }
 
    private class miTicker extends TickerBehaviour{
 
        int minticks;
 
        public miTicker(Agent a, long intervalo){
            super(a, intervalo);
            minticks = 0;
        }
 
        public void reset () {
            super.reset();
            
            System.out.println("reseteo!");
        }
 
        protected void onTick() {
            long tfin = System.currentTimeMillis() - tini;
            int nticks = getTickCount(); // obtenemos el numero de ticks desde el último reset
            minticks++;
            if (nticks == 5) {
                System.out.println("[" + tfin + "ms.] tick = " + nticks + ", mitick =  " + minticks + " y reseteo");
                reset();
            } else {
                System.out.println("[" + tfin + "ms.] tick = " + nticks + ", mitick =  " + minticks);
            }
        }
    }
}
 
