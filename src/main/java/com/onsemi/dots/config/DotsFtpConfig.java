/*
            * To change this license header, choose License Headers in Project Properties.
            * To change this template file, choose Tools | Templates
            * and open the template in the editor.
 */
package com.onsemi.dots.config;

import com.onsemi.dots.dao.AbnormalLoadingDAO;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.dao.LotPenangDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.dao.RmaDAO;
import com.onsemi.dots.model.AbnormalLoading;
import com.onsemi.dots.model.AbnormalLoadingTemp;
import com.onsemi.dots.model.FinalRequestTemp;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.model.LotPenangTemp;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.RequestTemp;
import com.onsemi.dots.model.Rma;
import com.onsemi.dots.model.RmaTemp;
import com.onsemi.dots.tools.CSV;
import com.onsemi.dots.tools.EmailSender;
import com.onsemi.dots.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author fg79cj
 */
@Configuration
@EnableScheduling
public class DotsFtpConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DotsFtpConfig.class);

    @Autowired
    ServletContext servletContext;

//    @Scheduled(cron = "0 20 * * * *") //every hour 20 minutes
    public void DownloadCsv() throws IOException {

        CSVReader csvReader = null;
        //        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");
            //            File file = new File("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");

            if (file.exists()) {
                LOGGER.info("File dots_rms_ftp found!!!");
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);
                //                csvReader = new CSVReader(new FileReader("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<RequestTemp> empList = new ArrayList<RequestTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    RequestTemp emp = new RequestTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5], inventory[6],
                            inventory[7], inventory[8], inventory[9], inventory[10], inventory[11]);
                    empList.add(emp);
                }

                //Lets print the Inventory List
                for (RequestTemp e : empList) {

                    // check rms_event + interval exist or not
                    RequestDAO reqD = new RequestDAO();
//                    int countRms = reqD.getCountRequestRmsLotEventInterval(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval());
                    int countRms = reqD.getCountRequestRmsLotEventIntervalRmsId(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval(), e.getRmsId());
                    if (countRms == 0) {
                        Request req = new Request();
                        req.setRms(e.getRms());
                        req.setEvent(e.getEvent());
                        req.setLotId(e.getLotId());
                        req.setDevice(e.getDevice());
                        req.setPackages(e.getPackages());
                        req.setInterval(e.getInterval());
                        if (e.getQuantity() != null) {
                            if (!"".equals(e.getQuantity())) {
                                req.setQuantity(e.getQuantity());
                            } else {
                                req.setQuantity("0");
                            }
                        } else {
                            req.setQuantity("0");
                        }
                        String expectedCondition = "";
                        if (!"".equals(e.getExpectedMinCondition()) && !"".equals(e.getExpectedMaxCondition())) {
                            expectedCondition = e.getExpectedMinCondition() + " / " + e.getExpectedMaxCondition() + " `C";
                        } else if (!"".equals(e.getExpectedMinCondition()) && "".equals(e.getExpectedMaxCondition())) {
                            expectedCondition = e.getExpectedMinCondition() + " `C";
                        } else if ("".equals(e.getExpectedMinCondition()) && !"".equals(e.getExpectedMaxCondition())) {
                            expectedCondition = e.getExpectedMaxCondition() + " `C";

                        } else {
                            expectedCondition = "";
                        }
                        req.setExpectedTestCondition(expectedCondition);
                        String shortLoadingDate = null;
                        String shortUnloadingDate = null;
                        if (!"".equals(e.getExpectedLoading())) {
                            DateFormat shortFormatLoading = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatLoading = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
                            String loadingdate = e.getExpectedLoading();
                            shortLoadingDate = shortFormatLoading.format(mediumFormatLoading.parse(loadingdate));
                        }
                        if (!"".equals(e.getExpectedUnloading())) {
                            DateFormat shortFormatUnloading = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatUnloading = new SimpleDateFormat("dd-MMM-yy hh:mm a", Locale.ENGLISH);
                            String unloadingdate = e.getExpectedUnloading();
                            shortUnloadingDate = shortFormatUnloading.format(mediumFormatUnloading.parse(unloadingdate));
                        }

                        req.setLoadingDate(shortLoadingDate);
                        req.setUnloadingDate(shortUnloadingDate);
                        req.setStatus("New");

                        String rms_event = e.getRms() + e.getLotId() + "_" + e.getEvent();
                        req.setRmsEvent(rms_event);
                        req.setRmsId(e.getRmsId());
                        req.setFlag("0");
                        req.setCreatedBy("From Ftp");
                        reqD = new RequestDAO();
//                        QueryResult add = reqD.insertRequestFromFtp(req);
                        QueryResult add2 = reqD.insertRequestWithRmsIdFromFtp(req);
                        if (add2.getGeneratedKey().equals("0")) {
                            LOGGER.info("Fail to insert data from FTP");
                        } else {
                            //add log
                            Log log = new Log();
                            log.setRequestId(add2.getGeneratedKey());
                            log.setModule("RMS FTP");
                            log.setStatus("Added from RMS FTP");
                            log.setCreatedBy("RMS FTP");
                            LogDAO logD = new LogDAO();
                            QueryResult addLog = logD.insertLog(log);
                        }

                    } else if (countRms > 1) {
                        LOGGER.info("Data have more than 1 in DB. Please re-check");
                    } else {

                        //update existing interval
                        reqD = new RequestDAO();
//                        Request request = reqD.getRequestByRmsLotEventIntervalForFtp(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval());
                        Request request = reqD.getRequestByRmsLotEventIntervalRmsIdForFtp(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval(), e.getRmsId());

                        String shortLoadingDateDb = "";
                        String shortLoadingDateFtp = "";
                        String shortUnloadingDateDb = "";
                        String shortUnloadingDateFtp = "";
                        if (!"".equals(e.getExpectedLoading())) {
                            DateFormat shortFormatLoadingFtp = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                            DateFormat mediumFormatLoadingFtp = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
                            shortLoadingDateFtp = shortFormatLoadingFtp.format(mediumFormatLoadingFtp.parse(e.getExpectedLoading()));

                        }
                        if (request.getLoadingDate() != null) {
                            DateFormat shortFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                            DateFormat mediumFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            shortLoadingDateDb = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(request.getLoadingDate()));
                        }
                        if (!"".equals(e.getExpectedUnloading())) {
                            DateFormat shortFormatLoadingFtp = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                            DateFormat mediumFormatLoadingFtp = new SimpleDateFormat("dd-MMM-yy hh:mm a", Locale.ENGLISH);
                            shortUnloadingDateFtp = shortFormatLoadingFtp.format(mediumFormatLoadingFtp.parse(e.getExpectedUnloading()));

                        }
                        if (request.getUnloadingDate() != null) {
                            DateFormat shortFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                            DateFormat mediumFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            shortUnloadingDateDb = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(request.getUnloadingDate()));
                        }

                        if (!"".equals(shortLoadingDateFtp) && (!shortLoadingDateDb.equals(shortLoadingDateFtp) || !shortUnloadingDateDb.equals(shortUnloadingDateFtp)) && "0".equals(request.getFlag())) {
                            LOGGER.info("first stage..loadingFtp" + shortLoadingDateFtp + ", shortLoadingDateDb - " + shortLoadingDateDb + ", shortUnloadingDateDb - " + shortUnloadingDateDb + ", shortUnloadingDateFtp - " + shortUnloadingDateFtp);
                            Request req = new Request();

                            DateFormat shortFormatLoading = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatLoading = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
                            String loadingdate = e.getExpectedLoading();
                            String shortLoadingDate = shortFormatLoading.format(mediumFormatLoading.parse(loadingdate));

                            DateFormat shortFormatUnloading = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatUnloading = new SimpleDateFormat("dd-MMM-yy hh:mm a", Locale.ENGLISH);
                            String unloadingdate = e.getExpectedUnloading();
                            String shortUnloadingDate = shortFormatUnloading.format(mediumFormatUnloading.parse(unloadingdate));

                            req.setLoadingDate(shortLoadingDate);
                            req.setUnloadingDate(shortUnloadingDate);
                            req.setRms(e.getRms());
                            req.setLotId(e.getLotId());
                            req.setEvent(e.getEvent());
                            req.setInterval(e.getInterval());
                            req.setModifiedBy("From FTP");
                            reqD = new RequestDAO();
                            QueryResult update = reqD.updateRequestLoadingAndUnloadingDateFTP(req);
                            if (update.getResult() == 0) {
                                LOGGER.info("Fail to update data from FTP");
                            } else {
                                LOGGER.info("update rmsFtp [" + e.getRms() + e.getLotId() + "_" + e.getEvent() + " - " + e.getInterval() + "]");
                                //add log
                                Log log = new Log();
                                log.setRequestId(request.getId());
                                log.setModule("RMS FTP");
                                log.setStatus("Updated from RMS FTP");
                                log.setCreatedBy("RMS FTP");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                            }
                        }
                        String expectedCondition = "";
                        if (!"".equals(e.getExpectedMinCondition()) && !"".equals(e.getExpectedMaxCondition())) {
                            expectedCondition = e.getExpectedMinCondition() + " / " + e.getExpectedMaxCondition() + " `C";
                        } else if (!"".equals(e.getExpectedMinCondition()) && "".equals(e.getExpectedMaxCondition())) {
                            expectedCondition = e.getExpectedMinCondition() + " `C";
                        } else if ("".equals(e.getExpectedMinCondition()) && !"".equals(e.getExpectedMaxCondition())) {
                            expectedCondition = e.getExpectedMaxCondition() + " `C";
                        } else {
                            expectedCondition = "";
                            LOGGER.info("No expectedCondition...");
                        }
                        if (request.getExpectedTestCondition() == null) {
//                            LOGGER.info("Second Stage...");
                            Request req = new Request();
                            req.setExpectedTestCondition(expectedCondition);
                            req.setRms(e.getRms());
                            req.setLotId(e.getLotId());
                            req.setEvent(e.getEvent());
                            req.setInterval(e.getInterval());
                            req.setModifiedBy("From FTP");
                            reqD = new RequestDAO();
                            QueryResult update = reqD.updateRequestExpectedConditionFTP(req);
                            if (update.getResult() == 0) {
                                LOGGER.info("Fail to update expected condition data from FTP");
                            } else {
                                LOGGER.info("succeed to update expected condition data from FTP");
                                Log log = new Log();
                                log.setRequestId(request.getId());
                                log.setModule("RMS FTP");
                                log.setStatus("Test Condition Updated from RMS FTP");
                                log.setCreatedBy("RMS FTP");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                            }

                        }
                    }
                }

            } else {
                LOGGER.info("csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

//    @Scheduled(cron = "0 22 * * * *") //every hour 30 minutes
    public void updateCSV() throws IOException {

        CSVReader csvReader = null;

        try {

            //update csv
            File filePenang = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
            if (filePenang.exists()) {
                LOGGER.info("dh ada header");
                FileWriter fileWriter = null;
                FileReader fileReader = null;
                try {
                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
                    fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
                    String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv";

                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String data = bufferedReader.readLine();

                    StringBuilder buff = new StringBuilder();

                    boolean flag = false;
                    boolean sentEmail = false;
                    int row = 0;
                    while (data != null && flag == false) {
                        buff.append(data).append(System.getProperty("line.separator"));

                        String[] split = data.split(",");
                        FinalRequestTemp inventoryPenang = new FinalRequestTemp(
                                split[0], split[1], split[2],
                                split[3], split[4], split[5],
                                split[6], split[7], split[8],
                                split[9], split[10], split[11],
                                split[12], split[13], split[14],
                                split[15], split[16], split[17],
                                split[18] //status = [18]
                        );

                        RequestDAO lotD = new RequestDAO();
                        int count = lotD.getCountRequestID(split[0]);
                        if (count == 1) {
                            lotD = new RequestDAO();
                            Request lot = lotD.getRequest(split[0]);
//                            LOGGER.info("id =  " + split[0] + ", loadingDateCsv = " + split[13] + ", loadingDateDb = " + lot.getLoadingDate() + ", unloadingDate = " + split[14]);
                            DateFormat shortFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                            DateFormat mediumFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            String shortLoadingDateDb = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(lot.getLoadingDate()));
                            String shortLoadingDateCsv = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(split[13]));
                            String shortUnloadingDateDb = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(lot.getUnloadingDate()));
                            String shortUnloadingDateCsv = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(split[14]));
                            if ((!shortLoadingDateDb.equals(shortLoadingDateCsv)) || (!shortUnloadingDateDb.equals(shortUnloadingDateCsv))) {
                                CSV csv = new CSV();
                                csv.open(new File(targetLocation));
                                csv.put(13, row, lot.getLoadingDate());
                                csv.put(14, row, lot.getUnloadingDate());
                                csv.save(new File(targetLocation));
                                sentEmail = true;
                                LOGGER.info("update csv loading & unloading [" + split[0] + "]");
                            } else {
                                flag = false;
                                LOGGER.info("no update: " + split[13] + " = " + lot.getLoadingDate());
                            }

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
//                        String[] to = {"potspenang@gmail.com", "fg79cj@onsemi.com"};
//                        String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                        String[] to = {"pots-penang@lsp2u.com.my", "fg79cj@onsemi.com"};
                        emailSenderToHIMSSF.htmlEmailWithAttachment(
                                servletContext,
                                //                    user name
                                user,
                                //                    to
                                to,
                                // attachment file
                                new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv"),
                                //                    subject
                                "List of RMS_event Ship from Rel Lab",
                                //                    msg
                                "List of RMS_event Ship from Rel Lab. "
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

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    //update from pots_receive.csv
//    @Scheduled(cron = "0 */3 * * * *") //every 3 minutes
    public void updatePotsReceiveCsv() throws IOException {

        CSVReader csvReader = null;
        //        LOGGER.info("cron detected222!!!");

        try {

            File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_receive.csv");
            //            File file = new File("C:\\Users\\fg79cj\\Documents\\test_receive.csv");
//            File file = new File("D:\\DOTS\\POTS_CSV\\pots_receive.csv");

            if (file.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_receive.csv"), ',', '"', 1);
                //                 csvReader = new CSVReader(new FileReader("C:\\Users\\fg79cj\\Documents\\test_receive.csv"), ',', '"', 1);
//                csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_receive.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<LotPenangTemp> empList = new ArrayList<LotPenangTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    LotPenangTemp emp = new LotPenangTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5], inventory[6],
                            inventory[7], inventory[8],
                            inventory[9], inventory[10],
                            inventory[11], inventory[12],
                            inventory[13], inventory[14],
                            inventory[15], inventory[16],
                            inventory[17], inventory[18],
                            inventory[19]);
                    empList.add(emp);
                }

                for (LotPenangTemp e : empList) {

                    LotPenangDAO lotD = new LotPenangDAO();
                    LotPenang lot = lotD.getLotPenangByRequestId(e.getRequestId());
                    if ("Receiving".equals(e.getStatus())) {
                        if (lot.getReceivedDate() == null && !"null".equals(e.getReceivedDate())) {
                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getReceivedDate();
                            String shortReceivedDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                            DateFormat actualFormatVerification = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat originalFormatVerification = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String verificationDate = e.getReceivedVerificationDate();
                            String actualVerificationDate = actualFormatVerification.format(originalFormatVerification.parse(verificationDate));

                            LotPenang lotR = new LotPenang();
                            lotR.setStatus("Received in Penang");
                            lotR.setReceivedBy(e.getReceivedBy());
                            lotR.setReceivedDate(shortReceivedDate);
                            lotR.setReceivedVerificationStatus(e.getReceivedVerificationStatus());
                            lotR.setReceivedVerificationDate(actualVerificationDate);
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenStatusReceiving(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data from POTS_receive FTP");
                            } else {

                                Request req = new Request();
                                req.setStatus("Received in Penang");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);

                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("Lot Penang");
                                log.setStatus("Updated Receiving Details from POTS CSV");
                                log.setCreatedBy("POTS CSV");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                                //                            LOGGER.info("succeed to insert data from POTS_receive FTP FTP");
                            }
                        }

                    } else if ("Preloading VM".equals(e.getStatus())) {
                        if (lot.getPreVmDate() == null && !"null".equals(e.getPreVmDate())) {
                            DateFormat actualFormatPreVmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat originalFormatPreVmDate = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String preVmDate = e.getPreVmDate();
                            String actualPreVmDate = actualFormatPreVmDate.format(originalFormatPreVmDate.parse(preVmDate));

                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getReceivedDate();
                            String shortReceivedDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                            DateFormat actualFormatVerification = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat originalFormatVerification = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String verificationDate = e.getReceivedVerificationDate();
                            String actualVerificationDate = actualFormatVerification.format(originalFormatVerification.parse(verificationDate));

                            LotPenang lotR = new LotPenang();
                            if ("Loading Process".equals(lot.getStatus())) {
                                lotR.setStatus("Loading Process");
                            } else {
                                lotR.setStatus("PreLoading VM");
                            }
//                            lotR.setStatus("PreLoading VM");
                            lotR.setReceivedQuantity(e.getReceivedQuantity());
                            lotR.setReceivedMixStatus(e.getReceivedMixStatus());
                            lotR.setReceivedMixRemarks(e.getReceivedMixRemarks());
                            lotR.setReceivedDemountStatus(e.getReceivedDemountStatus());
                            lotR.setReceivedDemountRemarks(e.getReceivedDemountRemarks());
                            lotR.setReceivedBrokenStatus(e.getReceivedBrokenStatus());
                            lotR.setReceivedBrokenRemarks(e.getReceivedBrokenRemarks());
                            lotR.setPreVmDate(actualPreVmDate);
                            lotR.setReceivedBy(e.getReceivedBy());
                            lotR.setReceivedDate(shortReceivedDate);
                            lotR.setReceivedVerificationStatus(e.getReceivedVerificationStatus());
                            lotR.setReceivedVerificationDate(actualVerificationDate);
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenStatusPreLoadingVM(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data from POTS_receive FTP");
                            } else {
                                Request req = new Request();
                                req.setStatus("PreLoading VM");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);

                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("Lot Penang");
                                log.setStatus("Updated PreLoading VM Details from POTS CSV");
                                log.setCreatedBy("POTS CSV");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                                //                            LOGGER.info("succeed to insert data from POTS_receive FTP FTP");
                            }
                            //if both have value but difference timestamp
                        } else if (lot.getPreVmDate() != null && !"null".equals(e.getPreVmDate())) {

                            DateFormat actualDbFormatPreVmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat originalDbFormatPreVmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            String dbPreVmDate = lot.getPreVmDate();
                            String actualDbPreVmDate = actualDbFormatPreVmDate.format(originalDbFormatPreVmDate.parse(dbPreVmDate));

                            DateFormat actualFormatPreVmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat originalFormatPreVmDate = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String preVmDate = e.getPreVmDate();
                            String actualPreVmDate = actualFormatPreVmDate.format(originalFormatPreVmDate.parse(preVmDate));

                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getReceivedDate();
                            String shortReceivedDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                            DateFormat actualFormatVerification = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat originalFormatVerification = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String verificationDate = e.getReceivedVerificationDate();
                            String actualVerificationDate = actualFormatVerification.format(originalFormatVerification.parse(verificationDate));

                            if (!actualPreVmDate.equals(actualDbPreVmDate)) {
                                LotPenang lotR = new LotPenang();
                                if ("Loading Process".equals(lot.getStatus())) {
                                    lotR.setStatus("Loading Process");
                                } else {
                                    lotR.setStatus("PreLoading VM");
                                }
                                lotR.setReceivedQuantity(e.getReceivedQuantity());
                                lotR.setReceivedMixStatus(e.getReceivedMixStatus());
                                lotR.setReceivedMixRemarks(e.getReceivedMixRemarks());
                                lotR.setReceivedDemountStatus(e.getReceivedDemountStatus());
                                lotR.setReceivedDemountRemarks(e.getReceivedDemountRemarks());
                                lotR.setReceivedBrokenStatus(e.getReceivedBrokenStatus());
                                lotR.setReceivedBrokenRemarks(e.getReceivedBrokenRemarks());
                                lotR.setPreVmDate(actualPreVmDate);
                                lotR.setReceivedBy(e.getReceivedBy());
                                lotR.setReceivedDate(shortReceivedDate);
                                lotR.setReceivedVerificationStatus(e.getReceivedVerificationStatus());
                                lotR.setReceivedVerificationDate(actualVerificationDate);
                                lotR.setRequestId(e.getRequestId());

                                LotPenangDAO lotdD = new LotPenangDAO();
                                QueryResult add = lotdD.updateLotPenangWhenStatusPreLoadingVM(lotR);
                                if (add.getResult() == 0) {
                                    LOGGER.info("Fail to insert data from POTS_receive FTP");
                                } else {
                                    Request req = new Request();
                                    req.setStatus("PreLoading VM");
                                    req.setModifiedBy("Penang CSV");
                                    req.setId(e.getRequestId());
                                    RequestDAO reqD = new RequestDAO();
                                    QueryResult reqStatus = reqD.updateRequestStatus(req);

                                    //update log
                                    Log log = new Log();
                                    log.setRequestId(e.getRequestId());
                                    log.setModule("Lot Penang");
                                    log.setStatus("Updated PreLoading VM Details from POTS CSV");
                                    log.setCreatedBy("POTS CSV");
                                    LogDAO logD = new LogDAO();
                                    QueryResult addLog = logD.insertLog(log);
                                    //                            LOGGER.info("succeed to insert data from POTS_receive FTP FTP");
                                }
                            }
                        }
                    }

                }

            } else {
                LOGGER.info("pots_receive csv file not found!");
            }

            //pots_process
            File filePotProcess = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_process.csv");
            //             File filePotProcess = new File("C:\\Users\\fg79cj\\Documents\\test_process.csv");
//            File filePotProcess = new File("D:\\DOTS\\POTS_CSV\\pots_process.csv");

            if (filePotProcess.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_process.csv"), ',', '"', 1);
                //                 csvReader = new CSVReader(new FileReader("C:\\Users\\fg79cj\\Documents\\test_process.csv"), ',', '"', 1);
//                csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_process.csv"), ',', '"', 1);

                String[] inventory = null;
                List<LotPenangTemp> empList = new ArrayList<LotPenangTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    LotPenangTemp emp = new LotPenangTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5], inventory[6],
                            inventory[7], inventory[8],
                            inventory[9], inventory[10],
                            inventory[11], inventory[12],
                            inventory[13], inventory[14],
                            inventory[15], inventory[16],
                            inventory[17], inventory[18],
                            inventory[19], inventory[20],
                            inventory[21]);
                    empList.add(emp);
                }

                for (LotPenangTemp e : empList) {

                    LotPenangDAO lotD = new LotPenangDAO();
                    LotPenang lot = lotD.getLotPenangByRequestId(e.getRequestId());

                    //loading
                    if ("Loading".equals(e.getStatus())) {
                        if (lot.getLoadingDate() == null && !"null".equals(e.getLoadingDate())) {
                            LotPenang lotR = new LotPenang();

                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getLoadingDate();
                            String shortLoadingDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                            lotR.setLoadingDate(shortLoadingDate);
                            lotR.setStatus("Loading Process");
                            lotR.setChamberId(e.getChamberId());
                            lotR.setChamberLevel(e.getChamberLevel());
                            lotR.setTestCondition(e.getTestCondition());
                            lotR.setLoadingRemarks(e.getLoadingRemarks());
                            lotR.setLoadingBy(e.getLoadingBy());
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenLoading(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data loading from POTS_process FTP");
                            } else {
                                Request req = new Request();
                                req.setStatus("Loading Process");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);

                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("Lot Penang");
                                log.setStatus("Updated Loading Details from POTS CSV");
                                log.setCreatedBy("POTS CSV");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);

                                //check old interval exist or not
                                if (lot.getOldLotPenangId() != null) {
                                    LotPenangDAO lotddD = new LotPenangDAO();
                                    int countOldId = lotddD.getCountId(lot.getOldLotPenangId());
                                    if (countOldId == 1) {
                                        lotddD = new LotPenangDAO();
                                        LotPenang oldLot = lotddD.getLotPenang(lot.getOldLotPenangId());

                                        //update old interval lot penang
                                        LotPenang oldLot1 = new LotPenang();
                                        oldLot1.setFlag("2");
                                        oldLot1.setStatus(oldLot.getStatus());
                                        oldLot1.setId(oldLot.getId());
                                        lotddD = new LotPenangDAO();
                                        QueryResult oldLotQuery = lotddD.updateLotPenangforStatusAndFlag(oldLot1);

                                        //update old interval request
                                        RequestDAO requestD = new RequestDAO();
                                        int countOldReqId = requestD.getCountRequestID(oldLot.getRequestId());
                                        if (countOldReqId == 1) {
                                            requestD = new RequestDAO();
                                            Request requestOld = requestD.getRequest(oldLot.getRequestId());

                                            Request request = new Request();
                                            request.setFlag("2");
                                            request.setStatus(requestOld.getStatus());
                                            request.setId(requestOld.getId());
                                            requestD = new RequestDAO();
                                            QueryResult oldRequestQuery = requestD.updateRequestStatusAndFlag(request);
                                        }

                                    }
                                }
                            }
                        }
                        //unloading
                    } else if ("Unloading".equals(e.getStatus())) {

                        if (lot.getUnloadingDate() == null && !"null".equals(e.getUnloadingDate())) {

                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getUnloadingDate();
                            String shortUnloadingDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                            //check if new interval exist
                            if (lot.getNewLotPenangId() == null) {
                                LotPenang lotR = new LotPenang();
                                lotR.setStatus("Unloading Process");
                                lotR.setUnloadingDate(shortUnloadingDate);
                                lotR.setUnloadingRemarks(e.getUnloadingRemarks());
                                lotR.setUnloadingBy(e.getUnloadingBy());
                                lotR.setRequestId(e.getRequestId());
                                LotPenangDAO lotdD = new LotPenangDAO();
                                QueryResult add = lotdD.updateLotPenangWhenUnloading(lotR);
                                if (add.getResult() == 0) {
                                    LOGGER.info("Fail to insert data unloading from POTS_process FTP");
                                } else {
                                    Request req = new Request();
                                    req.setStatus("Unloading Process");
                                    req.setModifiedBy("Penang CSV");
                                    req.setId(e.getRequestId());
                                    RequestDAO reqD = new RequestDAO();
                                    QueryResult reqStatus = reqD.updateRequestStatus(req);

                                    //update log
                                    Log log = new Log();
                                    log.setRequestId(e.getRequestId());
                                    log.setModule("Lot Penang");
                                    log.setStatus("Updated Unloading Details from POTS CSV");
                                    log.setCreatedBy("POTS CSV");
                                    LogDAO logD = new LogDAO();
                                    QueryResult addLog = logD.insertLog(log);
                                }

                            } else {
                                LotPenang lotR = new LotPenang();
                                lotR.setStatus("Unloading Process - Continue New Interval");
                                lotR.setUnloadingDate(shortUnloadingDate);
                                lotR.setUnloadingRemarks(e.getUnloadingRemarks());
                                lotR.setUnloadingBy(e.getUnloadingBy());
                                lotR.setRequestId(e.getRequestId());
                                LotPenangDAO lotdD = new LotPenangDAO();
                                QueryResult add = lotdD.updateLotPenangWhenUnloading(lotR);
                                if (add.getResult() == 0) {
                                    LOGGER.info("Fail to insert data unloading from POTS_process FTP");
                                } else {
                                    Request req = new Request();
                                    req.setStatus("Unloading Process - Continue New Interval");
                                    req.setModifiedBy("Penang CSV");
                                    req.setId(e.getRequestId());
                                    RequestDAO reqD = new RequestDAO();
                                    QueryResult reqStatus = reqD.updateRequestStatus(req);

                                    //update log
                                    Log log = new Log();
                                    log.setRequestId(e.getRequestId());
                                    log.setModule("Lot Penang");
                                    log.setStatus("Updated Unloading Details from POTS CSV");
                                    log.setCreatedBy("POTS CSV");
                                    LogDAO logD = new LogDAO();
                                    QueryResult addLog = logD.insertLog(log);
                                }
                            }

                        }

                        //postLoadingVM
                    } else if ("Postloading VM".equals(e.getStatus())) {

                        if (lot.getPostVmDate() == null && !"null".equals(e.getPostVmDate())) {

                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String postVmDate = e.getPostVmDate();
                            String shortPostVmDate = shortFormatReceived.format(mediumFormatReceived.parse(postVmDate));
                            String unloadDate = e.getUnloadingDate();
                            String shortUnloadingDate = shortFormatReceived.format(mediumFormatReceived.parse(unloadDate));

                            LotPenang lotR = new LotPenang();
                            lotR.setStatus("PostLoading VM");
                            lotR.setUnloadingMixStatus(e.getUnloadingMixStatus());
                            lotR.setUnloadingMixRemarks(e.getUnloadingMixRemarks());
                            lotR.setUnloadingDemountStatus(e.getUnloadingDemountStatus());
                            lotR.setUnloadingDemountRemarks(e.getUnloadingDemountRemarks());
                            lotR.setUnloadingBrokenStatus(e.getUnloadingBrokenStatus());
                            lotR.setUnloadingBrokenRemarks(e.getUnloadingBrokenRemarks());
                            lotR.setUnloadingQty(e.getUnloadingQty());
                            lotR.setPostVmDate(shortPostVmDate);
                            //save unloading too
                            lotR.setUnloadingDate(shortUnloadingDate);
                            lotR.setUnloadingRemarks(e.getUnloadingRemarks());
                            lotR.setUnloadingBy(e.getUnloadingBy());
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenPostLoadingVM(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data unloading from POTS_process FTP");
                            } else {
                                Request req = new Request();
                                req.setStatus("PostLoading VM");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);

                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("Lot Penang");
                                log.setStatus("Updated PostLoading VM Details from POTS CSV");
                                log.setCreatedBy("POTS CSV");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                            }
                        }

                    }

                }

            } else {
                LOGGER.info("csv pot_proecss file not found!");
            }

            //shipment from penang
            File filePotsShipment = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_shipment.csv");
//            File filePotsShipment = new File("D:\\DOTS\\POTS_CSV\\pots_shipment.csv");

            if (filePotsShipment.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_shipment.csv"), ',', '"', 1);
//                csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_shipment.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<LotPenangTemp> empList = new ArrayList<LotPenangTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    LotPenangTemp emp = new LotPenangTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5], inventory[6],
                            inventory[7]);
                    empList.add(emp);
                }

                for (LotPenangTemp e : empList) {

                    LotPenangDAO lotD = new LotPenangDAO();
                    LotPenang lot = lotD.getLotPenangByRequestId(e.getRequestId());
                    if (lot.getShipmentDate() == null && !"null".equals(e.getShipmentDate())) {
                        LotPenang lotR = new LotPenang();

                        DateFormat actualReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        DateFormat ftpFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                        String receivedDate = e.getShipmentDate();
                        String actualReceivedDate = actualReceived.format(ftpFormatReceived.parse(receivedDate));

                        lotR.setStatus("Ship to Rel Lab");
                        lotR.setShipmentBy(e.getShipmentBy());
                        lotR.setShipmentDate(actualReceivedDate);
                        lotR.setRequestId(e.getRequestId());

                        LotPenangDAO lotdD = new LotPenangDAO();
                        QueryResult add = lotdD.updateLotPenangWhenShipToRel(lotR);
                        if (add.getResult() == 0) {
                            LOGGER.info("Fail to insert data from POTS_shipment FTP");
                        } else {
                            Request req = new Request();
                            req.setStatus("Ship to Rel Lab");
                            req.setModifiedBy("Penang CSV");
                            req.setId(e.getRequestId());
                            RequestDAO reqD = new RequestDAO();
                            QueryResult reqStatus = reqD.updateRequestStatus(req);

                            //update log
                            Log log = new Log();
                            log.setRequestId(e.getRequestId());
                            log.setModule("Lot Penang");
                            log.setStatus("Updated Shipment Details from POTS CSV");
                            log.setCreatedBy("POTS CSV");
                            LogDAO logD = new LogDAO();
                            QueryResult addLog = logD.insertLog(log);
                            //                            LOGGER.info("succeed to insert data from POTS_shipment FTP FTP");
                        }
                    } else if (lot.getShipmentDate() != null && !"null".equals(e.getShipmentDate())) {

                        DateFormat actualFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String receivedDatedb = lot.getShipmentDate();
                        String actualReceivedDatedb = actualFormatReceiveddb.format(originalFormatReceiveddb.parse(receivedDatedb));

                        DateFormat actualFormatReceivedftp = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceivedftp = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                        String receivedDateftp = e.getShipmentDate();
                        String actualReceivedDateftp = actualFormatReceivedftp.format(originalFormatReceivedftp.parse(receivedDateftp));

                        if (!actualReceivedDateftp.equals(actualReceivedDatedb)) {

                            LotPenang lotR = new LotPenang();
                            DateFormat actualReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat ftpFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getShipmentDate();
                            String actualReceivedDate = actualReceived.format(ftpFormatReceived.parse(receivedDate));

                            lotR.setStatus("Ship to Rel Lab");
                            lotR.setShipmentBy(e.getShipmentBy());
                            lotR.setShipmentDate(actualReceivedDate);
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenShipToRel(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data from POTS_shipment FTP");
                            } else {
                                Request req = new Request();
                                req.setStatus("Ship to Rel Lab");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);

                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("Lot Penang");
                                log.setStatus("Updated Shipment Details from POTS CSV");
                                log.setCreatedBy("POTS FTP");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                                //                                LOGGER.info("succeed to insert data from POTS_shipment FTP FTP");
                            }

                        } else {
                            //                            LOGGER.info("no update from POTS_shipment FTP FTP");
                        }

                    } else {
                        //                        LOGGER.info("no data received from POTS_shipment FTP FTP");
                    }

                }

            } else {
                LOGGER.info("pots_shipment csv file not found!");
            }

            //pots_notify (handshake for break interval, new interval, change interval, cancel interval)
            File filePotsNotify = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_notify.csv");
//            File filePotsNotify = new File("D:\\DOTS\\POTS_CSV\\pots_notify.csv");

            if (filePotsNotify.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_notify.csv"), ',', '"', 1);
//                csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_notify.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<LotPenangTemp> empList = new ArrayList<LotPenangTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    LotPenangTemp emp = new LotPenangTemp(inventory[0],
                            inventory[1], inventory[2]);
                    empList.add(emp);
                }

                for (LotPenangTemp e : empList) {

                    LotPenangDAO lotD = new LotPenangDAO();
                    LotPenang lot = lotD.getLotPenangByRequestId(e.getRequestId());
                    if ("0".equals(lot.getPotsNotify())) {

                        if ("Cancel Interval".equals(lot.getStatus())) {
                            LotPenang lotR = new LotPenang();

                            DateFormat actualReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat ftpFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String notifyDate = e.getPotsNotifyDate();
                            String actualNotifyDate = actualReceived.format(ftpFormatReceived.parse(notifyDate));

                            lotR.setPotsNotify("1");
                            lotR.setPotsNotifyDate(actualNotifyDate);
                            lotR.setPotsNotifyBy(e.getPotsNotifyBy());
                            lotR.setFlag("0");
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenPotsNotifyForCancelInterval(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to update data from POTS_shipment FTP");
                            } else {
                                LOGGER.info("succeed to update data from POTS_notify FTP");
                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("POTS CSV");
                                log.setStatus("Updated Handshake from POTS CSV");
                                log.setCreatedBy("RMS FTP");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                            }
                        } else {
                            LotPenang lotR = new LotPenang();

                            DateFormat actualReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat ftpFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String notifyDate = e.getPotsNotifyDate();
                            String actualNotifyDate = actualReceived.format(ftpFormatReceived.parse(notifyDate));

                            lotR.setPotsNotify("1");
                            lotR.setPotsNotifyDate(actualNotifyDate);
                            lotR.setPotsNotifyBy(e.getPotsNotifyBy());
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenPotsNotify(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to update data from POTS_shipment FTP");
                            } else {
                                LOGGER.info("succeed to update data from POTS_notify FTP");
                                //update log
                                Log log = new Log();
                                log.setRequestId(e.getRequestId());
                                log.setModule("POTS CSV");
                                log.setStatus("Updated Handshake from POTS CSV");
                                log.setCreatedBy("RMS FTP");
                                LogDAO logD = new LogDAO();
                                QueryResult addLog = logD.insertLog(log);
                            }
                        }

                    }
                }

            } else {
                LOGGER.info("pots_notify csv file not found!");
            }

            //pots_notify (handshake for break interval, new interval, change interval, cancel interval)
            File filePotsAbnormal = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_abnormal.csv");
//            File filePotsAbnormal = new File("D:\\DOTS\\POTS_CSV\\pots_abnormal.csv");

            if (filePotsAbnormal.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_abnormal.csv"), ',', '"', 1);
//                csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_abnormal.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<AbnormalLoadingTemp> empList = new ArrayList<AbnormalLoadingTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    AbnormalLoadingTemp emp = new AbnormalLoadingTemp(inventory[0],
                            inventory[1], inventory[2], inventory[3], inventory[4], inventory[5]);
                    empList.add(emp);
                }

                for (AbnormalLoadingTemp e : empList) {

                    LotPenangDAO lotD = new LotPenangDAO();

                    int countReq = lotD.getCountRequestId(e.getRequestId());
                    if (countReq == 1) {

                        AbnormalLoadingDAO abD = new AbnormalLoadingDAO();
                        int countAbReq = abD.getCountRequestId(e.getRequestId());

                        if (countAbReq == 0) {

                            DateFormat actualFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat ftpFormat = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String suggestLoading = actualFormat.format(ftpFormat.parse(e.getSuggestedLoadingTime()));
                            String actualLoading = actualFormat.format(ftpFormat.parse(e.getActualLoadingTime()));
                            String suggestUnloading = actualFormat.format(ftpFormat.parse(e.getSuggestedUnloadingTime()));
                            String estimateUnloading = actualFormat.format(ftpFormat.parse(e.getNewEstimateUnloadingTime()));

                            AbnormalLoading ab = new AbnormalLoading();

                            ab.setRequestId(e.getRequestId());
                            ab.setSuggestedLoadingTime(suggestLoading);
                            ab.setActualLoadingTime(actualLoading);
                            ab.setSuggestedUnloadingTime(suggestUnloading);
                            ab.setNewEstimateUnloadingTime(estimateUnloading);
                            ab.setRemarks(e.getRemarks());
                            ab.setFlag("0");

                            abD = new AbnormalLoadingDAO();
                            QueryResult add = abD.insertAbnormalLoading(ab);
                            //update log
                            Log log = new Log();
                            log.setRequestId(e.getRequestId());
                            log.setModule("POTS CSV");
                            log.setStatus("Updated Loading Time Outside Window");
                            log.setCreatedBy("POTS CSV");
                            LogDAO logD = new LogDAO();
                            QueryResult addLog = logD.insertLog(log);

                            LotPenang lotP = new LotPenang();
                            lotP.setAbnormalId(add.getGeneratedKey());
                            lotP.setRequestId(e.getRequestId());

                            LotPenangDAO lotpD = new LotPenangDAO();
                            QueryResult updateLot = lotpD.updateLotPenangAbnormalIdWithRequestId(lotP);

                        } else {
                            LOGGER.info("Pots_abnormal : abnormal table already has this request ID");
                        }

                    } else {
                        LOGGER.info("Pots_abnormal : lot penang have more than 1 request ID");

                    }
                }

            } else {
                LOGGER.info("pots_abnormal csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

//    @Scheduled(cron = "0 */5 * * * *") //every 5 minutes
    public void DownloadRmaCsv() throws IOException {

        CSVReader csvReader = null;
        //        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_rma.csv");
//            File file = new File("D:\\DOTS\\POTS_CSV\\pots_rma.csv");

            if (file.exists()) {
//                LOGGER.info("File dots_rms_ftp found!!!");
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_rma.csv"), ',', '"', 1);
//                csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_rma.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<RmaTemp> empList = new ArrayList<RmaTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    RmaTemp emp = new RmaTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5]);
                    empList.add(emp);
                }

                //Lets print the Inventory List
                for (RmaTemp e : empList) {

                    // check rms_event + interval exist or not
                    RmaDAO reqD = new RmaDAO();
//                    int countRms = reqD.getCountRequestRmsLotEventInterval(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval());
                    int countRms = reqD.getCountRequestId(e.getRequestId());
                    if (countRms == 0) {

                        DateFormat toBeRmaDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        DateFormat ftpFormatRmaDate = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                        String rmaDate1 = e.getRmaDate1();
                        String actualRmaDate1 = toBeRmaDate.format(ftpFormatRmaDate.parse(rmaDate1));

                        Rma req = new Rma();
                        req.setRequestId(e.getRequestId());
                        req.setFlag(e.getFlag());
                        req.setRmaRemarks1(e.getRmaRemarks1());
                        req.setRmaDate1(actualRmaDate1);
                        req.setRmaCount("0");

                        //if flag 1 - insert 1 only
                        if ("1".equals(e.getFlag())) {
                            RmaDAO rD = new RmaDAO();
                            QueryResult queryAdd1 = rD.insertRmaIfFlag1Csv(req);
                            if (queryAdd1.getGeneratedKey().equals("0")) {
                                LOGGER.info("Fail to insert rma flag 1");
                            } else {
                                LotPenang lot = new LotPenang();
                                lot.setRmaId(queryAdd1.getGeneratedKey());
                                lot.setRequestId(e.getRequestId());
                                LotPenangDAO lotD = new LotPenangDAO();
                                QueryResult queryLotUpdate = lotD.updateLotPenangRmaIdWithRequestId(lot);
                            }

                        }//if flag 2 - insert all
                        else if ("2".equals(e.getFlag())) {
                            if (!"null".equals(e.getRmaDate2())) {
                                String rmaDate2 = e.getRmaDate2();
                                String actualRmaDate2 = toBeRmaDate.format(ftpFormatRmaDate.parse(rmaDate2));

                                req.setRmaRemarks2(e.getRmaRemarks2());
                                req.setRmaDate2(actualRmaDate2);

                                RmaDAO rD = new RmaDAO();
                                QueryResult queryAdd2 = rD.insertRmaIfFlag2Csv(req);
                                if (queryAdd2.getGeneratedKey().equals("0")) {
                                    LOGGER.info("Fail to insert rma flag 1");
                                } else {
                                    LotPenang lot = new LotPenang();
                                    lot.setRmaId(queryAdd2.getGeneratedKey());
                                    lot.setRequestId(e.getRequestId());
                                    LotPenangDAO lotD = new LotPenangDAO();
                                    QueryResult queryLotUpdate = lotD.updateLotPenangRmaIdWithRequestId(lot);
                                }
                            }

                        }

                    } else if (countRms > 1) {
                        LOGGER.info("Data have more than 1 in DB. Please re-check");
                    } else {

                        RmaDAO rmD = new RmaDAO();
                        Rma rma = rmD.getRmaByRequestId(e.getRequestId());

                        DateFormat toBeDbRmaDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        DateFormat dbFormatRmaDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String dbrmaDate1 = rma.getRmaDate1();
                        String actualDbRmaDate1 = toBeDbRmaDate.format(dbFormatRmaDate.parse(dbrmaDate1));

                        //update 
                        DateFormat toBeRmaDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        DateFormat ftpFormatRmaDate = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                        String rmaDate1 = e.getRmaDate1();
                        String actualRmaDate1 = toBeRmaDate.format(ftpFormatRmaDate.parse(rmaDate1));

                        Rma req = new Rma();
                        req.setRequestId(e.getRequestId());
                        req.setFlag(e.getFlag());
                        req.setRmaRemarks1(e.getRmaRemarks1());
                        req.setRmaDate1(actualRmaDate1);

                        req.setId(rma.getId());

                        if ("1".equals(e.getFlag())) {

                            //check if data same or not
                            if (!actualDbRmaDate1.equals(actualRmaDate1)) {
                                RmaDAO rD1 = new RmaDAO();
                                QueryResult queryUpdate1 = rD1.updateRmaifFlag1Csv(req);
                                LOGGER.info("x sama : " + actualDbRmaDate1 + " - " + actualRmaDate1);
                            } else {
//                                LOGGER.info("sama wei : " + actualDbRmaDate1 + " - " + actualRmaDate1);
                            }

                        } else if ("2".equals(e.getFlag())) {

                            if (rma.getRmaDate2() == null && !"null".equals(e.getRmaDate2())) {

                                String rmaDate2 = e.getRmaDate2();
                                String actualRmaDate2 = toBeRmaDate.format(ftpFormatRmaDate.parse(rmaDate2));

                                req.setRmaRemarks2(e.getRmaRemarks2());
                                req.setRmaDate2(actualRmaDate2);

                                RmaDAO rD1 = new RmaDAO();
                                QueryResult queryUpdate2 = rD1.updateRmaifFlag2Csv(req);

                            } else if (rma.getRmaDate2() != null && !"null".equals(e.getRmaDate2())) {

                                String dbrmaDate2 = rma.getRmaDate2();
                                String actualDbRmaDate2 = toBeDbRmaDate.format(dbFormatRmaDate.parse(dbrmaDate2));

                                String rmaDate2 = e.getRmaDate2();
                                String actualRmaDate2 = toBeRmaDate.format(ftpFormatRmaDate.parse(rmaDate2));

                                if (!actualDbRmaDate2.equals(actualRmaDate2)) {

                                    req.setRmaRemarks2(e.getRmaRemarks2());
                                    req.setRmaDate2(actualRmaDate2);

                                    RmaDAO rD1 = new RmaDAO();
                                    QueryResult queryUpdate2 = rD1.updateRmaifFlag2Csv(req);
                                } else {
//                                    LOGGER.info("same date for flag 2");
                                }

                            }

                            //check if data same or not
                        }

                    }
                }

            } else {
                LOGGER.info("csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

//    @Scheduled(cron = "0 15 11 * * *") //every 5 minutes
    public void TestConnection() throws UnknownHostException,
            IOException {
        try {
            try {
                URL url = new URL("http://www.google.com");
                System.out.println(url.getHost());
                HttpURLConnection con = (HttpURLConnection) url
                        .openConnection();
                con.connect();
                if (con.getResponseCode() == 200) {
                    LOGGER.info("Connection established!!");
                }
            } catch (Exception exception) {
                LOGGER.info("No Connection......!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //send email test
//    @Scheduled(cron = "0 30 11 * * *") //every hour 30 minutes
    public void emailTest() throws IOException {

        CSVReader csvReader = null;

//        try {
//
//            //update csv
//            File filePenang = new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
////            if (filePenang.exists()) {
//                LOGGER.info("dh ada header");
//                FileWriter fileWriter = null;
//                FileReader fileReader = null;
//                try {
//                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv", true);
//                    fileReader = new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv");
//                    String targetLocation = "\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv";
//
//                    BufferedReader bufferedReader = new BufferedReader(fileReader);
//                    String data = bufferedReader.readLine();
//
//                    StringBuilder buff = new StringBuilder();
//
//                    boolean flag = false;
//                    boolean sentEmail = false;
//                    int row = 0;
//                    while (data != null && flag == false) {
//                        buff.append(data).append(System.getProperty("line.separator"));
//
//                        String[] split = data.split(",");
//                        FinalRequestTemp inventoryPenang = new FinalRequestTemp(
//                                split[0], split[1], split[2],
//                                split[3], split[4], split[5],
//                                split[6], split[7], split[8],
//                                split[9], split[10], split[11],
//                                split[12], split[13], split[14],
//                                split[15], split[16], split[17],
//                                split[18] //status = [18]
//                        );
//
//                        RequestDAO lotD = new RequestDAO();
//                        int count = lotD.getCountRequestID(split[0]);
//                        if (count == 1) {
//                            lotD = new RequestDAO();
//                            Request lot = lotD.getRequest(split[0]);
////                            LOGGER.info("id =  " + split[0] + ", loadingDateCsv = " + split[13] + ", loadingDateDb = " + lot.getLoadingDate() + ", unloadingDate = " + split[14]);
//                            DateFormat shortFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
//                            DateFormat mediumFormatLoadingDb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//                            String shortLoadingDateDb = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(lot.getLoadingDate()));
//                            String shortLoadingDateCsv = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(split[13]));
//                            String shortUnloadingDateDb = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(lot.getUnloadingDate()));
//                            String shortUnloadingDateCsv = shortFormatLoadingDb.format(mediumFormatLoadingDb.parse(split[14]));
//                            if ((!shortLoadingDateDb.equals(shortLoadingDateCsv)) || (!shortUnloadingDateDb.equals(shortUnloadingDateCsv))) {
//                                CSV csv = new CSV();
//                                csv.open(new File(targetLocation));
//                                csv.put(13, row, lot.getLoadingDate());
//                                csv.put(14, row, lot.getUnloadingDate());
//                                csv.save(new File(targetLocation));
//                                sentEmail = true;
//                                LOGGER.info("update csv loading & unloading [" + split[0] + "]");
//                            } else {
//                                flag = false;
//                                LOGGER.info("no update: " + split[13] + " = " + lot.getLoadingDate());
//                            }
//
//                        }
//
//                        data = bufferedReader.readLine();
//
//                        row++;
//                    }
//                    bufferedReader.close();
//                    fileReader.close();

//                    if (sentEmail == true) {
                        EmailSender emailSenderToHIMSSF = new EmailSender();
                        com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                        user.setFullname("POTS");
//                        String[] to = {"potspenang@gmail.com", "fg79cj@onsemi.com"};
//                        String[] to = {"pots-penang@lsp2u.com", "fg79cj@onsemi.com"};
                        String[] to = {"farhannazri27@yahoo.com","fg79cj@onsemi.com"};
                        emailSenderToHIMSSF.htmlEmailWithAttachment(
                                servletContext,
                                //                    user name
                                user,
                                //                    to
                                to,
                                // attachment file
                                new File("\\\\mysed-rel-app03\\d$\\DOTS\\DOTS_CSV\\dots_planner.csv"),
                                //                    subject
                                "List of RMS_event Ship from Rel Lab",
                                //                    msg
                                "List of RMS_event Ship from Rel Lab. "
                        );
//                    }

//                } catch (Exception ee) {
//                    System.out.println("Error 1 occured while append the fileWriter");
//                } 
//                finally {
//                    try {
//                        fileWriter.close();
//                    } catch (IOException ie) {
//                        System.out.println("Error 2 occured while closing the fileWriter");
//                    }
//                }
////            } else {
////                LOGGER.info("File dots_planner not exists.................");
////            }
//
//        } catch (Exception ee) {
//            ee.printStackTrace();
//        }

    }

}
