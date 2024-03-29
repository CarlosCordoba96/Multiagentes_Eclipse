package contract;
 
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
 
import java.util.Date;
import java.util.Vector;
 
public class Cliente extends Agent {
 
    //Número de ofertas que se considerarán.
    private int numeroDeOfertas;
 
    //Precio máximo que se pagará por un coche.
    private int precionMaximo;
 
    protected void setup() {
        //El precio máximo se recibirá como argumento de entrada.
        Object[] args = this.getArguments();
 
        if (args != null && args.length == 1) {
            this.precionMaximo = Integer.parseInt(((String) args[0]));
 
            //Búsqueda del servicio de venta de coches en las páginas amarillas.
            ServiceDescription servicio = new ServiceDescription();
            servicio.setType("Autos");
            servicio.setName("Venta de coches");
 
            DFAgentDescription descripcion = new DFAgentDescription();
            descripcion.addLanguages("Español");
            descripcion.addServices(servicio);
 
            try {
                DFAgentDescription[] resultados = DFService.search(this, descripcion);
                if (resultados.length <= 0) {
                    System.out.println("No existen ventas de coches.");
                } else {
                    System.out.println("Busco coche, hay " + resultados.length + " ofertas...");
 
                    //Creamos el mensaje CFP(Call For Proposal) cumplimentando sus parámetros
                    ACLMessage mensajeCFP = new ACLMessage(ACLMessage.CFP);
                    for (DFAgentDescription agente:resultados) {
                        mensajeCFP.addReceiver(agente.getName());
                    }
            //Protocolo que vamos a utilizar
                    mensajeCFP.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
                    mensajeCFP.setContent("Busco coche, ¿proponeis precios?");
 
                    //Indicamos el tiempo que esperaremos por las ofertas.
                    mensajeCFP.setReplyByDate(new Date(System.currentTimeMillis() + 15000));
 
                    //Se añade el comportamiento que manejará las ofertas.
                    this.addBehaviour(new ManejoOpciones(this, mensajeCFP));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
 
        } else {
            System.out.println("Debe de introducir el precio maximo que esta dispuesto a pagar (pasado como parametro)");
        }
 
    } // Fin del setup
 
    private class ManejoOpciones extends ContractNetInitiator {
 
        public ManejoOpciones(Agent agente, ACLMessage plantilla) {
            super(agente, plantilla);
        }
 
        //Manejador de proposiciones.
        protected void handlePropose(ACLMessage propuesta, Vector aceptadas) {
            System.out.printf("%s: Recibida oferta de autos %s. Ofrece un coche por %s euros.\n",
                this.myAgent.getLocalName(), propuesta.getSender().getLocalName(), propuesta.getContent());
        }
 
        //Manejador de rechazos de proposiciones.
        protected void handleRefuse(ACLMessage rechazo) {
            System.out.printf("%s: Autos %s no tiene coches que ofrecer.\n",
                this.myAgent.getLocalName(), rechazo.getSender().getLocalName());
        }
 
        //Manejador de respuestas de fallo.
        protected void handleFailure(ACLMessage fallo) {
            if (fallo.getSender().equals(myAgent.getAMS())) {
 
        //Esta notificacion viene del entorno de ejecución JADE (no existe el receptor)
                System.out.println("AMS: Esta venta de autos no existe o no es accesible");
            } else {
                System.out.printf("%s: Autos %s ha sufrido un fallo.\n",
                    this.myAgent.getLocalName(), fallo.getSender().getLocalName());
            }
            //Falló, por lo tanto, no recibiremos respuesta desde ese agente
            Cliente.this.numeroDeOfertas--;
        }
 
        //Método colectivo llamado tras finalizar el tiempo de espera o recibir todas las propuestas.
        protected void handleAllResponses(Vector respuestas, Vector aceptados) {
 
        //Se comprueba si una venta de autos se pasó del plazo de envío de ofertas.
            if (respuestas.size() < numeroDeOfertas) {
                System.out.printf("%s: %d ventas de autos llegan tarde.\n",
                    this.myAgent.getLocalName(), Cliente.this.numeroDeOfertas - respuestas.size());
            }
 
            //Escogemos la mejor oferta
            int mejorOferta = Integer.MAX_VALUE;
            AID mejorAutos = null;
            ACLMessage aceptado = null;
            for (Object resp:respuestas) {
                ACLMessage mensaje = (ACLMessage) resp;
                if (mensaje.getPerformative() == ACLMessage.PROPOSE) {
                    ACLMessage respuesta = mensaje.createReply();
                    respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    aceptados.add(respuesta);
 
                    //Si la oferta es la mejor (inferior a todas las otras)
                    //Se almacena su precio y el AID de la venta de autos que la hizo.
                    int oferta = Integer.parseInt(mensaje.getContent());
                    if (oferta <= precionMaximo && oferta <= mejorOferta) {
                        mejorOferta = oferta;
                        mejorAutos = mensaje.getSender();
                        aceptado = respuesta;
                    }
                }
            }
 
            //Si hay una oferta aceptada se modifica su performativa.
            if (aceptado != null) {
                System.out.printf("%s: Decidido!!! Compro el coche de Autos %s\n",
                    this.myAgent.getLocalName(), mejorAutos.getLocalName());
                aceptado.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            }
        }
 
        //Manejador de los mensajes inform.
        protected void handleInform(ACLMessage inform) {
            System.out.printf("%s: Autos %s te ha enviado el contrato.\n",
                this.myAgent.getLocalName(), inform.getSender().getName());
        }
    }
}
