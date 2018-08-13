package com.onsemi.dots.tools;

///*

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;

// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.onsemi.dots.tools;
//
//import com.onsemi.cdars.dao.IonicFtpDAO;
//import com.onsemi.cdars.model.IonicFtp;
//import com.onsemi.cdars.model.IonicFtpTemp;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author fg79cj
// */
//
////contoh kalau pki buffered reader
////ad disadvantage klu ad comma dkt dlm column
public class ReadCSVFile_BufferedReader {

//
//    //Delimiters used in the CSV file
//    private static final String COMMA_DELIMITER = ",";
    
//    @Autowired
//    ServletContext servletContext;
//
    public static void main(String args[] ) {

        EmailSender emailSenderToHIMSSF = new EmailSender();
        com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
        user.setFullname("All");
        String[] to = {"fg79cj@onsemi.com"};
        ServletContext servletContext = null;
//                        String[] to = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
        emailSenderToHIMSSF.htmlEmailManyTo(
                servletContext,
                //                    user name
                user,
                //                    to
                to,
                //                    subject
                "Disposition for Interval Issues when Register DOTS",
                //                    msg
                //                            "Disposition for interval before " + nextRmsEvent + " - " + nextInterval + " " + test + " has been made."
                //                            + "Please click this <a href=\"http://localhost:8080/DOTS/do/request/intervalCheck?rms_event=" + nextRmsEvent + "&interval=" + nextInterval + " \">LINK</a> for further information."
                "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/ \">LINK</a> for further information."
                + " Thank you."
        );
//        BufferedReader br = null;
//        try {
//            //Reading the csv file
//            br = new BufferedReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-20160503.csv"));
//
//            //Create List for holding Employee objects
//            List<IonicFtpTemp> empList = new ArrayList<IonicFtpTemp>();
//
//            String line = "";
//            //Read to skip the header
//            br.readLine();
//            //Reading from the second line
//            while ((line = br.readLine()) != null) {
//                String[] employeeDetails = line.split(COMMA_DELIMITER);
//
//                if (employeeDetails.length > 0) {
//                    //Save the employee details in Employee object
//                    IonicFtpTemp emp = new IonicFtpTemp(employeeDetails[0],
//                            employeeDetails[1], employeeDetails[2],
//                            employeeDetails[3], employeeDetails[4],
//                            employeeDetails[5], employeeDetails[6],
//                            employeeDetails[7], employeeDetails[8]);
//                    empList.add(emp);
//                }
//            }
//
//            //Lets print the Employee List
//            for (IonicFtpTemp e : empList) {
////                System.out.println(e.getEmpId() + "   " + e.getFirstName() + "   "
////                        + e.getLastName() + "   " + e.getSalary());
//                System.out.println(e.toString());
//
//                IonicFtp ionicLimitTest = new IonicFtp();
////                ionicLimitTest.setId(e.getId());
//                ionicLimitTest.setEventCode(e.getEventCode());
//                ionicLimitTest.setRms(e.getRms());
//                ionicLimitTest.setIntervals(e.getIntervals());
//                ionicLimitTest.setCurrentStatus(e.getCurrentStatus());
//                ionicLimitTest.setDateOff(e.getDateOff());
//                ionicLimitTest.setEquipId(e.getEquipId());
//                ionicLimitTest.setLcode(e.getLcode());
//                ionicLimitTest.setHardwareFinal(e.getHardwareFinal());
//                ionicLimitTest.setSupportItem(e.getSupportItem());
////                IonicFtpDAO ionicLimitTestDAO = new IonicFtpDAO();
////                QueryResult queryResult = ionicLimitTestDAO.insertIonicFtp(ionicLimitTest); //comment sbb tkut ter run
//            }
//        } catch (Exception ee) {
//            ee.printStackTrace();
//        } finally {
//            try {
//                br.close();
//            } catch (IOException ie) {
//                System.out.println("Error occured while closing the BufferedReader");
//                ie.printStackTrace();
//            }
//        }
    }
//
}
