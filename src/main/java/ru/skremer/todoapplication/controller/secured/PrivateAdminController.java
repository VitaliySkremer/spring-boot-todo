package ru.skremer.todoapplication.controller.secured;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class PrivateAdminController {
    @GetMapping("/admin")
    public String getManagementPage() {
        return "private/admin/management-page";
    }
}
