package com.onsemi.dots.controller;

import com.onsemi.dots.dao.ChamberDAO;
import com.onsemi.dots.dao.LogDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.LotPenangDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.dao.RmaDAO;
import com.onsemi.dots.dao.TestConditionDAO;
import com.onsemi.dots.model.Chamber;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.model.LotPenangUploadTemp;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.Rma;
import com.onsemi.dots.model.RmaTemp;
import com.onsemi.dots.model.TestCondition;
import com.onsemi.dots.model.UserSession;
import com.onsemi.dots.tools.CSV;
import com.onsemi.dots.tools.EmailSender;
import com.onsemi.dots.tools.QueryResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/lotPenang")
@SessionAttributes({"userSession"})
public class LotPenangController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotPenangController.class);
    String[] args = {};

//Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HEADER = "request_id, do_number,gts_no,rms_event,interval,closed_date,status";
    private static final String HEADER2 = "request_id,interval,qty,estimate_loading_date,estimate_unloading_date,old_request_id,status,path,chamber,chamber_level,condition,remarks";
    private static final String HEADER3 = "request_id, disposition1,disposition1_remarks,disposition1_date,disposition2,disposition2_remarks,disposition2_date,dispo1_by,dispo2_by";
    private static final String HEADER4 = "request_id,gts_no,do_no,rmslot_event,rms,event,lot,device,package,quantity,interval,chamber_id,"
            + "chamber_level,expected_loading,expected_unloading,test_condition,remarks,shipping_date,status";

//Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_Trip_Ticket\\";

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String lotPenang(
            Model model, @ModelAttribute UserSession userSession
    ) {
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
//        List<LotPenang> lotPenangList = lotPenangDAO.getLotPenangListWithRequestTable();
        List<LotPenang> lotPenangList = lotPenangDAO.getLotPenangListWithRequestTableAndRmaTable();
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        model.addAttribute("lotPenangList", lotPenangList);
        return "lotPenang/lotPenang";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "lotPenang/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String doNumber,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String rmsEvent,
            @RequestParam(required = false) String chamberId,
            @RequestParam(required = false) String chamberLevel,
            @RequestParam(required = false) String loadingDate,
            @RequestParam(required = false) String loadingRemarks,
            @RequestParam(required = false) String testCondition,
            @RequestParam(required = false) String loadingBy,
            @RequestParam(required = false) String unloadingDate,
            @RequestParam(required = false) String unloadingRemarks,
            @RequestParam(required = false) String unloadingBy,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String receivedQuantity,
            @RequestParam(required = false) String receivedDate,
            @RequestParam(required = false) String receivedBy,
            @RequestParam(required = false) String shipmentBy,
            @RequestParam(required = false) String shipmentDate,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag
    ) {
        LotPenang lotPenang = new LotPenang();
        lotPenang.setDoNumber(doNumber);
        lotPenang.setRequestId(requestId);
        lotPenang.setRmsEvent(rmsEvent);
        lotPenang.setChamberId(chamberId);
        lotPenang.setChamberLevel(chamberLevel);
        lotPenang.setLoadingDate(loadingDate);
        lotPenang.setLoadingRemarks(loadingRemarks);
        lotPenang.setTestCondition(testCondition);
        lotPenang.setLoadingBy(loadingBy);
        lotPenang.setUnloadingDate(unloadingDate);
        lotPenang.setUnloadingRemarks(unloadingRemarks);
        lotPenang.setUnloadingBy(unloadingBy);
        lotPenang.setStatus(status);
        lotPenang.setReceivedQuantity(receivedQuantity);
        lotPenang.setReceivedDate(receivedDate);
        lotPenang.setReceivedBy(receivedBy);
        lotPenang.setShipmentBy(shipmentBy);
        lotPenang.setShipmentDate(shipmentDate);
        lotPenang.setCreatedBy(createdBy);
        lotPenang.setCreatedDate(createdDate);
        lotPenang.setFlag(flag);
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.insertLotPenang(lotPenang);
        args = new String[1];
        args[0] = doNumber + " - " + requestId;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("lotPenang", lotPenang);
            return "lotPenang/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/lotPenang/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{lotPenangId}", method = RequestMethod.GET)
    public String edit(
            Model model, @ModelAttribute UserSession userSession,
            @PathVariable("lotPenangId") String lotPenangId
    ) {

        LotPenangDAO lotPenangDAO = new LotPenangDAO();
//        LotPenang lotPenang = lotPenangDAO.getLotPenangWithRequestTable(lotPenangId);
        LotPenang lotPenang = lotPenangDAO.getLotPenangWithRequestTableAndRmaTable(lotPenangId);

        String oldInterval = "";
        //check old interval
        LotPenangDAO lotPCount = new LotPenangDAO();
        int count = lotPCount.getCountId(lotPenang.getOldLotPenangId());

        if (count == 1) {
            LotPenangDAO oldlotPenangDAO = new LotPenangDAO();
            LotPenang oldlotPenang = oldlotPenangDAO.getLotPenangWithRequestTable(lotPenang.getOldLotPenangId());

            RequestDAO reqD = new RequestDAO();
            Request req = reqD.getRequest(oldlotPenang.getRequestId());
            oldInterval = req.getInterval();

        }

        TestConditionDAO testd = new TestConditionDAO();
        TestCondition test = testd.getTestConditionByCondition(lotPenang.getExpectedCondition());
        String testCondition = test.getCondition();

//        for check which tab should active
//        if ("Ship to Penang".equals(lotPenang.getStatus()) || "Received in Penang".equals(lotPenang.getStatus()) || "PreLoading VM".equals(lotPenang.getStatus()) || "Pending Loading".equals(lotPenang.getStatus())) {
        if (lotPenang.getStatus().contains("Ship to Penang") || lotPenang.getStatus().contains("Received in Penang") || lotPenang.getStatus().contains("PreLoading VM") || lotPenang.getStatus().contains("Pending Loading")
                || lotPenang.getStatus().contains("Cancel Interval")) {

            String reActive = "active";
            String reActiveTab = "in active";
            model.addAttribute("reActive", reActive);
            model.addAttribute("reActiveTab", reActiveTab);
        } else {
            String reActive = "";
            String reActiveTab = "";
            model.addAttribute("reActive", reActive);
            model.addAttribute("reActiveTab", reActiveTab);
        }
        if ("Loading Process".equals(lotPenang.getStatus()) || "Break Interval".equals(lotPenang.getStatus()) || "Loading Process - Revise Interval".equals(lotPenang.getStatus())) {

            String ldActive = "active";
            String ldActiveTab = "in active";
            model.addAttribute("ldActive", ldActive);
            model.addAttribute("ldActiveTab", ldActiveTab);
        } else {
            String ldActive = "";
            String ldActiveTab = "";
            model.addAttribute("ldActive", ldActive);
            model.addAttribute("ldActiveTab", ldActiveTab);
        }
        if (lotPenang.getStatus().contains("Unloading Process") || "PostLoading VM".equals(lotPenang.getStatus())) {
            //as requested 2/11/16
            String udActive = "active";
            String udActiveTab = "in active";
            model.addAttribute("udActive", udActive);
            model.addAttribute("udActiveTab", udActiveTab);
        } else {
            String udActive = "";
            String udActiveTab = "";
            model.addAttribute("udActive", udActive);
            model.addAttribute("udActiveTab", udActiveTab);
        }
        if ("Ship to Rel Lab".equals(lotPenang.getStatus()) || "Pending Close Lot".equals(lotPenang.getStatus()) || "Closed".equals(lotPenang.getStatus())) {
            String clActive = "active";
            String clActiveTab = "in active";
            model.addAttribute("clActive", clActive);
            model.addAttribute("clActiveTab", clActiveTab);
        } else {
            String clActive = "";
            String clActiveTab = "";
            model.addAttribute("clActive", clActive);
            model.addAttribute("clActiveTab", clActiveTab);
        }

        model.addAttribute("lotPenang", lotPenang);
        model.addAttribute("testCondition", testCondition);
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        model.addAttribute("oldInterval", oldInterval);

        //rma
        String dis1 = "";
        String dis2 = "";
        RmaDAO rD = new RmaDAO();
        int countRma = rD.getCountRmaId(lotPenang.getRmaId());
        if (countRma == 1) {
            rD = new RmaDAO();
            Rma r = rD.getRma(lotPenang.getRmaId());
            dis1 = r.getRmaDispo1();
            dis2 = r.getRmaDispo2();
        }

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> Dis1List = sDAO.getGroupParameterDetailList(dis1, "020");
        model.addAttribute("Dis1List", Dis1List);
        ParameterDetailsDAO sDAO2 = new ParameterDetailsDAO();
        List<ParameterDetails> Dis2List = sDAO2.getGroupParameterDetailList(dis2, "021");
        model.addAttribute("Dis2List", Dis2List);
        return "lotPenang/edit";
    }

    @RequestMapping(value = "/updateRma", method = RequestMethod.POST)
    public String updateRma(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String dis1,
            @RequestParam(required = false) String rmaId,
            @RequestParam(required = false) String dis1Remarks,
            @RequestParam(required = false) String dis2,
            @RequestParam(required = false) String dis2Remarks,
            @RequestParam(required = false) String rmaCount,
            @RequestParam(required = false) String rmaFlag
    ) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        int countRma = Integer.parseInt(rmaCount) + 1;

        Rma rma = new Rma();
        rma.setId(rmaId);
        rma.setRmaCount(String.valueOf(countRma));
        //if flag = 1
        rma.setRmaDispo1(dis1);
        rma.setRmaDispo1Remarks(dis1Remarks);
        rma.setRmaDispo1By(userSession.getFullname());
        rma.setRmaDispo1Date(todayDate);
        //if flag = 2
        rma.setRmaDispo2(dis2);
        rma.setRmaDispo2Remarks(dis2Remarks);
        rma.setRmaDispo2By(userSession.getFullname());
        rma.setRmaDispo2Date(todayDate);

        RmaDAO rD = new RmaDAO();
        if ("1".equals(rmaFlag)) {
            QueryResult queryFlag1 = rD.updateRmaifFlag1(rma);
            if (queryFlag1.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", "Successful submit RMA disposition.");

                Log log = new Log();
                log.setRequestId(requestId);
                log.setModule("Lot Penang");
                log.setStatus("Added First RMA");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult addLog = logD.insertLog(log);

                String remarksDis1 = dis1Remarks;
                if (dis1Remarks.contains(",")) {
                    remarksDis1 = dis1Remarks.replaceAll(",", " ");
                }

                //check either has submit disposition1 or not
                RmaDAO rmaD = new RmaDAO();
                Rma rm = rmaD.getRma(rmaId);
                int countRm = Integer.parseInt(rm.getRmaCount());
                //has submit dis1 before this
                if (countRm > 1) {

                    //update csv
                    File filePenang = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv");
//                    File filePenang = new File("D:\\DOTS\\DOTS_CSV\\dots_rma.csv");
                    if (filePenang.exists()) {
                        LOGGER.info("dh ada header");
                        FileWriter fileWriter = null;
                        FileReader fileReader = null;
                        try {
                            fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv", true);
                            fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv");
                            String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv";
//                            fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_rma.csv", true);
//                            fileReader = new FileReader("D:\\DOTS\\DOTS_CSV\\dots_rma.csv");
//                            String targetLocation = "D:\\DOTS\\DOTS_CSV\\dots_rma.csv";

                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            String data = bufferedReader.readLine();

                            StringBuilder buff = new StringBuilder();

                            boolean flag = false;
                            boolean sentEmail = false;
                            int row = 0;
                            while (data != null && flag == false) {
                                buff.append(data).append(System.getProperty("line.separator"));

                                String[] split = data.split(",");
                                RmaTemp inventoryPenang = new RmaTemp(
                                        split[0], split[1], split[2],
                                        split[3], split[4], split[5],
                                        split[6], split[7], split[8]
                                );

//                                RmaDAO rmD = new RmaDAO();
//                                int count = rmD.getCountRequestId(split[0]);
//                                if (count == 1) {
                                if (split[0].equals(requestId)) {
                                    CSV csv = new CSV();
                                    csv.open(new File(targetLocation));
                                    csv.put(1, row, dis1);
                                    csv.put(2, row, remarksDis1);
                                    csv.put(3, row, todayDate);
                                    csv.put(7, row, userSession.getFullname());
                                    csv.save(new File(targetLocation));
                                    sentEmail = true;
                                    flag = true;
                                    LOGGER.info("update csv dis1 [" + split[0] + "]");
                                } else {
                                    flag = false;
                                }

                                data = bufferedReader.readLine();

                                row++;
                            }
                            bufferedReader.close();
                            fileReader.close();

                            if (sentEmail == true) {
                                EmailSender emailSenderToHIMSSF = new EmailSender();
                                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                                user.setFullname("POTS");
//                                String[] to = {"potspenang@gmail.com", "fg79cj@onsemi.com"};
//                                String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                                String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
                                emailSenderToHIMSSF.htmlEmailWithAttachment(
                                        servletContext,
                                        //                    user name
                                        user,
                                        //                    to
                                        to,
                                        // attachment file
                                        //                                        new File("D:\\DOTS\\DOTS_CSV\\dots_rma.csv"),
                                        new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv"),
                                        //                    subject
                                        "RMA Disposition 1",
                                        //                    msg
                                        "RMA Disposition 1. "
                                );
                                //send email to LSP personnel
                                RequestDAO reqD = new RequestDAO();
                                Request req = reqD.getRequest(requestId);
                                String rms_event = req.getRmsEvent() + " - " + req.getInterval();

                                EmailSender emailSenderToLsp = new EmailSender();
                                com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                                user1.setFullname("All");
                                String[] to2 = {"zuhairah@lsp2u.com", "danial@lsp2u.com‎", " peter@lsp2u.com",
                                    "fg79cj@onsemi.com", "tcw38v@onsemi.com"};
                                emailSenderToLsp.htmlEmailManyTo(
                                        servletContext,
                                        //                    user name
                                        user1,
                                        //                    to
                                        to2,
                                        //                    subject
                                        "RMA Disposition - " + rms_event,
                                        //                    msg
                                        "RMA Disposition for " + rms_event + " has been made. Please click "
                                        + "this <a href=\"http://pots-server:8080/POTS/chamber/chPlanner/verify/" + requestId + "\">LINK</a> for further action"
                                );

                            }

                        } catch (Exception ee) {
                            System.out.println("Error 1 occured while append the fileWriter");
                        } finally {
                            try {
                                fileWriter.close();
                            } catch (IOException ie) {
                                System.out.println("Error 2 occured while closing the fileWriter");
                            }
                        }
                    } else {
                        LOGGER.info("File dots_planner not exists.................");
                    }

                } //first time submit for dis1
                else {

                    //create csv
                    File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv");
//                    File file = new File("D:\\DOTS\\DOTS_CSV\\dots_rma.csv");

                    if (file.exists()) {
                        //create csv file
                        LOGGER.info("tiada header");
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv", true);
//                            fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_rma.csv", true);

                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);
                            fileWriter.append(requestId); //requestId
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(dis1); //dis1
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(remarksDis1); //dis1Remarks
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(todayDate); //dis1Date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2Remarks
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2Date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(userSession.getFullname()); //dis1By
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2By
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
                            fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv");
//                            fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_rma.csv");
                            LOGGER.info("no file yet");

                            //Adding the header
                            fileWriter.append(HEADER3);
                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);
                            fileWriter.append(requestId); //requestId
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(dis1); //dis1
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(remarksDis1); //dis1Remarks
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(todayDate); //dis1Date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2Remarks
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2Date
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(userSession.getFullname()); //dis1By
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append("null"); //dis2By
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

                    //send email
                    EmailSender emailSenderToHIMSSF = new EmailSender();
                    com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                    user.setFullname("POTS");
//                    String[] to = {"fg79cj@onsemi.com", "pots-penang@lsp2u.com"};
                    String[] to = {"fg79cj@onsemi.com", "pots-penang@lsp2u.com.my"};
                    emailSenderToHIMSSF.htmlEmailWithAttachment(
                            servletContext,
                            //                    user name
                            user,
                            //                    to
                            to,
                            // attachment file
                            new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv"),
                            //                            new File("D:\\DOTS\\DOTS_CSV\\dots_rma.csv"),
                            //                    subject
                            "RMA Disposition 1.",
                            //                    msg
                            "RMA Disposition 1. "
                    );

                    RequestDAO reqD = new RequestDAO();
                    Request req = reqD.getRequest(requestId);
                    String rms_event = req.getRmsEvent() + " - " + req.getInterval();

                    EmailSender emailSenderToLsp = new EmailSender();
                    com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                    user1.setFullname("All");
                    String[] to2 = {"zuhairah@lsp2u.com", "danial@lsp2u.com‎", " peter@lsp2u.com",
                        "fg79cj@onsemi.com", "tcw38v@onsemi.com"};
                    emailSenderToLsp.htmlEmailManyTo(
                            servletContext,
                            //                    user name
                            user1,
                            //                    to
                            to2,
                            //                    subject
                            "RMA Disposition - " + rms_event,
                            //                    msg
                            "RMA Disposition for " + rms_event + " has been made. Please click "
                            + "this <a href=\"http://pots-server:8080/POTS/chamber/chPlanner/verify/" + requestId + "\">LINK</a> for further action"
                    );

                }

            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to submit. Please re-check!");
            }
        } else if ("2".equals(rmaFlag)) {
            QueryResult queryFlag1 = rD.updateRmaifFlag2(rma);
            if (queryFlag1.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", "Successful submit RMA disposition.");

                Log log = new Log();
                log.setRequestId(requestId);
                log.setModule("Lot Penang");
                log.setStatus("Added Second RMA");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult addLog = logD.insertLog(log);

                String remarksDis2 = dis2Remarks;
                if (dis2Remarks.contains(",")) {
                    remarksDis2 = dis2Remarks.replaceAll(",", " ");
                }

                //update csv
                File filePenang = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv");
//                File filePenang = new File("D:\\DOTS\\DOTS_CSV\\dots_rma.csv");

                if (filePenang.exists()) {
                    LOGGER.info("dh ada header");
                    FileWriter fileWriter = null;
                    FileReader fileReader = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv", true);
                        fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv");
                        String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv";
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_rma.csv", true);
//                        fileReader = new FileReader("D:\\DOTS\\DOTS_CSV\\dots_rma.csv");
//                        String targetLocation = "D:\\DOTS\\DOTS_CSV\\dots_rma.csv";

                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String data = bufferedReader.readLine();

                        StringBuilder buff = new StringBuilder();

                        boolean flag = false;
                        boolean sentEmail = false;
                        int row = 0;
                        while (data != null && flag == false) {
                            buff.append(data).append(System.getProperty("line.separator"));

                            String[] split = data.split(",");
                            RmaTemp inventoryPenang = new RmaTemp(
                                    split[0], split[1], split[2],
                                    split[3], split[4], split[5],
                                    split[6], split[7], split[8]
                            );

//                            RmaDAO rmD = new RmaDAO();
//                            int count = rmD.getCountRequestId(split[0]);
//                            if (count == 1) {
                            if (split[0].equals(requestId)) {
                                CSV csv = new CSV();
                                csv.open(new File(targetLocation));
                                csv.put(4, row, dis2);
                                csv.put(5, row, remarksDis2);
                                csv.put(6, row, todayDate);
                                csv.put(8, row, userSession.getFullname());
                                csv.save(new File(targetLocation));
                                sentEmail = true;
                                flag = true;
                                LOGGER.info("update csv dis2 [" + split[0] + "]");
                            } else {
                                flag = false;
                            }

                            data = bufferedReader.readLine();

                            row++;
                        }
                        bufferedReader.close();
                        fileReader.close();

                        if (sentEmail == true) {
                            EmailSender emailSenderToHIMSSF = new EmailSender();
                            com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                            user.setFullname("POTS");
                            String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                            String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                            emailSenderToHIMSSF.htmlEmailWithAttachment(
                                    servletContext,
                                    //                    user name
                                    user,
                                    //                    to
                                    to,
                                    // attachment file
                                    new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_rma.csv"),
                                    //                                    new File("D:\\DOTS\\DOTS_CSV\\dots_rma.csv"),
                                    //                    subject
                                    "RMA Disposition 2",
                                    //                    msg
                                    "RMA Disposition 2. "
                            );

                            RequestDAO reqD = new RequestDAO();
                            Request req = reqD.getRequest(requestId);
                            String rms_event = req.getRmsEvent() + " - " + req.getInterval();

                            EmailSender emailSenderToLsp = new EmailSender();
                            com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                            user1.setFullname("All");
                            String[] to2 = {"zuhairah@lsp2u.com", "danial@lsp2u.com‎", " peter@lsp2u.com",
                                "fg79cj@onsemi.com", "tcw38v@onsemi.com"};
                            emailSenderToLsp.htmlEmailManyTo(
                                    servletContext,
                                    //                    user name
                                    user1,
                                    //                    to
                                    to2,
                                    //                    subject
                                    "RMA Disposition - " + rms_event,
                                    //                    msg
                                    "RMA Disposition for " + rms_event + " has been made. Please click "
                                    + "this <a href=\"http://pots-server:8080/POTS/chamber/chPlanner/verify/" + requestId + "\">LINK</a> for further action"
                            );
                        }

                    } catch (Exception ee) {
                        System.out.println("Error 1 occured while append the fileWriter");
                    } finally {
                        try {
                            fileWriter.close();
                        } catch (IOException ie) {
                            System.out.println("Error 2 occured while closing the fileWriter");
                        }
                    }
                } else {
                    LOGGER.info("File dots_planner not exists.................");
                }

            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to submit. Please re-check!");
            }
        }

        return "redirect:/lotPenang/edit/" + id;
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public String close(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String closedVerification
    ) {
        LotPenang lotPenang = new LotPenang();
        lotPenang.setId(id);
        lotPenang.setRequestId(requestId);
        lotPenang.setClosedVerification(closedVerification);
        lotPenang.setClosedBy(userSession.getFullname());
        lotPenang.setStatus("Closed");
        lotPenang.setFlag("1");
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.updateLotPenangWhenClosed(lotPenang);
        args = new String[1];
        args[0] = closedVerification + " Verified.";
        if (queryResult.getResult() == 1) {

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Penang");
            log.setStatus("Close Lot");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);
            //update request table
            Request req = new Request();
            req.setStatus("Closed");
            req.setFlag("1");
            req.setModifiedBy(userSession.getFullname());
            req.setId(requestId);
            RequestDAO reqD = new RequestDAO();
            QueryResult updateReq = reqD.updateRequestWhenClosed(req);
            if (updateReq.getResult() == 1) {

                //create csv
                File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_closed.csv");
//                File file = new File("D:\\DOTS\\DOTS_CSV\\dots_closed.csv");

                if (file.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_closed.csv", true);
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_closed.csv", true);

                        reqD = new RequestDAO();
                        Request req1 = reqD.getRequest(requestId);
                        lotPenangDAO = new LotPenangDAO();
                        LotPenang lot = lotPenangDAO.getLotPenang(id);

                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(req1.getId()); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getDoNumber()); //doNumber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getRmsEvent()); //rms_event
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getClosedDate()); //closedDate
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Closed"); //status
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
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_closed.csv");
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_closed.csv");
                        LOGGER.info("no file yet");

                        reqD = new RequestDAO();
                        Request req1 = reqD.getRequest(requestId);
                        lotPenangDAO = new LotPenangDAO();
                        LotPenang lot = lotPenangDAO.getLotPenang(id);
                        //Adding the header
                        fileWriter.append(HEADER);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(req1.getId()); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getDoNumber()); //doNumber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getRmsEvent()); //rms_event
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req1.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getClosedDate()); //closedDate
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Closed"); //status
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

                //send email
                EmailSender emailSenderToHIMSSF = new EmailSender();
                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                user.setFullname("POTS");
                String[] to = {"fg79cj@onsemi.com", "pots-penang@lsp2u.com.my"};
//                String[] to = {"fg79cj@onsemi.com", "pots-penang@lsp2u.com"};
                emailSenderToHIMSSF.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to,
                        // attachment file
                        new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_closed.csv"),
                        //                           new File("D:\\DOTS\\DOTS_CSV\\dots_closed.csv"),
                        //                    subject
                        "List of closed RMS_event.",
                        //                    msg
                        "List of closed RMS_event. "
                );

                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/lotPenang/edit/" + id;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String doNumber,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String rmsEvent,
            @RequestParam(required = false) String chamberId,
            @RequestParam(required = false) String chamberLevel,
            @RequestParam(required = false) String loadingDate,
            @RequestParam(required = false) String loadingRemarks,
            @RequestParam(required = false) String testCondition,
            @RequestParam(required = false) String loadingBy,
            @RequestParam(required = false) String unloadingDate,
            @RequestParam(required = false) String unloadingRemarks,
            @RequestParam(required = false) String unloadingBy,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String receivedQuantity,
            @RequestParam(required = false) String receivedDate,
            @RequestParam(required = false) String receivedBy,
            @RequestParam(required = false) String shipmentBy,
            @RequestParam(required = false) String shipmentDate,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag
    ) {
        LotPenang lotPenang = new LotPenang();
        lotPenang.setId(id);
        lotPenang.setDoNumber(doNumber);
        lotPenang.setRequestId(requestId);
        lotPenang.setRmsEvent(rmsEvent);
        lotPenang.setChamberId(chamberId);
        lotPenang.setChamberLevel(chamberLevel);
        lotPenang.setLoadingDate(loadingDate);
        lotPenang.setLoadingRemarks(loadingRemarks);
        lotPenang.setTestCondition(testCondition);
        lotPenang.setLoadingBy(loadingBy);
        lotPenang.setUnloadingDate(unloadingDate);
        lotPenang.setUnloadingRemarks(unloadingRemarks);
        lotPenang.setUnloadingBy(unloadingBy);
        lotPenang.setStatus(status);
        lotPenang.setReceivedQuantity(receivedQuantity);
        lotPenang.setReceivedDate(receivedDate);
        lotPenang.setReceivedBy(receivedBy);
        lotPenang.setShipmentBy(shipmentBy);
        lotPenang.setShipmentDate(shipmentDate);
        lotPenang.setCreatedBy(createdBy);
        lotPenang.setCreatedDate(createdDate);
        lotPenang.setFlag(flag);
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.updateLotPenang(lotPenang);
        args = new String[1];
        args[0] = doNumber + " - " + requestId;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/lotPenang/edit/" + id;
    }

    @RequestMapping(value = "/delete/{lotPenangId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("lotPenangId") String lotPenangId
    ) {
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        LotPenang lotPenang = lotPenangDAO.getLotPenang(lotPenangId);
        lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.deleteLotPenang(lotPenangId);
        args = new String[1];
        args[0] = lotPenang.getDoNumber() + " - " + lotPenang.getRequestId();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/lotPenang";
    }

    @RequestMapping(value = "/view/{lotPenangId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("lotPenangId") String lotPenangId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/lotPenang/viewLotPenangPdf/" + lotPenangId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/lotPenang";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Details Information for Lot in Penang");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewLotPenangPdf/{lotPenangId}", method = RequestMethod.GET)
    public ModelAndView viewLotPenangPdf(
            Model model,
            @PathVariable("lotPenangId") String lotPenangId
    ) {
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        LotPenang lotPenang = lotPenangDAO.getLotPenangWithRequestTable(lotPenangId);

        LotPenangDAO lotPD = new LotPenangDAO();
        int count = lotPD.getCountId(lotPenang.getOldLotPenangId());
        if (count == 1) {
            lotPD = new LotPenangDAO();
            LotPenang oldLot = lotPD.getLotPenang(lotPenang.getOldLotPenangId());

            RequestDAO reqD = new RequestDAO();
            Request req = reqD.getRequest(oldLot.getRequestId());
            model.addAttribute("req", req);
        }

        return new ModelAndView("lotPenangPdf", "lotPenang", lotPenang);
    }

    @RequestMapping(value = "/breakInterval/{lotPenangId}", method = RequestMethod.GET)
    public String breakInterval(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("lotPenangId") String lotPenangId
    ) {

        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        LotPenang lotPenang = lotPenangDAO.getLotPenang(lotPenangId);

        model.addAttribute("lotPenang", lotPenang);
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        return "lotPenang/breakInterval";
    }

    @RequestMapping(value = "/breakIntervalSave", method = RequestMethod.POST)
    public String breakIntervalSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) String oldLotPenangId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String interval
    ) {
        String event = "";
        String rms = "";
        String lotId = "";
        if (barcode.length() < 6) {
//            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
        } else {
            rms = barcode.substring(0, 6);
        }
        if (barcode.length() < 7) {
//            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
//            return "redirect:lotPenang/breakInterval";
            return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
        } else {
            lotId = barcode.substring(6, 7);
        }
        if (barcode.contains("_")) {
            event = barcode.split("_")[1];
        } else {
//            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
//            return "lotPenang/breakInterval";
            return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
        }
        RequestDAO requestDAO = new RequestDAO();
        int count = requestDAO.getCountRequestRmsLotEventIntervalFlag0(rms, lotId, event, interval);
        if (count == 0) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
        } else if (count > 1) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event found more than 1. Please re-check");
            return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
        } else {
            requestDAO = new RequestDAO();
            Request request = requestDAO.getRequestByRmsLotEventIntervalFlag0(barcode, interval);
            if ("Pending Shipment".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
            } else if ("Pending Pre VM".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
            } else if ("Ready for Shipment".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in DO list. Please re-check");
                return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
            } else if ("New".equals(request.getStatus())) {
                model.addAttribute("request", request);
                model.addAttribute("oldLotPenangId", oldLotPenangId);
                return "lotPenang/breakIntervalEdit";
            } else if ("Closed".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already closed. Please re-check");
                return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
            } else {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already shipped to Penang. Please re-check");
                return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
            }
        }
    }

    @RequestMapping(value = "/breakIntervalUpdate", method = RequestMethod.POST)
    public String breakIntervalUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String oldLotPenangId,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) MultipartFile fileUpload,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        String stringPath = "";
        //upload file
        if (fileUpload.isEmpty()) {
            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/lotPenang/breakIntervalSave";
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = fileUpload.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + requestId + "_" + fileUpload.getOriginalFilename());
            Files.write(path, bytes);
            stringPath = path.toString();
            LOGGER.info("path : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //get data from old lot penang table
        LOGGER.info("oldLotPenangId : " + oldLotPenangId);
        LotPenangDAO lotD = new LotPenangDAO();
        LotPenang lot = lotD.getLotPenang(oldLotPenangId);
        LotPenang lotPenang = new LotPenang();
        lotPenang.setDoNumber(lot.getDoNumber());
        lotPenang.setRequestId(requestId);
        lotPenang.setRmsEvent(lot.getRmsEvent());
        lotPenang.setChamberId(lot.getChamberId());
        lotPenang.setChamberLevel(lot.getChamberLevel());
        lotPenang.setLoadingDate(lot.getLoadingDate());
        lotPenang.setLoadingRemarks(lot.getLoadingRemarks());
        lotPenang.setTestCondition(lot.getTestCondition());
        lotPenang.setLoadingBy(lot.getLoadingBy());
        lotPenang.setStatus("Break Interval");
        lotPenang.setReceivedQuantity(lot.getReceivedQuantity());
        lotPenang.setReceivedDate(lot.getReceivedDate());
        lotPenang.setReceivedBy(lot.getReceivedBy());
        lotPenang.setReceivedVerificationStatus(lot.getReceivedVerificationStatus());
        lotPenang.setReceivedVerificationDate(lot.getReceivedVerificationDate());
        lotPenang.setReceivedMixStatus(lot.getReceivedMixStatus());
        lotPenang.setReceivedMixRemarks(lot.getReceivedMixRemarks());
        lotPenang.setReceivedDemountStatus(lot.getReceivedDemountStatus());
        lotPenang.setReceivedDemountRemarks(lot.getReceivedDemountRemarks());
        lotPenang.setReceivedBrokenStatus(lot.getReceivedBrokenStatus());
        lotPenang.setReceivedBrokenRemarks(lot.getReceivedBrokenRemarks());
        lotPenang.setPreVmDate(lot.getPreVmDate());
        lotPenang.setCreatedBy(userSession.getFullname());
        lotPenang.setOldLotPenangId(oldLotPenangId);
        lotPenang.setFlag("0");
        lotPenang.setTripTicketPath(stringPath);
        lotPenang.setPotsNotify("0");
        if ("".equals(remarks)) {
            lotPenang.setIntervalRemarks("null");
        } else {
            lotPenang.setIntervalRemarks(remarks);
        }
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.insertLotPenangBreakInterval(lotPenang);

        //get data from requesttable
        LOGGER.info("requestId : " + requestId);
        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithViewDate(requestId);
        args = new String[1];
        args[0] = lot.getRmsEvent() + " - " + req.getInterval();
        LOGGER.info("queryResult.getGeneratedKey() : " + queryResult.getGeneratedKey());
        if (queryResult.getGeneratedKey() != null) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Penang");
            log.setStatus("Added Break Interval");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //update request table for old interval
            LOGGER.info("lot.getRequestId() : " + lot.getRequestId());
            Request request = new Request();
            request.setStatus("Cancel Interval");
            request.setFlag("2");
            request.setModifiedBy(userSession.getFullname());
            request.setId(lot.getRequestId());
            reqD = new RequestDAO();
            QueryResult queryResult2 = reqD.updateRequestCancelInterval(request);

            log = new Log();
            log.setRequestId(lot.getRequestId());
            log.setModule("Lot Penang");
            log.setStatus("Added Cancel Interval");
            log.setCreatedBy(userSession.getFullname());
            logD = new LogDAO();
            QueryResult addOldLog = logD.insertLog(log);

            if (queryResult2.getResult() > 0) {

                //extract data from old request table
                RequestDAO reqD3 = new RequestDAO();
                Request req3 = reqD3.getRequest(lot.getRequestId());

                //update request table for new interval
                Request request2 = new Request();
                request2.setStatus("Break Interval");
                request2.setFlag("0");
                request2.setModifiedBy(userSession.getFullname());
                request2.setId(requestId);
                request2.setOldRequestId(lot.getRequestId());
                request2.setGts(req3.getGts());
                request2.setChamber(req3.getChamber());
                request2.setChamberLocation(req3.getChamberLocation());
                request2.setShippingDate(req3.getShippingDate());
                request2.setDoNumber(req3.getDoNumber());
                request2.setQuantity(quantity);
                request2.setTestCondition(req3.getTestCondition());
                RequestDAO req2D = new RequestDAO();
                QueryResult queryResult3 = req2D.updateRequestBreakInterval(request2);

                //update lot penang table for old interval
                LotPenang oldLot = new LotPenang();
                oldLot.setRequestId(lot.getRequestId());
                oldLot.setFlag("2");
                oldLot.setStatus("Cancel Interval");
                oldLot.setId(oldLotPenangId);
                LotPenangDAO oldLotD = new LotPenangDAO();
                QueryResult queryResult4 = oldLotD.updateLotPenangWhenBreakInterval(oldLot);

                String remarks2 = remarks;
                if (remarks.contains(",")) {
                    remarks2 = remarks.replaceAll(",", " ");
                }

                //create dots_interval csv file 
                File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                File file = new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");

                if (file.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);

                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //new interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //estimate_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //estimate_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getRequestId()); //old_request_id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Break Interval"); //status
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(requestId + "_" + fileUpload.getOriginalFilename()); //tripTicketPath
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamberLevel
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //testCondition
                        fileWriter.append(COMMA_DELIMITER);
                        if ("".equals(remarks2)) {
                            fileWriter.append("null"); //remarks
                        } else {
                            fileWriter.append(remarks2); //remarks
                        }
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
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");
                        LOGGER.info("no file yet");

                        //Adding the header
                        fileWriter.append(HEADER2);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //new interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //estimate_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //estimate_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getRequestId()); //old_request_id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Break Interval"); //status
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(requestId + "_" + fileUpload.getOriginalFilename()); //tripTicketPath
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamberLevel
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //testCondition
                        fileWriter.append(COMMA_DELIMITER);
                        if ("".equals(remarks2)) {
                            fileWriter.append("null"); //remarks
                        } else {
                            fileWriter.append(remarks2); //remarks
                        }
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

                //create dots_planner csv
                File filePlanner = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                      File file = new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");

                if (filePlanner.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                             fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv", true);

                        TestConditionDAO testD = new TestConditionDAO();
                        TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getDoNumber()); //do_no
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
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamber_level
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //expected_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //test_condition
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(null); //remarks
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("null"); //shipping_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Break Interval"); //status
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

                        TestConditionDAO testD = new TestConditionDAO();
                        TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                        //Adding the header
                        fileWriter.append(HEADER4);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getDoNumber()); //do_no
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
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamber_level
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //expected_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //test_condition
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(null); //remarks
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("null"); //shipping_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Break Interval"); //status
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

                //email for csv
                EmailSender emailSenderToHIMSSF = new EmailSender();
                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                user.setFullname("POTS");
                String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                emailSenderToHIMSSF.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to,
                        // attachment file
                        new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                        //                        new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                        //                    subject
                        "Break Interval",
                        //                    msg
                        "Break Interval. "
                );

                //email for trip ticket
                EmailSender emailSenderToPOTS = new EmailSender();
                com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                user1.setFullname("POTS");
                String[] to1 = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                String[] to1 = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                emailSenderToPOTS.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user1,
                        //                    to
                        to1,
                        // attachment file
                        new File(stringPath),
                        //                    subject
                        "Break Interval",
                        //                    msg
                        "Break Interval. "
                );
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            lotPenangDAO = new LotPenangDAO();
            LotPenang lotPenang2 = lotPenangDAO.getLotPenang(oldLotPenangId);

            model.addAttribute("lotPenang", lotPenang2);
            String groupId = userSession.getGroup();
            model.addAttribute("groupId", groupId);
//            return "lotPenang/breakInterval";
            return "redirect:/lotPenang/breakInterval/" + oldLotPenangId;
        }
        return "redirect:/lotPenang";
    }

    @RequestMapping(value = "/download/{lotPenangId}", method = RequestMethod.GET)
    public void doDownload(HttpServletRequest request,
            @PathVariable("lotPenangId") String lotPenangId,
            HttpServletResponse response) throws IOException {

        LotPenangDAO lotD = new LotPenangDAO();
        LotPenang lot = lotD.getLotPenangWithTripticketPath(lotPenangId);

        // construct the complete absolute path of the file
        String fullPath = lot.getTripTicketPath();
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

    @RequestMapping(value = "/changeTripTicket", method = RequestMethod.POST)
    public String changeTripTicket(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String reqId,
            @RequestParam(required = false) String lotPId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) MultipartFile fileUpload
    ) throws IOException {
        String stringPath = "";
        //upload file
        if (fileUpload.isEmpty()) {
            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/lotPenang/breakIntervalSave";
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = fileUpload.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + reqId + "_" + fileUpload.getOriginalFilename());
            Files.write(path, bytes);
            stringPath = path.toString();
            LOGGER.info("path : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        LotPenang lot = new LotPenang();
        lot.setTripTicketPath(stringPath);
        lot.setId(lotPId);
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.updateLotPenangChangeTripTicket(lot);

        if (queryResult.getResult() == 1) {

            Log log = new Log();
            log.setRequestId(reqId);
            log.setModule("Lot Penang");
            log.setStatus("Change Trip Ticket");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            RequestDAO reqD = new RequestDAO();
            Request req = reqD.getRequestWithViewDate(reqId);
            args = new String[1];
            args[0] = req.getRmsEvent() + " - " + req.getInterval();

            //update csv
            File filePenang = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//            File filePenang = new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");

            if (filePenang.exists()) {
                LOGGER.info("dh ada header");
                FileWriter fileWriter = null;
                FileReader fileReader = null;
                try {
                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);
                    fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
                    String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv";
//                    fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);
//                    fileReader = new FileReader("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                    String targetLocation = "D:\\DOTS\\DOTS_CSV\\dots_intervals.csv";

                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String data = bufferedReader.readLine();

                    StringBuilder buff = new StringBuilder();

                    boolean flag = false;
                    boolean sentEmail = false;
                    int row = 0;
                    while (data != null && flag == false) {
                        buff.append(data).append(System.getProperty("line.separator"));

                        String[] split = data.split(",");
                        LotPenangUploadTemp inventoryPenang = new LotPenangUploadTemp(
                                split[0], split[1], split[2],
                                split[3], split[4], split[5],
                                split[6], split[7] //status = [18]
                        );
                        if (split[0].equals(reqId)) {
                            CSV csv = new CSV();
                            csv.open(new File(targetLocation));
                            csv.put(7, row, reqId + "_" + fileUpload.getOriginalFilename());
                            csv.save(new File(targetLocation));
                            sentEmail = true;
                            flag = true;
                        } else {
                            flag = false;
                        }

                        data = bufferedReader.readLine();

                        row++;
                    }
                    bufferedReader.close();
                    fileReader.close();

                    if (sentEmail == true) {
                        //email csv
                        EmailSender emailSenderToHIMSSF = new EmailSender();
                        com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                        user.setFullname("POTS");
                        String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                        String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                        emailSenderToHIMSSF.htmlEmailWithAttachment(
                                servletContext,
                                //                    user name
                                user,
                                //                    to
                                to,
                                // attachment file
                                new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                                //                                new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                                //                    subject
                                "Break Interval - Change Trip Ticket",
                                //                    msg
                                "Break Interval - Change Trip Ticket. "
                        );

                        //email for trip ticket
                        EmailSender emailSenderToPOTS = new EmailSender();
                        com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                        user1.setFullname("POTS");
                        String[] to1 = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                        String[] to1 = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                        emailSenderToPOTS.htmlEmailWithAttachment(
                                servletContext,
                                //                    user name
                                user1,
                                //                    to
                                to1,
                                // attachment file
                                new File(stringPath),
                                //                    subject
                                "Break Interval - Change Trip Ticket",
                                //                    msg
                                "Break Interval - Change Trip Ticket. "
                        );
                    }
                } catch (Exception ee) {
                    System.out.println("Error 1 occured while append the fileWriter");
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException ie) {
                        System.out.println("Error 2 occured while closing the fileWriter");
                    }
                }
            } else {
                LOGGER.info("File dots_planner not exists.................");
            }
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }

        return "redirect:/lotPenang/edit/" + lotPId;
    }

    @RequestMapping(value = "/newInterval/{lotPenangId}", method = RequestMethod.GET)
    public String newInterval(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("lotPenangId") String lotPenangId
    ) {

        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        LotPenang lotPenang = lotPenangDAO.getLotPenang(lotPenangId);

        model.addAttribute("lotPenang", lotPenang);
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        return "lotPenang/newInterval";
    }

    @RequestMapping(value = "/newIntervalSave", method = RequestMethod.POST)
    public String newIntervalSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) String oldLotPenangId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String interval
    ) {
        String event = "";
        String rms = "";
        String lotId = "";
        if (barcode.length() < 6) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
        } else {
            rms = barcode.substring(0, 6);
        }
        if (barcode.length() < 7) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
        } else {
            lotId = barcode.substring(6, 7);
        }
        if (barcode.contains("_")) {
            event = barcode.split("_")[1];
        } else {
            return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
        }
        RequestDAO requestDAO = new RequestDAO();
        int count = requestDAO.getCountRequestRmsLotEventIntervalFlag0(rms, lotId, event, interval);
        if (count == 0) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
        } else if (count > 1) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event found more than 1. Please re-check");
            return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
        } else {
            requestDAO = new RequestDAO();
            Request request = requestDAO.getRequestByRmsLotEventIntervalFlag0(barcode, interval);
            if ("Pending Shipment".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
            } else if ("Pending Pre VM".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
            } else if ("Ready for Shipment".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in DO list. Please re-check");
                return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
            } else if ("New".equals(request.getStatus())) {

                //get old request id
                LotPenangDAO lotD = new LotPenangDAO();
                LotPenang lot = lotD.getLotPenang(oldLotPenangId);
                requestDAO = new RequestDAO();
                Request request2 = requestDAO.getRequest(lot.getRequestId());

                String event2 = request.getEvent().substring(0, 2);
                ChamberDAO chamberD = new ChamberDAO();
                List<Chamber> chamber = chamberD.getChamberListByChamberNameAndEvent(request2.getChamber(), event2);
                TestConditionDAO testD = new TestConditionDAO();
                List<TestCondition> test = testD.getTestConditionList();
                ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
                List<ParameterDetails> level = sDAO.getGroupParameterDetailList(request2.getChamberLocation(), "017");
                model.addAttribute("chamber", chamber);
                model.addAttribute("test", test);
                model.addAttribute("level", level);
                model.addAttribute("request", request);
                model.addAttribute("oldLotPenangId", oldLotPenangId);
                return "lotPenang/newIntervalEdit";
            } else if ("Closed".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already closed. Please re-check");
                return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
            } else {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already shipped to Penang. Please re-check");
                return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
            }
        }
    }

    @RequestMapping(value = "/newIntervalUpdate", method = RequestMethod.POST)
    public String newIntervalUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String oldLotPenangId,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String testCondition,
            @RequestParam(required = false) String chamber,
            @RequestParam(required = false) String chamberLevel,
            @RequestParam(required = false) MultipartFile fileUpload,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        String stringPath = "";
        //upload file
        if (fileUpload.isEmpty()) {
            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/lotPenang/newIntervalSave";
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = fileUpload.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + requestId + "_" + fileUpload.getOriginalFilename());
            Files.write(path, bytes);
            stringPath = path.toString();
            LOGGER.info("path : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //get data from old lot penang table
        LOGGER.info("oldLotPenangId : " + oldLotPenangId);
        LotPenangDAO lotD = new LotPenangDAO();
        LotPenang lot = lotD.getLotPenang(oldLotPenangId);

        //insert new lot penang
        LotPenang lotPenang = new LotPenang();
        lotPenang.setDoNumber(lot.getDoNumber());
        lotPenang.setRequestId(requestId);
        lotPenang.setRmsEvent(lot.getRmsEvent());
        lotPenang.setStatus("Pending Loading");
        lotPenang.setReceivedQuantity(lot.getReceivedQuantity());
        lotPenang.setReceivedDate(lot.getReceivedDate());
        lotPenang.setReceivedBy(lot.getReceivedBy());
        lotPenang.setReceivedVerificationStatus(lot.getReceivedVerificationStatus());
        lotPenang.setReceivedVerificationDate(lot.getReceivedVerificationDate());
        lotPenang.setReceivedMixStatus(lot.getReceivedMixStatus());
        lotPenang.setReceivedMixRemarks(lot.getReceivedMixRemarks());
        lotPenang.setReceivedDemountStatus(lot.getReceivedDemountStatus());
        lotPenang.setReceivedDemountRemarks(lot.getReceivedDemountRemarks());
        lotPenang.setReceivedBrokenStatus(lot.getReceivedBrokenStatus());
        lotPenang.setReceivedBrokenRemarks(lot.getReceivedBrokenRemarks());
        lotPenang.setPreVmDate(lot.getPreVmDate());
        lotPenang.setCreatedBy(userSession.getFullname());
        lotPenang.setOldLotPenangId(oldLotPenangId);
        lotPenang.setFlag("0");
        lotPenang.setTripTicketPath(stringPath);
        lotPenang.setPotsNotify("0");
        lotPenang.setIntervalRemarks(remarks);
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.insertLotPenangNewInterval(lotPenang);

        //get data from requesttable
        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithViewDate(requestId);
        args = new String[1];
        args[0] = lot.getRmsEvent() + " - " + req.getInterval();
        if (queryResult.getGeneratedKey() != null) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Penang");
            log.setStatus("Added New Interval");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //update request table for old interval
            Request request = new Request();
            request.setFlag("0");
            request.setNewRequestId(requestId);
            request.setModifiedBy(userSession.getFullname());
            request.setId(lot.getRequestId());
            reqD = new RequestDAO();
            QueryResult queryResult2 = reqD.updateRequestNewInterval(request);
            if (queryResult2.getResult() > 0) {

                log = new Log();
                log.setRequestId(lot.getRequestId());
                log.setModule("Lot Penang");
                log.setStatus("Cancel New Interval [Added New Interval]");
                log.setCreatedBy(userSession.getFullname());
                logD = new LogDAO();
                QueryResult addOldLog = logD.insertLog(log);

                //extract data from old request table
                RequestDAO reqD3 = new RequestDAO();
                Request req3 = reqD3.getRequest(lot.getRequestId());

                //update request table for new interval
                Request request2 = new Request();
                request2.setStatus("Pending Loading");
                request2.setFlag("0");
                request2.setModifiedBy(userSession.getFullname());
                request2.setId(requestId);
                request2.setOldRequestId(lot.getRequestId());
                request2.setGts(req3.getGts());
                request2.setChamber(chamber);
                request2.setChamberLocation(chamberLevel);
                request2.setShippingDate(req3.getShippingDate());
                request2.setDoNumber(req3.getDoNumber());
                request2.setQuantity(quantity);
                request2.setTestCondition(testCondition);
                RequestDAO req2D = new RequestDAO();
                QueryResult queryResult3 = req2D.updateRequestBreakInterval(request2);

                //update lot penang table for old interval
                LotPenang oldLot = new LotPenang();
                oldLot.setRequestId(lot.getRequestId());
                oldLot.setFlag("0");
                oldLot.setNewLotPenangId(queryResult.getGeneratedKey());
                oldLot.setId(oldLotPenangId);
                LotPenangDAO oldLotD = new LotPenangDAO();
                QueryResult queryResult4 = oldLotD.updateLotPenangWhenNewInterval(oldLot);

                String remarks2 = remarks;
                if (remarks.contains(",")) {
                    remarks2 = remarks.replaceAll(",", " ");
                }

                //create csv file
                File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                File file = new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");

                if (file.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);

                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //new interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //estimate_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //estimate_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getRequestId()); //old_request_id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("New Interval"); //status
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(requestId + "_" + fileUpload.getOriginalFilename()); //tripTicketPath
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamber); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamberLevel); //chamberLevel
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(testCondition); //testCondition
                        fileWriter.append(COMMA_DELIMITER);
                        if ("".equals(remarks2)) {
                            fileWriter.append("null"); //remarks
                        } else {
                            fileWriter.append(remarks2); //remarks
                        }
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
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");
                        LOGGER.info("no file yet");

                        //Adding the header
                        fileWriter.append(HEADER2);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //new interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //estimate_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //estimate_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getRequestId()); //old_request_id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("New Interval"); //status
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(requestId + "_" + fileUpload.getOriginalFilename()); //tripTicketPath
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamber); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamberLevel); //chamberLevel
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(testCondition); //testCondition
                        fileWriter.append(COMMA_DELIMITER);
                        if ("".equals(remarks2)) {
                            fileWriter.append("null"); //remarks
                        } else {
                            fileWriter.append(remarks2); //remarks
                        }
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

                //create dots_planner csv
                File filePlanner = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                      File file = new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");

                if (filePlanner.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                             fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv", true);

                        TestConditionDAO testD = new TestConditionDAO();
                        TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getDoNumber()); //do_no
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
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamber); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamberLevel); //chamber_level
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //expected_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(testCondition); //test_condition
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(null); //remarks
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("null"); //shipping_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("New Interval"); //status
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

                        TestConditionDAO testD = new TestConditionDAO();
                        TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                        //Adding the header
                        fileWriter.append(HEADER4);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getDoNumber()); //do_no
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
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamber); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(chamberLevel); //chamber_level
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //expected_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(testCondition); //test_condition
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(null); //remarks
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("null"); //shipping_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("New Interval"); //status
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
                //email for csv
                EmailSender emailSenderToHIMSSF = new EmailSender();
                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                user.setFullname("POTS");
                String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                emailSenderToHIMSSF.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to,
                        // attachment file
                        new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                        //                        new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                        //                    subject
                        "Break Interval",
                        //                    msg
                        "Break Interval. "
                );

                //email for trip ticket
                EmailSender emailSenderToPOTS = new EmailSender();
                com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                user1.setFullname("POTS");
                String[] to1 = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                String[] to1 = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                emailSenderToPOTS.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user1,
                        //                    to
                        to1,
                        // attachment file
                        new File(stringPath),
                        //                    subject
                        "Break Interval",
                        //                    msg
                        "Break Interval. "
                );
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            lotPenangDAO = new LotPenangDAO();
            LotPenang lotPenang2 = lotPenangDAO.getLotPenang(oldLotPenangId);

            model.addAttribute("lotPenang", lotPenang2);
            String groupId = userSession.getGroup();
            model.addAttribute("groupId", groupId);
//            return "lotPenang/breakInterval";
            return "redirect:/lotPenang/newInterval/" + oldLotPenangId;
        }
        return "redirect:/lotPenang";
    }

    @RequestMapping(value = "/reviseInterval/{lotPenangId}", method = RequestMethod.GET)
    public String changeInterval(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("lotPenangId") String lotPenangId
    ) {

        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        LotPenang lotPenang = lotPenangDAO.getLotPenang(lotPenangId);

        model.addAttribute("lotPenang", lotPenang);
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        return "lotPenang/reviseInterval";
    }

    @RequestMapping(value = "/reviseIntervalSave", method = RequestMethod.POST)
    public String changeIntervalSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) String oldLotPenangId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String interval
    ) {
        String event = "";
        String rms = "";
        String lotId = "";
        if (barcode.length() < 6) {
//            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        } else {
            rms = barcode.substring(0, 6);
        }
        if (barcode.length() < 7) {
//            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
//            return "redirect:lotPenang/breakInterval";
            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        } else {
            lotId = barcode.substring(6, 7);
        }
        if (barcode.contains("_")) {
            event = barcode.split("_")[1];
        } else {
//            model.addAttribute("error", "RMS#_Event not found. Please re-check");
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
//            return "lotPenang/breakInterval";
            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        }
        RequestDAO requestDAO = new RequestDAO();
        int count = requestDAO.getCountRequestRmsLotEventIntervalFlag0(rms, lotId, event, interval);
        if (count == 0) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event not found. Please re-check");
            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        } else if (count > 1) {
            redirectAttrs.addFlashAttribute("error", "RMS#_Event found more than 1. Please re-check");
            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        } else {
            requestDAO = new RequestDAO();
            Request request = requestDAO.getRequestByRmsLotEventIntervalFlag0(barcode, interval);
            if ("Pending Shipment".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
            } else if ("Pending Pre VM".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in the list. Please re-check");
                return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
            } else if ("Ready for Shipment".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already in DO list. Please re-check");
                return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
            } else if ("New".equals(request.getStatus())) {
                model.addAttribute("request", request);
                model.addAttribute("oldLotPenangId", oldLotPenangId);
                return "lotPenang/reviseIntervalEdit";
            } else if ("Closed".equals(request.getStatus())) {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already closed. Please re-check");
                return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
            } else {
                redirectAttrs.addFlashAttribute("error", "RMS#_Event already shipped to Penang. Please re-check");
                return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
            }
        }
    }

    @RequestMapping(value = "/reviseIntervalUpdate", method = RequestMethod.POST)
    public String changeIntervalUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String oldLotPenangId,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) MultipartFile fileUpload,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        String stringPath = "";
        //upload file
        if (fileUpload.isEmpty()) {
            redirectAttrs.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/lotPenang/reviseIntervalSave";
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = fileUpload.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + requestId + "_" + fileUpload.getOriginalFilename());
            Files.write(path, bytes);
            stringPath = path.toString();
            LOGGER.info("path : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //get data from old lot penang table
        LOGGER.info("oldLotPenangId : " + oldLotPenangId);
        LotPenangDAO lotD = new LotPenangDAO();
        LotPenang lot = lotD.getLotPenang(oldLotPenangId);
        LotPenang lotPenang = new LotPenang();
        lotPenang.setDoNumber(lot.getDoNumber());
        lotPenang.setRequestId(requestId);
        lotPenang.setRmsEvent(lot.getRmsEvent());
        lotPenang.setChamberId(lot.getChamberId());
        lotPenang.setChamberLevel(lot.getChamberLevel());
        lotPenang.setLoadingDate(lot.getLoadingDate());
        lotPenang.setLoadingRemarks(lot.getLoadingRemarks());
        lotPenang.setTestCondition(lot.getTestCondition());
        lotPenang.setLoadingBy(lot.getLoadingBy());
        lotPenang.setStatus(lot.getStatus() + " - Revise Interval");
        lotPenang.setReceivedQuantity(lot.getReceivedQuantity());
        lotPenang.setReceivedDate(lot.getReceivedDate());
        lotPenang.setReceivedBy(lot.getReceivedBy());
        lotPenang.setReceivedVerificationStatus(lot.getReceivedVerificationStatus());
        lotPenang.setReceivedVerificationDate(lot.getReceivedVerificationDate());
        lotPenang.setReceivedMixStatus(lot.getReceivedMixStatus());
        lotPenang.setReceivedMixRemarks(lot.getReceivedMixRemarks());
        lotPenang.setReceivedDemountStatus(lot.getReceivedDemountStatus());
        lotPenang.setReceivedDemountRemarks(lot.getReceivedDemountRemarks());
        lotPenang.setReceivedBrokenStatus(lot.getReceivedBrokenStatus());
        lotPenang.setReceivedBrokenRemarks(lot.getReceivedBrokenRemarks());
        lotPenang.setPreVmDate(lot.getPreVmDate());
        lotPenang.setCreatedBy(userSession.getFullname());
        lotPenang.setOldLotPenangId(oldLotPenangId);
        lotPenang.setFlag("0");
        lotPenang.setTripTicketPath(stringPath);
        lotPenang.setPotsNotify("0");
        lotPenang.setIntervalRemarks(remarks);
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.insertLotPenangBreakInterval(lotPenang);

        //get data from requesttable
        LOGGER.info("requestId : " + requestId);
        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithViewDate(requestId);
        args = new String[1];
        args[0] = lot.getRmsEvent() + " - " + req.getInterval();
        LOGGER.info("queryResult.getGeneratedKey() : " + queryResult.getGeneratedKey());
        if (queryResult.getGeneratedKey() != null) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Penang");
            log.setStatus("Added Revise Interval");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //update request table for old interval
            LOGGER.info("lot.getRequestId() : " + lot.getRequestId());
            Request request = new Request();
            request.setStatus("Revise Interval");
            request.setFlag("2");
            request.setModifiedBy(userSession.getFullname());
            request.setId(lot.getRequestId());
            reqD = new RequestDAO();
            QueryResult queryResult2 = reqD.updateRequestCancelInterval(request);
            if (queryResult2.getResult() > 0) {

                log = new Log();
                log.setRequestId(lot.getRequestId());
                log.setModule("Lot Penang");
                log.setStatus("Cancel Interval [Added Revise Interval]");
                log.setCreatedBy(userSession.getFullname());
                logD = new LogDAO();
                QueryResult addOldLog = logD.insertLog(log);

                //extract data from old request table
                RequestDAO reqD3 = new RequestDAO();
                Request req3 = reqD3.getRequest(lot.getRequestId());

                //update request table for new interval
                Request request2 = new Request();
                request2.setStatus(lot.getStatus() + " - Revise Interval");
                request2.setFlag("0");
                request2.setModifiedBy(userSession.getFullname());
                request2.setId(requestId);
                request2.setOldRequestId(lot.getRequestId());
                request2.setGts(req3.getGts());
                request2.setChamber(req3.getChamber());
                request2.setChamberLocation(req3.getChamberLocation());
                request2.setShippingDate(req3.getShippingDate());
                request2.setDoNumber(req3.getDoNumber());
                request2.setQuantity(quantity);
                request2.setTestCondition(req3.getTestCondition());
                RequestDAO req2D = new RequestDAO();
                QueryResult queryResult3 = req2D.updateRequestBreakInterval(request2);

                //update lot penang table for old interval
                LotPenang oldLot = new LotPenang();
                oldLot.setRequestId(lot.getRequestId());
                oldLot.setFlag("2");
                oldLot.setStatus("Revise Interval");
                oldLot.setId(oldLotPenangId);
                LotPenangDAO oldLotD = new LotPenangDAO();
                QueryResult queryResult4 = oldLotD.updateLotPenangWhenBreakInterval(oldLot);

                String remarks2 = remarks;
                if (remarks.contains(",")) {
                    remarks2 = remarks.replaceAll(",", " ");
                }

                //create csv file
                File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                File file = new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");

                if (file.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);

                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //new interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //estimate_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //estimate_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getRequestId()); //old_request_id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Revise Interval"); //status
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(requestId + "_" + fileUpload.getOriginalFilename()); //tripTicketPath
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamberLevel
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //testCondition
                        fileWriter.append(COMMA_DELIMITER);
                        if ("".equals(remarks2)) {
                            fileWriter.append("null"); //remarks
                        } else {
                            fileWriter.append(remarks2); //remarks
                        }
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
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                        fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");
                        LOGGER.info("no file yet");

                        //Adding the header
                        fileWriter.append(HEADER2);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //new interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //estimate_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //estimate_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(lot.getRequestId()); //old_request_id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Revise Interval"); //status
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(requestId + "_" + fileUpload.getOriginalFilename()); //tripTicketPath
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamberLevel
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //testCondition
                        fileWriter.append(COMMA_DELIMITER);
                        if ("".equals(remarks2)) {
                            fileWriter.append("null"); //remarks
                        } else {
                            fileWriter.append(remarks2); //remarks
                        }
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

                //create dots_planner csv
                File filePlanner = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                      File file = new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");

                if (filePlanner.exists()) {
                    //create csv file
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                             fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv", true);

                        TestConditionDAO testD = new TestConditionDAO();
                        TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getDoNumber()); //do_no
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
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamber_level
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //expected_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //test_condition
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(null); //remarks
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("null"); //shipping_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Revise Interval"); //status
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

                        TestConditionDAO testD = new TestConditionDAO();
                        TestCondition test = testD.getTestConditionByCondition(req.getTestCondition());
                        //Adding the header
                        fileWriter.append(HEADER4);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);
                        fileWriter.append(requestId); //id
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getGts()); //gts
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getDoNumber()); //do_no
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
                        fileWriter.append(quantity); //quantity
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getInterval()); //interval
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamber()); //chamber
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getChamberLocation()); //chamber_level
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getLoadingDate()); //expected_loading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req.getUnloadingDate()); //expected_unloading_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(req3.getTestCondition()); //test_condition
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(null); //remarks
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("null"); //shipping_date
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append("Revise Interval"); //status
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

                //email for csv
                EmailSender emailSenderToHIMSSF = new EmailSender();
                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                user.setFullname("POTS");
                String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                emailSenderToHIMSSF.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to,
                        // attachment file
                        new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                        //                        new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                        //                    subject
                        "Revise Interval",
                        //                    msg
                        "Revise Interval. "
                );

                //email for trip ticket
                EmailSender emailSenderToPOTS = new EmailSender();
                com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
                user1.setFullname("POTS");
                String[] to1 = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//                String[] to1 = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                emailSenderToPOTS.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user1,
                        //                    to
                        to1,
                        // attachment file
                        new File(stringPath),
                        //                    subject
                        "Revise Interval",
                        //                    msg
                        "Revise Interval. "
                );
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            lotPenangDAO = new LotPenangDAO();
            LotPenang lotPenang2 = lotPenangDAO.getLotPenang(oldLotPenangId);

            model.addAttribute("lotPenang", lotPenang2);
            String groupId = userSession.getGroup();
            model.addAttribute("groupId", groupId);
//            return "lotPenang/breakInterval";
            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        }
        return "redirect:/lotPenang";
    }

    @RequestMapping(value = "/cancelInterval/{lotPenangId}", method = RequestMethod.GET)
    public String cancelInterval(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("lotPenangId") String lotPenangId
    ) {
        String stringPath = "";

        LotPenangDAO lotD = new LotPenangDAO();
        LotPenang lot = lotD.getLotPenang(lotPenangId);

        LotPenang lotPenang = new LotPenang();
        lotPenang.setStatus("Cancel Interval");
        lotPenang.setPotsNotify("0");
        lotPenang.setFlag("0");
        lotPenang.setId(lotPenangId);
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        QueryResult queryResult = lotPenangDAO.updateLotPenangWhenCancelInterval(lotPenang);

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequestWithViewDate(lot.getRequestId());
        args = new String[1];
        args[0] = lot.getRmsEvent() + " - " + req.getInterval();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", "Cancellation for " + lot.getRmsEvent() + " - " + req.getInterval() + " is successful");

            Log log = new Log();
            log.setRequestId(lot.getRequestId());
            log.setModule("Lot Penang");
            log.setStatus("Added Cancel Interval");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //update request       
            Request request = new Request();
            request.setStatus("Cancel Interval");
            request.setModifiedBy(userSession.getFullname());
            request.setFlag("2");
            request.setId(lot.getRequestId());
            RequestDAO reqD2 = new RequestDAO();
            QueryResult queryResult1 = reqD2.updateRequestStatusAndFlagWithModifedBy(request);

            RequestDAO reqDNew = new RequestDAO();
            Request reqNew = reqDNew.getRequest(lot.getRequestId());
            if (queryResult1.getResult() == 1) {

                //insert new request status new for same interval
                Request newRequest = new Request();
                newRequest.setRmsEvent(reqNew.getRmsEvent());
                newRequest.setRms(reqNew.getRms());
                newRequest.setEvent(reqNew.getEvent());
                newRequest.setLotId(reqNew.getLotId());
                newRequest.setDevice(reqNew.getDevice());
                newRequest.setPackages(reqNew.getPackages());
                newRequest.setInterval(reqNew.getInterval());
                newRequest.setQuantity(reqNew.getQuantity());
                newRequest.setExpectedTestCondition(reqNew.getExpectedTestCondition());
                newRequest.setLoadingDate(reqNew.getLoadingDate());
                newRequest.setUnloadingDate(reqNew.getUnloadingDate());
                newRequest.setStatus("New");
                newRequest.setRmsId(reqNew.getRmsId());
                newRequest.setCreatedBy(userSession.getFullname());
                newRequest.setFlag("0");
                reqDNew = new RequestDAO();
                QueryResult queryResultNew = reqDNew.insertRequestWithRmsIdFromFtp(newRequest);

                log = new Log();
                log.setRequestId(lot.getRequestId());
                log.setModule("Lot Request");
                log.setStatus("Added New Request After Cancel Interval");
                log.setCreatedBy(userSession.getFullname());
                logD = new LogDAO();
                QueryResult addNewLog = logD.insertLog(log);

            }

            //create csv file
            File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//            File file = new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");

            if (file.exists()) {
                //create csv file
                LOGGER.info("tiada header");
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);
//                    fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv", true);

                    //New Line after the header
                    fileWriter.append(LINE_SEPARATOR);
                    fileWriter.append(lot.getRequestId()); //id
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getInterval()); //new interval
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getQuantity()); //quantity
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getLoadingDate()); //estimate_loading_date
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getUnloadingDate()); //estimate_unloading_date
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(""); //old_request_id
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("Cancel Interval"); //status
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(""); //tripTicketPath
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getChamber()); //chamber
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getChamberLocation()); //chamberLevel
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getTestCondition()); //testCondition
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("null"); //remarks
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
                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv");
//                    fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv");
                    LOGGER.info("no file yet");

                    //Adding the header
                    fileWriter.append(HEADER2);
                    //New Line after the header
                    fileWriter.append(LINE_SEPARATOR);
                    fileWriter.append(lot.getRequestId()); //id
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getInterval()); //new interval
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getQuantity()); //quantity
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getLoadingDate()); //estimate_loading_date
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getUnloadingDate()); //estimate_unloading_date
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(""); //old_request_id
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("Cancel Interval"); //status
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(""); //tripTicketPath
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getChamber()); //chamber
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getChamberLocation()); //chamberLevel
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(reqNew.getTestCondition()); //testCondition
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("null"); //remarks
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
            //email for csv
            EmailSender emailSenderToHIMSSF = new EmailSender();
            com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
            user.setFullname("POTS");
            String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
//            String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
            emailSenderToHIMSSF.htmlEmailWithAttachment(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    to,
                    // attachment file
                    new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                    //                    new File("D:\\DOTS\\DOTS_CSV\\dots_intervals.csv"),
                    //                    subject
                    "Cancel Interval",
                    //                    msg
                    "Cancel Interval. "
            );
//            //email for trip ticket
//            EmailSender emailSenderToPOTS = new EmailSender();
//            com.onsemi.dots.model.User user1 = new com.onsemi.dots.model.User();
//            user1.setFullname("POTS");
//            String[] to1 = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
//            emailSenderToPOTS.htmlEmailWithAttachment(
//                    servletContext,
//                    //                    user name
//                    user1,
//                    //                    to
//                    to1,
//                    // attachment file
//                    new File(stringPath),
//                    //                    subject
//                    "Cancel Interval",
//                    //                    msg
//                    "Cancel Interval. "
//            );

        } else {
            redirectAttrs.addFlashAttribute("error", "Fail to cancel " + lot.getRmsEvent() + " - " + req.getInterval());
//            lotPenangDAO = new LotPenangDAO();
//            LotPenang lotPenang2 = lotPenangDAO.getLotPenang(oldLotPenangId);
//
//            model.addAttribute("lotPenang", lotPenang2);
//            String groupId = userSession.getGroup();
//            model.addAttribute("groupId", groupId);
////            return "lotPenang/breakInterval";
//            return "redirect:/lotPenang/reviseInterval/" + oldLotPenangId;
        }
        return "redirect:/lotPenang";
    }

    @RequestMapping(value = "/checkRequestId/{requestId}", method = RequestMethod.GET)
    public String edit1(
            Model model, @ModelAttribute UserSession userSession,
            @PathVariable("requestId") String requestId
    ) {

        LotPenangDAO lotPenangDAO = new LotPenangDAO();

        int count = lotPenangDAO.getCountRequestId(requestId);
        if (count == 1) {
            LotPenangDAO lotD = new LotPenangDAO();
            LotPenang lot = lotD.getLotPenangByRequestId(requestId);
            LOGGER.info("lot.getId()" + lot.getId());
            return "redirect:/lotPenang/edit/" + lot.getId();
        } else {
            return "redirect:/lotPenang";
        }

//        return "lotPenang/edit";
    }

}
