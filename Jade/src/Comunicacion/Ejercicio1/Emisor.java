package Comunicacion.Ejercicio1;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
 
public class Emisor extends Agent
{
    protected void setup()
    {
        addBehaviour(new EmisorComportaminento());
    }

    class EmisorComportaminento extends SimpleBehaviour
    {
        private int num;
        private int numero;
        private String msg;
        private Boolean fin=false;
        public void onStart(){
            num=1000;
        }
        public void action()
        {
            numero=(((int)(Math.random()*num)));
            System.out.println("Se enviara el numero"+numero);
            System.out.println(getLocalName() +": Preparandose para enviar un mensaje a receptor");
            AID id = new AID();
            id.setLocalName("receptor");
 
        // Creación del objeto ACLMessage
            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
 
        //Rellenar los campos necesarios del mensaje
            mensaje.setSender(getAID());

            if((numero%2)==0){
                
                 mensaje.setLanguage("Par");
            }else{
                mensaje.setLanguage("Impar");
            }

            mensaje.addReceiver(id);
            msg=Integer.toString(numero);
            mensaje.setContent(msg);
 
       //Envia el mensaje a los destinatarios
            send(mensaje);
            System.out.println(getLocalName() +": Enviando "+  msg +" a receptor");
            System.out.println(mensaje.toString());
 
       //Espera la respuesta
            ACLMessage mensaje2 = blockingReceive();
            if (mensaje2!= null)
            {
                System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje: ");
                System.out.println(mensaje2.getContent());
                
            }
        }
 
        public boolean done()
        {
            return fin;
        }
    }
    
}
