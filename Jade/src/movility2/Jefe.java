
/*
 * Ejecutar Jefe en tercer lugar
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
 
public class Jefe extends .......... {
  private ArrayList containers = new ArrayList();
  private Location origen = null;
  private Location destino= null;
 
  //Metodo para actualizar la lista de containers disponibles
  protected void ..........(){
    containers.clear();
    ACLMessage request= new ACLMessage(ACLMessage.REQUEST);
    request.setLanguage(..........);
    // Establecemos que MobilityOntology sea la ontologia de este mensaje.
    request.setOntology(MobilityOntology.getInstance().getName());
    // Le solicitamos al AMS una lista de los containers disponibles
    Action action= new Action(getAMS(), ..........);
    try {
      getContentManager().fillContent(.........., ..........);
      request.addReceiver(action.getActor());
      send(request);
 
      // Filtramos los mensajes INFORM que nos llegan desde el AMS
      .......... mt = MessageTemplate.and(MessageTemplate.MatchSender(getAMS()), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
 
      ACLMessage resp= blockingReceive(mt);
      ContentElement ce= getContentManager().extractContent(resp);
      Result result=(Result) ce;
      jade.util.leap.Iterator it= result.getItems().iterator();
      // Almacena en el array los containers disponibles.
      while(..........) {
    Location loc=(Location) it.next();
    containers.add(loc);
      }
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }
 
  //Metodo para visualizar los containers disponibles en la plataforma
  protected void verContainers(){
    //ACTUALIZAR
    ..........
    //VISUALIZAR
    System.out.println("=== Containers disponibles: ===");
    for(int i=0; i<containers.size(); i++){
      System.out.println("["+ .......... + "] " + ((Location)containers.get(i)).getName());
    }
  }
 
  protected void setup(){
    // Registramos el lenguaje y la ontologia para la movilidad en el manejador del agente
    ..........
    getContentManager().registerOntology(MobilityOntology.getInstance());
 
    actualizarContainers();
	System.out.println("El jefe esta preparado para mover empleados");
    addBehaviour(new MoverAgenteBehaviour(this));
  }

  class .......... extends SimpleBehaviour {
    private boolean parar= false;
 
    public MoverAgenteBehaviour(..........) {
      super(a);
    }
 
    public void ..........() {
      try {
		verContainers();
		System.out.println();
		System.out.print("Introduce el numero del container al que mover al empleado: ");
		BufferedReader lee= new BufferedReader(new InputStreamReader(System.in));
		String lectura = lee.readLine();
		int container=0;
		container = Integer.parseInt(lectura);
		System.out.print("Introduce el nombre del agente que quieres mover: ");
		String nombre = lee.readLine();
 
 
		try{
		  origen = ..........;
		  destino=(Location)containers.get(container);
		  AID aid = new ..........(nombre, AID.ISLOCALNAME); 
		  MobileAgentDescription mad = ..........;
		  mad.setName(aid);
		  mad.setDestination(..........);
		  MoveAction ma = new MoveAction();
		  ma.setMobileAgentDescription(mad);
		  ..........(new Action(getAMS(), ma));
		  
    }catch(Exception ex){
      System.out.println("Problema al intentar mover el agente");
    }
      }catch(Exception io){
    System.out.println(io);
      }
    }
 
    public boolean ..........() {
      return parar;
    }
  }
 
  protected void takeDown(){
    ..........
  }
 
  void sendRequest(Action action) {
    .......... request = new ACLMessage(ACLMessage.REQUEST);
    request.setLanguage(new SLCodec().getName());
    request.setOntology(..........);
    try {
      getContentManager().fillContent(request, action);
      request.addReceiver(action.getActor());
      send(..........);
    }catch (Exception ex) { ex.printStackTrace(); }
   }
 
}
