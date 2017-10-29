package Comunicacion.Ejercicio1;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
 
public class Receptor extends Agent
{
    protected void setup()
    {
      ParallelBehaviour pb = (new ParallelBehaviour(this,1){
      public int onEnd(){
           System.out.println("Agente terminado ");
           doDelete();
            return 0;
        }

      });
        pb.addSubBehaviour(new ComportamientoPar());
        pb.addSubBehaviour(new ComportamientoImpar());
        addBehaviour(pb);
    }
protected void takeDown(){
        System.out.println("Liberando Recursos...");
    }
    
      class ComportamientoPar extends SimpleBehaviour
      {
          private int Nejecuciones=0;
          
          MessageTemplate plantilla = null;
          public ComportamientoPar ()
          {
            AID emisor = new AID();
            emisor.setLocalName("emisor");
 
          //Devuelve una plantilla de mensaje que coincida con algún mensaje con un slot :sender dado.
            MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
 
          //Devuelve una plantilla de mensaje que coincida con algún mensaje con una perfomativa dada.
            MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
 
          //Devuelve una plantilla de mensaje que conicida con algún mensaje con una slot :language dado
            MessageTemplate filtroIdioma = MessageTemplate.MatchLanguage("Par");
 
          //Operación lógica AND entre dos objetos de esta clase.
            plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
            plantilla = MessageTemplate.and(plantilla,filtroIdioma);
          }
 
          public void action()
          {
            
            ACLMessage mensaje = receive(plantilla);
 
            if (mensaje!= null)
            {
                Nejecuciones++;
                System.out.println("Ejecucion "+Nejecuciones+" de "+getLocalName()+"Par");
                System.out.println(getLocalName() + ":Par ha recibido el siguiente mensaje: ");
                System.out.println(mensaje.getContent());
                int recv=Integer.parseInt(mensaje.getContent());
                recv=recv*2;
                String mens=Integer.toString(recv);
                // Envia constestación
                    System.out.println(getLocalName() +":Par Enviando contestacion");
                    ACLMessage respuesta = mensaje.createReply();
                    respuesta.setPerformative( ACLMessage.INFORM );
                    respuesta.setContent( mens );
                    send(respuesta);
                    System.out.println(getLocalName() +": Enviando "+ mens  +" a emisor");
  
                
            }else
            {
                System.out.println(getLocalName() + ":Esperando mensaje Par...");
                block();
            }
        }
 
        public boolean done()
        {
            return (Nejecuciones==10);
        }
        public int onEnd(){
          System.out.println("Par ha terminado");
          return 0;
        }
    }
    class ComportamientoImpar extends SimpleBehaviour
      {
          private int Nejecuciones=0;
          MessageTemplate plantilla = null;
          public ComportamientoImpar()
          {
            AID emisor = new AID();
            emisor.setLocalName("emisor");
 
          //Devuelve una plantilla de mensaje que coincida con algún mensaje con un slot :sender dado.
            MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
 
          //Devuelve una plantilla de mensaje que coincida con algún mensaje con una perfomativa dada.
            MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
 
          //Devuelve una plantilla de mensaje que conicida con algún mensaje con una slot :language dado
            MessageTemplate filtroIdioma = MessageTemplate.MatchLanguage("Impar");
 
          //Operación lógica AND entre dos objetos de esta clase.
            plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
            plantilla = MessageTemplate.and(plantilla,filtroIdioma);
          }
 
          public void action()
          {
            
            ACLMessage mensaje = receive(plantilla);
        
            if (mensaje!= null)
            {
              Nejecuciones++;
              System.out.println("Ejecucion "+Nejecuciones+" de "+getLocalName()+ " Impar");
                System.out.println(getLocalName() + ": IMPAR ha recibido el siguiente mensaje: ");
                System.out.println(mensaje.getContent());
                int recv=Integer.parseInt(mensaje.getContent());
                recv=recv/2;
                String mens=Integer.toString(recv);
                // Envia constestación
                    System.out.println(getLocalName() +":ImPar Enviando contestacion");
                    ACLMessage respuesta = mensaje.createReply();
                    respuesta.setPerformative( ACLMessage.INFORM );
                    respuesta.setContent( mens );
                    send(respuesta);
                    System.out.println(getLocalName() +": Enviando "+ mens  +" a emisor");       
            }else
            {
                System.out.println(getLocalName() + ":Esperando mensaje ImPar...");
                block();
            }
        }
 
        public boolean done()
        {
            return(Nejecuciones==10);
        }
        public int onEnd(){
          System.out.println("ImPar ha terminado");
          return 0;
        }
    }
 
    
}
