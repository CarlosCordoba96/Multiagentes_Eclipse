/*
 * Ejecutar Empleado en primer lugar
 */

import java.util.*;
import java.io.*;
 
import jade.lang.acl.*;
import jade.content.*;
import jade.content.onto.basic.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.core.behaviours.*;
import jade.domain.*;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.core.*;
import ..........
import ..........
 
public class Empleado extends .......... {
  private ArrayList containers = new ArrayList();
 
  protected void setup(){
    System.out.println("El " + (getAID()).getName() + " esta trabajando");
    addBehaviour(new ..........);
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil antes de desplazarse.
  protected void ..........(){
    System.out.println("El " + .......... + " esta listo para moverse");
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil despues de desplazarse.
  protected void ..........() {
    getContentManager().registerLanguage(..........);
    getContentManager()...........(MobilityOntology.getInstance());
    System.out.println("El " + (getAID()).getName() + " ha llegado ");
  }
 
  class MoverAgenteBehaviour extends .......... {
    private boolean parar= false;
 
    public ..........(Agent a) {
      super(a);
    }
 
    public void action() { }
 
[style=java]
    protected void ..........(){
		System.out.println("El empleado termina");
	}
 
    public boolean done() {
      return parar;
    }
  }
}

