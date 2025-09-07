package com.oc.frontend.controllers.dashboards;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard/patient")
public class PatientDashboardController {

    @GetMapping
    public ModelAndView patientDashboard(Model model) {

        model.addAttribute("message", "Bonjour Mr./Mme.");

        return new ModelAndView("dashboard/patient", model.asMap());
    }
}
