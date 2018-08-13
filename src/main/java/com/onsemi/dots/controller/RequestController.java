package com.onsemi.dots.controller;

import com.onsemi.dots.dao.ChamberDAO;
import com.onsemi.dots.dao.DispoEmailListDAO;
import com.onsemi.dots.dao.FinalRequestDAO;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import com.onsemi.dots.dao.PreVmDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.dao.TestConditionDAO;
import com.onsemi.dots.model.Chamber;
import com.onsemi.dots.model.DispoEmailList;
import com.onsemi.dots.model.FinalRequest;
import com.onsemi.dots.model.JSONResponse;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.PreVm;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.TestCondition;
import com.onsemi.dots.model.UserSession;
import com.onsemi.dots.tools.EmailSender;
import com.onsemi.dots.tools.QueryResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "do/request")
@SessionAttributes({"userSession"})
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);
    String[] args = {};
    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HEADER = "request_id,gts_no,do_no,rmslot_event,rms,event,lot,device,package,quantity,interval,chamber_id,"
            + "chamber_level,expected_loading,expected_unloading,test_condition,remarks,shipping_date,status";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String request(
            Model model, @ModelAttribute UserSession userSession
    ) {
        RequestDAO requestDAO = new RequestDAO();
        List<Request> requestList = requestDAO.getRequestListForStatusPending();
        String groupId = userSession.getGroup();
        model.addAttribute("requestList", requestList);
        model.addAttribute("groupId", groupId);
        return "request/request";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "request/add";
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        return "request/test";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) String barcode
    ) {
        String event = "";
        String rms = "";
        String lotId = "";
        //if barcode contain interval
//        String interval = "";
        String rmsEvent = "";
        if (barcode.length() < 6) {
            LOGGER.info("intervals1 : " + interval);
            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            return "request/add";
        } else {
            rms = barcode.substring(0, 6);
        }
        if (barcode.length() < 7) {
            LOGGER.info("intervals2 : " + interval);
            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            return "request/add";
        } else {
            lotId = barcode.substring(6, 7);
        }
        if (barcode.contains("_")) {
            event = barcode.split("_")[1];

            //if barcode contain interval
//            interval = barcode.split("_")[2];
        } else {
            LOGGER.info("intervals3 : " + interval);
            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            return "request/add";
        }
        RequestDAO requestDAO = new RequestDAO();
//        int count = requestDAO.getCountRequestRmsLotEventInterval(rms, lotId, event, interval);
        int count = requestDAO.getCountRequestRmsLotEventIntervalFlag0(rms, lotId, event, interval);
        if (count == 0) {
//            rmsEvent = barcode.split("_")[0] + "_" + barcode.split("_")[1];
            model.addAttribute("barcode", barcode);
            model.addAttribute("interval", interval);
            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            return "request/add";
        } else if (count > 1) {
            model.addAttribute("barcode", barcode);
            model.addAttribute("interval", interval);
            model.addAttribute("error", "RMS#_Event found more than 1. Please re-check");
            return "request/add";
        } else {
            requestDAO = new RequestDAO();
            //get rms_event
            rmsEvent = barcode.split("_")[0] + "_" + barcode.split("_")[1];
//            Request request = requestDAO.getRequestByRmsLotEventInterval(barcode, interval);
//            Request request = requestDAO.getRequestByRmsLotEventIntervalFlag0(barcode, interval);
            Request request = requestDAO.getRequestByRmsLotEventIntervalFlag0(rmsEvent, interval);
            if ("Pending Shipment".equals(request.getStatus())) {
                model.addAttribute("barcode", barcode);
                model.addAttribute("interval", interval);
                model.addAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "request/add";
            }
            if ("Pending Pre VM".equals(request.getStatus())) {
                model.addAttribute("barcode", barcode);
                model.addAttribute("interval", interval);
                model.addAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "request/add";
            } else if ("Ready for Shipment".equals(request.getStatus())) {
                model.addAttribute("barcode", barcode);
                model.addAttribute("interval", interval);
                model.addAttribute("error", "RMS#_Event already in DO list. Please re-check");
                return "request/add";
            } else if ("New".equals(request.getStatus())) {
                String event2 = request.getEvent().substring(0, 2);

                //get rms_event
                rmsEvent = barcode.split("_")[0] + "_" + barcode.split("_")[1];

                RequestDAO reqD = new RequestDAO();
//                int countPrevInterval = reqD.getCountPreviousInterval(barcode, interval);
                int countPrevInterval = reqD.getCountPreviousInterval(rmsEvent, interval);

                if (countPrevInterval > 0) {
                    model.addAttribute("countPrevInterval", countPrevInterval);
                    model.addAttribute("barcode", barcode);
                    model.addAttribute("interval", interval);
                    String reqEmail = userSession.getEmail();
                    model.addAttribute("reqEmail", reqEmail);
                    LOGGER.info("intervals1 : " + interval);
                    return "request/add";
                } else {
                    ChamberDAO chamberD = new ChamberDAO();
                    List<Chamber> chamber = chamberD.getChamberListByChamberNameAndEvent("", event2);
                    TestConditionDAO testD = new TestConditionDAO();
                    List<TestCondition> test = testD.getTestConditionList();
                    ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
                    List<ParameterDetails> level = sDAO.getGroupParameterDetailList("", "017");
                    model.addAttribute("request", request);
                    model.addAttribute("chamber", chamber);
                    model.addAttribute("test", test);
                    model.addAttribute("level", level);
                    model.addAttribute("countPrevInterval", countPrevInterval);
                    LOGGER.info("intervals1 : " + interval);
                    return "request/edit";
                }
            } else if ("Closed".equals(request.getStatus())) {
                model.addAttribute("barcode", barcode);
                model.addAttribute("interval", interval);
                model.addAttribute("error", "RMS#_Event already closed. Please re-check");
                return "request/add";
            } else {
                model.addAttribute("barcode", barcode);
                model.addAttribute("interval", interval);
                model.addAttribute("error", "RMS#_Event already shipped to Penang. Please re-check");
                return "request/add";
            }
        }
    }

    //anta email bila ad clash interval
    @RequestMapping(value = "/sendEmail/{barcode}/{intervals}/{countPrevInterval}/{reqEmail}", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendEmail(
            Model model, @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("barcode") String barcode,
            @PathVariable("intervals") String intervals,
            @PathVariable("countPrevInterval") String countPrevInterval,
            @PathVariable("reqEmail") String reqEmail
    ) {

        RequestDAO reqD = new RequestDAO();
        int count = reqD.getCountRequestRmsLotEventInterval(barcode, intervals);
        if (count == 1) {
            reqD = new RequestDAO();
            Request req = reqD.getRequestByRmsLotEventIntervalFlag0(barcode, intervals);

            Log log = new Log();
            log.setRequestId(req.getId());
            log.setModule("Added to DOTS");
            log.setStatus("Added Disposition Request [Interval Issue]");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addOldLog = logD.insertLog(log);
        } else {
            LOGGER.info("No data");
        }

        //send email
        DispoEmailListDAO disD = new DispoEmailListDAO();
        List<DispoEmailList> dis = disD.getDispoEmailListApproverOnly();
        String[] to2 = new String[dis.size()];
        for (int i = 0; i < dis.size(); i++) {
            to2[i] = dis.get(i).getEmail();
        }

        String test = "";
        if (barcode.contains("TC")) {
            test = "cyc";
        } else if (barcode.contains("HTSL")) {
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
                "Interval Issues when Register DOTS",
                //                    msg
                "There are " + countPrevInterval + " interval before " + barcode + " - " + intervals + " " + test + " has not been register into DOTS yet. "
                //                + "Please click this <a href=\"http://localhost:8080/DOTS/do/request/intervalCheck?rms_event=" + barcode + "&interval=" + intervals + " \">LINK</a> for disposition action for those readout."
                + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/do/request/intervalCheck?rms_event=" + barcode + "&interval=" + intervals + " \">LINK</a> for disposition action for those readout."
                + " Thank you."
        );

        model.addAttribute("success", "Email has been send to Supervisor.");
        redirectAttrs.addFlashAttribute("success", "Email has been send to Supervisor.");
        return "redirect:/do/request/add";
    }

    @RequestMapping(value = "/edit/{requestId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("requestId") String requestId
    ) {
        RequestDAO requestDAO = new RequestDAO();
        Request request = requestDAO.getRequest(requestId);
        model.addAttribute("request", request);
        String event = request.getEvent().substring(0, 2);
        ChamberDAO chamberD = new ChamberDAO();
        List<Chamber> chamber = chamberD.getChamberListByChamberNameAndEvent(request.getChamber(), event);
        model.addAttribute("chamber", chamber);
        TestConditionDAO testD = new TestConditionDAO();
        List<TestCondition> test = testD.getTestConditionListByCondition(request.getTestCondition());
        model.addAttribute("test", test);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> level = sDAO.getGroupParameterDetailList(request.getChamberLocation(), "017");
        model.addAttribute("level", level);
        return "request/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String lotId,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String chamber,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String test,
            @RequestParam(required = false) String loadingDate,
            @RequestParam(required = false) String unloadingDate,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String multiplier
    ) {
        Request request = new Request();
        request.setId(id);
        request.setInterval(interval);
        request.setQuantity(quantity);
        request.setChamber(chamber);
        request.setChamberLocation(level);
        request.setTestCondition(test);
//        request.setLoadingDate(loadingDate);
//        request.setUnloadingDate(unloadingDate);
        request.setRemarks(remarks);
        request.setModifiedBy(userSession.getFullname());
//        request.setMultiplier(multiplier);
        request.setStatus("Pending Pre VM");
        request.setFlag("0");
        String rmsEvent = rms + lotId + "_" + event;
        request.setRmsEvent(rmsEvent);
        RequestDAO requestDAO = new RequestDAO();
        QueryResult queryResult = requestDAO.updateRequestforOtherDetails(request);
        args = new String[1];
        args[0] = rms + lotId + " - " + event;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update log
            Log log = new Log();
            log.setRequestId(id);
            log.setModule("Lot Request");
            log.setStatus("Added to DOTS");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/do/request";
    }

    @RequestMapping(value = "/updateToDO", method = RequestMethod.POST)
    public @ResponseBody
    JSONResponse updateToDO(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String lotId,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String flag
    ) {
        JSONResponse response = new JSONResponse();
        Request request = new Request();
        request.setId(id);
        request.setModifiedBy(userSession.getFullname());
        request.setStatus("Ready for Shipment");
        request.setFlag("0");
        RequestDAO requestDAO = new RequestDAO();
        QueryResult queryResult = requestDAO.updateRequestStatusToDO(request);

        if (queryResult.getResult() <= 0) {
            LOGGER.info("fail dkt request........");
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage(queryResult.getErrorMessage());
            response.setResult(request);
        } else {
            //check ad x request id
            FinalRequestDAO finalD = new FinalRequestDAO();
            int count = finalD.getCountRequestId(id);
            if (count != 0) {
                LOGGER.info("fail dkt final........");
                response.setStatus(Boolean.FALSE);
                response.setStatusMessage(queryResult.getErrorMessage());
                response.setResult(count);
            } else {
                //add to DO list
                FinalRequest finalR = new FinalRequest();
                finalR.setRequestId(id);
                finalR.setCreatedBy(userSession.getFullname());
                finalR.setStatus("Ready for Shipment");
                finalR.setFlag("0");
                finalD = new FinalRequestDAO();
                QueryResult queryResultFinal = finalD.insertFinalRequestFromRequest(finalR);
                if (queryResultFinal.getGeneratedKey().equals("0")) {
                    LOGGER.info("fail dkt final........");
                    response.setStatus(Boolean.FALSE);
                    response.setStatusMessage(queryResult.getErrorMessage());
                    response.setResult(finalR);
                } else {
                    response.setStatus(Boolean.TRUE);
                    response.setStatusMessage("Added to DO List");
                    response.setResult(finalR);

                    Log log = new Log();
                    log.setRequestId(id);
                    log.setModule("Lot Request");
                    log.setStatus("Added to DO List");
                    log.setCreatedBy(userSession.getFullname());
                    LogDAO logD = new LogDAO();
                    QueryResult addLog = logD.insertLog(log);

                    //create csv
                    File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                    File file = new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");

                    if (file.exists()) {
                        //create csv file
                        LOGGER.info("tiada header");
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                            fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv", true);

                            RequestDAO reqD = new RequestDAO();
                            Request req = reqD.getRequest(id);
                            TestConditionDAO testD = new TestConditionDAO();
                            TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);
                            fileWriter.append(req.getId()); //id
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //gts
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //do_no
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getRmsEvent()); //rms_event
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getRms()); //rms
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getEvent()); //event
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getLotId()); //lot
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getDevice()); //device
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getPackages()); //package
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getQuantity()); //quantity
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getInterval()); //interval
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getChamber()); //chamber
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getChamberLocation()); //chamber_level
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getLoadingDate()); //expected_loading_date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(test.getCondition()); //test_condition
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //remarks
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //shipping_date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("Ship"); //status
                            fileWriter.append(COMMA_DELIMITER);
                            System.out.println("append to CSV file Succeed!!!");
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        } finally {
                            try {
                                fileWriter.close();
                            } catch (IOException ie) {
                                System.out.println("Error occured while closing the fileWriter");
                                ie.printStackTrace();
                            }
                        }
                    } else {
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                            fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");
                            LOGGER.info("no file yet");

                            RequestDAO reqD = new RequestDAO();
                            Request req = reqD.getRequest(id);
                            TestConditionDAO testD = new TestConditionDAO();
                            TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                            //Adding the header
                            fileWriter.append(HEADER);
                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);
                            fileWriter.append(req.getId()); //id
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //gts
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //do_no
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getRmsEvent()); //rms_event
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getRms()); //rms
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getEvent()); //event
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getLotId()); //lot
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getDevice()); //device
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getPackages()); //package
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getQuantity()); //quantity
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getInterval()); //interval
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getChamber()); //chamber
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getChamberLocation()); //chamber_level
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getLoadingDate()); //expected_loading_date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(test.getCondition()); //test_condition
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //remarks
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(null); //shipping_date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("Ship"); //status
                            fileWriter.append(COMMA_DELIMITER);
                            System.out.println("Write new to CSV file Succeed!!!");
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        } finally {
                            try {
                                fileWriter.close();
                            } catch (IOException ie) {
                                System.out.println("Error occured while closing the fileWriter");
                                ie.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
        return response;
    }

    @RequestMapping(value = "/delete/{requestId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("requestId") String requestId
    ) {

        RequestDAO requestDAO = new RequestDAO();
        Request request = requestDAO.getRequest(requestId);
        String preVmId = request.getPreVmId();
        requestDAO = new RequestDAO();
        Request re = new Request();
        re.setModifiedBy(userSession.getFullname());
        re.setFlag("0");
        re.setId(requestId);
        QueryResult queryResult = requestDAO.updateRequestforDelete(re);
        args = new String[1];
        args[0] = request.getRms() + request.getLotId() + "_" + request.getEvent();

        if (queryResult.getResult() == 1) {
            //delete data pre vm
            PreVmDAO preVmDAO = new PreVmDAO();
            QueryResult queryResultPreVm = preVmDAO.deletePreVm(preVmId);

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Request");
            log.setStatus("Deleted from Request List");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/do/request";
    }

    @RequestMapping(value = "/view/{requestId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("requestId") String requestId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/do/request/viewRequestPdf/" + requestId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/request";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.request");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewRequestPdf/{requestId}", method = RequestMethod.GET)
    public ModelAndView viewRequestPdf(
            Model model,
            @PathVariable("requestId") String requestId
    ) {
        RequestDAO requestDAO = new RequestDAO();
        Request request = requestDAO.getRequest(requestId);
        return new ModelAndView("requestPdf", "request", request);
    }

    @RequestMapping(value = "/check", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryCheck(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rms_event,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) String status
    ) {
        String query = "";
        int count = 0;

        if (rms_event != null) {
            if (!("").equals(rms_event)) {
                count++;
                if (count == 1) {
                    query = " rms_event = \'" + rms_event + "\' ";
                } else if (count > 1) {
                    query = query + " AND rms_event = \'" + rms_event + "\' ";
                }
            }
        }

        if (rms != null) {
            if (!rms.equals("")) {
                count++;
                if (count == 1) {
                    query = " rms = \'" + rms + "\' ";
                } else if (count > 1) {
                    query = query + " AND rms = \'" + rms + "\' ";
                }
            }
        }

        if (event != null) {
            if (!event.equals("")) {
                count++;
                if (count == 1) {
                    query = " event LIKE '" + event + "%' ";

                } else if (count > 1) {
                    query = query + " AND event LIKE '" + event + "%' ";
                }
            }
        }

        if (interval != null) {
            if (!interval.equals("")) {
                count++;
                if (count == 1) {
                    query = " intervals = \'" + interval + "\' ";
                } else if (count > 1) {
                    query = query + " AND intervals = \'" + interval + "\' ";
                }
            }
        }

        System.out.println("QueryCheck: " + query);
        RequestDAO wh = new RequestDAO();
        List<Request> retrieveQueryList = wh.getRequestListForQueryCheck(query);
        model.addAttribute("retrieveQueryList", retrieveQueryList);

//        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
//        List<ParameterDetails> eventList = sDAO.getGroupParameterDetailList("", "018");
//        model.addAttribute("eventList", eventList);
        return "request/queryCheck";
    }

    @RequestMapping(value = "/intervalCheck", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryIntervalCheck(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rms_event,
            //            @RequestParam(required = false) String event,
            @RequestParam(required = false) String interval
    ) {
        String query = "";
        int count = 0;

        if (rms_event != null) {
            if (!("").equals(rms_event)) {
                count++;
                if (count == 1) {
                    query = " rms_event = \'" + rms_event + "\' ";
                } else if (count > 1) {
                    query = query + " AND rms_event = \'" + rms_event + "\' ";
                }
            }
        }

        if (interval != null) {
            if (!interval.equals("")) {
                count++;
                if (count == 1) {
                    query = " CAST(intervals AS UNSIGNED) < '" + interval + "' ";
                } else if (count > 1) {
                    query = query + " AND CAST(intervals AS UNSIGNED) < '" + interval + "' ";
                }
            }
        }

        String reqId = "";
        System.out.println("QueryCheck: " + query);
        RequestDAO wh = new RequestDAO();
        List<Request> retrieveQueryList = wh.getRequestListForPreviousInterval(query);
        wh = new RequestDAO();
        int countReqId = wh.getCountRequestRmsLotEventInterval(rms_event, interval);
        if (countReqId == 1) {
            wh = new RequestDAO();
            Request req = wh.getRequestByRmsLotEventInterval(rms_event, interval);
            reqId = req.getId();
        } else {
            reqId = "";
        }
        model.addAttribute("reqId", reqId);
        model.addAttribute("retrieveQueryList", retrieveQueryList);
        model.addAttribute("rmsEvent", rms_event);
        model.addAttribute("interval", interval);

        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);

        return "request/queryCheck";
    }
}
