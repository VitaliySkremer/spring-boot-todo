package ru.skremer.todoapplication.controller.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusObj == null) {
            return "redirect:/error/500";
        }

        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(statusObj.toString()));


        return switch (status) {
            case FORBIDDEN -> "redirect:/error/403";
            case NOT_FOUND -> "redirect:/error/404";
            default -> "redirect:/error/500";
        };
    }
}