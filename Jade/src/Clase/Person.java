package Clase;

import jade.util.leap.Serializable;

public class Person implements Serializable{
	  private String firstName;
	  private String lastName;
	  public String getFirstName(){
	     return firstName;
	  }
	  public void setFirstName(String fName){
	     this.firstName=fName;
	  }
	  public String getLastName(){
	     return lastName;
	  }
	  public void setLastName(String lName){
	     this.lastName=lName;
	  }
	}
