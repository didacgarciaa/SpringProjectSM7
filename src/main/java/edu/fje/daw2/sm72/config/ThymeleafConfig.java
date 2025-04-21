package edu.fje.daw2.sm72.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import edu.fje.daw2.sm72.util.CustomDialect;

/**
 * Configuración de ThymeLeaf para la aplicación Spring MVC.
 * Registra dialectos personalizados en el motor de plantillas.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
@Configuration
public class ThymeleafConfig {

    /**
     * Configura el motor de plantillas de ThymeLeaf.
     * Añade dialectos personalizados al motor estándar.
     * 
     * @param templateResolver Resolvedor de plantillas inyectado por Spring
     * @return Motor de plantillas configurado
     */
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        
        // Registrar nuestro dialecto personalizado
        templateEngine.addDialect(new CustomDialect());
        
        return templateEngine;
    }
} 