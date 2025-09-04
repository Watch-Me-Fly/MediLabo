package com.oc.frontend.controllers.dashboards;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard/doctor")
public class DoctorDashboardController {

    @GetMapping
    public ModelAndView doctorDashboard(Model model) {

        model.addAttribute("message", "Bonjour Docteur");

        return new ModelAndView("/dashboard/doctor", model.asMap());
    }

}
