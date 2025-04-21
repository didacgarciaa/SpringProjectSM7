package edu.fje.daw2.sm72.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
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
     * Configura el resolvedor de plantillas de ThymeLeaf
     * 
     * @return Resolvedor de plantillas configurado
     */
    @Bean
    @Primary
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        return templateResolver;
    }
    
    /**
     * Configura el motor de plantillas de ThymeLeaf.
     * Añade dialectos personalizados al motor estándar.
     * 
     * @return Motor de plantillas configurado
     */
    @Bean
    @Primary
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        
        // Registrar nuestro dialecto personalizado
        templateEngine.addDialect(new CustomDialect());
        
        return templateEngine;
    }
    
    /**
     * Configura el resolvedor de vistas de ThymeLeaf
     * 
     * @return Resolvedor de vistas configurado
     */
    @Bean
    @Primary
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }
} 