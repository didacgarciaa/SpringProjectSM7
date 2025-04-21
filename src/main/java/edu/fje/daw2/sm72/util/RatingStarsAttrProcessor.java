package edu.fje.daw2.sm72.util;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Procesador de atributos personalizado para ThymeLeaf.
 * Permite convertir puntuaciones numéricas en estrellas visuales.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
public class RatingStarsAttrProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "rating-stars";
    private static final int PRECEDENCE = 10000;

    /**
     * Constructor principal que inicializa el procesador.
     * 
     * @param dialectPrefix Prefijo del dialecto
     */
    public RatingStarsAttrProcessor(final String dialectPrefix) {
        super(
            TemplateMode.HTML, // Modo de plantilla
            dialectPrefix,     // Prefijo del dialecto
            null,              // Nombre de la etiqueta (null = se aplica a todas)
            false,             // Aplicar a etiquetas específicas solamente
            ATTR_NAME,         // Nombre del atributo
            true,              // Nombre del atributo tiene prefijo
            PRECEDENCE,        // Precedencia
            true               // Eliminar el atributo después de procesar
        );
    }

    /**
     * Procesa el atributo personalizado para convertir puntuación en estrellas.
     * 
     * @param context Contexto de la plantilla
     * @param tag Etiqueta procesable
     * @param attributeName Nombre del atributo
     * @param attributeValue Valor del atributo (puntuación)
     * @param structureHandler Manejador de estructura
     */
    @Override
    protected void doProcess(
            final ITemplateContext context,
            final IProcessableElementTag tag,
            final AttributeName attributeName,
            final String attributeValue,
            final IElementTagStructureHandler structureHandler) {
        
        try {
            double rating = Double.parseDouble(attributeValue);
            int fullStars = (int) Math.floor(rating);
            boolean halfStar = (rating - fullStars) >= 0.5;
            int emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
            
            StringBuilder starsHtml = new StringBuilder();
            
            // Agregar estrellas completas
            for (int i = 0; i < fullStars; i++) {
                starsHtml.append("<i class=\"fas fa-star text-warning\"></i>");
            }
            
            // Agregar media estrella si corresponde
            if (halfStar) {
                starsHtml.append("<i class=\"fas fa-star-half-alt text-warning\"></i>");
            }
            
            // Agregar estrellas vacías
            for (int i = 0; i < emptyStars; i++) {
                starsHtml.append("<i class=\"far fa-star text-warning\"></i>");
            }
            
            // Añadir el valor numérico
            starsHtml.append("<span class=\"ms-2\">").append(rating).append("</span>");
            
            // Establecer el HTML generado
            structureHandler.setBody(starsHtml.toString(), false);
            
        } catch (NumberFormatException e) {
            structureHandler.setBody("Puntuación inválida", false);
        }
    }
} 