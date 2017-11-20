package Clase;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class Agent2 extends Agent{
    @Override
      protected void setup() {
    	
          addBehaviour(new CyclicBehaviour(this) {
          @Override
          public void action(){
        	  String firsname = null;
           ACLMessage msg = receive();
           System.out.println("Waiting to receive...");
           if(msg!=null){
        	   System.out.println("Message received!!!!");
             //Here i want to get the first name of the object Person
        	   try {
				firsname=((Person)msg.getContentObject()).getFirstName();
				System.out.println("The first name is: "+firsname);
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	   
           }else {
        	   block();
           }
          }

         });
      }
}
