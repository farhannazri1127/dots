package com.onsemi.dots.controller;

import com.onsemi.dots.dao.DispoEmailListDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.DispositionDAO;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.model.DispoEmailList;
import com.onsemi.dots.model.Disposition;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.UserSession;
import com.onsemi.dots.tools.EmailSender;
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
@RequestMapping(value = "/disposition")
@SessionAttributes({"userSession"})
public class DispositionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispositionController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String disposition(
            Model model
    ) {
        DispositionDAO dispositionDAO = new DispositionDAO();
        List<Disposition> dispositionList = dispositionDAO.getDispositionList();
        model.addAttribute("dispositionList", dispositionList);
        return "disposition/disposition";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "disposition/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String disposition,
            @RequestParam(required = false) String dispoBy,
            @RequestParam(required = false) String dispoDate,
            @RequestParam(required = false) String nextReqId,
            @RequestParam(required = false) String nextRmsEvent,
            @RequestParam(required = false) String nextInterval,
            @RequestParam(required = false) String flag
    ) {
        Disposition disposition1 = new Disposition();
        disposition1.setRequestId(requestId);
        disposition1.setRemarks(remarks);
        disposition1.setDisposition(disposition);
        disposition1.setDispoBy(userSession.getFullname());
        disposition1.setFlag("0");
        DispositionDAO dispositionDAO = new DispositionDAO();
        QueryResult queryResult = dispositionDAO.insertDisposition(disposition1);
        args = new String[1];
        args[0] = requestId + " - " + remarks;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("disposition", disposition);
            return "redirect:/disposition/edit/" + requestId;
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/disposition/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{requestId}/{nextReqId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("requestId") String requestId,
            @PathVariable("nextReqId") String nextReqId
    ) {
        RequestDAO reqD = new RequestDAO();
        Request request = reqD.getRequestWithDisposition(requestId);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> disposition = sDAO.getGroupParameterDetailList(request.getDisposition(), "022");
        //get next request id
        reqD = new RequestDAO();
        Request req = reqD.getRequest(nextReqId);

        model.addAttribute("request", request);
        model.addAttribute("disposition", disposition);
        model.addAttribute("nextReqId", nextReqId);
        model.addAttribute("nextRmsEvent", req.getRmsEvent());
        model.addAttribute("nextInterval", req.getInterval());
        return "disposition/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String dispoId,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String disposition,
            @RequestParam(required = false) String dispoBy,
            @RequestParam(required = false) String dispoDate,
            @RequestParam(required = false) String nextReqId,
            @RequestParam(required = false) String nextRmsEvent,
            @RequestParam(required = false) String nextInterval,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String lotId,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) String flag
    ) {
        //add if first time
        if (dispoId == null || "".equals(dispoId)) {
            Disposition disposition1 = new Disposition();
            disposition1.setRequestId(requestId);
            disposition1.setRemarks(remarks);
            disposition1.setDisposition(disposition);
            disposition1.setDispoBy(userSession.getFullname());
            disposition1.setFlag("0");
            DispositionDAO dispositionDAO = new DispositionDAO();
            QueryResult queryResult = dispositionDAO.insertDisposition(disposition1);
            args = new String[1];
            args[0] = rms + lotId + "_" + event + " - " + interval;
            if (queryResult.getGeneratedKey().equals("0")) {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            } else {
                Log log = new Log();
                log.setRequestId(requestId);
                log.setModule("Lot Request");
                log.setStatus("Added Disposition");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult addLog = logD.insertLog(log);

                Request req = new Request();
                req.setId(requestId);
                req.setDispositionId(queryResult.getGeneratedKey());
                if ("Cancel Interval".equals(disposition)) {
                    req.setStatus(disposition);
                    req.setFlag("3");
                } else {
                    req.setStatus("New");
                    req.setFlag("0");
                }
                RequestDAO reqD = new RequestDAO();
                QueryResult updateReq = reqD.updateRequestWithDisposition(req);
                if (updateReq.getResult() == 1) {
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

                    //send email
                    DispoEmailListDAO disD = new DispoEmailListDAO();
                    List<DispoEmailList> dis = disD.getDispoEmailListOnly();
                    String[] to2 = new String[dis.size()];
                    for (int i = 0; i < dis.size(); i++) {
                        to2[i] = dis.get(i).getEmail();
                    }

                    String test = "";
                    if (nextRmsEvent.contains("TC")) {
                        test = "cyc";
                    } else if (nextRmsEvent.contains("HTSL")) {
                        test = "hrs";
                    }
                    EmailSender emailSenderToHIMSSF = new EmailSender();
                    com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                    user.setFullname("All");
                    String[] to = {"fg79cj@onsemi.com"};
//                        String[] to = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
                    emailSenderToHIMSSF.htmlEmailManyTo(
                            servletContext,
                            //                    user name
                            user,
                            //                    to
                            to2,
                            //                    subject
                            "Disposition for Interval Issues when Register DOTS",
                            //                    msg
                            "Disposition for interval before " + nextRmsEvent + " - " + nextInterval + " " + test + " has been made."
                            //                            + "Please click this <a href=\"http://localhost:8080/DOTS/do/request/intervalCheck?rms_event=" + nextRmsEvent + "&interval=" + nextInterval + " \">LINK</a> for further information."
                            + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/do/request/intervalCheck?rms_event=" + nextRmsEvent + "&interval=" + nextInterval + " \">LINK</a> for further information."
                            + " Thank you."
                    );
                } else {
                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
                }

            }
        } //update 
        else {
            Disposition disposition1 = new Disposition();
            disposition1.setId(dispoId);
            disposition1.setRequestId(requestId);
            disposition1.setRemarks(remarks);
            disposition1.setDisposition(disposition);
            disposition1.setDispoBy(userSession.getFullname());
            disposition1.setDispoDate(dispoDate);
            disposition1.setFlag("0");
            DispositionDAO dispositionDAO = new DispositionDAO();
            QueryResult queryResult = dispositionDAO.updateDisposition(disposition1);
            args = new String[1];
            args[0] = rms + lotId + "_" + event + " - " + interval;
            if (queryResult.getResult() == 1) {
                Request req = new Request();
                req.setId(requestId);
                req.setDispositionId(dispoId);
                if ("Cancel Interval".equals(disposition)) {
                    req.setStatus(disposition);
                    req.setFlag("3");
                } else {
                    req.setStatus("New");
                    req.setFlag("0");
                }
                RequestDAO reqD = new RequestDAO();
                QueryResult updateReq = reqD.updateRequestWithDisposition(req);

                Log log = new Log();
                log.setRequestId(requestId);
                log.setModule("Lot Request");
                log.setStatus("Added Disposition");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult addLog = logD.insertLog(log);

                if (updateReq.getResult() == 1) {
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

                    //send email
                    DispoEmailListDAO disD = new DispoEmailListDAO();
                    List<DispoEmailList> dis = disD.getDispoEmailListOnly();
                    String[] to2 = new String[dis.size()];
                    for (int i = 0; i < dis.size(); i++) {
                        to2[i] = dis.get(i).getEmail();
                    }

                    String test = "";
                    if (nextRmsEvent.contains("TC")) {
                        test = "cyc";
                    } else if (nextRmsEvent.contains("HTSL")) {
                        test = "hrs";
                    }
                    EmailSender emailSenderToHIMSSF = new EmailSender();
                    com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                    user.setFullname("All");
                    String[] to = {"fg79cj@onsemi.com"};
//                        String[] to = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
                    emailSenderToHIMSSF.htmlEmailManyTo(
                            servletContext,
                            //                    user name
                            user,
                            //                    to
                            to2,
                            //                    subject
                            "Disposition for Interval Issues when Register DOTS",
                            //                    msg
                            "Disposition for interval before " + nextRmsEvent + " - " + nextInterval + " " + test + " has been made."
//                            + "Please click this <a href=\"http://localhost:8080/DOTS/do/request/intervalCheck?rms_event=" + nextRmsEvent + "&interval=" + nextInterval + " \">LINK</a> for further information."
                            + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/do/request/intervalCheck?rms_event=" + nextRmsEvent + "&interval=" + nextInterval + " \">LINK</a> for further information."
                            + " Thank you."
                    );

                } else {
                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
                }
//                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        }
//        return "redirect:/disposition/edit/" + requestId + "/" + nextReqId;
        return "redirect:/do/request/intervalCheck?rms_event=" + nextRmsEvent + "&interval=" + nextInterval;
    }

    @RequestMapping(value = "/delete/{dispositionId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("dispositionId") String dispositionId
    ) {
        DispositionDAO dispositionDAO = new DispositionDAO();
        Disposition disposition = dispositionDAO.getDisposition(dispositionId);
        dispositionDAO = new DispositionDAO();
        QueryResult queryResult = dispositionDAO.deleteDisposition(dispositionId);
        args = new String[1];
        args[0] = disposition.getRequestId() + " - " + disposition.getRemarks();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/disposition";
    }

    @RequestMapping(value = "/view/{dispositionId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("dispositionId") String dispositionId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/disposition/viewDispositionPdf/" + dispositionId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/disposition";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.disposition");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewDispositionPdf/{dispositionId}", method = RequestMethod.GET)
    public ModelAndView viewDispositionPdf(
            Model model,
            @PathVariable("dispositionId") String dispositionId
    ) {
        DispositionDAO dispositionDAO = new DispositionDAO();
        Disposition disposition = dispositionDAO.getDisposition(dispositionId);
        return new ModelAndView("dispositionPdf", "disposition", disposition);
    }
}
