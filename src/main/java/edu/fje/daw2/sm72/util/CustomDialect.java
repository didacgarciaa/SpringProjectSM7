package edu.fje.daw2.sm72.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

/**
 * Dialecto personalizado para ThymeLeaf.
 * Registra los procesadores de atributos personalizados para su uso en las plantillas.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
@Component
public class CustomDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Spring Project Dialect";
    private static final String DIALECT_PREFIX = "sm";
    
    /**
     * Constructor que inicializa el dialecto con un nombre y prefijo específicos.
     */
    public CustomDialect() {
        super(DIALECT_NAME, DIALECT_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    /**
     * Proporciona el conjunto de procesadores disponibles en este dialecto.
     * 
     * @param dialectPrefix El prefijo del dialecto
     * @return Set con los procesadores de atributos
     */
    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        
        // Añadir el procesador de atributo de estrellas para calificaciones
        processors.add(new RatingStarsAttrProcessor(dialectPrefix));
        
        // Aquí se pueden añadir más procesadores personalizados
        
        return processors;
    }
} 