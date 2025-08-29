package com.onsemi.rats.controller;

import com.onsemi.rats.dao.DashboardDAO;
import java.util.List;
import com.onsemi.rats.dao.IncidentDAO;
import com.onsemi.rats.dao.NewRequestDAO;
import com.onsemi.rats.dao.ParameterDetailsDAO;
import com.onsemi.rats.model.Incident;
import com.onsemi.rats.model.NewRequest;
import com.onsemi.rats.model.ParameterDetails;
import com.onsemi.rats.model.UserSession;
import java.util.Locale;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/query")
@SessionAttributes({"userSession"})
public class QueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ticketNo,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subCategory,
            @RequestParam(required = false) String urgency,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String site,
            @RequestParam(required = false) String pic,
            @RequestParam(required = false) String eta,
            @RequestParam(required = false) String requestDate,
            @RequestParam(required = false) String status
    ) {
        String query = "";
        int count = 0;
//        String query2 = "";

        if (ticketNo != null) {
            if (!("").equals(ticketNo)) {
                count++;
                if (count == 1) {
                    query = query + " WHERE ticket_no = \'" + ticketNo + "\' ";
                } else if (count > 1) {
                    query = query + " AND ticket_no = \'" + ticketNo + "\' ";
                }
            }
        }

        if (category != null) {
            if (!category.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE category = \'" + category + "\' ";
                } else if (count > 1) {
                    query = query + " AND category = \'" + category + "\' ";
                }
            }
        }

        if (subCategory != null) {
            if (!subCategory.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE sub_category = \'" + subCategory + "\' ";
                } else if (count > 1) {
                    query = query + " AND sub_category = \'" + subCategory + "\' ";
                }
            }
        }

        if (urgency != null) {
            if (!urgency.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE urgency = \'" + urgency + "\' ";
                } else if (count > 1) {
                    query = query + " AND urgency = \'" + urgency + "\' ";
                }
            }
        }

        if (user != null) {
            if (!("").equals(user)) {
                count++;
                if (count == 1) {
                    query = query + " WHERE user = \'" + user + "\' ";
                } else if (count > 1) {
                    query = query + " AND user = \'" + user + "\' ";
                }
            }
        }
        if (site != null) {
            if (!("").equals(site)) {
                count++;
                if (count == 1) {
                    query = query + " WHERE site = \'" + site + "\' ";
                } else if (count > 1) {
                    query = query + " AND site = \'" + site + "\' ";
                }
            }
        }

        if (pic != null) {
            if (!("").equals(pic)) {
                count++;
                if (count == 1) {
                    query = query + " WHERE assignee = \'" + pic + "\' ";
                } else if (count > 1) {
                    query = query + " AND assignee = \'" + pic + "\' ";
                }
            }
        }

        if (eta != null) {
            if (!eta.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE eta LIKE '" + eta + "%'";
                } else if (count > 1) {
                    query = query + " AND eta LIKE '" + eta + "%'";
                }
            }
        }

        if (requestDate != null) {
            if (!requestDate.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE created_date LIKE '" + requestDate + "%'";
                } else if (count > 1) {
                    query = query + " AND created_date LIKE '" + requestDate + "%'";
                }
            }
        }
        if (status != null) {
            if (!status.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE status LIKE '" + status + "%'";
                } else if (count > 1) {
                    query = query + " AND status LIKE '" + status + "%'";
                }
            }
        }

        String finalQuery = "";

        if ("Incident".equals(type)) {

            finalQuery = "SELECT *, SUBSTRING(ticket_no,1,1) AS ticket, DATE_FORMAT(created_date,'%d-%M-%Y %h:%i %p') AS createdDate FROM rats_incident " + query;

        } else if ("Request".equals(type)) {

            finalQuery = "SELECT *, SUBSTRING(ticket_no,1,1) AS ticket, DATE_FORMAT(created_date,'%d-%M-%Y %h:%i %p') AS createdDate FROM rats_new_request " + query;

        } else if ("Incident & Request".equals(type)) {

            finalQuery = "SELECT *, SUBSTRING(ticket_no,1,1) AS ticket, DATE_FORMAT(created_date,'%d-%M-%Y %h:%i %p') AS createdDate "
                    + "FROM rats_incident " + query + " UNION ALL "
                    + "SELECT *, SUBSTRING(ticket_no,1,1) AS ticket,DATE_FORMAT(created_date,'%d-%M-%Y %h:%i %p') AS createdDate "
                    + "FROM rats_new_request " + query + " ORDER BY created_date DESC";
        } else {
            finalQuery = "SELECT * FROM rats_incident WHERE flag = '5'";
        }
//
//        if (count > 0) {
//            query = "WHERE" + query + " ORDER BY re.request_id";
//        } else {
//            query = "WHERE re.flag = '5'";
//        }
//        System.out.println("Query: " + query);
        System.out.println("finalQuery: " + finalQuery);

        DashboardDAO dashD = new DashboardDAO();
        List<NewRequest> queryList = dashD.getQuery(finalQuery);
        model.addAttribute("queryList", queryList);
        
        dashD = new DashboardDAO();
        List<NewRequest> userList = dashD.getUserForQuery();
        model.addAttribute("userList", userList);
        
        dashD = new DashboardDAO();
        List<NewRequest> siteList = dashD.getSiteForQuery();
        model.addAttribute("siteList", siteList);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> categoryList = sDAO.getGroupParameterDetailList("", "028");
        model.addAttribute("categoryList", categoryList);
        
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> subCategoryList = sDAO.getGroupParameterDetailList("", "029");
        model.addAttribute("subCategoryList", subCategoryList);
        
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> urgencyList = sDAO.getGroupParameterDetailList("", "030");
        model.addAttribute("urgencyList", urgencyList);
        
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> typeList = sDAO.getGroupParameterDetailList("", "036");
        model.addAttribute("typeList", typeList);
        
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> picList = sDAO.getGroupParameterDetailList("", "031");
        model.addAttribute("picList", picList);

        return "admin/query";
    }

}
