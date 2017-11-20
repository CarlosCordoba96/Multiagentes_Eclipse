package Clase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Agent1 extends Agent{
	  @Override
	    protected void setup() {
	        addBehaviour(new OneShotBehaviour(this) {
	          @Override
	          public void action(){
	        	  
	        	  System.out.println("\nIntroduce el firstname: ");
	              BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
	              String firstname;
	              Person Person= new Person();
				try {
					firstname = buff.readLine();
					Person.setFirstName(firstname);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	              System.out.println("\nIntroduce el lastname: ");
	              String lastname;
				try {
					lastname = buff.readLine();
					 Person.setLastName(lastname);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	          ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
	          aclmsg.addReceiver(new AID("Agent2", AID.ISLOCALNAME));
	          try {
				aclmsg.setContentObject(Person);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          send(aclmsg);
	          }
	        });
	    }
	}