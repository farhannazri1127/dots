/*
            * To change this license header, choose License Headers in Project Properties.
            * To change this template file, choose Tools | Templates
            * and open the template in the editor.
 */
package com.onsemi.dots.config;

import com.onsemi.dots.dao.LotPenangDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.model.FinalRequestTemp;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.model.LotPenangTemp;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.RequestTemp;
import com.onsemi.dots.tools.CSV;
import com.onsemi.dots.tools.EmailSender;
import com.onsemi.dots.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
//@Configuration
//@EnableScheduling
public class DotsFtpConfigOld {

    private static final Logger LOGGER = LoggerFactory.getLogger(DotsFtpConfigOld.class);

    @Autowired
    ServletContext servletContext;

//    @Scheduled(cron = "0 20 * * * *") //every hour 30 minutes
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
                            //                            LOGGER.info("succeed to insert data from FTP");
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
                        String[] to = {"potspenang@gmail.com", "fg79cj@onsemi.com"};
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
            //                     File file = new File("D:\\DOTS\\POTS_CSV\\pots_receive.csv");

            if (file.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_receive.csv"), ',', '"', 1);
                //                 csvReader = new CSVReader(new FileReader("C:\\Users\\fg79cj\\Documents\\test_receive.csv"), ',', '"', 1);
                //                        csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_receive.csv"), ',', '"', 1);

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
                        lotR.setReceivedQuantity(e.getReceivedQuantity());
                        lotR.setReceivedVerificationStatus(e.getReceivedVerificationStatus());
                        lotR.setReceivedVerificationDate(actualVerificationDate);
                        lotR.setReceivedMixStatus(e.getReceivedMixStatus());
                        lotR.setReceivedMixRemarks(e.getReceivedMixRemarks());
                        lotR.setReceivedDemountStatus(e.getReceivedDemountStatus());
                        lotR.setReceivedDemountRemarks(e.getReceivedDemountRemarks());
                        lotR.setReceivedBrokenStatus(e.getReceivedBrokenStatus());
                        lotR.setReceivedBrokenRemarks(e.getReceivedBrokenRemarks());
                        lotR.setRequestId(e.getRequestId());

                        LotPenangDAO lotdD = new LotPenangDAO();
                        QueryResult add = lotdD.updateLotPenangWhenPenangReceived(lotR);
                        if (add.getResult() == 0) {
                            LOGGER.info("Fail to insert data from POTS_receive FTP");
                        } else {

                            Request req = new Request();
                            req.setStatus("Received in Penang");
                            req.setModifiedBy("Penang CSV");
                            req.setId(e.getRequestId());
                            RequestDAO reqD = new RequestDAO();
                            QueryResult reqStatus = reqD.updateRequestStatus(req);
                            //                            LOGGER.info("succeed to insert data from POTS_receive FTP FTP");
                        }
                    } else if (lot.getReceivedDate() != null && !"null".equals(e.getReceivedDate())) {

                        DateFormat actualFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String receivedDatedb = lot.getReceivedDate();
                        String actualReceivedDatedb = actualFormatReceiveddb.format(originalFormatReceiveddb.parse(receivedDatedb));

                        DateFormat actualFormatReceivedftp = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceivedftp = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.ENGLISH);
                        String receivedDateftp = e.getReceivedDate();
                        String actualReceivedDateftp = actualFormatReceivedftp.format(originalFormatReceivedftp.parse(receivedDateftp));

                        if (!actualReceivedDateftp.equals(actualReceivedDatedb)) {

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
                            lotR.setReceivedQuantity(e.getReceivedQuantity());
                            lotR.setReceivedVerificationStatus(e.getReceivedVerificationStatus());
                            lotR.setReceivedVerificationDate(actualVerificationDate);
                            lotR.setReceivedMixStatus(e.getReceivedMixStatus());
                            lotR.setReceivedMixRemarks(e.getReceivedMixRemarks());
                            lotR.setReceivedDemountStatus(e.getReceivedDemountStatus());
                            lotR.setReceivedDemountRemarks(e.getReceivedDemountRemarks());
                            lotR.setReceivedBrokenStatus(e.getReceivedBrokenStatus());
                            lotR.setReceivedBrokenRemarks(e.getReceivedBrokenRemarks());
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            QueryResult add = lotdD.updateLotPenangWhenPenangReceived(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data from POTS_receive FTP");
                            } else {
                                Request req = new Request();
                                req.setStatus("Received in Penang");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);
                                //                                LOGGER.info("succeed to insert data from POTS_receive FTP FTP");
                            }

                        } else {
                            //                            LOGGER.info("no update from POTS_receive FTP FTP");
                        }

                    } else {
                        //                        LOGGER.info("no data received from POTS_receive FTP FTP");
                    }

                }

            } else {
                LOGGER.info("pots_receive csv file not found!");
            }

            //pots_process
            File filePotProcess = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_process.csv");
            //             File filePotProcess = new File("C:\\Users\\fg79cj\\Documents\\test_process.csv");
            //                     File filePotProcess = new File("D:\\DOTS\\POTS_CSV\\pots_process.csv");

            if (filePotProcess.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_process.csv"), ',', '"', 1);
                //                 csvReader = new CSVReader(new FileReader("C:\\Users\\fg79cj\\Documents\\test_process.csv"), ',', '"', 1);
                //                        csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_process.csv"), ',', '"', 1);

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
                            //                            LOGGER.info("succeed to insert data loading from POTS_process FTP FTP");
                        }
                    } else if (!"null".equals(e.getLoadingDate()) && lot.getLoadingDate() != null) {

                        DateFormat actualFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String receivedDatedb = lot.getLoadingDate();
                        String actualLoadingDatedb = actualFormatReceiveddb.format(originalFormatReceiveddb.parse(receivedDatedb));

                        DateFormat actualFormatReceivedftp = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceivedftp = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.ENGLISH);
                        String receivedDateftp = e.getLoadingDate();
                        String actualLoadingDateftp = actualFormatReceivedftp.format(originalFormatReceivedftp.parse(receivedDateftp));

                        if (!actualLoadingDateftp.equals(actualLoadingDatedb)) {

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
                                //                                LOGGER.info("succeed to insert data loading from POTS_processs FTP FTP");
                            }

                        } else {
                            //                            LOGGER.info("no update loading from POTS_process FTP FTP");
                        }

                    } else if ("null".equals(e.getLoadingDate()) && lot.getLoadingDate() != null) {
                        //                        LOGGER.info("no data loading from POTS_process FTP FTP [ftp null]");
                    } else {
                        //                        LOGGER.info("no data loading from POTS_process FTP FTP [both null]");
                    }

                    //unloading
                    if (lot.getUnloadingDate() == null && !"null".equals(e.getUnloadingDate())) {

                        DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                        //                        DateFormat mediumFormatReceived = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.ENGLISH);
                        String receivedDate = e.getUnloadingDate();
                        String shortUnloadingDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                        LotPenang lotR = new LotPenang();
                        lotR.setUnloadingDate(shortUnloadingDate);
                        lotR.setStatus("Unloading Process");
                        lotR.setUnloadingRemarks(e.getUnloadingRemarks());
                        lotR.setUnloadingBy(e.getUnloadingBy());
                        lotR.setUnloadingMixStatus(e.getUnloadingMixStatus());
                        lotR.setUnloadingMixRemarks(e.getUnloadingMixRemarks());
                        lotR.setUnloadingDemountStatus(e.getUnloadingDemountStatus());
                        lotR.setUnloadingDemountRemarks(e.getUnloadingDemountRemarks());
                        lotR.setUnloadingBrokenStatus(e.getUnloadingBrokenStatus());
                        lotR.setUnloadingBrokenRemarks(e.getUnloadingBrokenRemarks());
                        lotR.setRequestId(e.getRequestId());

                        LotPenangDAO lotdD = new LotPenangDAO();
                        //                        QueryResult add = lotdD.updateLotPenangWhenUnloading(lotR);
                        QueryResult add = lotdD.updateLotPenangWhenUnloadingWithVM(lotR);
                        if (add.getResult() == 0) {
                            LOGGER.info("Fail to insert data unloading from POTS_process FTP");
                        } else {
                            Request req = new Request();
                            req.setStatus("Unloading Process");
                            req.setModifiedBy("Penang CSV");
                            req.setId(e.getRequestId());
                            RequestDAO reqD = new RequestDAO();
                            QueryResult reqStatus = reqD.updateRequestStatus(req);
                            //                            LOGGER.info("succeed to insert data unloading from POTS_process FTP FTP");
                        }
                    } else if (lot.getUnloadingDate() != null && !"null".equals(e.getUnloadingDate())) {

                        DateFormat actualFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceiveddb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String receivedDatedb = lot.getUnloadingDate();
                        String actualLoadingDatedb = actualFormatReceiveddb.format(originalFormatReceiveddb.parse(receivedDatedb));

                        DateFormat actualFormatReceivedftp = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                        DateFormat originalFormatReceivedftp = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.ENGLISH);
                        String receivedDateftp = e.getUnloadingDate();
                        String actualLoadingDateftp = actualFormatReceivedftp.format(originalFormatReceivedftp.parse(receivedDateftp));

                        if (!actualLoadingDateftp.equals(actualLoadingDatedb)) {

                            DateFormat shortFormatReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            DateFormat mediumFormatReceived = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ENGLISH);
                            String receivedDate = e.getUnloadingDate();
                            String shortUnloadingDate = shortFormatReceived.format(mediumFormatReceived.parse(receivedDate));

                            LotPenang lotR = new LotPenang();
                            lotR.setUnloadingDate(shortUnloadingDate);
                            lotR.setStatus("Unloading Process");
                            lotR.setUnloadingRemarks(e.getUnloadingRemarks());
                            lotR.setUnloadingBy(e.getUnloadingBy());
                            lotR.setUnloadingMixStatus(e.getUnloadingMixStatus());
                            lotR.setUnloadingMixRemarks(e.getUnloadingMixRemarks());
                            lotR.setUnloadingDemountStatus(e.getUnloadingDemountStatus());
                            lotR.setUnloadingDemountRemarks(e.getUnloadingDemountRemarks());
                            lotR.setUnloadingBrokenStatus(e.getUnloadingBrokenStatus());
                            lotR.setUnloadingBrokenRemarks(e.getUnloadingBrokenRemarks());
                            lotR.setRequestId(e.getRequestId());

                            LotPenangDAO lotdD = new LotPenangDAO();
                            //                            QueryResult add = lotdD.updateLotPenangWhenUnloading(lotR);
                            QueryResult add = lotdD.updateLotPenangWhenUnloadingWithVM(lotR);
                            if (add.getResult() == 0) {
                                LOGGER.info("Fail to insert data unloading from POTS_process FTP");
                            } else {
                                Request req = new Request();
                                req.setStatus("Unloading Process");
                                req.setModifiedBy("Penang CSV");
                                req.setId(e.getRequestId());
                                RequestDAO reqD = new RequestDAO();
                                QueryResult reqStatus = reqD.updateRequestStatus(req);
                                //                                LOGGER.info("succeed to insert data unloading from POTS_processs FTP FTP");
                            }

                        } else {
                            //                            LOGGER.info("no update unloading from POTS_process FTP FTP");
                        }

                    } else if ("null".equals(e.getUnloadingDate()) && lot.getUnloadingDate() != null) {
                        //                        LOGGER.info("no data unloading from POTS_process FTP FTP [ftp null]");
                    } else {
                        //                        LOGGER.info("no data unloading from POTS_process FTP FTP [both null]");
                    }

                }

            } else {
                LOGGER.info("csv pot_proecss file not found!");
            }

            //shipment from penang
            File filePotsShipment = new File("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_shipment.csv");
            //                      File filePotsShipment = new File("D:\\DOTS\\POTS_CSV\\pots_shipment.csv");

            if (filePotsShipment.exists()) {
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\POTS_CSV\\pots_shipment.csv"), ',', '"', 1);
                //                        csvReader = new CSVReader(new FileReader("D:\\DOTS\\POTS_CSV\\pots_shipment.csv"), ',', '"', 1);

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

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    //temporary for update rmsid
//    @Scheduled(cron = "0 42 * * * *") //every hour 30 minutes
//    public void updateRmsIdFtp() throws IOException {
//
//        CSVReader csvReader = null;
//        //        LOGGER.info("cron detected!!!");
//
//        try {
//            /**
//             * Reading the CSV File Delimiter is comma Start reading from line 1
//             *
//             */
//
//            File file = new File("\\\\mysed-rel-app03\\d$\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");
//            //            File file = new File("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");
//
//            if (file.exists()) {
//                LOGGER.info("File dots_rms_ftp found!!!");
//                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\d$\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);
//                //                csvReader = new CSVReader(new FileReader("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);
//
//                String[] inventory = null;
//                //Create List for holding Employee objects
//                List<RequestTemp> empList = new ArrayList<RequestTemp>();
//
//                while ((inventory = csvReader.readNext()) != null) {
//                    //Save the employee details in Employee object
//                    RequestTemp emp = new RequestTemp(inventory[0],
//                            inventory[1], inventory[2],
//                            inventory[3], inventory[4],
//                            inventory[5], inventory[6],
//                            inventory[7], inventory[8], inventory[9], inventory[10], inventory[11]);
//                    empList.add(emp);
//                }
//
//                //Lets print the Inventory List
//                for (RequestTemp e : empList) {
//
//                    // check rms_event + interval exist or not
//                    RequestDAO reqD = new RequestDAO();
//                    int countRms = reqD.getCountRequestRmsLotEventInterval(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval());
//                    if (countRms > 1) {
//                        LOGGER.info("Data have more than 1 in DB. Please re-check");
//                    } else if (countRms == 0) {
//                        Request req = new Request();
//                        req.setRms(e.getRms());
//                        req.setEvent(e.getEvent());
//                        req.setLotId(e.getLotId());
//                        req.setDevice(e.getDevice());
//                        req.setPackages(e.getPackages());
//                        req.setInterval(e.getInterval());
//                        if (e.getQuantity() != null) {
//                            if (!"".equals(e.getQuantity())) {
//                                req.setQuantity(e.getQuantity());
//                            } else {
//                                req.setQuantity("0");
//                            }
//                        } else {
//                            req.setQuantity("0");
//                        }
//                        String expectedCondition = "";
//                        if (!"".equals(e.getExpectedMinCondition()) && !"".equals(e.getExpectedMaxCondition())) {
//                            expectedCondition = e.getExpectedMinCondition() + " / " + e.getExpectedMaxCondition() + " `C";
//                        } else if (!"".equals(e.getExpectedMinCondition()) && "".equals(e.getExpectedMaxCondition())) {
//                            expectedCondition = e.getExpectedMinCondition() + " `C";
//                        } else if ("".equals(e.getExpectedMinCondition()) && !"".equals(e.getExpectedMaxCondition())) {
//                            expectedCondition = e.getExpectedMaxCondition() + " `C";
//
//                        } else {
//                            expectedCondition = "";
//                        }
//                        req.setExpectedTestCondition(expectedCondition);
//                        String shortLoadingDate = null;
//                        String shortUnloadingDate = null;
//                        if (!"".equals(e.getExpectedLoading())) {
//                            DateFormat shortFormatLoading = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//                            DateFormat mediumFormatLoading = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
//                            String loadingdate = e.getExpectedLoading();
//                            shortLoadingDate = shortFormatLoading.format(mediumFormatLoading.parse(loadingdate));
//                        }
//                        if (!"".equals(e.getExpectedUnloading())) {
//                            DateFormat shortFormatUnloading = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//                            DateFormat mediumFormatUnloading = new SimpleDateFormat("dd-MMM-yy hh:mm a", Locale.ENGLISH);
//                            String unloadingdate = e.getExpectedUnloading();
//                            shortUnloadingDate = shortFormatUnloading.format(mediumFormatUnloading.parse(unloadingdate));
//                        }
//
//                        req.setLoadingDate(shortLoadingDate);
//                        req.setUnloadingDate(shortUnloadingDate);
//                        req.setStatus("New");
//
//                        String rms_event = e.getRms() + e.getLotId() + "_" + e.getEvent();
//                        req.setRmsEvent(rms_event);
//                        req.setRmsId(e.getRmsId());
//                        req.setFlag("0");
//                        req.setCreatedBy("From Ftp");
//                        reqD = new RequestDAO();
//                        QueryResult add = reqD.insertRequestWithRmsIdFromFtp(req);
//                        if (add.getGeneratedKey().equals("0")) {
//                            LOGGER.info("Fail to insert data from FTP");
//                        } else {
//                            //                            LOGGER.info("succeed to insert data from FTP");
//                        }
//                    } else {
//                      
//                        reqD = new RequestDAO();
//                        Request request = reqD.getRequestByRmsLotEventIntervalForFtp(e.getRms(), e.getLotId(), e.getEvent(), e.getInterval());
//                        
//                        //update rmsid
//                        if (request.getRmsId() == null) {
//                            Request requestRmsId = new Request();
//                            requestRmsId.setRmsId(e.getRmsId());
//                            requestRmsId.setId(request.getId());
//                            reqD = new RequestDAO();
//                            QueryResult updateRmsId = reqD.updateRequestRmsIdFTP(requestRmsId);
//                        }
//                    }
//                }
//
//            } else {
//                LOGGER.info("csv file not found!");
//            }
//
//        } catch (Exception ee) {
//            ee.printStackTrace();
//        }
//
//    }
}
