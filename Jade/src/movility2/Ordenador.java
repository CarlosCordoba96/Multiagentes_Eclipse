
/*
 * Ejecutar Ordenador en cuarto lugar
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
 
public class Ordenador extends Agent {
  private ArrayList containers = new ArrayList();
  private Location origen = null;
  private Location destino= null;
 
  //Metodo para actualizar la lista de containers disponibles
  protected void actualizarContainers(){
    containers.clear();
    ACLMessage request= new ..........(ACLMessage.REQUEST);
    request.setLanguage(new SLCodec().getName());
    // Establecemos que MobilityOntology sea la ontologia de este mensaje.
    request.setOntology(MobilityOntology.getInstance().getName());
    // Le solicitamos al AMS una lista de los containers disponibles
    Action action= new .......... (getAMS(), new QueryPlatformLocationsAction());
    try {
      getContentManager().fillContent(request, action);
      request.addReceiver(..........);
      send(request);
 
      // Filtramos los mensajes INFORM que nos llegan desde el AMS
      MessageTemplate mt= MessageTemplate.and(MessageTemplate.MatchSender(getAMS()), MessageTemplate.MatchPerformative(..........));
 
      ACLMessage resp= blockingReceive(mt);
      ContentElement ce= getContentManager().extractContent(resp);
      Result result=(Result) ce;
      jade.util.leap.Iterator it= result.getItems().iterator();
      // Almacena en un ArrayList "Locations" de los "Containers" a los que puede moverse el agente movil.
      while(it.hasNext()) {
    Location loc=(Location) it.next();
    containers.add(..........);
      }
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }
 
  //Metodo para visualizar los containers disponibles en la plataforma
  protected void ..........(){
    //ACTUALIZAR
    actualizarContainers();
    //VISUALIZAR
    System.out.println("=== Containers disponibles: ===");
    for(int i=0; i<containers.size(); i++){
      System.out.println("["+ i + "] " + ..........);
    }
  }
 
 
  protected void ..........(){
    // Registramos el lenguaje y la ontologia para la movilidad en el manejador del agente
    getContentManager().registerLanguage(new SLCodec());
    ..........
 
    actualizarContainers();
	System.out.println("Los ordenadores estan listos para ser repartidos");
    addBehaviour(new ClonarAgenteBehaviour(..........));
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil antes de desplazarse.
  protected void ..........(){
    System.out.println("El ordenador se clona al container: "+ destino.getName());
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil despues de desplazarse.
  protected void ..........() {
    getContentManager().registerLanguage(new SLCodec());
    getContentManager().registerOntology(..........);
    System.out.println("Departamento listo para trabajar ");
	while (true) {
	try {
		Thread.sleep(40000); 
	}
    catch(InterruptedException ie){}
	}
  }
 
  class .......... extends SimpleBehaviour {
    private boolean parar= false;
 
    public ClonarAgenteBehaviour(Agent a) {
      super(a);
    }
 
    public void action() {
      try {
		verContainers();
		System.out.print("Introduce el numero del container en el que clonar: ");
		BufferedReader lee= new BufferedReader(new InputStreamReader(System.in));
		String lectura = lee.readLine();
		int container = Integer.parseInt(lectura);
		System.out.print("Introduce el nombre del ordenador: ");
		String nombre = lee.readLine();
 
    try{
      .......... = here();
      destino=(Location)containers.get(..........);
 
      // Metodo al que llama el agente para desplazarse a su nuevo destino.
      // Este destino es de tipo "Location".
      ..........(destino, nombre);
 
    }catch(Exception ex){
      System.out.println("Problema al intentar clonar el agente");
    }
      }catch(IOException io){
    System.out.println(io);
      }
    }
 
    public boolean done() {
      return parar;
    }
  }
  
  protected void takeDown(){}
  
}
