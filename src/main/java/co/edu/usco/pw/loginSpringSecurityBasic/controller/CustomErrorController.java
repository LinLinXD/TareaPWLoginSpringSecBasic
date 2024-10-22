package co.edu.usco.pw.loginSpringSecurityBasic.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Ha ocurrido un error inesperado";
        String errorDetails = "";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            switch(statusCode) {
                case 404:
                    errorMessage = "¡Página no encontrada!";
                    errorDetails = "La página que estás buscando no existe.";
                    break;

                case 403:
                    errorMessage = "¡Acceso denegado!";
                    errorDetails = "No tienes permisos para acceder a esta página.";
                    break;

                case 500:
                    errorMessage = "¡Error interno del servidor!";
                    errorDetails = "Ocurrió un problema en el servidor. Por favor, intenta más tarde.";
                    break;

                default:
                    errorMessage = "¡Error " + statusCode + "!";
                    errorDetails = "Ha ocurrido un error inesperado.";
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorDetails", errorDetails);
        model.addAttribute("statusCode", status);

        // Agregar información de depuración en modo desarrollo
        String errorPath = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String errorException = (String) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        model.addAttribute("errorPath", errorPath);
        model.addAttribute("errorException", errorException);

        return "error";
    }
}