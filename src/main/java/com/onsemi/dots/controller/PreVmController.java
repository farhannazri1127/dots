package com.onsemi.dots.controller;

import com.onsemi.dots.dao.DispoEmailListDAO;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.PreVmDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.model.DispoEmailList;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.PreVm;
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
@RequestMapping(value = "/do/preVm")
@SessionAttributes({"userSession"})
public class PreVmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreVmController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String preVm(
            Model model
    ) {
        PreVmDAO preVmDAO = new PreVmDAO();
        List<PreVm> preVmList = preVmDAO.getPreVmList();
        model.addAttribute("preVmList", preVmList);
        return "preVm/preVm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "preVm/add";
    }

    @RequestMapping(value = "/add/{requestId}", method = RequestMethod.GET)
    public String add2(Model model,
            @PathVariable("requestId") String requestId) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> match = sDAO.getGroupParameterDetailList("", "024");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> yesNo = sDAO.getGroupParameterDetailList("", "025");

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequest(requestId);

        model.addAttribute("match", match);
        model.addAttribute("yesNo", yesNo);
        model.addAttribute("request", req);
        return "preVm/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestId,
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
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String cdpaSample
    ) {
        boolean totalfail = true;

        PreVm preVm = new PreVm();
        preVm.setRequestId(requestId);
        preVm.setRmsMatch(rmsMatch);
        if ("Fail".equals(rmsMatch)) {
            totalfail = false;
        }
        preVm.setPackageMatch(packageMatch);
        if ("Fail".equals(packageMatch)) {
            totalfail = false;
        }
        preVm.setEventMatch(eventMatch);
        if ("Fail".equals(eventMatch)) {
            totalfail = false;
        }
        preVm.setIntervalMatch(intervalMatch);
        if ("Fail".equals(intervalMatch)) {
            totalfail = false;
        }
        preVm.setPcbEventTestCode(pcbEventMatch);
        if ("Fail".equals(pcbEventMatch)) {
            totalfail = false;
        }
        preVm.setChamberLocation(chamberMatch);
        if ("Fail".equals(chamberMatch)) {
            totalfail = false;
        }
        preVm.setQuantityMatch(quantityMatch);
        if ("Fail".equals(quantityMatch)) {
            totalfail = false;
        }
        preVm.setVmMix(vmMix);
        preVm.setVmMixRemarks(vmMixRemarks);
        if ("Fail".equals(vmMix)) {
            totalfail = false;
        }

        preVm.setVmDemount(vmDemount);
        preVm.setVmDemountRemarks(vmDemountRemarks);
        if ("Fail".equals(vmDemount)) {
            totalfail = false;
        }

        preVm.setVmBroken(vmBroken);
        preVm.setVmBrokenRemarks(vmBrokenRemarks);
        if ("Fail".equals(vmBroken)) {
            totalfail = false;
        }
        preVm.setRemarks(remarks);
        preVm.setCreatedBy(userSession.getFullname());
        preVm.setCdpaSample(cdpaSample);
        PreVmDAO preVmDAO = new PreVmDAO();
        QueryResult queryResult = preVmDAO.insertPreVm(preVm);
        args = new String[1];
        args[0] = requestId + " - " + rmsMatch;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", "Failed to update Pre VM before Shipment");
            model.addAttribute("preVm", preVm);
            return "preVm/add";
        } else {
            //update preVmId into request table
            Request req = new Request();
            req.setId(requestId);
            req.setStatus("Pending Shipment");
            req.setPreVmId(queryResult.getGeneratedKey());
            req.setFlag("0");
            RequestDAO reqD = new RequestDAO();
            QueryResult queryReqEdit = reqD.updateRequestPreVmId(req);

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Request");
            log.setStatus("Added Pre VM Information");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            LOGGER.info("totalfail.... : " + totalfail);

            //send email if any vm fail
            if (totalfail == false) {

                reqD = new RequestDAO();
                Request req1 = reqD.getRequest(requestId);
                String rmsEventInterval = req1.getRmsEvent() + " - " + req1.getInterval();

                String vmFail = "Quantity per Lot Match? : " + quantityMatch + "<br> "
                        + "Any CDPA/DPA/SAT/OTHERS samples? : " + cdpaSample + "<br> "
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
                        "Fail Pre VM before Shipment [" + rmsEventInterval + "]",
                        //                    msg
                        vmFail
                        //                        + "Please click this <a href=\"http://localhost:8080/DOTS/do/preVm/edit/" + queryResult.getGeneratedKey() + " \">LINK</a> for further details."
                        + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/do/preVm/edit/" + queryResult.getGeneratedKey() + " \">LINK</a> for further details."
                        + " Thank you."
                );

            }
            redirectAttrs.addFlashAttribute("success", "Pre VM before Shipment Successfully Updated");
            return "redirect:/do/preVm/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{preVmId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("preVmId") String preVmId
    ) {
        PreVmDAO preVmDAO = new PreVmDAO();
        PreVm preVm = preVmDAO.getPreVm(preVmId);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> rmsMatch = sDAO.getGroupParameterDetailList(preVm.getRmsMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> packageMatch = sDAO.getGroupParameterDetailList(preVm.getPackageMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> eventMatch = sDAO.getGroupParameterDetailList(preVm.getEventMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> intervalMatch = sDAO.getGroupParameterDetailList(preVm.getIntervalMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pcbEventMatch = sDAO.getGroupParameterDetailList(preVm.getPcbEventTestCode(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> chamberMatch = sDAO.getGroupParameterDetailList(preVm.getChamberLocation(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> vmMix = sDAO.getGroupParameterDetailList(preVm.getVmMix(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> vmDemount = sDAO.getGroupParameterDetailList(preVm.getVmDemount(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> vmBroken = sDAO.getGroupParameterDetailList(preVm.getVmBroken(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> quantityMatch = sDAO.getGroupParameterDetailList(preVm.getQuantityMatch(), "024");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> cdpaSample = sDAO.getGroupParameterDetailList(preVm.getCdpaSample(), "025");

        RequestDAO reqD = new RequestDAO();
        Request req = reqD.getRequest(preVm.getRequestId());

        model.addAttribute("request", req);
        model.addAttribute("preVm", preVm);
        model.addAttribute("cdpaSample", cdpaSample);
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
        return "preVm/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
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
//            @RequestParam(required = false) String createdBy,
//            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String cdpaSample
    ) {

        //check if fail/pass before edit
        boolean totalfail = true;
        PreVmDAO preD = new PreVmDAO();
        PreVm pre = preD.getPreVm(id);
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

        PreVm preVm = new PreVm();
        preVm.setId(id);
        preVm.setRequestId(requestId);
        preVm.setRmsMatch(rmsMatch);
        preVm.setPackageMatch(packageMatch);
        preVm.setEventMatch(eventMatch);
        preVm.setIntervalMatch(intervalMatch);
        preVm.setPcbEventTestCode(pcbEventMatch);
        preVm.setChamberLocation(chamberMatch);
        preVm.setQuantityMatch(quantityMatch);
        preVm.setVmMix(vmMix);
        preVm.setVmMixRemarks(vmMixRemarks);
        preVm.setVmDemount(vmDemount);
        preVm.setVmDemountRemarks(vmDemountRemarks);
        preVm.setVmBroken(vmBroken);
        preVm.setVmBrokenRemarks(vmBrokenRemarks);
        preVm.setRemarks(remarks);
//        preVm.setCreatedBy(createdBy);
//        preVm.setCreatedDate(createdDate);
        preVm.setCdpaSample(cdpaSample);
        PreVmDAO preVmDAO = new PreVmDAO();
        QueryResult queryResult = preVmDAO.updatePreVm(preVm);

        args = new String[1];
        args[0] = requestId + " - " + rmsMatch;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", "Pre VM before Shipment Successfully Updated");

            Log log = new Log();
            log.setRequestId(requestId);
            log.setModule("Lot Request");
            log.setStatus("Added Pre VM Information");
            log.setCreatedBy(userSession.getFullname());
            LogDAO logD = new LogDAO();
            QueryResult addLog = logD.insertLog(log);

            //send email if any vm fail
            if (totalfail == false) {

                RequestDAO reqD = new RequestDAO();
                Request req1 = reqD.getRequest(requestId);
                String rmsEventInterval = req1.getRmsEvent() + " - " + req1.getInterval();

                String vmFail = "Quantity per Lot Match? : " + quantityMatch + "<br> "
                        + "Any CDPA/DPA/SAT/OTHERS samples? : " + cdpaSample + "<br> "
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
//                String[] to = {"fg79cj@onsemi.com"};
                String[] to = {"farhannazri27@yahoo.com"};
//                        String[] to = {"potspenangtest@gmail.com", "fg79cj@onsemi.com"};
                emailSenderToHIMSSF.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to2,
                        //                    subject
                        "Fail Pre VM before Shipment [" + rmsEventInterval + "]",
                        //                    msg
                        vmFail
                        //                        + "Please click this <a href=\"http://localhost:8080/DOTS/do/preVm/edit/" + id + " \">LINK</a> for further details."
                        + "Please click this <a href=\"http://mysed-rel-app03:8080/DOTS/do/preVm/edit/" + id + " \">LINK</a> for further details."
                        + " Thank you."
                );

            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to update Pre VM before Shipment");
        }
        return "redirect:/do/preVm/edit/" + id;
    }

    @RequestMapping(value = "/delete/{preVmId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("preVmId") String preVmId
    ) {
        PreVmDAO preVmDAO = new PreVmDAO();
        PreVm preVm = preVmDAO.getPreVm(preVmId);
        preVmDAO = new PreVmDAO();
        QueryResult queryResult = preVmDAO.deletePreVm(preVmId);
        args = new String[1];
        args[0] = preVm.getRequestId() + " - " + preVm.getRmsMatch();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/do/preVm";
    }

    @RequestMapping(value = "/view/{preVmId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("preVmId") String preVmId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/do/preVm/viewPreVmPdf/" + preVmId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/preVm";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.preVm");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewPreVmPdf/{preVmId}", method = RequestMethod.GET)
    public ModelAndView viewPreVmPdf(
            Model model,
            @PathVariable("preVmId") String preVmId
    ) {
        PreVmDAO preVmDAO = new PreVmDAO();
        PreVm preVm = preVmDAO.getPreVm(preVmId);
        return new ModelAndView("preVmPdf", "preVm", preVm);
    }
}
