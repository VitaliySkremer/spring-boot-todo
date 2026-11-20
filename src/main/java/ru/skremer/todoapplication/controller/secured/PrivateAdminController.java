package ru.skremer.todoapplication.controller.secured;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skremer.todoapplication.entity.User;
import ru.skremer.todoapplication.service.UserService;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class PrivateAdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String getManagementPage(Model model) {
        User currentUser = userService.getCurrentUser();

        model.addAttribute("userName", currentUser.getName());

        return "private/admin/management-page";
    }
}
