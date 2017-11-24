
/*
 * Ejecutar Supervisor en segundo lugar
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
 
public class Supervisor extends Agent {
  private ArrayList containers = new ArrayList();
  private Location origen = null;
  private Location destino= null;
 
  //Metodo para actualizar la lista de containers disponibles
  protected void actualizarContainers(){
    origen = here();
    containers.clear();
    ACLMessage request= new ACLMessage(ACLMessage.REQUEST);
    request.setLanguage(new SLCodec().getName());
    // Establecemos que MobilityOntology sea la ontologia de este mensaje.
    ..........(MobilityOntology.getInstance().getName());
    // Le solicitamos al AMS una lista de los containers disponibles
    Action action= new Action(.........., new QueryPlatformLocationsAction());
    try {
      getContentManager().fillContent(request, action);
      request.addReceiver(action.getActor());
      ..........(request);
 
      // Filtramos los mensajes INFORM que nos llegan desde el AMS
      MessageTemplate mt= MessageTemplate.and(MessageTemplate.MatchSender(..........), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
 
      .......... resp= blockingReceive(mt);
      ContentElement ce= getContentManager().extractContent(resp);
      Result result=(Result) ce;
      jade.util.leap.Iterator it= result.getItems().iterator();
      // Almacena en un ArrayList "Locations" de los "Containers" a los que puede moverse el agente movil.
      while(it.hasNext()) {
    Location loc=..........;
    containers.add(loc);
      }
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }
 
  //Metodo para visualizar los containers disponibles en la plataforma
  protected void verContainers(){
    //ACTUALIZAR
    actualizarContainers();
    //VISUALIZAR
    System.out.println("=== Containers disponibles: ===");
    for(int i=0; i<containers.size(); i++){
      System.out.println("["+ i + "] " + ((Location)containers.get(i)).getName());
    }
  }
 
  protected void ..........(){
    // Registramos el lenguaje y la ontologia para la movilidad en el manejador del agente
    getContentManager().registerLanguage(..........);
    getContentManager().registerOntology(MobilityOntology.getInstance());
 
    actualizarContainers();
	System.out.println("El supervisor esta preparado para vigilar empleados");
    ..........(new MoverAgenteBehaviour(this));
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil antes de desplazarse.
  protected void ..........(){
    System.out.println("El supervisor se marcha a vigilar a los empleados");
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil despues de desplazarse.
  protected void ..........() {
    getContentManager().registerLanguage(new SLCodec());
    getContentManager().registerOntology(..........);
    System.out.println("El supervisor ha llegado para vigilar a los empleados");
  }
 
  class .......... extends SimpleBehaviour {
    private boolean parar= false;
 
    public MoverAgenteBehaviour(Agent a) {
      ..........(a);
    }
 
    public void .......... () {
      try {
        verContainers();
        System.out.print("Introduce el numero del container al que mover: ");
		BufferedReader lee= new BufferedReader(new InputStreamReader(System.in));
		String lectura = lee.readLine();
		int container = Integer.parseInt(lectura);
 
    try{
      ..........=(Location)containers.get(container);
      // Metodo al que llama el agente para desplazarse a su nuevo destino.
      ..........(destino);
    }catch(Exception ex){
      System.out.println("Problema al intentar mover el supervisor");
    }
      }catch(IOException io){
    System.out.println(io);
      }
      parar = true;
    }
 
    public boolean ..........() {
      return parar;
    }
  }
 
  protected void ..........(){
      System.out.println("El supervisor termina");
  }
 
}
