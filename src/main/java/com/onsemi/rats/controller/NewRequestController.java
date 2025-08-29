package com.onsemi.rats.controller;

import com.onsemi.rats.dao.EmailCcDAO;
import com.onsemi.rats.dao.NewRequestAttachmentDAO;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.rats.dao.NewRequestDAO;
import com.onsemi.rats.dao.NewRequestLogDAO;
import com.onsemi.rats.dao.ParameterDetailsDAO;
import com.onsemi.rats.model.EmailCc;
import com.onsemi.rats.model.NewRequest;
import com.onsemi.rats.model.NewRequestAttachment;
import com.onsemi.rats.model.NewRequestLog;
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
import java.util.Arrays;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/newRequest")
@SessionAttributes({"userSession"})
public class NewRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewRequestController.class);
    String[] args = {};

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\GRATS-Attachment\\Request\\"; //server 

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String newRequest(
            Model model
    ) {
        NewRequestDAO newRequestDAO = new NewRequestDAO();
        List<NewRequest> newRequestList = newRequestDAO.getNewRequestList();
        model.addAttribute("newRequestList", newRequestList);
        return "newRequest/newRequest";
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
        EmailCcDAO email = new EmailCcDAO();
        List<EmailCc> listCc = email.getEmailCcListBySite(location);
//        List<EmailCc> listCc = email.getEmailCcList();
        model.addAttribute("listCc", listCc);
        return "newRequest/add";
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

        NewRequest inc = new NewRequest();
        inc.setCategory(category);
        inc.setSubCategory(subCategory);
        inc.setTitle(title);
        inc.setDescription(description.replaceAll("\\R", " ")); //remove line break
        inc.setUrgency(urgency);
        inc.setCc(emailCc);
        inc.setStatus("New Request");
        inc.setFlag("0");
        inc.setUser(userSession.getFullname());
        inc.setSite(userSession.getSite());
        inc.setUserEmail(userSession.getEmail());
        inc.setUserId(userSession.getLoginId());
        NewRequestDAO incD = new NewRequestDAO();
        QueryResult query = incD.insertNewRequest(inc);

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
            String requestNo = "R" + nowDate + index;
            inc = new NewRequest();
            inc.setTicketNo(requestNo);
            inc.setId(query.getGeneratedKey());
            incD = new NewRequestDAO();
            QueryResult query1 = incD.updateRequestNo(inc);

            //add log
            NewRequestLog incL = new NewRequestLog();
            incL.setRequestId(query.getGeneratedKey());
            incL.setStatus("New Ticket");
            incL.setDescription("Ticket " + requestNo + " was Created by " + userSession.getFullname());
            incL.setCreatedBy(userSession.getFullname());
            NewRequestLogDAO incDL = new NewRequestLogDAO();
            QueryResult logQ = incDL.insertNewRequestLog(incL);

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

                NewRequestAttachment incA = new NewRequestAttachment();
                incA.setFilename(formFile.getOriginalFilename());
                incA.setAttachment(stringPath);
                incA.setRequestId(query.getGeneratedKey());
                incA.setCreatedBy(userSession.getFullname());
                NewRequestAttachmentDAO incAD = new NewRequestAttachmentDAO();
                QueryResult incAQ = incAD.insertNewRequestAttachment(incA);

                attachment = "Yes";

            }

            //send email
            LOGGER.info("send email to person in charge");
            EmailSender emailSender = new EmailSender();

            incD = new NewRequestDAO();
            NewRequest incident = incD.getNewRequest(query.getGeneratedKey());

            List<String> emails = new ArrayList<String>();
            emails.add("Global-Rel-IT@onsemi.com");
            if (incident.getUserEmail() != null) {
                emails.add(incident.getUserEmail());
            }
            String[] myArray = new String[emails.size()];
            String[] emailTo = emails.toArray(myArray);

            if (incident.getCc() != null) {//send email with CC

                String[] ccs = incident.getCc().split(",");

                emailSender.htmlEmailManyToNewWithCc(
                        servletContext,
                        //                    user, //user name requestor
                        emailTo,
                        "GRATS - New Request Ticket #" + requestNo + " [" + incident.getStatus() + "]", //subject
                        "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/newRequest/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //server
                        //                        "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/newRequest/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //local
                        + "<pre>"
                        + "<br />"
                        + "<b>Title        : </b>" + incident.getTitle() + "<br />"
                        //                    + "<br />"
                        + "<b>Requestor    : </b>" + incident.getUser() + "  <br />"
                        //                    + "<br />"
                        + "<b>Site         : </b>" + incident.getSite() + " <br />"
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

            } else {

                emailSender.htmlEmailManyToNew(
                        servletContext,
                        //                    user, //user name requestor
                        emailTo,
                        "GRATS - New Request Ticket #" + requestNo + " [" + incident.getStatus() + "]", //subject
                        "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/newRequest/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //server
                        //                        "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/newRequest/edit/" + query.getGeneratedKey() + " \">LINK</a> for more details. <br />" //local
                        + "<pre>"
                        + "<br />"
                        + "<b>Title        : </b>" + incident.getTitle() + "<br />"
                        //                    + "<br />"
                        + "<b>Requestor    : </b>" + incident.getUser() + "  <br />"
                        //                    + "<br />"
                        + "<b>Site         : </b>" + incident.getSite() + " <br />"
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

//                String[] emailTo = {"fg79cj@onsemi.com", "MohdArif.DzainalAbidin@onsemi.com", userSession.getEmail()};
//        String[] emailTo = {"fg79cj@onsemi.com"};
            redirectAttrs.addFlashAttribute("success", "Ticket : " + requestNo + " is succesfully created.");
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }

        return "redirect:/";

    }

    @RequestMapping(value = "/edit/{requestId}", method = RequestMethod.GET)
    public String edit(Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("requestId") String requestId
    ) {

        String GroupID = userSession.getGroup();
        model.addAttribute("GroupID", GroupID);

        NewRequestDAO reqD = new NewRequestDAO();
        NewRequest request = reqD.getNewRequest(requestId);
        model.addAttribute("request", request);

        NewRequestLogDAO incL = new NewRequestLogDAO();
        List<NewRequestLog> log = incL.getNewRequestLogListByReqId(requestId);
        model.addAttribute("log", log);

        NewRequestAttachmentDAO incA = new NewRequestAttachmentDAO();
        List<NewRequestAttachment> attachment = incA.getNewRequestAttachmentListByReqId(requestId);
        model.addAttribute("attachment", attachment);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> category = sDAO.getGroupParameterDetailList(request.getCategory(), "028");
        model.addAttribute("category", category);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> subCategory = sDAO.getGroupParameterDetailList(request.getSubCategory(), "029");
        model.addAttribute("subCategory", subCategory);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> urgency = sDAO.getGroupParameterDetailList(request.getUrgency(), "030");
        model.addAttribute("urgency", urgency);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pic = sDAO.getGroupParameterDetailList(request.getAssignee(), "031");
        model.addAttribute("pic", pic);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> status = sDAO.getGroupParameterDetailList(request.getStatus(), "033");
        model.addAttribute("status", status);
//        LOGGER.info("pic: " + pic);
        return "newRequest/edit";
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
            @RequestParam(required = false) String requestNo,
            @RequestParam(required = false) MultipartFile formFile,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String flag
    ) throws IOException {
        String stringPath = "";
        String att = "No";

        if ("1".equals(groupId)) { //only admin can update info

            NewRequest incident = new NewRequest();
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
            NewRequestDAO incidentDAO = new NewRequestDAO();
            QueryResult queryResult = incidentDAO.updateNewRequestLatest(incident);

            if (queryResult.getResult() == 1) {

                //add log
                NewRequestLog incL = new NewRequestLog();
                incL.setRequestId(id);
                incL.setStatus("Ticket Update");
                incL.setDescription("Ticket has been updated by " + userSession.getFullname() + ". PIC: " + pic + " ; ETA: " + eta + " ; Status: " + status);
                incL.setCreatedBy(userSession.getFullname());
                NewRequestLogDAO incDL = new NewRequestLogDAO();
                QueryResult logQ = incDL.insertNewRequestLog(incL);

                //add note
                incL = new NewRequestLog();
                incL.setRequestId(id);
                incL.setStatus("Note from: " + userSession.getFullname());
                incL.setDescription(note.replaceAll("\\R", " ")); //remove line break);
                incL.setCreatedBy(userSession.getFullname());
                incDL = new NewRequestLogDAO();
                QueryResult logQ2 = incDL.insertNewRequestLog(incL);

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
                    NewRequestAttachment incA = new NewRequestAttachment();
                    incA.setFilename(formFile.getOriginalFilename());
                    incA.setAttachment(stringPath);
                    incA.setRequestId(id);
                    incA.setCreatedBy(userSession.getFullname());
                    NewRequestAttachmentDAO incAD = new NewRequestAttachmentDAO();
                    QueryResult incAQ = incAD.insertNewRequestAttachment(incA);

                    incL = new NewRequestLog();
                    incL.setRequestId(id);
                    incL.setStatus("Ticket Update");
                    incL.setDescription("Attachment uploaded");
                    incL.setCreatedBy(userSession.getFullname());
                    incDL = new NewRequestLogDAO();
                    QueryResult logQ3 = incDL.insertNewRequestLog(incL);

                    att = "Yes";
                }

                //send email
                LOGGER.info("send email to person in charge");
                EmailSender emailSender = new EmailSender();

                NewRequestDAO incD = new NewRequestDAO();
                NewRequest incident2 = incD.getNewRequest(id);

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
                            "GRATS - New Request Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //local
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
                            "GRATS - New Request Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //local
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

                redirectAttrs.addFlashAttribute("success", "Ticket : " + requestNo + " has been updated.");
            } else {
                redirectAttrs.addFlashAttribute("error", "Ticket : " + requestNo + " was failed to update.");
            }

        } else { //if not admin, only can update note and attachment

            //add note
            NewRequestLog incLog = new NewRequestLog();
            incLog.setRequestId(id);
            incLog.setStatus("Note from: " + userSession.getFullname());
            incLog.setDescription(note.replaceAll("\\R", " ")); //remove line break);
            incLog.setCreatedBy(userSession.getFullname());
            NewRequestLogDAO incDLog = new NewRequestLogDAO();
            QueryResult logQ2 = incDLog.insertNewRequestLog(incLog);

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

                    NewRequestAttachment incA = new NewRequestAttachment();
                    incA.setFilename(formFile.getOriginalFilename());
                    incA.setAttachment(stringPath);
                    incA.setRequestId(id);
                    incA.setCreatedBy(userSession.getFullname());
                    NewRequestAttachmentDAO incAD = new NewRequestAttachmentDAO();
                    QueryResult incAQ = incAD.insertNewRequestAttachment(incA);

                    NewRequestLog incLA = new NewRequestLog();
                    incLA.setRequestId(id);
                    incLA.setStatus("Ticket Update");
                    incLA.setDescription("Attachment uploaded");
                    incLA.setCreatedBy(userSession.getFullname());
                    NewRequestLogDAO incDLA = new NewRequestLogDAO();
                    QueryResult logQ3 = incDLA.insertNewRequestLog(incLA);

                    att = "Yes";
                }

                //send email
                LOGGER.info("send email to person in charge");
                EmailSender emailSender = new EmailSender();

                NewRequestDAO incD = new NewRequestDAO();
                NewRequest incident2 = incD.getNewRequest(id);

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
                            "GRATS - New Request Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //local
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
                            "GRATS - New Request Ticket #" + incident2.getTicketNo() + " [" + incident2.getStatus() + "]", //subject
                            "Please click this <a href=\"http://mysed-rel-app05:8084/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //server
                            //                            "Please click this <a href=\"http://fg79cj-l1:8088/GRATS/newRequest/edit/" + id + " \">LINK</a> for more details. <br />" //local
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

                redirectAttrs.addFlashAttribute("success", "Ticket : " + requestNo + " has been updated.");
            } else {
                redirectAttrs.addFlashAttribute("error", "Ticket : " + requestNo + " was failed to update.");
            }

        }
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{newRequestId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("newRequestId") String newRequestId
    ) {
        NewRequestDAO newRequestDAO = new NewRequestDAO();
        NewRequest newRequest = newRequestDAO.getNewRequest(newRequestId);
        newRequestDAO = new NewRequestDAO();
        QueryResult queryResult = newRequestDAO.deleteNewRequest(newRequestId);
        args = new String[1];
        args[0] = newRequest.getTicketNo() + " - " + newRequest.getCategory();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/newRequest";
    }

    @RequestMapping(value = "/downloadAttach/{id}", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletRequest request,
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        NewRequestAttachmentDAO attD = new NewRequestAttachmentDAO();
        NewRequestAttachment att = attD.getNewRequestAttachment(id);

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
}
