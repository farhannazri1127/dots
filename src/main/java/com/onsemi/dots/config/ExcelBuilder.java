/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.dots.config;

import com.onsemi.dots.dao.UslTimelapseDAO;
import com.onsemi.dots.model.UslTimelapse;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author fg79cj
 */
public class ExcelBuilder extends AbstractExcelView {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelBuilder.class);

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        Integer year = (Integer) model.get("year");
        Integer month = (Integer) model.get("month");

//surrogate of month and year for second loop
        Integer month2 = month;
        Integer year2 = year;

        Month monthHead = Month.of(month);
        String monthNameHeadFull = monthHead.name();

        String monthNameHead = monthNameHeadFull.substring(1, 3).toLowerCase();
        String monthNameHead2 = monthNameHeadFull.substring(0, 1);
        String todayDate = monthNameHead2 + monthNameHead + " " + year;

        response.setHeader("Content-Disposition", "attachment; filename=\"DOTS - Time Lapse Monthly Performance Report (" + todayDate + ").xls");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("DOTS - Time Lapse Monthly Performance Report (" + todayDate + ").xlst");
        sheet.setDefaultColumnWidth(10);
        sheet.setDisplayGridlines(false);
//        sheet.setDefaultColumnWidth(30);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.COLOR_NORMAL);
        font.setBold(true);
        font.setColor(HSSFColor.DARK_BLUE.index);
        style.setFont(font);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        CellStyle styleBlueWithBorder = workbook.createCellStyle();
        Font fontBlue = workbook.createFont();
        fontBlue.setFontHeightInPoints((short) 10);
        fontBlue.setFontName(HSSFFont.FONT_ARIAL);
        fontBlue.setBoldweight(HSSFFont.COLOR_NORMAL);
        fontBlue.setBold(true);
        fontBlue.setColor(HSSFColor.DARK_BLUE.index);
        styleBlueWithBorder.setFont(fontBlue);
        styleBlueWithBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleBlueWithBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleBlueWithBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleBlueWithBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);

        CellStyle styleWithBorder = workbook.createCellStyle();
        styleWithBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleWithBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleWithBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleWithBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);

        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 10);
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setBoldweight(HSSFFont.COLOR_NORMAL);
        font2.setBold(true);
        font2.setColor(HSSFColor.RED.index);
        style2.setFont(font2);

        CellStyle styleGreen = workbook.createCellStyle();
        Font fontGreen = workbook.createFont();
        fontGreen.setFontHeightInPoints((short) 10);
        fontGreen.setFontName(HSSFFont.FONT_ARIAL);
        fontGreen.setBoldweight(HSSFFont.COLOR_NORMAL);
        fontGreen.setBold(true);
        fontGreen.setColor(HSSFColor.GREEN.index);
        styleGreen.setFont(fontGreen);

        CellStyle styleRed = workbook.createCellStyle();
        Font fontRedRemark = workbook.createFont();
        fontRedRemark.setFontHeightInPoints((short) 9);
//        fontRedRemark.setColor(HSSFColor.RED.index);
        styleRed.setFont(fontRedRemark);

        CellStyle styleBlueandFillGrey = workbook.createCellStyle();
        Font fontBlueNGray = workbook.createFont();
        fontBlueNGray.setFontHeightInPoints((short) 10);
        fontBlueNGray.setFontName(HSSFFont.FONT_ARIAL);
        fontBlueNGray.setBoldweight(HSSFFont.COLOR_NORMAL);
        fontBlueNGray.setBold(true);
        fontBlueNGray.setColor(HSSFColor.BLACK.index);
        styleBlueandFillGrey.setFont(fontBlueNGray);
        styleBlueandFillGrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleBlueandFillGrey.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleBlueandFillGrey.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleBlueandFillGrey.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleBlueandFillGrey.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleBlueandFillGrey.setBorderTop(HSSFCellStyle.BORDER_THIN);

        //create dynamic rownum
        Short rowNum = 0;

        HSSFRow rowtitle = sheet.createRow((short) rowNum);
//        rowtitle.setRowStyle(style);

        HSSFCell cellt_0 = rowtitle.createCell(0);
        cellt_0.setCellStyle(style);
        cellt_0.setCellValue("DOTS - Time Lapse Monthly Performance Report (" + todayDate + ")");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 2);

        HSSFRow rowhead = sheet.createRow((short) rowNum);

        HSSFCell cell1_0 = rowhead.createCell(0);
        cell1_0.setCellStyle(styleBlueWithBorder);
        cell1_0.setCellValue("Event");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //insert to first row for ship to SF
        HSSFRow rowforShiptoSF = sheet.createRow((short) rowNum);

        HSSFCell celltitleShpToSf = rowforShiptoSF.createCell(0);
        celltitleShpToSf.setCellStyle(styleWithBorder);
        celltitleShpToSf.setCellValue("TC");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //insert to 2nd row for ship to RL
        HSSFRow rowforShiptoRL = sheet.createRow((short) rowNum);

        HSSFCell celltitleShpToRL = rowforShiptoRL.createCell(0);
        celltitleShpToRL.setCellStyle(styleWithBorder);
        celltitleShpToRL.setCellValue("HTSL");

        HSSFFont fontRed = workbook.createFont();
        fontRed.setColor(HSSFColor.RED.index);

        Integer cellCol = 1;

        //current month minus 1 coz wanna start from previous month
//        monthTemp = monthTemp - 1;
        //loop for total activity
        for (int x = 1; x <= 12; x++) {
            if (month < 1) {
//                year = year - 1;
                year -= 1;
                month = 12;
            }

            Month monthN = Month.of(month);
            String monthNameFull = monthN.name();

            String monthName = monthNameFull.substring(0, 3);
            String year1 = year.toString().substring(2, 4);

            //total Ship for TC
            UslTimelapseDAO count = new UslTimelapseDAO();
            Integer totalItemTC = count.getCountTotalByEventAndMonthAndYear("TC", month.toString(), year.toString());

            count = new UslTimelapseDAO();
            Integer totalFailTC = count.getCountFailByEventAndMonthAndYear("TC", month.toString(), year.toString());

            count = new UslTimelapseDAO();
            Integer totalItemHTSL = count.getCountTotalByEventAndMonthAndYear("HTSL", month.toString(), year.toString());

            count = new UslTimelapseDAO();
            Integer totalFailHTSL = count.getCountFailByEventAndMonthAndYear("HTSL", month.toString(), year.toString());

            HSSFCell cell1_month = rowhead.createCell(cellCol);
            cell1_month.setCellStyle(style);
            cell1_month.setCellStyle(styleBlueWithBorder);
            cell1_month.setCellValue(monthName + "-" + year1);

            HSSFRichTextString ship = new HSSFRichTextString(totalFailTC + "/" + totalItemTC);
            ship.applyFont(0, Integer.toString(totalFailTC).length(), fontRed);

            HSSFCell cellContentDec16 = rowforShiptoSF.createCell(cellCol);
            cellContentDec16.setCellStyle(styleWithBorder);
            cellContentDec16.setCellValue(ship);

            HSSFRichTextString retrieve = new HSSFRichTextString(totalFailHTSL + "/" + totalItemHTSL);
            retrieve.applyFont(0, Integer.toString(totalFailHTSL).length(), fontRed);

            HSSFCell cellContentRetrieveDec16 = rowforShiptoRL.createCell(cellCol);
            cellContentRetrieveDec16.setCellStyle(styleWithBorder);
            cellContentRetrieveDec16.setCellValue(retrieve);

//            monthTemp = monthTemp - 1;
//            cellCol = cellCol + 1;
            month -= 1;
            cellCol += 1;
        }

        rowNum = (short) (rowNum + 3);

        //remark
        HSSFRow rowforRemarks = sheet.createRow((short) rowNum);

        HSSFCell celltitleRemarks = rowforRemarks.createCell(0);
//        celltitleRemarks.setCellStyle(style);
        celltitleRemarks.setCellStyle(styleRed);
        celltitleRemarks.setCellValue("Remarks *");

        HSSFCell celltitleRemarksContent = rowforRemarks.createCell(1);
        celltitleRemarksContent.setCellStyle(styleRed);
        celltitleRemarksContent.setCellValue("- Number of items fail from total activity USL / number of items");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //remark
        HSSFRow rowforRemarks2 = sheet.createRow((short) rowNum);

        HSSFCell celltitleRemarksContent2 = rowforRemarks2.createCell(1);
        celltitleRemarksContent2.setCellStyle(styleRed);
        celltitleRemarksContent2.setCellValue("- Total USL = 74 hours");

        //add 1 row to current rowNum
        rowNum = (short) (rowNum + 1);

        //remark
        HSSFRow rowforRemarks3 = sheet.createRow((short) rowNum);

        HSSFCell celltitleRemarksContent3 = rowforRemarks3.createCell(1);
        celltitleRemarksContent3.setCellStyle(styleRed);
        celltitleRemarksContent3.setCellValue("- Shipped to Received (12h), Received to Loading (24h), Unloading to Shipped (14h), Shipped to Closed (24h)");

        //add 2 row to current rowNum
        rowNum = (short) (rowNum + 2);

        //Title
        HSSFRow rowfortitle = sheet.createRow((short) rowNum);

        HSSFCell celltitle = rowfortitle.createCell(0);
        celltitle.setCellStyle(style);
        celltitle.setCellValue("List of Failed Items");

        Integer cellColumn = 0;
        Integer stop = 0; //to stop on december

        //loop for all item details in month
//        for (int x = 12; x > 0; x--) {
        for (int x = 12; x > 0 && stop == 0; x--) { //stop loop when reach december

            if (month2 < 1) {
//                year2 = year2 - 1;
                year2 -= 1;
                month2 = 12;
                stop = 1;
            }

            if (month2 == 12) {
                stop = 1;
            }

            //insert activity failed
            String failSteps = "";
            String flag = "0";
            String usl = "";

            //add 2 row to current rowNum
            rowNum = (short) (rowNum + 2);

            Month monthN1 = Month.of(month2);
            String monthName = monthN1.name();
            String yearSub = year2.toString().substring(2, 4);

            //Item fail header Dec 17
            HSSFRow rowforItemFailDec17 = sheet.createRow((short) rowNum);
            HSSFCell cell1Dec17 = rowforItemFailDec17.createCell(0);
            cell1Dec17.setCellStyle(style2);
            cell1Dec17.setCellValue(monthName + " " + yearSub);

            //add 1 row to current rowNum
            rowNum = (short) (rowNum + 1);

            HSSFRow rowforItemFailDec17Ship = sheet.createRow((short) rowNum);
            HSSFCell cell1Dec17Fail = rowforItemFailDec17Ship.createCell(0);
            cell1Dec17Fail.setCellStyle(styleGreen);
            cell1Dec17Fail.setCellValue("TC");

            //add 1 row to current rowNum
            rowNum = (short) (rowNum + 1);

            //Item fail details Dec 17
            HSSFRow rowforItemFailHeaderDec17 = sheet.createRow((short) rowNum);

            HSSFCell cell1ItemtypeDec17 = rowforItemFailHeaderDec17.createCell(0);
            cell1ItemtypeDec17.setCellStyle(styleBlueandFillGrey);
            cell1ItemtypeDec17.setCellValue("RMS_Event");

//            HSSFCell cell1ItemIdDec17 = rowforItemFailHeaderDec17.createCell(2);
//            cell1ItemIdDec17.setCellStyle(styleBlueandFillGrey);
//            cell1ItemIdDec17.setCellValue("Lot");
            HSSFCell cell1mpNoDec17 = rowforItemFailHeaderDec17.createCell(2);
            cell1mpNoDec17.setCellStyle(styleBlueandFillGrey);
            cell1mpNoDec17.setCellValue("Interval");

            HSSFCell cell1DurationDec17 = rowforItemFailHeaderDec17.createCell(4);
            cell1DurationDec17.setCellStyle(styleBlueandFillGrey);
            cell1DurationDec17.setCellValue("Duration (hrs)");

            HSSFCell cell1FailedDec17 = rowforItemFailHeaderDec17.createCell(6);
            cell1FailedDec17.setCellStyle(styleBlueandFillGrey);
            cell1FailedDec17.setCellValue("Process Steps Over USL");

//            HSSFCell cell1Rc = rowforItemFailHeaderDec17.createCell(13);
//            cell1Rc.setCellStyle(styleBlueandFillGrey);
//            cell1Rc.setCellValue("Root Cause");
//
//            HSSFCell cell1Ca = rowforItemFailHeaderDec17.createCell(17);
//            cell1Ca.setCellStyle(styleBlueandFillGrey);
//            cell1Ca.setCellValue("Correlative Action");
            HSSFCell cel1mpNoDec17 = rowforItemFailHeaderDec17.createCell(1);
            cel1mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell3mpNoDec17 = rowforItemFailHeaderDec17.createCell(3);
            cell3mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell5mpNoDec17 = rowforItemFailHeaderDec17.createCell(5);
            cell5mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell7mpNoDec17 = rowforItemFailHeaderDec17.createCell(7);
            cell7mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell8mpNoDec17 = rowforItemFailHeaderDec17.createCell(8);
            cell8mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell9mpNoDec17 = rowforItemFailHeaderDec17.createCell(9);
            cell9mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell0mpNoDec17 = rowforItemFailHeaderDec17.createCell(10);
            cell0mpNoDec17.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell11mpNoDec17 = rowforItemFailHeaderDec17.createCell(11);
            cell11mpNoDec17.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell13mpNoDec17 = rowforItemFailHeaderDec17.createCell(12);
//            cell13mpNoDec17.setCellStyle(styleBlueandFillGrey);

//            HSSFCell cell14mpNoDec17 = rowforItemFailHeaderDec17.createCell(14);
//            cell14mpNoDec17.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell15mpNoDec17 = rowforItemFailHeaderDec17.createCell(15);
//            cell15mpNoDec17.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell117mpNoDec17 = rowforItemFailHeaderDec17.createCell(16);
//            cell117mpNoDec17.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell118mpNoDec17 = rowforItemFailHeaderDec17.createCell(18);
//            cell118mpNoDec17.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell19mpNoDec17 = rowforItemFailHeaderDec17.createCell(19);
//            cell19mpNoDec17.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell19mpNoDec20 = rowforItemFailHeaderDec17.createCell(20);
//            cell19mpNoDec20.setCellStyle(styleBlueandFillGrey);
            UslTimelapseDAO countTCFail = new UslTimelapseDAO();
            Integer totalFailTC = countTCFail.getCountFailByEventAndMonthAndYear("TC", month2.toString(), year2.toString());

            if (totalFailTC > 0) {

                UslTimelapseDAO fail = new UslTimelapseDAO();
                List<UslTimelapse> failTc = fail.GetListOfFailedItemByEventAndMonthAndYear("TC", month2.toString(), year2.toString());
                for (int i = 0; i < failTc.size(); i++) {
                    //add 1 row to current rowNum
                    rowNum = (short) (rowNum + 1);
                    //insert to failed item details for dec 17
                    HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);

                    HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                    celltitleShpToSFFailType.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailType.setCellValue(failTc.get(i).getRms() + failTc.get(i).getLot() + "_" + failTc.get(i).getEvent());

//                    HSSFCell celltitleShpToSFFailId = rowforShiptoSFFail.createCell(2);
//                    celltitleShpToSFFailId.setCellStyle(styleWithBorder);
//                    celltitleShpToSFFailId.setCellValue(failTc.get(i).getLot());
                    HSSFCell celltitleShpToSFFailMP = rowforShiptoSFFail.createCell(2);
                    celltitleShpToSFFailMP.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailMP.setCellValue(failTc.get(i).getIntervals());

//                    HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(4);
//                    celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
//                    celltitleShpToSFFailDur.setCellValue(failTc.get(i).getTotalUSl());
                    failSteps = "";
                    flag = "0";
                    usl = "";

                    if (Integer.parseInt(failTc.get(i).getShipToReceived()) > 12) {
                        failSteps = "Shipped to Received in Penang";
                        flag = "1";
                        usl = failTc.get(i).getShipToReceived();
                    }

                    if (Integer.parseInt(failTc.get(i).getReceivedToLoad()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Received to Loading";
                            flag = "1";
                            usl = usl + "; " + failTc.get(i).getReceivedToLoad();
                        } else {
                            failSteps = "Received to Loading";
                            flag = "1";
                            usl = failTc.get(i).getReceivedToLoad();
                        }
                    }

                    if (Integer.parseInt(failTc.get(i).getUnloadToShip()) > 14) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Unloading to Shipped to SBN Rel Lab";
                            flag = "1";
                            usl = usl + "; " + failTc.get(i).getUnloadToShip();
                        } else {
                            failSteps = "Unloading to Shipped to SBN Rel Lab";
                            flag = "1";
                            usl = failTc.get(i).getUnloadToShip();
                        }
                    }

                    if (Integer.parseInt(failTc.get(i).getShipToClosed()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Shipped from Penang to Closed in DOTS";
                            flag = "1";
                            usl = usl + "; " + failTc.get(i).getShipToClosed();
                        } else {
                            failSteps = "Shipped from Penang to Closed in DOTS";
                            flag = "1";
                            usl = failTc.get(i).getShipToClosed();
                        }
                    }

                    HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(4);
                    celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailDur.setCellValue(usl);

                    HSSFCell celltitleFailStepFailDur = rowforShiptoSFFail.createCell(6);
                    celltitleFailStepFailDur.setCellStyle(styleWithBorder);
                    celltitleFailStepFailDur.setCellValue(failSteps);

//                    HSSFCell celltitleFailShipRc = rowforShiptoSFFail.createCell(13);
//                    celltitleFailShipRc.setCellStyle(styleWithBorder);
//                    celltitleFailShipRc.setCellValue("");
//
//                    HSSFCell celltitleFailShipCa = rowforShiptoSFFail.createCell(17);
//                    celltitleFailShipCa.setCellStyle(styleWithBorder);
//                    celltitleFailShipCa.setCellValue("");
                    HSSFCell cell2 = rowforShiptoSFFail.createCell(1);
                    cell2.setCellStyle(styleWithBorder);

                    HSSFCell cell4 = rowforShiptoSFFail.createCell(3);
                    cell4.setCellStyle(styleWithBorder);

                    HSSFCell cell7 = rowforShiptoSFFail.createCell(5);
                    cell7.setCellStyle(styleWithBorder);

                    HSSFCell cell8 = rowforShiptoSFFail.createCell(7);
                    cell8.setCellStyle(styleWithBorder);

                    HSSFCell cell88 = rowforShiptoSFFail.createCell(8);
                    cell88.setCellStyle(styleWithBorder);

                    HSSFCell cell9 = rowforShiptoSFFail.createCell(9);
                    cell9.setCellStyle(styleWithBorder);

                    HSSFCell cell10 = rowforShiptoSFFail.createCell(10);
                    cell10.setCellStyle(styleWithBorder);

                    HSSFCell cell11 = rowforShiptoSFFail.createCell(11);
                    cell11.setCellStyle(styleWithBorder);

//                    HSSFCell cell12 = rowforShiptoSFFail.createCell(12);
//                    cell12.setCellStyle(styleWithBorder);
//                    HSSFCell cell14 = rowforShiptoSFFail.createCell(14);
//                    cell14.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell15 = rowforShiptoSFFail.createCell(15);
//                    cell15.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell16 = rowforShiptoSFFail.createCell(16);
//                    cell16.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell18 = rowforShiptoSFFail.createCell(18);
//                    cell18.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell19 = rowforShiptoSFFail.createCell(19);
//                    cell19.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell120 = rowforShiptoSFFail.createCell(20);
//                    cell120.setCellStyle(styleWithBorder);
                }
            } else {
                rowNum = (short) (rowNum + 1);
                //insert to failed item details for dec 17
                HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);
                HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                celltitleShpToSFFailType.setCellValue("N/A");

            }

            //add 2 row to current rowNum
            rowNum = (short) (rowNum + 2);

            HSSFRow rowforItemFailDec17Retrieve = sheet.createRow((short) rowNum);
            HSSFCell cell1Dec17FailRet = rowforItemFailDec17Retrieve.createCell(0);
            cell1Dec17FailRet.setCellStyle(styleGreen);
            cell1Dec17FailRet.setCellValue("HTSL");

            //add 1 row to current rowNum
            rowNum = (short) (rowNum + 1);

            //Item fail details Dec 17
            HSSFRow rowforItemFailHeaderDec17Retrieve = sheet.createRow((short) rowNum);

            HSSFCell cell1ItemtypeDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(0);
            cell1ItemtypeDec17Retrieve.setCellStyle(styleBlueandFillGrey);
            cell1ItemtypeDec17Retrieve.setCellValue("RMS_Event");

//            HSSFCell cell1ItemIdDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(2);
//            cell1ItemIdDec17Retrieve.setCellStyle(styleBlueandFillGrey);
//            cell1ItemIdDec17Retrieve.setCellValue("Lot");
            HSSFCell cell1mpNoDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(2);
            cell1mpNoDec17Retrieve.setCellStyle(styleBlueandFillGrey);
            cell1mpNoDec17Retrieve.setCellValue("Interval");

            HSSFCell cell1DurationDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(4);
            cell1DurationDec17Retrieve.setCellStyle(styleBlueandFillGrey);
            cell1DurationDec17Retrieve.setCellValue("Duration (hrs)");

            HSSFCell cell1StepDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(6);
            cell1StepDec17Retrieve.setCellStyle(styleBlueandFillGrey);
            cell1StepDec17Retrieve.setCellValue("Process Steps Over USL");

//            HSSFCell cell1RcDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(13);
//            cell1RcDec17Retrieve.setCellStyle(styleBlueandFillGrey);
//            cell1RcDec17Retrieve.setCellValue("Root Cause");
//
//            HSSFCell cell1CaDec17Retrieve = rowforItemFailHeaderDec17Retrieve.createCell(17);
//            cell1CaDec17Retrieve.setCellStyle(styleBlueandFillGrey);
//            cell1CaDec17Retrieve.setCellValue("Correlative Action");
            HSSFCell cell22 = rowforItemFailHeaderDec17Retrieve.createCell(1);
            cell22.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell44 = rowforItemFailHeaderDec17Retrieve.createCell(3);
            cell44.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell77 = rowforItemFailHeaderDec17Retrieve.createCell(5);
            cell77.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell88 = rowforItemFailHeaderDec17Retrieve.createCell(7);
            cell88.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell888 = rowforItemFailHeaderDec17Retrieve.createCell(8);
            cell888.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell99 = rowforItemFailHeaderDec17Retrieve.createCell(9);
            cell99.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell100 = rowforItemFailHeaderDec17Retrieve.createCell(10);
            cell100.setCellStyle(styleBlueandFillGrey);

            HSSFCell cell11 = rowforItemFailHeaderDec17Retrieve.createCell(11);
            cell11.setCellStyle(styleBlueandFillGrey);

//            HSSFCell cell12 = rowforItemFailHeaderDec17Retrieve.createCell(12);
//            cell12.setCellStyle(styleBlueandFillGrey);
//            HSSFCell cell14 = rowforItemFailHeaderDec17Retrieve.createCell(14);
//            cell14.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell15 = rowforItemFailHeaderDec17Retrieve.createCell(15);
//            cell15.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell16 = rowforItemFailHeaderDec17Retrieve.createCell(16);
//            cell16.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell18 = rowforItemFailHeaderDec17Retrieve.createCell(18);
//            cell18.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell19 = rowforItemFailHeaderDec17Retrieve.createCell(19);
//            cell19.setCellStyle(styleBlueandFillGrey);
//
//            HSSFCell cell20 = rowforItemFailHeaderDec17Retrieve.createCell(20);
//            cell20.setCellStyle(styleBlueandFillGrey);
            UslTimelapseDAO countHtslFail = new UslTimelapseDAO();
            Integer totalHtslFail = countHtslFail.getCountFailByEventAndMonthAndYear("HTSL", month2.toString(), year2.toString());

            if (totalHtslFail > 0) {

                UslTimelapseDAO fail = new UslTimelapseDAO();
                List<UslTimelapse> failHtsl = fail.GetListOfFailedItemByEventAndMonthAndYear("HTSL", month2.toString(), year2.toString());
                for (int i = 0; i < failHtsl.size(); i++) {
                    //add 1 row to current rowNum
                    rowNum = (short) (rowNum + 1);
                    //insert to failed item details for dec 17
                    HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);

                    HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                    celltitleShpToSFFailType.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailType.setCellValue(failHtsl.get(i).getRms() + failHtsl.get(i).getLot() + "_" + failHtsl.get(i).getEvent());

//                    HSSFCell celltitleShpToSFFailId = rowforShiptoSFFail.createCell(2);
//                    celltitleShpToSFFailId.setCellStyle(styleWithBorder);
//                    celltitleShpToSFFailId.setCellValue(failHtsl.get(i).getLot());
                    HSSFCell celltitleShpToSFFailMP = rowforShiptoSFFail.createCell(2);
                    celltitleShpToSFFailMP.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailMP.setCellValue(failHtsl.get(i).getIntervals());

//                    HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(4);
//                    celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
//                    celltitleShpToSFFailDur.setCellValue(failHtsl.get(i).getTotalUSl());
                    flag = "0";
                    failSteps = "";
                    usl = "";

                    if (Integer.parseInt(failHtsl.get(i).getShipToReceived()) > 12) {
                        failSteps = "Shipped to Received in Penang";
                        flag = "1";
                        usl = failHtsl.get(i).getShipToReceived();
                    }

                    if (Integer.parseInt(failHtsl.get(i).getReceivedToLoad()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Received to Loading";
                            flag = "1";
                            usl = usl + "; " + failHtsl.get(i).getReceivedToLoad();
                        } else {
                            failSteps = "Received to Loading";
                            flag = "1";
                            usl = failHtsl.get(i).getReceivedToLoad();
                        }
                    }

                    if (Integer.parseInt(failHtsl.get(i).getUnloadToShip()) > 14) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Unloading to Shipped to SBN Rel Lab";
                            flag = "1";
                            usl = usl + "; " + failHtsl.get(i).getUnloadToShip();
                        } else {
                            failSteps = "Unloading to Shipped to SBN Rel Lab";
                            flag = "1";
                            usl = failHtsl.get(i).getUnloadToShip();
                        }
                    }

                    if (Integer.parseInt(failHtsl.get(i).getShipToClosed()) > 24) {
                        if ("1".equals(flag)) {
                            failSteps = failSteps + "; Shipped from Penang to Closed in DOTS";
                            flag = "1";
                            usl = usl + "; " + failHtsl.get(i).getShipToClosed();
                        } else {
                            failSteps = "Shipped from Penang to Closed in DOTS";
                            flag = "1";
                            usl = failHtsl.get(i).getShipToClosed();
                        }
                    }

                    HSSFCell celltitleShpToSFFailDur = rowforShiptoSFFail.createCell(4);
                    celltitleShpToSFFailDur.setCellStyle(styleWithBorder);
                    celltitleShpToSFFailDur.setCellValue(usl);

                    HSSFCell celltitleFailStepFailDur = rowforShiptoSFFail.createCell(6);
                    celltitleFailStepFailDur.setCellStyle(styleWithBorder);
                    celltitleFailStepFailDur.setCellValue(failSteps);

//                    HSSFCell celltitleFailStepFailRc = rowforShiptoSFFail.createCell(13);
//                    celltitleFailStepFailRc.setCellStyle(styleWithBorder);
//                    celltitleFailStepFailRc.setCellValue("");
//
//                    HSSFCell celltitleFailStepFailCa = rowforShiptoSFFail.createCell(17);
//                    celltitleFailStepFailCa.setCellStyle(styleWithBorder);
//                    celltitleFailStepFailCa.setCellValue("");
                    HSSFCell cell1 = rowforShiptoSFFail.createCell(1);
                    cell1.setCellStyle(styleWithBorder);

                    HSSFCell cell3 = rowforShiptoSFFail.createCell(3);
                    cell3.setCellStyle(styleWithBorder);

                    HSSFCell cell5 = rowforShiptoSFFail.createCell(5);
                    cell5.setCellStyle(styleWithBorder);

                    HSSFCell cell7 = rowforShiptoSFFail.createCell(7);
                    cell7.setCellStyle(styleWithBorder);

                    HSSFCell cell8 = rowforShiptoSFFail.createCell(8);
                    cell8.setCellStyle(styleWithBorder);

                    HSSFCell cell9 = rowforShiptoSFFail.createCell(9);
                    cell9.setCellStyle(styleWithBorder);

                    HSSFCell cell10 = rowforShiptoSFFail.createCell(10);
                    cell10.setCellStyle(styleWithBorder);

                    HSSFCell cell11R = rowforShiptoSFFail.createCell(11);
                    cell11R.setCellStyle(styleWithBorder);

//                    HSSFCell cell12R = rowforShiptoSFFail.createCell(12);
//                    cell12R.setCellStyle(styleWithBorder);
//                    HSSFCell cell14R = rowforShiptoSFFail.createCell(14);
//                    cell14R.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell15R = rowforShiptoSFFail.createCell(15);
//                    cell15R.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell16R = rowforShiptoSFFail.createCell(16);
//                    cell16R.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell8R = rowforShiptoSFFail.createCell(18);
//                    cell8R.setCellStyle(styleWithBorder);
//
//                    HSSFCell cell9R = rowforShiptoSFFail.createCell(19);
//                    cell9R.setCellStyle(styleWithBorder);
//
//                    HSSFCell cel20R = rowforShiptoSFFail.createCell(20);
//                    cel20R.setCellStyle(styleWithBorder);
                }
            } else {
                rowNum = (short) (rowNum + 1);
                //insert to failed item details for dec 17
                HSSFRow rowforShiptoSFFail = sheet.createRow((short) rowNum);
                HSSFCell celltitleShpToSFFailType = rowforShiptoSFFail.createCell(0);
                celltitleShpToSFFailType.setCellValue("N/A");

            }

            //add 2 row to current rowNum
//            rowNum = (short) (rowNum + 2);
            flag = "0";
            failSteps = "";
            usl = "";

//            monthTemp2 = monthTemp2 - 1;
//            cellColumn = cellColumn + 1;
            month2 -= 1;
            cellColumn += 1;

        }
        //end of loop for all items details in month

        //auto resize column
//        sheet.autoSizeColumn(0);    //autosize utk title
//        for (int columnIndex = 2; columnIndex < 15; columnIndex++) {
//            sheet.autoSizeColumn(columnIndex);
//        }
        //merger cell for remark content
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4)); //rowstr, rowend, colstr, colend
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 6)); //rowstr, rowend, colstr, colend
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 6)); //rowstr, rowend, colstr, colend
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 5)); //rowstr, rowend, colstr, colend

        for (int columnIndex = 15; columnIndex < (rowNum + 1); columnIndex++) {
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 0, 1));  //rms  rowstr, rowend, colstr, colend 
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 2, 3));  //interval
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 4, 5));  //duration
            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 6, 11));  //status
//            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 8, 12)); //status
//            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 13, 16));//root cause
//            sheet.addMergedRegion(new CellRangeAddress(columnIndex, columnIndex, 17, 20));//ca
        }
    }

}
