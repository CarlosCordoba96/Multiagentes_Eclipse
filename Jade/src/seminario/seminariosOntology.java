package seminario;
import jade.content.onto.*;
import jade.content.schema.*;
 
public class seminariosOntology extends Ontology {
   // Nombre de la ontología
   public static final String ONTOLOGY_NAME = "ontologia de seminario";
 
  // Vocabulario de la ontología que van a manejar los agentes
  public static final String SEMINARIO = "Seminario";
  public static final String SEMINARIO_TEMA = "________";
  public static final String SEMINARIO_PUNTOS = "puntos";
 
  public static final String TRABAJO = "Trabajo";
  public static final String TRABAJO_SEMINARIO = "Seminario";
 
  public static final String REALIZAR = "________";
  public static final String REALIZAR_SEMINARIO = "Seminario";
 
  // Instancia de la ontología (que será única)
  private static Ontology instancia = new seminariosOntology();
 
  // Método para acceder a la instancia de la ontología
  public static Ontology getInstance() {
     return instancia;
   }
 
   // Constructor privado
   private seminariosOntology() {
     // frutasOntology extiende la ontología básica
     super(ONTOLOGY_NAME, BasicOntology.getInstance());
 
     try {
       // Añade los elementos
       add(new ConceptSchema(SEMINARIO), Seminario.class);
       add(new PredicateSchema(________), Trabajo.class);
       add(new AgentActionSchema(REALIZAR), Realizar.class);
 
       // Estructura del esquema para el concepto SEMINARIO
       ConceptSchema cs = (ConceptSchema) getSchema(SEMINARIO);
       cs.add(SEMINARIO_TEMA, (PrimitiveSchema)
	getSchema(BasicOntology.STRING));
       cs.add(SEMINARIO_PUNTOS, (PrimitiveSchema)
	getSchema(BasicOntology.INTEGER));
 
       // Estructura del esquema para el predicado TRABAJO
       PredicateSchema ps = (PredicateSchema) getSchema(TRABAJO);
       ps.add(TRABAJO_SEMINARIO, (ConceptSchema) getSchema(SEMINARIO));
 
       // Estructura del esquema para la acción REALIZAR
       AgentActionSchema as = (AgentActionSchema) getSchema(REALIZAR);
       as.add(________, (ConceptSchema) getSchema(SEMINARIO));
     }
     catch (OntologyException oe) {
       oe.printStackTrace();
     }
   }
} 

