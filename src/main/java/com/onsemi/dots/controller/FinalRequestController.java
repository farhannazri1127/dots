package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.FinalRequestDAO;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.dao.LotPenangDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.model.FinalRequest;
import com.onsemi.dots.model.FinalRequestTemp;
import com.onsemi.dots.model.JSONResponse;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.UserSession;
import com.onsemi.dots.tools.CSV;
import com.onsemi.dots.tools.EmailSender;
import com.onsemi.dots.tools.QueryResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(value = "do/finalRequest")
@SessionAttributes({"userSession"})
public class FinalRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinalRequestController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String finalRequest(
            Model model, @ModelAttribute UserSession userSession
    ) {
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        List<FinalRequest> finalRequestList = finalRequestDAO.getFinalRequestListWithRequestData();
        model.addAttribute("finalRequestList", finalRequestList);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        model.addAttribute("nowDate", dateFormat.format(date));
        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);
        return "finalRequest/finalRequest";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "finalRequest/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String gts,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String shippingDate,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String flag
    ) {
        FinalRequest finalRequest = new FinalRequest();
        finalRequest.setRequestId(requestId);
        finalRequest.setGts(gts);
        finalRequest.setStatus(status);
        finalRequest.setShippingDate(shippingDate);
        finalRequest.setCreatedBy(createdBy);
        finalRequest.setCreatedDate(createdDate);
        finalRequest.setModifiedBy(modifiedBy);
        finalRequest.setModifiedDate(modifiedDate);
        finalRequest.setFlag(flag);
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        QueryResult queryResult = finalRequestDAO.insertFinalRequest(finalRequest);
        args = new String[1];
        args[0] = requestId + " - " + gts;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("finalRequest", finalRequest);
            return "finalRequest/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/do/finalRequest/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{finalRequestId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("finalRequestId") String finalRequestId
    ) {
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        FinalRequest finalRequest = finalRequestDAO.getFinalRequest(finalRequestId);
        model.addAttribute("finalRequest", finalRequest);
        return "finalRequest/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String gts,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String shippingDate,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String flag
    ) {
        FinalRequest finalRequest = new FinalRequest();
        finalRequest.setId(id);
        finalRequest.setRequestId(requestId);
        finalRequest.setGts(gts);
        finalRequest.setStatus(status);
        finalRequest.setShippingDate(shippingDate);
        finalRequest.setCreatedBy(createdBy);
        finalRequest.setCreatedDate(createdDate);
        finalRequest.setModifiedBy(modifiedBy);
        finalRequest.setModifiedDate(modifiedDate);
        finalRequest.setFlag(flag);
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        QueryResult queryResult = finalRequestDAO.updateFinalRequest(finalRequest);
        args = new String[1];
        args[0] = requestId + " - " + gts;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/do/finalRequest/edit/" + id;
    }

    @RequestMapping(value = "/updateGtsAndShipDate", method = RequestMethod.POST)
    public String updateGtsAndShipDate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String gts,
            @RequestParam(required = false) String shippingDate
    ) throws ParseException {
        FinalRequest finalRequest = new FinalRequest();
        finalRequest.setGts(gts);
        finalRequest.setStatus("Ready for Shipment");
        finalRequest.setShippingDate(shippingDate);
        finalRequest.setModifiedBy(userSession.getFullname());
        finalRequest.setFlag("0");

        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        QueryResult queryResult = finalRequestDAO.updateFinalRequestGtsAndShipDate(finalRequest);
        args = new String[1];
        args[0] = gts + " - " + shippingDate;
        if (queryResult.getResult() != 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //set do number
            String year = shippingDate.substring(2, 4);
            String month = shippingDate.substring(5, 7);
            String day = shippingDate.substring(8, 10);
            String shipdate = year + month + day;
            String doNumber = "";
            finalRequestDAO = new FinalRequestDAO();
            String seqcount = finalRequestDAO.getMaxDoNumber(shipdate);
            if (!"0".equals(seqcount)) {
                seqcount = seqcount.substring(6, 9);
            } else {
                LOGGER.info("seqcount0: " + seqcount);
            }
            int seqcountInt = Integer.parseInt(seqcount);
            finalRequestDAO = new FinalRequestDAO();
            List<FinalRequest> list = finalRequestDAO.getFinalRequestListDoNumberNotLikeFlag0(shipdate);
            for (int i = 0; i < list.size(); i++) {
                LOGGER.info("checkkk1!!!");
                String id = list.get(i).getId();
                seqcountInt = seqcountInt + 1;
                String counts = Integer.toString(seqcountInt);
                doNumber = shipdate + StringUtils.leftPad(counts, 3, "0");

                finalRequest = new FinalRequest();
                finalRequest.setDoNumber(doNumber);
                finalRequest.setId(id);
                finalRequestDAO = new FinalRequestDAO();
                QueryResult updateDoNumber = finalRequestDAO.updateFinalRequestDoNumber(finalRequest);

                String reqId = list.get(i).getRequestId();
                Request req = new Request();
                req.setDoNumber(doNumber);
                req.setId(reqId);
                RequestDAO reqUpdate = new RequestDAO();
                QueryResult updateDoNumberRequest = reqUpdate.updateRequestDoNumber(req);

                Log log = new Log();
                log.setRequestId(reqId);
                log.setModule("Lot Request");
                log.setStatus("Added GTS and DO Number");
                log.setCreatedBy(userSession.getFullname());
                LogDAO logD = new LogDAO();
                QueryResult addLog = logD.insertLog(log);

                DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                DateFormat mediumFormatReceived = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
                String receivedDate = shippingDate;
                String shortReceivedDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                updateCsv(reqId, doNumber, gts, shortReceivedDate);
            }

            //update request table
            Request req = new Request();
            req.setGts(gts);
            req.setStatus("Ready for Shipment");
            req.setShippingDate(shippingDate);
            req.setFlag("0");
            req.setModifiedBy(userSession.getFullname());
            RequestDAO reqD = new RequestDAO();
            QueryResult updateReq = reqD.updateRequestforShipDateAndGts(req);

            if (updateReq.getResult() == 0) {
                LOGGER.info("fail to update request table");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/do/finalRequest";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public @ResponseBody
    JSONResponse cancel(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id
    ) {
        JSONResponse response = new JSONResponse();
        FinalRequestDAO finalD = new FinalRequestDAO();
        FinalRequest frequest = finalD.getFinalRequest(id);
        Request request = new Request();
        request.setId(frequest.getRequestId());
        request.setModifiedBy(userSession.getFullname());
        request.setFlag("0");
        RequestDAO requestDAO = new RequestDAO();
        QueryResult queryResult = requestDAO.updateRequestforCancelShipment(request);
        if (queryResult.getResult() <= 0) {
            LOGGER.info("fail dkt request........");
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage(queryResult.getErrorMessage());
            response.setResult(request);
        } else {

            Log log = new Log();
            log.setRequestId(frequest.getRequestId());
            log.setModule("Lot Request");
            log.setStatus("Cancelled from DO List");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //update csv
            File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//            File file = new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");
            if (file.exists()) {
                LOGGER.info("dh ada header");
                FileWriter fileWriter = null;
                FileReader fileReader = null;
                FinalRequestDAO finalDa = new FinalRequestDAO();
                FinalRequest freq = finalDa.getFinalRequest(id);
                try {
                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
                    fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
                    String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv";
//                    fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                    fileReader = new FileReader("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                    String targetLocation = "D:\\DOTS\\DOTS_CSV\\dots_planner.csv";

                    BufferedReader bufferedReader = new BufferedReader(fileReader);
//                    ReversedLinesFileReader reader = new ReversedLinesFileReader(file);
//                    String data = reader.readLine();
                    String data = bufferedReader.readLine();

                    StringBuilder buff = new StringBuilder();

                    boolean flag = false;
                    int row = 0;
                    while (data != null && flag == false) {
                        buff.append(data).append(System.getProperty("line.separator"));

                        String[] split = data.split(",");
                        FinalRequestTemp inventory = new FinalRequestTemp(
                                split[0], split[1], split[2],
                                split[3], split[4], split[5],
                                split[6], split[7], split[8],
                                split[9], split[10], split[11],
                                split[12], split[13], split[14],
                                split[15], split[16], split[17],
                                split[18] //status = [18]
                        );

                        if (split[0].equals(freq.getRequestId())) {
                            CSV csv = new CSV();
                            csv.open(new File(targetLocation));
                            csv.put(0, row, "0");
                            csv.put(18, row, "Cancelled");
                            csv.save(new File(targetLocation));
                            flag = true;
                        } else {
                            flag = false;
                        }
                        data = bufferedReader.readLine();
//                        data = reader.readLine();

                        row++;
                    }
                    bufferedReader.close();
//                    reader.close();
                    fileReader.close();

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
                LOGGER.info("File not exists.................");
            }

            finalD = new FinalRequestDAO();
            FinalRequest fre = finalD.getFinalRequest(id);
            finalD = new FinalRequestDAO();
            QueryResult queryResultF = finalD.deleteFinalRequest(id);
            if (queryResultF.getResult() <= 0) {
                response.setStatus(Boolean.FALSE);
                response.setStatusMessage(queryResultF.getErrorMessage());
                response.setResult(fre);
            } else {
                response.setStatus(Boolean.TRUE);
                response.setStatusMessage("Cancellation Done.");
                response.setResult(request);
            }

        }
        return response;
    }

    @RequestMapping(value = "/delete/{finalRequestId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("finalRequestId") String finalRequestId
    ) {
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        FinalRequest finalRequest = finalRequestDAO.getFinalRequest(finalRequestId);
        finalRequestDAO = new FinalRequestDAO();
        QueryResult queryResult = finalRequestDAO.deleteFinalRequest(finalRequestId);
        args = new String[1];
        args[0] = finalRequest.getRequestId() + " - " + finalRequest.getGts();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/do/finalRequest";
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public String deleteAll(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs
    ) {
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        QueryResult queryResult = finalRequestDAO.deleteFinalRequestAfterShip();
        args = new String[1];
        args[0] = "All shipped rms_event has been ";
        if (queryResult.getResult() != 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", "No data is deleted.");
        }
        return "redirect:/do/finalRequest";
    }

    @RequestMapping(value = "/view/{finalRequestId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("finalRequestId") String finalRequestId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/finalRequest/viewFinalRequestPdf/" + finalRequestId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/finalRequest";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.finalRequest");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewFinalRequestPdf/{finalRequestId}", method = RequestMethod.GET)
    public ModelAndView viewFinalRequestPdf(
            Model model,
            @PathVariable("finalRequestId") String finalRequestId
    ) {
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        FinalRequest finalRequest = finalRequestDAO.getFinalRequest(finalRequestId);
        return new ModelAndView("finalRequestPdf", "finalRequest", finalRequest);
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public ModelAndView viewDoListPdf(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
        FinalRequestDAO finalRequestDAO = new FinalRequestDAO();
        int count = finalRequestDAO.getCountGts();
        if (count > 1) {
            LOGGER.info("Print DO - gts has more than 1");
            return new ModelAndView("doListFailPdf");
        } else if (count == 0) {
            LOGGER.info("Print DO - no gts");
            return new ModelAndView("doListBlankPdf");
        } else {
            finalRequestDAO = new FinalRequestDAO();
            QueryResult queryResult = finalRequestDAO.updateFinalRequestAndRequestTableShipPenangFlag1();
//
            finalRequestDAO = new FinalRequestDAO();
            FinalRequest freq = finalRequestDAO.getGtsAndShipmentDate();
            model.addAttribute("freq", freq);
//
            finalRequestDAO = new FinalRequestDAO();
            List<FinalRequest> finalRequest = finalRequestDAO.getFinalRequestListWithRequestData();
//
            for (FinalRequest e : finalRequest) {
                LotPenangDAO lpD = new LotPenangDAO();
                int countReqId = lpD.getCountRequestId(e.getRequestId());
                if (countReqId == 0) {
                    LotPenang lp = new LotPenang();
                    lp.setDoNumber(e.getDoNumber());
                    lp.setRequestId(e.getRequestId());
                    lp.setRmsEvent(e.getRmsEvent());
                    lp.setCreatedBy(userSession.getFullname());
                    lp.setStatus("Ship to Penang");
                    lp.setFlag("0");
                    lpD = new LotPenangDAO();
                    QueryResult qr = lpD.insertLotPenangWhenShip(lp);

                    Log log = new Log();
                    log.setRequestId(e.getRequestId());
                    log.setModule("Lot Request");
                    log.setStatus("Ship to Penang");
                    log.setCreatedBy(userSession.getFullname());
                    LogDAO logD = new LogDAO();
                    QueryResult addLog = logD.insertLog(log);

                } else {
                    LOGGER.info("[Lot Penang] - request id already exist.");
                }

            }

            EmailSender emailSenderToHIMSSF = new EmailSender();
            com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
            user.setFullname("POTS");
//            String[] to = {"potspenang@gmail.com", "fg79cj@onsemi.com"};
//            String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
            String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
            emailSenderToHIMSSF.htmlEmailWithAttachment(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    to,
                    // attachment file
                    new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv"),
                    //                                        new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv"),
                    //                    subject
                    "List of RMS_event Ship from Rel Lab",
                    //                    msg
                    "List of RMS_event Ship from Rel Lab. "
            );

            return new ModelAndView("doListPdf", "doList", finalRequest);
        }

    }

    public void updateCsv(String reqId, String doNumber, String gts, String shortReceivedDate) {
        //update csv for gts, doNo, shpmentDate
        File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//        File file = new File("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");
        if (file.exists()) {
            LOGGER.info("dh ada header");
            FileWriter fileWriter = null;
            FileReader fileReader = null;
            try {
                fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
                fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
                String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv";
//                fileWriter = new FileWriter("D:\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                fileReader = new FileReader("D:\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                String targetLocation = "D:\\DOTS\\DOTS_CSV\\dots_planner.csv";

                BufferedReader bufferedReader = new BufferedReader(fileReader);
                LOGGER.info(fileReader.toString());
                String data = bufferedReader.readLine();

                StringBuilder buff = new StringBuilder();

                boolean flag = false;
                int row = 0;
                while (data != null && flag == false) {
                    LOGGER.info("start reading file..........");
                    buff.append(data).append(System.getProperty("line.separator"));

                    String[] split = data.split(",");
                    FinalRequestTemp inventory = new FinalRequestTemp(
                            split[0], split[1], split[2],
                            split[3], split[4], split[5],
                            split[6], split[7], split[8],
                            split[9], split[10], split[11],
                            split[12], split[13], split[14],
                            split[15], split[16], split[17],
                            split[18] //status = [18]
                    );

                    if (split[0].equals(reqId)) {
//                            LOGGER.info(row + " : refId found...................." + data);
                        CSV csv = new CSV();
                        csv.open(new File(targetLocation));
                        csv.put(1, row, gts);
                        csv.put(2, row, doNumber);
                        csv.put(17, row, shortReceivedDate);
                        csv.save(new File(targetLocation));
                        flag = true;
                    } else {
                        flag = false;
                    }
                    data = bufferedReader.readLine();
                    row++;
                }
                bufferedReader.close();
                fileReader.close();

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
            LOGGER.info("File not exists.................");
        }
    }
}
