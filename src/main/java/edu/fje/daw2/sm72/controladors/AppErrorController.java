package edu.fje.daw2.sm72.controladors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador para manejar errores HTTP.
 * Implementa la interfaz ErrorController de Spring Boot para proporcionar
 * una página de error personalizada para diferentes tipos de errores.
 * 
 * @author Spring Project SM7
 * @version 1.0
 */
@Controller
public class AppErrorController implements ErrorController {

    /**
     * Maneja las solicitudes dirigidas a la ruta /error.
     * Determina el tipo de error y proporciona información adecuada en el modelo.
     * 
     * @param request Solicitud HTTP que generó el error
     * @param model Modelo para pasar datos a la vista
     * @return Vista de la página de error
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Obtener el código de estado de error
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        // Valores por defecto
        String errorTitle = "Error";
        String errorMessage = "Se ha producido un error inesperado.";
        String errorDetails = "Por favor, inténtalo de nuevo más tarde.";
        
        // Determinar el tipo de error basado en el código de estado
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            model.addAttribute("statusCode", statusCode);
            
            // Personalizar el mensaje según el código de error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorTitle = "Página no encontrada";
                errorMessage = "La página que has solicitado no existe.";
                errorDetails = "Comprueba la URL o vuelve a la página de inicio.";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorTitle = "Acceso denegado";
                errorMessage = "No tienes permisos para acceder a este recurso.";
                errorDetails = "Contacta con el administrador si crees que esto es un error.";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorTitle = "Error interno del servidor";
                errorMessage = "Se ha producido un error interno en el servidor.";
                errorDetails = "Por favor, inténtalo de nuevo más tarde o contacta con soporte.";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                errorTitle = "Solicitud incorrecta";
                errorMessage = "La solicitud enviada no es válida.";
                errorDetails = "Verifica los datos de tu solicitud e inténtalo de nuevo.";
            }
        }
        
        // Añadir información del error al modelo
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorDetails", errorDetails);
        
        // Devolver la vista de error personalizada
        return "error/custom-error";
    }
} 