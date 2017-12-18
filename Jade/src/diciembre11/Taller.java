package diciembre11;
import java.util.StringTokenizer;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
 
 
public class Taller extends Agent {
 
    public int CODIGO_AVERIA;
    Object[] args;
    String marca1;
    String marca2;
    protected void setup()
    {
    	Object[] args = getArguments();
       // Recuperar argumentos
        if (args.length == 2) {
        marca1=(String)args[0];
        marca2=(String)args[1];
		CODIGO_AVERIA = (int)(Math.random()*40); //Pueden reparar desde 0 hasta este random
        	System.out.println("Taller "+getLocalName()+": Esperando avisos... codigo limite averia: "+CODIGO_AVERIA);
        	MessageTemplate protocolo = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        	MessageTemplate performativa = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        	//Aqui se compone la "plantilla"
        	MessageTemplate plantilla=MessageTemplate.and(protocolo, performativa);
        	addBehaviour(new ManejadorResponder(this, plantilla));
    }

	else 

		System.out.println("Debe de indicar la marca de los veh√≠culos a reparar en este taller.");

    }
 
    class ManejadorResponder extends AchieveREResponder
    {
        public ManejadorResponder(Agent a,MessageTemplate mt) {
            super(a,mt);
        }
 
        protected ACLMessage handleRequest(ACLMessage request)throws NotUnderstoodException, RefuseException
        {
      
            StringTokenizer st=new StringTokenizer(request.getContent());
            String contenido=st.nextToken();
		
            if(contenido.equals(marca1)||contenido.equals(marca2) ) 
            {

                st.nextToken();
                int CodAveria=Integer.parseInt(st.nextToken());
                if (CodAveria<=CODIGO_AVERIA)//Capacidad de reparar aver√≠a
                {
                    ACLMessage agree = request.createReply();
		    //Texto del Mensaje
                    agree.setContent("Salimos ahora hacia el lugar de la averia");
                    agree.setPerformative(ACLMessage.AGREE);
                    return agree;
                }
                else
                {
                   ACLMessage refuse=request.createReply();
                   refuse.setContent("No se puede tratar este tipo de averias en este taller");
                   refuse.setPerformative(ACLMessage.REFUSE);
                    return refuse;
                }
            }
            else {
            	ACLMessage notunderstood=request.createReply();
            	notunderstood.setContent("los mec·nicos no entienden de esta marca de vehÌculos.");
            	notunderstood.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                 return notunderstood;
            	
            }
        }
 
        protected ACLMessage prepareResultNotification(ACLMessage request,ACLMessage response) throws FailureException
        {
        	int rand=(int)(Math.random()*100);
        	System.out.println("Probabilidad ha sido: "+rand);
            if (rand<=60) {//Probabilidad de llegar a direcci√≥n correcta
                
                ACLMessage inform = request.createReply();
                inform.setContent("La gr˙a ha recogido el vehiculo");
                inform.setPerformative(ACLMessage.INFORM);
                return inform;
            }
            else
            {
            	ACLMessage failure = request.createReply();
            	failure.setContent("direcciÛn desconocida no se ha podido recoger el veÌculo.");
            	failure.setPerformative(ACLMessage.FAILURE);
                return failure;
            }
        }
    }
}
