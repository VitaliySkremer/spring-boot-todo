package ru.skremer.todoapplication.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/500")
    public String getCommonErrorPage() {
        return "public/error/common-error-page";
    }

    @RequestMapping("/403")
    public String getForbiddenErrorPage() {
        return "public/error/forbidden-error-page";
    }

    @RequestMapping("/404")
    public String getNotFoundErrorPage() {
        return "public/error/not-found-error-page";
    }
}
