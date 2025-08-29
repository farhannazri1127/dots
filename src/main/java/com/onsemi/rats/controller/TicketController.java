package com.onsemi.rats.controller;

import java.util.List;
import com.onsemi.rats.dao.IncidentDAO;
import com.onsemi.rats.dao.NewRequestDAO;
import com.onsemi.rats.model.Incident;
import com.onsemi.rats.model.NewRequest;
import com.onsemi.rats.model.UserSession;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "/ticket")
@SessionAttributes({"userSession"})
public class TicketController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
    String[] args = {};
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    ServletContext servletContext;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String incident(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
        String id = userSession.getLoginId();
        
        IncidentDAO incidentDAO = new IncidentDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByUser(id);
        model.addAttribute("incidentList", incidentList);
        
        NewRequestDAO reqD = new NewRequestDAO();
        List<NewRequest> requestList = reqD.getNewRequestListByUserId(id);
        model.addAttribute("requestList", requestList);
        return "incident/ticket";
    }
    
}
