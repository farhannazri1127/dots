package com.onsemi.rats.pdf.viewer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.rats.pdf.AbstractITextPdfViewDoList;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoListBlankPdf extends AbstractITextPdfViewDoList {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoListBlankPdf.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Font fontContent = fontOpenSans();

        PdfPTable tableHeader = new PdfPTable(3);
        tableHeader.setWidths(new float[]{4.0f, 15.0f, 5.0f});
        tableHeader.setTotalWidth(527);
        tableHeader.setLockedWidth(true);

        PdfPCell cellBlank = new PdfPCell();
        cellBlank.setFixedHeight(35);
        cellBlank.setPaddingBottom(5);
        cellBlank.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellBlank);

        PdfPCell cellTitle = new PdfPCell();
        cellTitle.setPhrase(new Phrase("TRUCK LOADING CHECKSHEET / DO TO PENANG", fontOpenSans(12f, Font.BOLD)));
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setFixedHeight(40);
        cellTitle.setPaddingBottom(5);
        cellTitle.setPaddingTop(0);
        cellTitle.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellTitle);

        ServletContext context = getServletContext();
        String file = "/resources/img/OnSemiLogo.jpg";
        InputStream is = context.getResourceAsStream(file);
        byte[] bytes = IOUtils.toByteArray(is);
        Image image = Image.getInstance(bytes);

        PdfPCell cellLogo = new PdfPCell(image, true);
        cellLogo.setFixedHeight(45);
        cellLogo.setPaddingBottom(5);
        cellLogo.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellLogo);

        doc.add(tableHeader);

        doc.add(Chunk.NEWLINE);

        PdfPTable address = new PdfPTable(3);
        address.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        address.setTotalWidth(527);
        address.setLockedWidth(true);

        PdfPCell cellShipper = new PdfPCell();
        cellShipper.setPhrase(new Phrase("SHIPPER", fontOpenSans(10f, Font.UNDERLINE)));
        cellShipper.setPaddingTop(0);
        cellShipper.setPaddingLeft(5);
        cellShipper.setBorder(Rectangle.NO_BORDER);
        address.addCell(cellShipper);

        PdfPCell cellBlank2 = new PdfPCell();
        cellBlank2.setBorder(Rectangle.NO_BORDER);
        address.addCell(cellBlank2);

        PdfPCell cellConsignee = new PdfPCell();
        cellConsignee.setPhrase(new Phrase("CONSIGNEE", fontOpenSans(10f, Font.UNDERLINE)));
        cellConsignee.setPaddingTop(0);
        cellConsignee.setPaddingLeft(10);
        cellConsignee.setBorder(Rectangle.NO_BORDER);
        address.addCell(cellConsignee);

        doc.add(address);

        doc.add(Chunk.NEWLINE);

        PdfPTable address2 = new PdfPTable(3);
        address2.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        address2.setTotalWidth(527);
        address2.setLockedWidth(true);

        PdfPCell cellShipper2 = new PdfPCell();
        cellShipper2.setPhrase(new Phrase("ON SEMICONDUCTOR MALAYSIA SDN BHD\nLOT 55 SENAWANG IND ESTATE\n70450 SEREMBAN\nNEGERI SEMBILAN", fontContent));
        cellShipper2.setLeading(1.1f, 1.1f);
        cellShipper2.setFixedHeight(65);
        cellShipper2.setPaddingBottom(5);
        cellShipper2.setPaddingTop(5);
        cellShipper2.setPaddingLeft(5);
        cellShipper2.setBorder(Rectangle.NO_BORDER);
        address2.addCell(cellShipper2);

        PdfPCell cellBlank3 = new PdfPCell();
        cellBlank3.setFixedHeight(65);
        cellBlank3.setPaddingBottom(5);
        cellBlank3.setBorder(Rectangle.NO_BORDER);
        address2.addCell(cellBlank3);

        PdfPCell cellConsignee2 = new PdfPCell();
        cellConsignee2.setPhrase(new Phrase("LSP ADVANCE SDN BHD\nNO L1-2, PENANG SME CENTRE, PLOT 105\nHILIR SUNGAI KLUANG 5\nBAYAN LEPAS IND PARK FASA 4"
                + "\n11900 BAYAN LEPAS, PENANG", fontContent));
        cellConsignee2.setLeading(1.1f, 1.1f);
        cellConsignee2.setFixedHeight(65);
        cellConsignee2.setPaddingBottom(5);
        cellConsignee2.setPaddingTop(5);
        cellConsignee2.setPaddingLeft(10);
        cellConsignee2.setBorder(Rectangle.NO_BORDER);
        address2.addCell(cellConsignee2);

        doc.add(address2);

        doc.add(Chunk.NEWLINE);

//        FinalRequest fReq = (FinalRequest) model.get("freq");
        PdfPTable gts = new PdfPTable(3);
        gts.setWidths(new float[]{1.8f, 4.0f, 9.0f});
        gts.setTotalWidth(527);
        gts.setLockedWidth(true);

        PdfPCell gtsHeader = new PdfPCell();
        gtsHeader.setPhrase(new Phrase("GTS/INV#", fontOpenSans(9f, Font.BOLD)));
        gtsHeader.setFixedHeight(20);
        gtsHeader.setPaddingBottom(5);
        gtsHeader.setPaddingTop(5);
        gtsHeader.setPaddingLeft(5);
        gtsHeader.setBorder(Rectangle.NO_BORDER);
        gts.addCell(gtsHeader);

        PdfPCell gtsCell = new PdfPCell();
        gtsCell.setPhrase(new Phrase("", fontOpenSans(9f, Font.BOLD)));
        gtsCell.setFixedHeight(20);
//        gtsCell.setPaddingBottom(5);
//        gtsHeader.setPaddingTop(5);
//        gtsCell.setPaddingRight(100);
//        gtsCell.setBorder(Rectangle.NO_BORDER);
        gts.addCell(gtsCell);

        PdfPCell gtsCellBlank = new PdfPCell();
        gtsCellBlank.setFixedHeight(20);
        gtsCellBlank.setPaddingBottom(5);
        gtsCellBlank.setBorder(Rectangle.NO_BORDER);
        gts.addCell(gtsCellBlank);

        doc.add(gts);

        doc.add(Chunk.NEWLINE);

        PdfPTable date = new PdfPTable(3);
        date.setWidths(new float[]{0.9f, 5.0f, 6.0f});
        date.setTotalWidth(527);
        date.setLockedWidth(true);

        PdfPCell dateHeader = new PdfPCell();
        dateHeader.setPhrase(new Phrase("\nDATE:", fontContent));
        dateHeader.setFixedHeight(25);
        dateHeader.setPaddingBottom(5);
        dateHeader.setPaddingLeft(5);
        dateHeader.setBorder(Rectangle.NO_BORDER);
        date.addCell(dateHeader);

        PdfPCell dateCell = new PdfPCell();
        dateCell.setPhrase(new Phrase("\n", fontContent));
        dateCell.setFixedHeight(25);
        dateCell.setPaddingBottom(5);
        dateCell.setPaddingRight(50);
        dateCell.setBorder(Rectangle.NO_BORDER);
        date.addCell(dateCell);

        PdfPCell dateCellBlank = new PdfPCell();
        dateCellBlank.setFixedHeight(25);
        dateCellBlank.setPaddingBottom(5);
        dateCellBlank.setBorder(Rectangle.NO_BORDER);
        date.addCell(dateCellBlank);

        doc.add(date);

        Integer cellPadding = 5;

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100.0f);
//        table.setWidths(new float[]{0.4f, 0.8f, 0.6f, 0.6f, 2.7f, 1.0f, 0.8f, 0.8f, 0.6f, 1.1f});
        table.setWidths(new float[]{0.4f, 0.7f, 0.6f, 0.4f, 2.9f, 0.9f, 1.0f, 0.8f, 0.6f, 0.8f, 0.5f});
        table.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(6f, Font.BOLD);
//        fontHeader.setColor(BaseColor.WHITE);
        PdfPCell cellHeader = new PdfPCell();
//        cellHeader.setBackgroundColor(BaseColor.GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent2 = fontOpenSans(6.5f, Font.NORMAL);
        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);

        int i = 0;
        while (i < 20) {
            if (i == 0) {
                cellHeader.setPhrase(new Phrase("No", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("RMS", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("EVENT", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("LOT", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("BARCODE (RMS#_EVENT)", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("DEVICE", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("PACKAGE", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("INTERVAL", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("S.SIZE", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("DO#", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("BOX", fontHeader));
                table.addCell(cellHeader);
            }
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(" ", fontContent2));
            table.addCell(cellContent);
            i++;
        }

        doc.add(table);

        doc.add(Chunk.NEWLINE);

        PdfPTable blank = new PdfPTable(1);
        blank.setWidths(new float[]{10.0f});
        blank.setTotalWidth(527);
        blank.setLockedWidth(true);

        PdfPCell blankcell = new PdfPCell();
        blankcell.setPhrase(new Phrase("\n", fontContent));
        blankcell.setFixedHeight(25);
        blankcell.setPaddingBottom(5);
        blankcell.setPaddingLeft(5);
        blankcell.setBorder(Rectangle.NO_BORDER);
        blank.addCell(blankcell);
        doc.add(blank);
//        doc.add(blank);

        PdfPTable box = new PdfPTable(3);
        box.setWidths(new float[]{4.3f, 2.0f, 20.0f});
        box.setTotalWidth(527);
        box.setLockedWidth(true);

        PdfPCell boxHeader = new PdfPCell();
        boxHeader.setPhrase(new Phrase("BOX QUANTITY:", fontOpenSans(9f, Font.NORMAL)));
        boxHeader.setFixedHeight(20);
        boxHeader.setPaddingBottom(5);
        boxHeader.setPaddingTop(5);
        boxHeader.setPaddingLeft(5);
        boxHeader.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxHeader);

        PdfPCell boxCell = new PdfPCell();
//        boxHeader.setPhrase(new Phrase("\n", fontContent));
        boxHeader.setFixedHeight(19);
        boxHeader.setPaddingBottom(8);
        boxHeader.setPaddingRight(100);
//        boxCell.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxCell);

        PdfPCell boxCellBlank = new PdfPCell();
        boxCellBlank.setPhrase(new Phrase("*REMARKS: BOX QTY MUST MATCH GTS QTY:", fontOpenSans(9f, Font.NORMAL)));
        boxCellBlank.setFixedHeight(20);
        boxCellBlank.setPaddingBottom(5);
        boxCellBlank.setPaddingTop(5);
        boxCellBlank.setPaddingLeft(5);
        boxCellBlank.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxCellBlank);

        doc.add(box);

        doc.add(blank);

        PdfPTable sign = new PdfPTable(3);
        sign.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        sign.setTotalWidth(527);
        sign.setLockedWidth(true);

        PdfPCell sign2 = new PdfPCell();
        sign2.setPhrase(new Phrase("SECURITY STAFF :\n\n\n\nREL LAB STAFF :", fontContent));
        sign2.setFixedHeight(100);
        sign2.setPaddingBottom(5);
        sign2.setPaddingTop(5);
        sign2.setPaddingLeft(5);
        sign2.setBorder(Rectangle.NO_BORDER);
        sign.addCell(sign2);

        PdfPCell cellBlank4 = new PdfPCell();
        cellBlank4.setFixedHeight(100);
        cellBlank4.setPaddingBottom(5);
        cellBlank4.setBorder(Rectangle.NO_BORDER);
        sign.addCell(cellBlank4);

        PdfPCell cellDriver = new PdfPCell();
        cellDriver.setPhrase(new Phrase("DRIVER:\nI/C NO: _________________________________\n\nSEAL NO :_______________________________\nTRUCK/LORRY NO: _____________________\n"
                + "TIME DEPARTURE: ______________________", fontContent));
        cellDriver.setLeading(1.4f, 1.4f);
        cellDriver.setFixedHeight(100);
        cellDriver.setPaddingBottom(5);
        cellDriver.setPaddingTop(5);
        cellDriver.setPaddingLeft(10);
        cellDriver.setBorder(Rectangle.NO_BORDER);
        sign.addCell(cellDriver);

        doc.add(sign);

    }
}
