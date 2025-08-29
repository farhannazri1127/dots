package com.onsemi.rats.controller;

import com.onsemi.rats.dao.EmailCcDAO;
import com.onsemi.rats.dao.IncidentAttachmentDAO;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.rats.dao.IncidentDAO;
import com.onsemi.rats.dao.IncidentLogDAO;
import com.onsemi.rats.dao.ParameterDetailsDAO;
import com.onsemi.rats.model.EmailCc;
import com.onsemi.rats.model.Incident;
import com.onsemi.rats.model.IncidentAttachment;
import com.onsemi.rats.model.IncidentLog;
import com.onsemi.rats.model.LDAPUser;
import com.onsemi.rats.model.LDAPUserEmailList;
import com.onsemi.rats.model.ParameterDetails;
import com.onsemi.rats.model.UserSession;
import com.onsemi.rats.tools.EmailSender;
import com.onsemi.rats.tools.QueryResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/incident")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class IncidentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentController.class);
    String[] args = {};

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\GRATS-Attachment\\Incident\\"; //server 

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @Autowired
    private Environment env;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String incident(
            Model model
    ) {
        IncidentDAO incidentDAO = new IncidentDAO();
        List<Incident> incidentList = incidentDAO.getIncidentList();
        model.addAttribute("incidentList", incidentList);
        return "incident/incident";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute UserSession userSession) {

        String location = userSession.getSite();

        if (location.contains("MYSE")) {
            location = "MYSE%";
        }

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList("", "028");
        model.addAttribute("category", category);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> subCategory = sDAO.getGroupParameterDetailList("", "029");
        model.addAttribute("subCategory", subCategory);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> urgency = sDAO.getGroupParameterDetailList("", "030");
        model.addAttribute("urgency", urgency);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> test = sDAO.getGroupParameterDetailList("", "030");
        model.addAttribute("test", test);
        EmailCcDAO email = new EmailCcDAO();
        List<EmailCc> listCc = email.getEmailCcListBySite(location);
//        List<EmailCc> listCc = email.getEmailCcList();
        model.addAttribute("listCc", listCc);

//        add email list into db
//        List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();
//
//        Hashtable h = new Hashtable();
//        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));
//
//        DirContext ctx = null;
//        NamingEnumeration results = null;
//
//        try {
//            ctx = new InitialDirContext(h);
//            SearchControls controls = new SearchControls();
//            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            String[] attrIDs = {"givenname", "sn", "cn", "mail", "onoraclelocation"};
////            String[] attrIDs = {"mail"};
//            controls.setReturningAttributes(attrIDs);
//            //Local
////                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
//            //Onsemi
////                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
////            results = ctx.search("ou=ONSemi", "(onoraclelocation=MYSE02)", controls);
//            results = ctx.search("ou=ONSemi", "(&(mail=*)(onemployeestatus=A)(onoraclelocation=*)(onoraclelocation=PHCA01))", controls);
//
//            while (results.hasMore()) {
//                SearchResult searchResult = (SearchResult) results.next();
//                Attributes attributes = searchResult.getAttributes();
//
//                LDAPUser user = new LDAPUser();
//
//                Enumeration e = attributes.getIDs();
//                while (e.hasMoreElements()) {
//                    String key = (String) e.nextElement();
//                    if (key.equalsIgnoreCase("givenName")) {
//                        user.setFirstname(attributes.get(key).get().toString());
//                    }
//                    if (key.equalsIgnoreCase("sn")) {
//                        user.setLastname(attributes.get(key).get().toString());
//                    }
//                    if (key.equalsIgnoreCase("cn")) {
//                        user.setLoginId(attributes.get(key).get().toString());
//                    }
//                    if (key.equalsIgnoreCase("mail")) {
//                        user.setEmail(attributes.get(key).get().toString());
//                    }
//                    if (key.equalsIgnoreCase("onoraclelocation")) {
//                        user.setLocation(attributes.get(key).get().toString());
//                    }
//                }
//
//                ldapUserList.add(user);
//
//            }
//            for (int i = 0; i < ldapUserList.size(); i++) {
//
//                EmailCc emailcc = new EmailCc();
//                emailcc.setCid(ldapUserList.get(i).getLoginId());
//                emailcc.setName(ldapUserList.get(i).getFirstname() + " " + ldapUserList.get(i).getLastname());
//                emailcc.setLocation(ldapUserList.get(i).getLocation());
//                emailcc.setEmail(ldapUserList.get(i).getEmail());
//                emailcc.setFlag("0");
//
//                EmailCcDAO emD = new EmailCcDAO();
//                QueryResult emaD = emD.insertEmailCc(emailcc);
//            }
//        } catch (NamingException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (results != null) {
//                try {
//                    results.close();
//                } catch (Exception e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//            if (ctx != null) {
//                try {
//                    ctx.close();
//                } catch (Exception e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        model.addAttribute("ldapUserList", ldapUserList);
//        LOGGER.info("ldapUserList" + ldapUserList);
        return "incident/add";
    }

    @RequestMapping(value = "/loadUser", method = RequestMethod.GET)
    @ResponseBody
    public List<LDAPUser> loadUser(Model model
    ) {

        //get ldap detail for cc
        List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();

        Hashtable h = new Hashtable();
        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

        DirContext ctx = null;
        NamingEnumeration results = null;

        try {
            ctx = new InitialDirContext(h);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"givenname", "sn", "cn", "mail", "onoraclelocation"};
            controls.setReturningAttributes(attrIDs);
            //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
            //Onsemi
//                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
//            results = ctx.search("ou=ONSemi", "(onoraclelocation=PHCE01)", controls);
//            results = ctx.search("ou=ONSemi", "(&(mail=*)(onemployeestatus=A)(onoraclelocation=*))", controls);
            results = ctx.search("ou=ONSemi", "(&(mail=*)(onemployeestatus=A)(onoraclelocation=*)(onoraclelocation=MYSE02))", controls);

            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();

                LDAPUser user = new LDAPUser();

                Enumeration e = attributes.getIDs();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    if (key.equalsIgnoreCase("givenName")) {
                        user.setFirstname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("sn")) {
                        user.setLastname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("cn")) {
                        user.setLoginId(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("mail")) {
                        user.setEmail(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("onoraclelocation")) {
                        user.setLocation(attributes.get(key).get().toString());
                    }
//                            System.out.println("onoraclelocation: " + attributes.get(key).get().toString());
//                        LOGGER.info("onoraclelocation: " + attributes.get(key).get().toString());
                }

                ldapUserList.add(user);
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
//        model.addAttribute("ldapUserList", ldapUserList);
//        LOGGER.info("ldapUserList" + ldapUserList);

//        return model.addAttribute("ldapUserList", ldapUserList);
        return ldapUserList;

    }

    @RequestMapping(value = "/loadUser2", method = RequestMethod.GET)
    @ResponseBody
    public List<EmailCc> loadUser2(Model model
    ) {

        //get ldap detail for cc
        EmailCcDAO email = new EmailCcDAO();
        List<EmailCc> listCc = email.getEmailCcList();
//        model.addAttribute("listCc", listCc);
//        List<LDAPUserEmailList> ldapUserList = new ArrayList<LDAPUserEmailList>();
//
//        Hashtable h = new Hashtable();
//        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));
//
//        DirContext ctx = null;
//        NamingEnumeration results = null;
//
//        try {
//            ctx = new InitialDirContext(h);
//            SearchControls controls = new SearchControls();
//            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            String[] attrIDs = {"mail"};
//            controls.setReturningAttributes(attrIDs);
//            //Local
////                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
//            //Onsemi
////                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
////            results = ctx.search("ou=ONSemi", "(onoraclelocation=PHCE01)", controls);
////            results = ctx.search("ou=ONSemi", "(&(mail=*)(onemployeestatus=A)(onoraclelocation=*))", controls);
//            results = ctx.search("ou=ONSemi", "(&(mail=*)(onemployeestatus=A)(onoraclelocation=*)(onoraclelocation=MYSE02))", controls);
//
//            while (results.hasMore()) {
//                SearchResult searchResult = (SearchResult) results.next();
//                Attributes attributes = searchResult.getAttributes();
//
//                LDAPUserEmailList user = new LDAPUserEmailList();
//
//                Enumeration e = attributes.getIDs();
//                while (e.hasMoreElements()) {
//                    String key = (String) e.nextElement();
//                    if (key.equalsIgnoreCase("mail")) {
//                        user.setEmail(attributes.get(key).get().toString());
//                    }
////                            System.out.println("onoraclelocation: " + attributes.get(key).get().toString());
////                        LOGGER.info("onoraclelocation: " + attributes.get(key).get().toString());
//                }
//
//                ldapUserList.add(user);
//            }
//        } catch (NamingException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (results != null) {
//                try {
//                    results.close();
//                } catch (Exception e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//            if (ctx != null) {
//                try {
//                    ctx.close();
//                } catch (Exception e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        model.addAttribute("ldapUserList", ldapUserList);
//        LOGGER.info("ldapUserList" + ldapUserList);

//        return model.addAttribute("ldapUserList", ldapUserList);
        return listCc;

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subCategory,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String urgency,
            @RequestParam(required = false) String emailCc,
            @RequestParam(required = false) MultipartFile formFile
    ) throws IOException {
        String stringPath = "";
        String attachment = "No"; //for email notification

        Incident inc = new Incident();
        inc.setCategory(category);
        inc.setSubCategory(subCategory);
        inc.setTitle(title);
        inc.setDescription(description.replaceAll("\\R", " ")); //remove line break

        inc.setUrgency(urgency);
        inc.setStatus("New Incident");
        inc.setFlag("0");
        inc.setUser(userSession.getFullname());
        inc.setSite(userSession.getSite());
        inc.setUserEmail(userSession.getEmail());
        inc.setUserId(userSession.getLoginId());
        inc.setCc(emailCc);
        IncidentDAO incD = new IncidentDAO();
        QueryResult query = incD.insertIncident(inc);

        if (!"0".equals(query.getGeneratedKey())) {

            //create incidentNo
            DateFormat df = new SimpleDateFormat("yyMMdd");
            Date dateBox = new Date();
            String nowDate = df.format(dateBox);

            String index = query.getGeneratedKey();
            switch (index.length()) {
                case 1:
                    index = "00000" + index;
                    break;
                case 2:
                    index = "0000" + index;
                    break;
                case 3:
                    index = "000" + index;
                    break;
                case 4:
                    index = "00" + index;
                    break;
                case 5:
                    index = "0" + index;
                    break;
                default:
                    break;
            }
            String incidentNo = "I" + nowDate + index;
            inc = new Incident();
            inc.setTicketNo(incidentNo);
            inc.setId(query.getGeneratedKey());
            incD = new IncidentDAO();
            QueryResult query1 = incD.updateIncidentNo(inc);

            //add log
            IncidentLog incL = new IncidentLog();
            incL.setIncidentId(query.getGeneratedKey());
            incL.setStatus("New Ticket");
            incL.setDescription("Ticket " + incidentNo + " was Created by " + userSession.getFullname());
            incL.setCreatedBy(userSession.getFullname());
            IncidentLogDAO incDL = new IncidentLogDAO();
            QueryResult logQ = incDL.insertIncidentLog(incL);

            //check if user upload any attachment
            if (!formFile.isEmpty()) {
                try {

                    // Get the file and save it somewhere
                    byte[] bytes = formFile.getBytes();
                    Path path = Paths.get(UPLOADED_FOLDER + query.getGeneratedKey() + "_" + formFile.getOriginalFilename());
                    Files.write(path, bytes);
                    stringPath = path.toString();
                    LOGGER.info("path : " + path);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                IncidentAttachment incA = new IncidentAttachment();
                incA.setFilename(formFile.getOriginalFilename());
                incA.setAttachment(stringPath);
                incA.setIncidentId(query.getGeneratedKey());
                incA.setCreatedBy(userSession.getFullname());
                IncidentAttachmentDAO incAD = new IncidentAttachmentDAO();
                QueryResult incAQ = incAD.insertIncidentAttachment(incA);

                attachment = "Yes";

            }

            //send email
            LOGGER.info("send email to person in charge");
            EmailSender emailSender = new EmailSender();

            incD = new IncidentDAO();
            Incident incident = incD.getIncident(query.getGeneratedKey());

            List<String> emails = new ArrayList<String>();
            emails.add("Global-Rel-IT@onsemi.com");
            if (incident.getUserEmail() != null) {
                emails.add(incident.getUserEmail());
            }
            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            if (incident.getCc() != null) { //send email with CC

                String[] ccs = incident.getCc().split(",");

                emailSender.htmlEmailManyToNewWithCc(
                        servletContext,
                        //                    user, //user name requestor
                        emailTo,
                        "GRATS - Incident Ticket #" + incidentNo + " [" + incident.getStatus() + "]", //subject
                        "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/incident/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //server
                        //                        "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/incident/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //local
                        + "<pre>"
                        + "<br />"
                        + "<b>Title        : </b>" + incident.getTitle() + "<br />"
                        //                    + "<br />"
                        + "<b>Requestor    : </b>" + userSession.getFullname() + "  <br />"
                        //                    + "<br />"
                        + "<b>Site         : </b>" + userSession.getSite() + " <br />"
                        //                    + "<br />"
                        + "<b>Request Date : </b>" + incident.getCreatedDate() + "<br />"
                        //                    + "<br />"
                        + "<b>Category     : </b>" + incident.getCategory() + "<br />"
                        //                    + "<br />"
                        + "<b>Sub-Category : </b>" + incident.getSubCategory() + "<br />"
                        //                    + "<br />"
                        + "<b>Urgency      : </b>" + incident.getUrgency() + "<br />"
                        //                    + "<br />"
                        + "<b>Attachment   : </b>" + attachment + "<br />"
                        //                    + "<br />"
                        + "<b>Description  : </b>" + incident.getDescription() + "<br />"
                        //                    + "<br />"
                        //                    + "<hr class=\"double\">"
                        + "<br />Thank you."//msg
                        + "</pre>"
                        + "<br />"
                        + "<br />"
                        + "Please <b>do not reply</b> to this message. Replies to this message are routed to an unmonitored mailbox",
                        ccs
                );

            } else { //send email without cc

                emailSender.htmlEmailManyToNew(
                        servletContext,
                        //                    user, //user name requestor
                        emailTo,
                        "GRATS - Incident Ticket #" + incidentNo + " [" + incident.getStatus() + "]", //subject
                        "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/incident/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //server
                        //                        "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/incident/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //local
                        + "<pre>"
                        + "<br />"
                        + "<b>Title        : </b>" + incident.getTitle() + "<br />"
                        //                    + "<br />"
                        + "<b>Requestor    : </b>" + userSession.getFullname() + "  <br />"
                        //                    + "<br />"
                        + "<b>Site         : </b>" + userSession.getSite() + " <br />"
                        //                    + "<br />"
                        + "<b>Request Date : </b>" + incident.getCreatedDate() + "<br />"
                        //                    + "<br />"
                        + "<b>Category     : </b>" + incident.getCategory() + "<br />"
                        //                    + "<br />"
                        + "<b>Sub-Category : </b>" + incident.getSubCategory() + "<br />"
                        //                    + "<br />"
                        + "<b>Urgency      : </b>" + incident.getUrgency() + "<br />"
                        //                    + "<br />"
                        + "<b>Attachment   : </b>" + attachment + "<br />"
                        //                    + "<br />"
                        + "<b>Description  : </b>" + incident.getDescription() + "<br />"
                        //                    + "<br />"
                        //                    + "<hr class=\"double\">"
                        + "<br />Thank you."//msg
                        + "</pre>"
                        + "<br />"
                        + "<br />"
                        + "Please <b>do not reply</b> to this message. Replies to this message are routed to an unmonitored mailbox"
                );
            }

//        String[] emailTo = {"fg79cj@onsemi.com"};
            redirectAttrs.addFlashAttribute("success", "Ticket : " + incidentNo + " is succesfully created.");
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }

        return "redirect:/";

    }

    @RequestMapping(value = "/edit/{incidentId}", method = RequestMethod.GET)
    public String edit(Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("incidentId") String incidentId
    ) {

        String GroupID = userSession.getGroup();
        model.addAttribute("GroupID", GroupID);

        IncidentDAO incD = new IncidentDAO();
        Incident incident = incD.getIncident(incidentId);
        model.addAttribute("incident", incident);
//        LOGGER.info("user : " + incident.getUser());

        IncidentLogDAO incL = new IncidentLogDAO();
        List<IncidentLog> log = incL.getIncidentLogListByIncidentId(incidentId);
        model.addAttribute("log", log);

        IncidentAttachmentDAO incA = new IncidentAttachmentDAO();
        List<  IncidentAttachment> attachment = incA.getIncidentAttachmentListByIncidentId(incidentId);
        model.addAttribute("attachment", attachment);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList(incident.getCategory(), "028");
        model.addAttribute("category", category);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> subCategory = sDAO.getGroupParameterDetailList(incident.getSubCategory(), "029");
        model.addAttribute("subCategory", subCategory);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> urgency = sDAO.getGroupParameterDetailList(incident.getUrgency(), "030");
        model.addAttribute("urgency", urgency);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pic = sDAO.getGroupParameterDetailList(incident.getAssignee(), "031");
        model.addAttribute("pic", pic);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> status = sDAO.getGroupParameterDetailList(incident.getStatus(), "032");
        model.addAttribute("status", status);
//        LOGGER.info("pic: " + pic);
        return "incident/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String note,
            @RequestParam(required = false) String attachment,
            @RequestParam(required = false) String pic,
            @RequestParam(required = false) String eta,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String incidentNo,
            @RequestParam(required = false) MultipartFile formFile,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String flag
    ) throws IOException {
        String stringPath = "";
        String att = "No";

        if ("1".equals(groupId)) { //only admin can update info

            Incident incident = new Incident();
            incident.setId(id);
            incident.setAssignee(pic);
            incident.setStatus(status);
            if ("".equals(eta)) {
                incident.setEta(null);
            } else {
                incident.setEta(eta);
            }
            if ("Closed".equals(status) || "Cancelled".equals(status)) {
                incident.setFlag("1");
            } else {
                incident.setFlag("0");
            }
            IncidentDAO incidentDAO = new IncidentDAO();
            QueryResult queryResult = incidentDAO.updateIncidentLatest(incident);

            if (queryResult.getResult() == 1) {

                //add log
                IncidentLog incL = new IncidentLog();
                incL.setIncidentId(id);
                incL.setStatus("Ticket Update");
                incL.setDescription("Ticket has been updated by " + userSession.getFullname() + ". PIC: " + pic + " ; ETA: " + eta + " ; Status: " + status);
                incL.setCreatedBy(userSession.getFullname());
                IncidentLogDAO incDL = new IncidentLogDAO();
                QueryResult logQ = incDL.insertIncidentLog(incL);

                //add note
                incL = new IncidentLog();
                incL.setIncidentId(id);
                incL.setStatus("Note from: " + userSession.getFullname());
                incL.setDescription(note.replaceAll("\\R", " ")); //remove line break);
                incL.setCreatedBy(userSession.getFullname());
                incDL = new IncidentLogDAO();
                QueryResult logQ2 = incDL.insertIncidentLog(incL);

                //check if user upload any attachment
                if (!formFile.isEmpty()) {
                    try {

                        // Get the file and save it somewhere
                        byte[] bytes = formFile.getBytes();
                        Path path = Paths.get(UPLOADED_FOLDER + id + "_" + formFile.getOriginalFilename());
                        Files.write(path, bytes);
                        stringPath = path.toString();
                        LOGGER.info("path : " + path);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    IncidentAttachment incA = new IncidentAttachment();
                    incA.setFilename(formFile.getOriginalFilename());
                    incA.setAttachment(stringPath);
                    incA.setIncidentId(id);
                    incA.setCreatedBy(userSession.getFullname());
                    IncidentAttachmentDAO incAD = new IncidentAttachmentDAO();
                    QueryResult incAQ = incAD.insertIncidentAttachment(incA);

                    incL = new IncidentLog();
                    incL.setIncidentId(id);
                    incL.setStatus("Ticket Update");
                    incL.setDescription("Attachment uploaded");
                    incL.setCreatedBy(userSession.getFullname());
                    incDL = new IncidentLogDAO();
                    QueryResult logQ3 = incDL.insertIncidentLog(incL);

                    att = "Yes";
                }

                //send email
                LOGGER.info("send email to person in charge");
                EmailSender emailSender = new EmailSender();

                IncidentDAO incD = new IncidentDAO();
                Incident incident2 = incD.getIncident(id);

                List<String> emails = new ArrayList<String>();
                emails.add("Global-Rel-IT@onsemi.com");
                if (incident2.getUserEmail() != null) {
                    emails.add(incident2.getUserEmail());
                }
                String[] myArray = new String[emails.size()];
                String[] emailTo = emails.toArray(myArray);

                if (incident2.getCc() != null) { //send email with cc

                    String[] ccs = incident2.getCc().split(",");

                    emailSender.htmlEmailManyToNewWithCc(
                            servletContext,
                            //                    user, //user name requestor
                            emailTo,
                            "GRATS - Incident Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //local
                            + "<pre>"
                            + "<br />"
                            + "<b>Title        : </b>" + incident2.getTitle() + "<br />"
                            + "<b>Requestor    : </b>" + incident2.getUser() + "  <br />"
                            + "<b>Site         : </b>" + incident2.getSite() + " <br />"
                            + "<b>Request Date : </b>" + incident2.getCreatedDate() + "<br />"
                            + "<b>Category     : </b>" + incident2.getCategory() + "<br />"
                            + "<b>Sub-Category : </b>" + incident2.getSubCategory() + "<br />"
                            + "<b>Urgency      : </b>" + incident2.getUrgency() + "<br />"
                            //                        + "<b>Attachment   : </b>" + att + "<br />"
                            + "<b>Description  : </b>" + incident2.getDescription() + "<br />"
                            + "<b>PIC          : </b>" + incident2.getAssignee() + "<br />"
                            + "<b>ETA          : </b>" + incident2.getEta() + "<br />"
                            + "<b>Status       : </b>" + incident2.getStatus() + "<br />"
                            + "<br />"
                            + "<hr class=\"double\"><br />"
                            + "<b>NOTE</b><br />"
                            + "<br />"
                            + "<b>From         : </b>" + userSession.getFullname() + "<br />"
                            + "<b>Note         : </b>" + note.replaceAll("\\R", " ") + "<br />"
                            + "<b>Attachment   : </b>" + att + "<br />"
                            + "<br />Thank you."//msg
                            + "</pre>"
                            + "<br />"
                            + "<br />"
                            + "Please <b>do not reply</b> to this message. Replies to this message are routed to an unmonitored mailbox",
                            ccs
                    );

                } else { //send email without cc

                    emailSender.htmlEmailManyToNew(
                            servletContext,
                            //                    user, //user name requestor
                            emailTo,
                            "GRATS - Incident Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //local
                            + "<pre>"
                            + "<br />"
                            + "<b>Title        : </b>" + incident2.getTitle() + "<br />"
                            + "<b>Requestor    : </b>" + incident2.getUser() + "  <br />"
                            + "<b>Site         : </b>" + incident2.getSite() + " <br />"
                            + "<b>Request Date : </b>" + incident2.getCreatedDate() + "<br />"
                            + "<b>Category     : </b>" + incident2.getCategory() + "<br />"
                            + "<b>Sub-Category : </b>" + incident2.getSubCategory() + "<br />"
                            + "<b>Urgency      : </b>" + incident2.getUrgency() + "<br />"
                            //                        + "<b>Attachment   : </b>" + att + "<br />"
                            + "<b>Description  : </b>" + incident2.getDescription() + "<br />"
                            + "<b>PIC          : </b>" + incident2.getAssignee() + "<br />"
                            + "<b>ETA          : </b>" + incident2.getEta() + "<br />"
                            + "<b>Status       : </b>" + incident2.getStatus() + "<br />"
                            + "<br />"
                            + "<hr class=\"double\"><br />"
                            + "<b>NOTE</b><br />"
                            + "<br />"
                            + "<b>From         : </b>" + userSession.getFullname() + "<br />"
                            + "<b>Note         : </b>" + note.replaceAll("\\R", " ") + "<br />"
                            + "<b>Attachment   : </b>" + att + "<br />"
                            + "<br />Thank you."//msg
                            + "</pre>"
                            + "<br />"
                            + "<br />"
                            + "Please <b>do not reply</b> to this message. Replies to this message are routed to an unmonitored mailbox"
                    );

                }

                redirectAttrs.addFlashAttribute("success", "Ticket : " + incidentNo + " has been updated.");
            } else {
                redirectAttrs.addFlashAttribute("error", "Ticket : " + incidentNo + " was failed to update.");
            }

        } else { //if not admin, only can update note and attachment

            //add note
            IncidentLog incLog = new IncidentLog();
            incLog.setIncidentId(id);
            incLog.setStatus("Note from: " + userSession.getFullname());
            incLog.setDescription(note.replaceAll("\\R", " ")); //remove line break);
            incLog.setCreatedBy(userSession.getFullname());
            IncidentLogDAO incDLog = new IncidentLogDAO();
            QueryResult logQ2 = incDLog.insertIncidentLog(incLog);

            if (!"0".equals(logQ2.getGeneratedKey())) {

                if (!formFile.isEmpty()) {
                    try {

                        // Get the file and save it somewhere
                        byte[] bytes = formFile.getBytes();
                        Path path = Paths.get(UPLOADED_FOLDER + id + "_" + formFile.getOriginalFilename());
                        Files.write(path, bytes);
                        stringPath = path.toString();
                        LOGGER.info("path : " + path);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    IncidentAttachment incA = new IncidentAttachment();
                    incA.setFilename(formFile.getOriginalFilename());
                    incA.setAttachment(stringPath);
                    incA.setIncidentId(id);
                    incA.setCreatedBy(userSession.getFullname());
                    IncidentAttachmentDAO incAD = new IncidentAttachmentDAO();
                    QueryResult incAQ = incAD.insertIncidentAttachment(incA);

                    IncidentLog incLA = new IncidentLog();
                    incLA.setIncidentId(id);
                    incLA.setStatus("Ticket Update");
                    incLA.setDescription("Attachment uploaded");
                    incLA.setCreatedBy(userSession.getFullname());
                    IncidentLogDAO incDLA = new IncidentLogDAO();
                    QueryResult logQ3 = incDLA.insertIncidentLog(incLA);

                    att = "Yes";
                }

                //send email
                LOGGER.info("send email to person in charge");
                EmailSender emailSender = new EmailSender();

                IncidentDAO incD = new IncidentDAO();
                Incident incident2 = incD.getIncident(id);

                List<String> emails = new ArrayList<String>();
                emails.add("Global-Rel-IT@onsemi.com");
                if (incident2.getUserEmail() != null) {
                    emails.add(incident2.getUserEmail());
                }
                String[] myArray = new String[emails.size()];
                String[] emailTo = emails.toArray(myArray);

                if (incident2.getCc() != null) {  //send email with cc

                    String[] ccs = incident2.getCc().split(",");

                    emailSender.htmlEmailManyToNewWithCc(
                            servletContext,
                            //                    user, //user name requestor
                            emailTo,
                            "GRATS - Incident Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //local
                            + "<pre>"
                            + "<br />"
                            + "<b>Title        : </b>" + incident2.getTitle() + "<br />"
                            + "<b>Requestor    : </b>" + incident2.getUser() + "  <br />"
                            + "<b>Site         : </b>" + incident2.getSite() + " <br />"
                            + "<b>Request Date : </b>" + incident2.getCreatedDate() + "<br />"
                            + "<b>Category     : </b>" + incident2.getCategory() + "<br />"
                            + "<b>Sub-Category : </b>" + incident2.getSubCategory() + "<br />"
                            + "<b>Urgency      : </b>" + incident2.getUrgency() + "<br />"
                            //                        + "<b>Attachment   : </b>" + att + "<br />"
                            + "<b>Description  : </b>" + incident2.getDescription() + "<br />"
                            + "<b>PIC          : </b>" + incident2.getAssignee() + "<br />"
                            + "<b>ETA          : </b>" + incident2.getEta() + "<br />"
                            + "<b>Status       : </b>" + incident2.getStatus() + "<br />"
                            + "<br />"
                            + "<hr class=\"double\"><br />"
                            + "<b>NOTE</b><br />"
                            + "<br />"
                            + "<b>From         : </b>" + userSession.getFullname() + "<br />"
                            + "<b>Note         : </b>" + note.replaceAll("\\R", " ") + "<br />"
                            + "<b>Attachment   : </b>" + att + "<br />"
                            + "<br />Thank you."//msg
                            + "</pre>"
                            + "<br />"
                            + "<br />"
                            + "Please <b>do not reply</b> to this message. Replies to this message are routed to an unmonitored mailbox",
                            ccs
                    );

                } else {

                    emailSender.htmlEmailManyToNew(
                            servletContext,
                            //                    user, //user name requestor
                            emailTo,
                            "GRATS - Incident Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/incident/edit/" + id + " \">LINK</a> for more details. <br />" //local
                            + "<pre>"
                            + "<br />"
                            + "<b>Title        : </b>" + incident2.getTitle() + "<br />"
                            + "<b>Requestor    : </b>" + incident2.getUser() + "  <br />"
                            + "<b>Site         : </b>" + incident2.getSite() + " <br />"
                            + "<b>Request Date : </b>" + incident2.getCreatedDate() + "<br />"
                            + "<b>Category     : </b>" + incident2.getCategory() + "<br />"
                            + "<b>Sub-Category : </b>" + incident2.getSubCategory() + "<br />"
                            + "<b>Urgency      : </b>" + incident2.getUrgency() + "<br />"
                            //                        + "<b>Attachment   : </b>" + att + "<br />"
                            + "<b>Description  : </b>" + incident2.getDescription() + "<br />"
                            + "<b>PIC          : </b>" + incident2.getAssignee() + "<br />"
                            + "<b>ETA          : </b>" + incident2.getEta() + "<br />"
                            + "<b>Status       : </b>" + incident2.getStatus() + "<br />"
                            + "<br />"
                            + "<hr class=\"double\"><br />"
                            + "<b>NOTE</b><br />"
                            + "<br />"
                            + "<b>From         : </b>" + userSession.getFullname() + "<br />"
                            + "<b>Note         : </b>" + note.replaceAll("\\R", " ") + "<br />"
                            + "<b>Attachment   : </b>" + att + "<br />"
                            + "<br />Thank you."//msg
                            + "</pre>"
                            + "<br />"
                            + "<br />"
                            + "Please <b>do not reply</b> to this message. Replies to this message are routed to an unmonitored mailbox"
                    );

                }

                redirectAttrs.addFlashAttribute("success", "Ticket : " + incidentNo + " has been updated.");
            } else {
                redirectAttrs.addFlashAttribute("error", "Ticket : " + incidentNo + " was failed to update.");
            }

        }
        return "redirect:/";
    }

    @RequestMapping(value = "/downloadAttach/{id}", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletRequest request,
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        IncidentAttachmentDAO attD = new IncidentAttachmentDAO();
        IncidentAttachment att = attD.getIncidentAttachment(id);

        // construct the complete absolute path of the file
        String fullPath = att.getAttachment();
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = servletContext.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }

    @RequestMapping(value = "/delete/{incidentId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("incidentId") String incidentId
    ) {
        IncidentDAO incidentDAO = new IncidentDAO();
        Incident incident = incidentDAO.getIncident(incidentId);
        incidentDAO = new IncidentDAO();
        QueryResult queryResult = incidentDAO.deleteIncident(incidentId);
        args = new String[1];
        args[0] = incident.getCategory() + " - " + incident.getSubCategory();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/incident";
    }
}
