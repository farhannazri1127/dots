package com.onsemi.dots.controller;

import com.onsemi.dots.dao.DispoEmailListDAO;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.dao.LotPenangDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.PostVmDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.model.DispoEmailList;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.PostVm;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.UserSession;
import com.onsemi.dots.tools.EmailSender;
import com.onsemi.dots.tools.QueryResult;
import javax.servlet.ServletContext;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/lotPenang/postVm")
@SessionAttributes({"userSession"})
public class PostVmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostVmController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String postVm(
            Model model
    ) {
        PostVmDAO postVmDAO = new PostVmDAO();
        List<PostVm> postVmList = postVmDAO.getPostVmList();
        model.addAttribute("postVmList", postVmList);
        return "postVm/postVm";
    }

    @RequestMapping(value = "/add/{lotPenangId}", method = RequestMethod.GET)
    public String add(Model model,
            @PathVariable("lotPenangId") String lotPenangId) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> match = sDAO.getGroupParameterDetailList("", "024");

        LotPenangDAO lotD = new LotPenangDAO();
        LotPenang lot = lotD.getLotPenang(lotPenangId);

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequest(lot.getRequestId());

        model.addAttribute("match", match);
        model.addAttribute("lotPenangId", lotPenangId);
        model.addAttribute("request", req);
        return "postVm/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String lotPenangId,
            @RequestParam(required = false) String rmsMatch,
            @RequestParam(required = false) String packageMatch,
            @RequestParam(required = false) String eventMatch,
            @RequestParam(required = false) String intervalMatch,
            @RequestParam(required = false) String pcbEventMatch,
            @RequestParam(required = false) String chamberMatch,
            @RequestParam(required = false) String quantityMatch,
            @RequestParam(required = false) String vmMix,
            @RequestParam(required = false) String vmMixRemarks,
            @RequestParam(required = false) String vmDemount,
            @RequestParam(required = false) String vmDemountRemarks,
            @RequestParam(required = false) String vmBroken,
            @RequestParam(required = false) String vmBrokenRemarks,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate
    ) {
        boolean totalfail = true;
        if ("Fail".equals(rmsMatch)) {
            totalfail = false;
        }

        if ("Fail".equals(packageMatch)) {
            totalfail = false;
        }
        if ("Fail".equals(eventMatch)) {
            totalfail = false;
        }
        if ("Fail".equals(intervalMatch)) {
            totalfail = false;
        }
        if ("Fail".equals(pcbEventMatch)) {
            totalfail = false;
        }
        if ("Fail".equals(chamberMatch)) {
            totalfail = false;
        }
        if ("Fail".equals(quantityMatch)) {
            totalfail = false;
        }
        if ("Fail".equals(vmMix)) {
            totalfail = false;
        }
        if ("Fail".equals(vmDemount)) {
            totalfail = false;
        }
        if ("Fail".equals(vmBroken)) {
            totalfail = false;
        }
        PostVm postVm = new PostVm();
        postVm.setRequestId(requestId);
        postVm.setLotPenangId(lotPenangId);
        postVm.setRmsMatch(rmsMatch);
        postVm.setPackageMatch(packageMatch);
        postVm.setEventMatch(eventMatch);
        postVm.setIntervalMatch(intervalMatch);
        postVm.setChamberLocation(chamberMatch);
        postVm.setQuantityMatch(quantityMatch);
        postVm.setPcbEventTestCode(pcbEventMatch);
        postVm.setVmMix(vmMix);
        postVm.setVmMixRemarks(vmMixRemarks);
        postVm.setVmDemount(vmDemount);
        postVm.setVmDemountRemarks(vmDemountRemarks);
        postVm.setVmBroken(vmBroken);
        postVm.setVmBrokenRemarks(vmBrokenRemarks);
        postVm.setRemarks(remarks);
        postVm.setCreatedBy(userSession.getFullname());
        PostVmDAO postVmDAO = new PostVmDAO();
        QueryResult queryResult = postVmDAO.insertPostVm(postVm);
        args = new String[1];
        args[0] = requestId + " - " + rmsMatch;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", "Failed to update Pre VM before Shipment");
            model.addAttribute("postVm", postVm);
            return "postVm/add";
        } else {
            //update preVmId into request table
            Request req = new Request();
            req.setId(requestId);
            req.setStatus("Pending Close Lot");
            req.setPostVmId(queryResult.getGeneratedKey());
            req.setFlag("0");
            RequestDAO reqD = new RequestDAO();
            QueryResult queryReqEdit = reqD.updateRequestPostVmId(req);

            //update preVmId into penang table
            LotPenang lot = new LotPenang();
            lot.setPostVmId(queryResult.getGeneratedKey());
            lot.setStatus("Pending Close Lot");
            lot.setId(lotPenangId);
            LotPenangDAO lotD = new LotPenangDAO();
            QueryResult queryLotEdit = lotD.updateLotPenangWithPostVmId(lot);

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Penang");
            log.setStatus("Added Post VM Information");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //send email if any vm fail
            if (totalfail == false) {

                reqD = new RequestDAO();
                Request req1 = reqD.getRequest(requestId);
                String rmsEventInterval = req1.getRmsEvent() + " - " + req1.getInterval();

                String vmFail = "Quantity per Lot Match? : " + quantityMatch + "<br> "
                        + "RMS/Lot Match? : " + rmsMatch + "<br> "
                        + "Package Match? : " + packageMatch + "<br> "
                        + "Event Match? : " + eventMatch + "<br> "
                        + "Interval Match? : " + intervalMatch + "<br> "
                        + "Event Code on PCB Match? : " + pcbEventMatch + "<br> "
                        + "Chamber in Penang Match? : " + chamberMatch + "<br> "
                        + "VM Mix : " + vmMix + "<br> "
                        + "VM Mix Remarks : " + vmMixRemarks + "<br> "
                        + "VM Demount : " + vmDemount + "<br> "
                        + "VM Demount Remarks : " + vmDemountRemarks + "<br> "
                        + "VM Broken : " + vmBroken + "<br> "
                        + "VM Broken Remarks : " + vmBrokenRemarks + "<br><br> "
                        + "Remarks : " + remarks + "<br> ";

                DispoEmailListDAO disD = new DispoEmailListDAO();
//                List<DispoEmailList> dis = disD.getDispoEmailListManagementOnly();
                List<DispoEmailList> dis = disD.getDispoEmailListApproverOnly();
                String[] to2 = new String[dis.size()];
                for (int i = 0; i < dis.size(); i++) {
                    to2[i] = dis.get(i).getEmail();
                }

                EmailSender emailSenderToHIMSSF = new EmailSender();
                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                user.setFullname("All");
                String[] to = {"fg79cj@onsemi.com"};
//                        String[] to = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
                emailSenderToHIMSSF.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to2,
                        //                    subject
                        "Fail Post VM after Shipment [" + rmsEventInterval + "]",
                        //                    msg
                        vmFail
                        //                        + "Please click this <a href=\"http://localhost:8080/DOTS/lotPenang/postVm/edit/" + queryResult.getGeneratedKey() + " \">LINK</a> for further details."
                        + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/lotPenang/postVm/edit/" + queryResult.getGeneratedKey() + " \">LINK</a> for further details."
                        + " Thank you."
                );

            }

            redirectAttrs.addFlashAttribute("success", "Post VM after Shipment Successfully Updated");
            return "redirect:/lotPenang/postVm/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{postVmId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("postVmId") String postVmId
    ) {
        PostVmDAO postVmDAO = new PostVmDAO();
        PostVm postVm = postVmDAO.getPostVm(postVmId);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> rmsMatch = sDAO.getGroupParameterDetailList(postVm.getRmsMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> packageMatch = sDAO.getGroupParameterDetailList(postVm.getPackageMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> eventMatch = sDAO.getGroupParameterDetailList(postVm.getEventMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> intervalMatch = sDAO.getGroupParameterDetailList(postVm.getIntervalMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pcbEventMatch = sDAO.getGroupParameterDetailList(postVm.getPcbEventTestCode(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> chamberMatch = sDAO.getGroupParameterDetailList(postVm.getChamberLocation(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> vmMix = sDAO.getGroupParameterDetailList(postVm.getVmMix(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> vmDemount = sDAO.getGroupParameterDetailList(postVm.getVmDemount(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> vmBroken = sDAO.getGroupParameterDetailList(postVm.getVmBroken(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> quantityMatch = sDAO.getGroupParameterDetailList(postVm.getQuantityMatch(), "024");

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequest(postVm.getRequestId());

        model.addAttribute("request", req);
        model.addAttribute("rmsMatch", rmsMatch);
        model.addAttribute("packageMatch", packageMatch);
        model.addAttribute("eventMatch", eventMatch);
        model.addAttribute("intervalMatch", intervalMatch);
        model.addAttribute("pcbEventMatch", pcbEventMatch);
        model.addAttribute("chamberMatch", chamberMatch);
        model.addAttribute("vmMix", vmMix);
        model.addAttribute("vmDemount", vmDemount);
        model.addAttribute("vmBroken", vmBroken);
        model.addAttribute("quantityMatch", quantityMatch);
        model.addAttribute("postVm", postVm);
        return "postVm/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String lotPenangId,
            @RequestParam(required = false) String rmsMatch,
            @RequestParam(required = false) String packageMatch,
            @RequestParam(required = false) String eventMatch,
            @RequestParam(required = false) String intervalMatch,
            @RequestParam(required = false) String pcbEventMatch,
            @RequestParam(required = false) String chamberMatch,
            @RequestParam(required = false) String quantityMatch,
            @RequestParam(required = false) String vmMix,
            @RequestParam(required = false) String vmMixRemarks,
            @RequestParam(required = false) String vmDemount,
            @RequestParam(required = false) String vmDemountRemarks,
            @RequestParam(required = false) String vmBroken,
            @RequestParam(required = false) String vmBrokenRemarks,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate
    ) {
        boolean totalfail = true;
        PostVmDAO preD = new PostVmDAO();
        PostVm pre = preD.getPostVm(id);
        if (!rmsMatch.equals(pre.getRmsMatch()) && "Fail".equals(rmsMatch)) {
            totalfail = false;
        }
        if (!packageMatch.equals(pre.getPackageMatch()) && "Fail".equals(packageMatch)) {
            totalfail = false;
        }
        if (!eventMatch.equals(pre.getEventMatch()) && "Fail".equals(eventMatch)) {
            totalfail = false;
        }
        if (!quantityMatch.equals(pre.getQuantityMatch()) && "Fail".equals(quantityMatch)) {
            totalfail = false;
        }
        if (!intervalMatch.equals(pre.getIntervalMatch()) && "Fail".equals(intervalMatch)) {
            totalfail = false;
        }
        if (!pcbEventMatch.equals(pre.getPcbEventTestCode()) && "Fail".equals(pcbEventMatch)) {
            totalfail = false;
        }
        if (!chamberMatch.equals(pre.getChamberLocation()) && "Fail".equals(chamberMatch)) {
            totalfail = false;
        }
        if (!vmMix.equals(pre.getVmMix()) && "Fail".equals(vmMix)) {
            totalfail = false;
        }
        if (!vmDemount.equals(pre.getVmDemount()) && "Fail".equals(vmDemount)) {
            totalfail = false;
        }
        if (!vmBroken.equals(pre.getVmBroken()) && "Fail".equals(vmBroken)) {
            totalfail = false;
        }
        PostVm postVm = new PostVm();
        postVm.setId(id);
        postVm.setRequestId(requestId);
        postVm.setLotPenangId(lotPenangId);
        postVm.setRmsMatch(rmsMatch);
        postVm.setPackageMatch(packageMatch);
        postVm.setEventMatch(eventMatch);
        postVm.setIntervalMatch(intervalMatch);
        postVm.setChamberLocation(chamberMatch);
        postVm.setQuantityMatch(quantityMatch);
        postVm.setPcbEventTestCode(pcbEventMatch);
        postVm.setVmMix(vmMix);
        postVm.setVmMixRemarks(vmMixRemarks);
        postVm.setVmDemount(vmDemount);
        postVm.setVmDemountRemarks(vmDemountRemarks);
        postVm.setVmBroken(vmBroken);
        postVm.setVmBrokenRemarks(vmBrokenRemarks);
        postVm.setRemarks(remarks);
        postVm.setCreatedBy(createdBy);
        postVm.setCreatedDate(createdDate);
        PostVmDAO postVmDAO = new PostVmDAO();
        QueryResult queryResult = postVmDAO.updatePostVm(postVm);
        args = new String[1];
        args[0] = requestId + " - " + rmsMatch;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", "Post VM after Shipment Successfully Updated");

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Penang");
            log.setStatus("Added Post VM Information");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //send email if any vm fail
            if (totalfail == false) {

                RequestDAO reqD = new RequestDAO();
                Request req1 = reqD.getRequest(requestId);
                String rmsEventInterval = req1.getRmsEvent() + " - " + req1.getInterval();

                String vmFail = "Quantity per Lot Match? : " + quantityMatch + "<br> "
                        + "RMS/Lot Match? : " + rmsMatch + "<br> "
                        + "Package Match? : " + packageMatch + "<br> "
                        + "Event Match? : " + eventMatch + "<br> "
                        + "Interval Match? : " + intervalMatch + "<br> "
                        + "Event Code on PCB Match? : " + pcbEventMatch + "<br> "
                        + "Chamber in Penang Match? : " + chamberMatch + "<br> "
                        + "VM Mix : " + vmMix + "<br> "
                        + "VM Mix Remarks : " + vmMixRemarks + "<br> "
                        + "VM Demount : " + vmDemount + "<br> "
                        + "VM Demount Remarks : " + vmDemountRemarks + "<br> "
                        + "VM Broken : " + vmBroken + "<br> "
                        + "VM Broken Remarks : " + vmBrokenRemarks + "<br><br> "
                        + "Remarks : " + remarks + "<br> ";

                DispoEmailListDAO disD = new DispoEmailListDAO();
//                List<DispoEmailList> dis = disD.getDispoEmailListManagementOnly();
                List<DispoEmailList> dis = disD.getDispoEmailListApproverOnly();
                String[] to2 = new String[dis.size()];
                for (int i = 0; i < dis.size(); i++) {
                    to2[i] = dis.get(i).getEmail();
                }

                EmailSender emailSenderToHIMSSF = new EmailSender();
                com.onsemi.dots.model.User user = new com.onsemi.dots.model.User();
                user.setFullname("All");
                String[] to = {"fg79cj@onsemi.com"};
//                        String[] to = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
                emailSenderToHIMSSF.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to2,
                        //                    subject
                        "Fail Post VM after Shipment [" + rmsEventInterval + "]",
                        //                    msg
                        vmFail
                        //                        + "Please click this <a href=\"http://localhost:8080/DOTS/lotPenang/postVm/edit/" + id + " \">LINK</a> for further details."
                        + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/lotPenang/postVm/edit/" + id + " \">LINK</a> for further details."
                        + " Thank you."
                );

            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to update Pre VM before Shipment");
        }
        return "redirect:/lotPenang/postVm/edit/" + id;
    }

    @RequestMapping(value = "/delete/{postVmId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("postVmId") String postVmId
    ) {
        PostVmDAO postVmDAO = new PostVmDAO();
        PostVm postVm = postVmDAO.getPostVm(postVmId);
        postVmDAO = new PostVmDAO();
        QueryResult queryResult = postVmDAO.deletePostVm(postVmId);
        args = new String[1];
        args[0] = postVm.getRequestId() + " - " + postVm.getRmsMatch();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/lotPenang/postVm";
    }

    @RequestMapping(value = "/view/{postVmId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("postVmId") String postVmId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/lotPenang/postVm/viewPostVmPdf/" + postVmId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/postVm";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.postVm");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewPostVmPdf/{postVmId}", method = RequestMethod.GET)
    public ModelAndView viewPostVmPdf(
            Model model,
            @PathVariable("postVmId") String postVmId
    ) {
        PostVmDAO postVmDAO = new PostVmDAO();
        PostVm postVm = postVmDAO.getPostVm(postVmId);
        return new ModelAndView("postVmPdf", "postVm", postVm);
    }
}
