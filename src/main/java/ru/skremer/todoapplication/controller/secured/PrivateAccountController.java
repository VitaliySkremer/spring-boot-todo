package ru.skremer.todoapplication.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skremer.todoapplication.entity.dto.RecordsContainerDto;
import ru.skremer.todoapplication.service.RecordService;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {

    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public String getMainPage(
            Model model,
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter
    ) {
        RecordsContainerDto recordsContainerDto = recordService.findAll(filter);

        model.addAttribute("records", recordsContainerDto.getRecords());
        model.addAttribute("numberOfDoneItems", recordsContainerDto.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveItems", recordsContainerDto.getNumberOfActiveRecords());

        return "private/account-page";
    }

    @PostMapping(value = "/add-record")
    public String addRecord(@RequestParam("title") String title) {
        recordService.addRecord(title);

        return "redirect:/account";
    }

    @PostMapping(value = "/make-record-done")
    public String makeRecordDone(@RequestParam("id") int id,
                                 @RequestParam(value = "filter", required = false) String filter) {
        recordService.makeRecordDone(id);

        return "redirect:/account" + generateFilterParam(filter);
    }

    @PostMapping(value = "/delete-record")
    public String deleteRecord(@RequestParam("id") int id,
                               @RequestParam(value = "filter", required = false) String filter) {
        recordService.deleteRecord(id);

        return "redirect:/account" + generateFilterParam(filter);
    }

    public String generateFilterParam(String filterParam) {
        return filterParam == null || filterParam.isBlank() ? "" : "?filter=" + filterParam;
    }
}
