package org.alesapps.xmrtask.web;

import org.alesapps.xmrtask.service.ResumeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Anatoliy Kozhayev on 03.09.2017.
 */
@Controller
public class ResumeController {

    private static final Logger LOG = getLogger(ResumeController.class);

    private ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/")
    public ModelAndView root() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("resumes", resumeService.findAll());
        return model;
    }

    @PostMapping("/")
    public ModelAndView parse(@RequestParam(name = "action", required = true) String action, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("index");
        if ("parse".equals(action)) {
            LOG.info("Parsing resumes");
            resumeService.parse();
            model.addObject("resumes", resumeService.findAll());
        } else if ("filter".equals(action)) {
            String headerFilter = request.getParameter("header");
            LOG.info("Filtering resumes by text {}", headerFilter);
            model.addObject("resumes", resumeService.findByHeaderContainingIgnoreCase(headerFilter));
        }
        return model;
    }
}
