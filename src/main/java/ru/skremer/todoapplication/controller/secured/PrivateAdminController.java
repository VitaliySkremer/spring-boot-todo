package ru.skremer.todoapplication.controller.secured;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skremer.todoapplication.entity.User;
import ru.skremer.todoapplication.entity.UserRole;
import ru.skremer.todoapplication.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class PrivateAdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String getManagementPage(Model model) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.isSuperAdmin()) {
            List<User> candidatesToDelete = userService.findAllByRoleIn(Arrays.asList(UserRole.USER, UserRole.ADMIN));
            List<User> candidatesToUpgrade = candidatesToDelete
                    .stream()
                    .filter(User::isSimpleUser)
                    .toList();

            model.addAttribute("candidatesToDelete", candidatesToDelete);
            model.addAttribute("candidatesToUpgrade", candidatesToUpgrade);
        } else {
            List<User> candidatesToDelete = userService.findAllByRoleIn(Collections.singleton(UserRole.USER));
            model.addAttribute("candidatesToDelete", candidatesToDelete);
        }

        return "private/admin/management-page";
    }

    @PostMapping("/admin/delete-user")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteById(id);

        return "redirect:/management/admin";
    }

    @PostMapping("/super-admin/make-user-admin")
    public String makeUserAmin(@RequestParam("id") int id) {
        userService.updateRole(id, UserRole.ADMIN);

        return  "redirect:/management/admin";
    }
}
