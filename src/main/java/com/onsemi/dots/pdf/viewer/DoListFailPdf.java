package com.onsemi.dots.pdf.viewer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.dots.pdf.AbstractITextPdfViewDoList;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoListFailPdf extends AbstractITextPdfViewDoList {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoListFailPdf.class);

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
        cellTitle.setPhrase(new Phrase("THERE ARE MORE THAN 1 GTS NUMBER IN DO LIST. PLEASE RE-CHECK", fontOpenSans(12f, Font.BOLD)));
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

    }
}
