package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.DispoEmailListDAO;
import com.onsemi.dots.dao.LDAPUserDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import com.onsemi.dots.model.DispoEmailList;
import com.onsemi.dots.model.LDAPUser;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.UserSession;
import com.onsemi.dots.tools.QueryResult;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/dispoEmailList")
@SessionAttributes({"userSession"})
public class DispoEmailListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispoEmailListController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String dispoEmailList(
            Model model, @ModelAttribute UserSession userSession
    ) {
        DispoEmailListDAO dispoEmailListDAO = new DispoEmailListDAO();
        List<DispoEmailList> dispoEmailListList = dispoEmailListDAO.getDispoEmailListList();
        String groupId = userSession.getGroup();
        model.addAttribute("emailConfigList", dispoEmailListList);
        model.addAttribute("groupId", groupId);
        return "dispoEmailList/dispoEmailList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "023");

        LDAPUserDAO ldapDao = new LDAPUserDAO();
        List<LDAPUser> user = ldapDao.list();
        model.addAttribute("user", user);
        model.addAttribute("emailTo", emailTo);

        return "dispoEmailList/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String distList,
            @RequestParam(required = false) String oncid,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        DispoEmailList dispoEmailList = new DispoEmailList();
        dispoEmailList.setDistList(distList);
        dispoEmailList.setOncid(oncid);

        LDAPUserDAO ldap = new LDAPUserDAO();
        LDAPUser lu = ldap.getByOncid(oncid);

        dispoEmailList.setName(lu.getFirstname() + " " + lu.getLastname());
        dispoEmailList.setEmail(email);
        dispoEmailList.setRemarks(remarks);
        dispoEmailList.setFlag("0");
        DispoEmailListDAO dispoEmailListDAO = new DispoEmailListDAO();
        if (dispoEmailListDAO.getCountTask(distList) != 0) {
            model.addAttribute("error", lu.getFirstname() + " " + lu.getLastname() + " already been assigned.");
            model.addAttribute("emailConfig", dispoEmailList);
            ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
            List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "014");

            LDAPUserDAO ldapDao = new LDAPUserDAO();
            List<LDAPUser> user = ldapDao.list();
            model.addAttribute("emailTo", emailTo);
            model.addAttribute("user", user);
            return "dispoEmailList/add";

        } else {
            dispoEmailListDAO = new DispoEmailListDAO();
            QueryResult queryResult = dispoEmailListDAO.insertDispoEmailList(dispoEmailList);
            args = new String[1];
            args[0] = distList + " - " + oncid;
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                model.addAttribute("emailConfig", dispoEmailList);
                ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
                List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList("", "023");

                LDAPUserDAO ldapDao = new LDAPUserDAO();
                List<LDAPUser> user = ldapDao.list();
                model.addAttribute("emailTo", emailTo);
                model.addAttribute("user", user);
                return "dispoEmailList/add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                return "redirect:/admin/dispoEmailList/edit/" + queryResult.getGeneratedKey();
            }
        }
    }

    @RequestMapping(value = "/edit/{dispoEmailListId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("dispoEmailListId") String dispoEmailListId
    ) {
        DispoEmailListDAO dispoEmailListDAO = new DispoEmailListDAO();
        DispoEmailList dispoEmailList = dispoEmailListDAO.getDispoEmailList(dispoEmailListId);
        model.addAttribute("emailConfig", dispoEmailList);

        LDAPUserDAO ldap = new LDAPUserDAO();
        List<LDAPUser> user = ldap.list(dispoEmailList.getOncid());

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> emailTo = sDAO.getGroupParameterDetailList(dispoEmailList.getDistList(), "023");
        model.addAttribute("user", user);
        model.addAttribute("emailTo", emailTo);
        return "dispoEmailList/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String distList,
            @RequestParam(required = false) String oncid,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        DispoEmailList dispoEmailList = new DispoEmailList();
        dispoEmailList.setId(id);
        dispoEmailList.setDistList(distList);
        dispoEmailList.setOncid(oncid);
        
        LDAPUserDAO ldap = new LDAPUserDAO();
        LDAPUser lu = ldap.getByOncid(oncid);
        
        dispoEmailList.setName(lu.getFirstname() + " " + lu.getLastname());
        dispoEmailList.setEmail(email);
        dispoEmailList.setRemarks(remarks);
        dispoEmailList.setFlag("0");
        DispoEmailListDAO dispoEmailListDAO = new DispoEmailListDAO();
        QueryResult queryResult = dispoEmailListDAO.updateDispoEmailList(dispoEmailList);
        args = new String[1];
        args[0] = distList + " - " + lu.getFirstname() + " " + lu.getLastname();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/admin/dispoEmailList/edit/" + id;
    }

    @RequestMapping(value = "/delete/{dispoEmailListId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("dispoEmailListId") String dispoEmailListId
    ) {
        DispoEmailListDAO dispoEmailListDAO = new DispoEmailListDAO();
        DispoEmailList dispoEmailList = dispoEmailListDAO.getDispoEmailList(dispoEmailListId);
        dispoEmailListDAO = new DispoEmailListDAO();
        QueryResult queryResult = dispoEmailListDAO.deleteDispoEmailList(dispoEmailListId);
        args = new String[1];
        args[0] = dispoEmailList.getDistList() + " - " + dispoEmailList.getOncid();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/dispoEmailList";
    }

    @RequestMapping(value = "/view/{dispoEmailListId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("dispoEmailListId") String dispoEmailListId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/admin/dispoEmailList/viewDispoEmailListPdf/" + dispoEmailListId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/dispoEmailList";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.dispoEmailList");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewDispoEmailListPdf/{dispoEmailListId}", method = RequestMethod.GET)
    public ModelAndView viewDispoEmailListPdf(
            Model model,
            @PathVariable("dispoEmailListId") String dispoEmailListId
    ) {
        DispoEmailListDAO dispoEmailListDAO = new DispoEmailListDAO();
        DispoEmailList dispoEmailList = dispoEmailListDAO.getDispoEmailList(dispoEmailListId);
        return new ModelAndView("dispoEmailListPdf", "dispoEmailList", dispoEmailList);
    }
}
