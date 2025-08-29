package com.onsemi.rats.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.rats.dao.DocsDAO;
import com.onsemi.rats.dao.ParameterDetailsDAO;
import com.onsemi.rats.model.Docs;
import com.onsemi.rats.model.ParameterDetails;
import com.onsemi.rats.model.UserSession;
import com.onsemi.rats.tools.QueryResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@RequestMapping(value = "/docs")
@SessionAttributes({"userSession"})
public class DocsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocsController.class);
    String[] args = {};

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\GRATS-Attachment\\Docs\\"; //server 

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String docs(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> site = sDAO.getGroupParameterDetailList("", "034");
        model.addAttribute("site", site);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> type = sDAO.getGroupParameterDetailList("", "035");
        model.addAttribute("type", type);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> application = sDAO.getGroupParameterDetailList("", "029");
        model.addAttribute("application", application);

        String GroupID = userSession.getGroup();
        model.addAttribute("GroupID", GroupID);

        DocsDAO docsDAO = new DocsDAO();
        List<Docs> docsList = docsDAO.getDocsList();
        model.addAttribute("docsList", docsList);
        return "docs/docs";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "docs/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String application,
            @RequestParam(required = false) String site,
            @RequestParam(required = false) MultipartFile formFile
    ) {
        String stringPath = "";

        Docs docs = new Docs();
        docs.setType(type);
        docs.setApplication(application);
        docs.setSite(site);
        docs.setUploadBy(userSession.getFullname());
        docs.setFlag("0");
        DocsDAO docsDAO = new DocsDAO();
        QueryResult queryResult = docsDAO.insertDocs(docs);

        args = new String[1];
        args[0] = type + " - " + application;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("docs", docs);
            return "docs/add";
        } else {

            try {
                // Get the file and save it somewhere
                byte[] bytes = formFile.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + queryResult.getGeneratedKey() + "_" + formFile.getOriginalFilename());
                Files.write(path, bytes);
                stringPath = path.toString();
                LOGGER.info("path : " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            docs = new Docs();
            docs.setId(queryResult.getGeneratedKey());
            docs.setName(formFile.getOriginalFilename());
            docs.setPath(stringPath);
            docsDAO = new DocsDAO();
            QueryResult queryResult1 = docsDAO.updateDocsDetail(docs);

            redirectAttrs.addFlashAttribute("success", "File : " + formFile.getOriginalFilename() + " is succesfully uploaded.");
            return "redirect:/docs/";
        }
    }

    @RequestMapping(value = "/downloadAttach/{id}", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletRequest request,
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        DocsDAO attD = new DocsDAO();
        Docs att = attD.getDocs(id);

        // construct the complete absolute path of the file
        String fullPath = att.getPath();
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

    @RequestMapping(value = "/edit/{docsId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("docsId") String docsId
    ) {
        DocsDAO docsDAO = new DocsDAO();
        Docs docs = docsDAO.getDocs(docsId);
        model.addAttribute("docs", docs);
        return "docs/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String application,
            @RequestParam(required = false) String site,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String path,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String uploadBy,
            @RequestParam(required = false) String flag
    ) {
        Docs docs = new Docs();
        docs.setId(id);
        docs.setType(type);
        docs.setApplication(application);
        docs.setSite(site);
        docs.setName(name);
        docs.setPath(path);
        docs.setCreatedDate(createdDate);
        docs.setUploadBy(uploadBy);
        docs.setFlag(flag);
        DocsDAO docsDAO = new DocsDAO();
        QueryResult queryResult = docsDAO.updateDocs(docs);
        args = new String[1];
        args[0] = type + " - " + application;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/docs/edit/" + id;
    }

    @RequestMapping(value = "/delete/{docsId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("docsId") String docsId
    ) {
        DocsDAO docsDAO = new DocsDAO();
        Docs docs = docsDAO.getDocs(docsId);
        docsDAO = new DocsDAO();
        QueryResult queryResult = docsDAO.deleteDocs(docsId);
        args = new String[1];
        args[0] = docs.getType() + " - " + docs.getApplication();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/docs";
    }

    @RequestMapping(value = "/view/{docsId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("docsId") String docsId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/docs/viewDocsPdf/" + docsId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/docs";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.docs");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewDocsPdf/{docsId}", method = RequestMethod.GET)
    public ModelAndView viewDocsPdf(
            Model model,
            @PathVariable("docsId") String docsId
    ) {
        DocsDAO docsDAO = new DocsDAO();
        Docs docs = docsDAO.getDocs(docsId);
        return new ModelAndView("docsPdf", "docs", docs);
    }
}
