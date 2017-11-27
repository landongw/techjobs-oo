package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();
    private JobForm jobForm;

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam int id) {

        Job job = jobData.findById(id);
        model.addAttribute("job", job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm newJobForm, Errors errors) {

        if (errors.hasErrors()) {
            return "new-job";
        }

        Job newJob = new Job(newJobForm.getName(),
                             jobData.getEmployers().findById(newJobForm.getEmployerId()),
                             jobData.getLocations().findById(newJobForm.getLocation()),
                             jobData.getPositionTypes().findById(newJobForm.getPositionType()),
                             jobData.getCoreCompetencies().findById(newJobForm.getCoreCompetency()));
        jobData.add(newJob);
        int newJobId = newJob.getId();
        return "redirect:?id=" + newJobId;

    }
}
