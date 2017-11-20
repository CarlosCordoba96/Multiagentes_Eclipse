package ontologia;
 
import jade.content.onto.*;
import jade.content.schema.*;
 
public class frutasOntology extends Ontology {
   // Nombre de la ontología
   public static final String ONTOLOGY_NAME = "ontología de frutas";
 
  // Vocabulario de la ontología que van a manejar los agentes
  public static final String FRUTA = "Fruta";
  public static final String FRUTA_NOMBRE = "nombre";
  public static final String FRUTA_PRECIO = "precio";
 
  public static final String OFERTA = "Oferta";
  public static final String OFERTA_FRUTA = "fruta";
 
  public static final String COMPRAR = "Comprar";
  public static final String COMPRAR_FRUTA = "fruta";
 
  // Instancia de la ontología (que será única)
  private static Ontology instancia = new frutasOntology();
 
  // Método para acceder a la instancia de la ontología
  public static Ontology getInstance() {
     return instancia;
   }
 
   // Constructor privado
   private frutasOntology() {
     // frutasOntology extiende la ontología básica
     super(ONTOLOGY_NAME, BasicOntology.getInstance());
 
     try {
       // Añade los elementos
       add(new ConceptSchema(FRUTA), Fruta.class);
       add(new PredicateSchema(OFERTA), Oferta.class);
       add(new AgentActionSchema(COMPRAR), Comprar.class);
 
       // Estructura del esquema para el concepto FRUTA
       ConceptSchema cs = (ConceptSchema) getSchema(FRUTA);
       cs.add(FRUTA_NOMBRE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
       cs.add(FRUTA_PRECIO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
 
       // Estructura del esquema para el predicado OFERTA
       PredicateSchema ps = (PredicateSchema) getSchema(OFERTA);
       ps.add(OFERTA_FRUTA, (ConceptSchema) getSchema(FRUTA));
 
       // Estructura del esquema para la acción COMPRAR
       AgentActionSchema as = (AgentActionSchema) getSchema(COMPRAR);
       as.add(COMPRAR_FRUTA, (ConceptSchema) getSchema(FRUTA));
     }
     catch (OntologyException oe) {
       oe.printStackTrace();
     }
   }
} 
