package com.onsemi.dots.pdf.viewer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.pdf.AbstractITextPdfViewPotrait;
import java.util.List;

public class DotsQueryPdf extends AbstractITextPdfViewPotrait {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String title = "LOT IN PENANG INFORMATION";

        Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
        viewTitle.setAlignment(Element.ALIGN_CENTER);

        doc.add(viewTitle);

        Integer cellPadding = 5;

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{2.0f, 4.0f});
        table.setSpacingBefore(20);

        PdfPTable table2 = new PdfPTable(4);
        table2.setWidthPercentage(100.0f);
        table2.setWidths(new float[]{4.0f, 2.0f, 3.0f, 3.0f});
        table2.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(9f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.DARK_GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);

        LotPenang lotPenang = (LotPenang) model.get("lotPenang");

        cellHeader.setPhrase(new Phrase("RMS_Event", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getRmsEvent(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Interval", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getInterval(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Device", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getDevice(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Package", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getPackages(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Chamber", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getExpectedChamber(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Chamber Level", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getExpectedChamberLevel(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Test Condition", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getExpectedCondition(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getExpectedQuantity(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Estimate Loading Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getExpectedLoadingDate(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Estimate Unloading Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getExpectedUnloadingDate(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Do Number", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getDoNumber(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("GTS Number", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getGts(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Ship Date from Rel Lab", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getShipmentFromRelView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Receieved Date in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getReceivedDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Receieved Quantity in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getReceivedQuantity(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Pre Loading Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getPreVmDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Chamber in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getChamberId(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Chamber Level in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getChamberLevel(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Test Condition in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getTestCondition(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Loading Date in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getLoadingDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Unloading Date in Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getUnloadingDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Post Loading Date", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getPostVmDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Ship Date from Penang", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getShipmentDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Received Date in Rel Lab", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getClosedDateView(), fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Status", fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase(lotPenang.getStatus(), fontContent));
        table.addCell(cellContent);

        doc.add(table);

        String title5 = "\n\n";
        Paragraph viewTitle5 = new Paragraph(title5, fontOpenSans(8f, Font.BOLD));
        viewTitle5.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle5);
        String title6 = "Log";
        Paragraph viewTitle6 = new Paragraph(title6, fontOpenSans(8f, Font.BOLD));
        viewTitle6.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle6);

        //Log
        List<Log> test = (List<Log>) model.get("Log");

        int i = 0;
        while (i < test.size()) {
            String moduleName = test.get(i).getModule();
            if (i == 0) {
                //Header
                cellHeader.setPhrase(new Phrase("Status", fontHeader));
                table2.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Timestamp", fontHeader));
                table2.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Module Stage", fontHeader));
                table2.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Created by", fontHeader));
                table2.addCell(cellHeader);
            }
            cellContent.setPhrase(new Phrase(test.get(i).getStatus(), fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(test.get(i).getStatusDate(), fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(moduleName, fontContent));
            table2.addCell(cellContent);
            cellContent.setPhrase(new Phrase(test.get(i).getCreatedBy(), fontContent));
            table2.addCell(cellContent);

            i++;
        }
        doc.add(table2);

    }
}
