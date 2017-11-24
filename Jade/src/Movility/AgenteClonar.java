package Movility;
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
import jade.domain.JADEAgentManagement.*;
import jade.domain.mobility.*;
 
public class AgenteClonar extends Agent {
  private ArrayList containers = new ArrayList();
  private Location origen = null;
  private Location destino= null;
  private String mensaje = "";
 
  //Metodo para actualizar la lista de containers disponibles
  protected void actualizarContainers(){
    containers.clear();
    ACLMessage request= new ACLMessage(ACLMessage.REQUEST);
    request.setLanguage(new SLCodec().getName());
    // Establecemos que MobilityOntology sea la ontologia de este mensaje.
    request.setOntology(MobilityOntology.getInstance().getName());
    // Le solicitamos al AMS una lista de los containers disponibles
    Action action= new Action(getAMS(), new QueryPlatformLocationsAction());
    try {
      getContentManager().fillContent(request, action);
      request.addReceiver(action.getActor());
      send(request);
 
      // Filtramos los mensajes INFORM que nos llegan desde el AMS
      MessageTemplate mt= MessageTemplate.and(MessageTemplate.MatchSender(getAMS()), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
 
      ACLMessage resp= blockingReceive(mt);
      ContentElement ce= getContentManager().extractContent(resp);
      Result result=(Result) ce;
      jade.util.leap.Iterator it= result.getItems().iterator();
      // Almacena en un ArrayList "Locations" de los "Containers" a los que puede moverse el agente movil.
      while(it.hasNext()) {
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
    actualizarContainers();
    //VISUALIZAR
    System.out.println("******Containers disponibles: *******");
    for(int i=0; i<containers.size(); i++){
      System.out.println("["+ i + "] " + ((Location)containers.get(i)).getName());
    }
  }
 
  protected void setup(){
    // Registramos el lenguaje y la ontologia para la movilidad en el manejador del agente
    getContentManager().registerLanguage(new SLCodec());
    getContentManager().registerOntology(MobilityOntology.getInstance());
 
    actualizarContainers();
    addBehaviour(new ClonarAgenteBehaviour(this));
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil antes de desplazarse.
  protected void beforeMove(){
    System.out.println("[MSG] El agente se clona al container: "+ destino.getName());
  }
 
  // Metodo que contiene las operaciones que realiza el agente movil despues de desplazarse.
  protected void afterMove() {
    getContentManager().registerLanguage(new SLCodec());
    getContentManager().registerOntology(MobilityOntology.getInstance());
    System.out.println("[MSG] El agente ha llegado desde: "+ origen.getName());
    leerArchivo();
    System.out.println(mensaje);
  }
 
  class ClonarAgenteBehaviour extends SimpleBehaviour {
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
    System.out.print("Introduce el nombre del clon: ");
    String nombre = lee.readLine();
 
    try{
      origen = here();
      destino=(Location)containers.get(container);
 
      // Metodo al que llama el agente para desplazarse a su nuevo destino.
      // Este destino es de tipo "Location".
      doClone(destino, nombre);
 
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
 
  private void leerArchivo(){
    File archivo = null;
    FileReader fr = null;
    try {
      archivo = new File ("arc.txt");
      if(archivo.exists()){
    String linea;
    fr = new FileReader (archivo);
    BufferedReader br = new BufferedReader(fr);
    while((linea=br.readLine())!=null)
      mensaje = linea;
      //System.out.println(linea);
      }
    }
    catch(IOException e){
      System.out.println(e);
    }
  }
}
