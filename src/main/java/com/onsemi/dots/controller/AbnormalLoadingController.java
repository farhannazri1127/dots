package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.AbnormalLoadingDAO;
import com.onsemi.dots.model.AbnormalLoading;
import com.onsemi.dots.model.UserSession;
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
@RequestMapping(value = "/abnormalLoading")
@SessionAttributes({"userSession"})public class AbnormalLoadingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbnormalLoadingController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String abnormalLoading(
			Model model
	) {
		AbnormalLoadingDAO abnormalLoadingDAO = new AbnormalLoadingDAO();
		List<AbnormalLoading> abnormalLoadingList = abnormalLoadingDAO.getAbnormalLoadingListJoinWithLotPenang();
		model.addAttribute("abnormalLoadingList", abnormalLoadingList);
		return "abnormalLoading/abnormalLoading";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "abnormalLoading/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String suggestedLoadingTime,
			@RequestParam(required = false) String actualLoadingTime,
			@RequestParam(required = false) String suggestedUnloadingTime,
			@RequestParam(required = false) String newEstimateUnloadingTime,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String flag
	) {
		AbnormalLoading abnormalLoading = new AbnormalLoading();
		abnormalLoading.setRequestId(requestId);
		abnormalLoading.setSuggestedLoadingTime(suggestedLoadingTime);
		abnormalLoading.setActualLoadingTime(actualLoadingTime);
		abnormalLoading.setSuggestedUnloadingTime(suggestedUnloadingTime);
		abnormalLoading.setNewEstimateUnloadingTime(newEstimateUnloadingTime);
		abnormalLoading.setRemarks(remarks);
		abnormalLoading.setFlag(flag);
		AbnormalLoadingDAO abnormalLoadingDAO = new AbnormalLoadingDAO();
		QueryResult queryResult = abnormalLoadingDAO.insertAbnormalLoading(abnormalLoading);
		args = new String[1];
		args[0] = requestId + " - " + suggestedLoadingTime;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("abnormalLoading", abnormalLoading);
			return "abnormalLoading/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/abnormalLoading/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{abnormalLoadingId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("abnormalLoadingId") String abnormalLoadingId
	) {
		AbnormalLoadingDAO abnormalLoadingDAO = new AbnormalLoadingDAO();
		AbnormalLoading abnormalLoading = abnormalLoadingDAO.getAbnormalLoading(abnormalLoadingId);
		model.addAttribute("abnormalLoading", abnormalLoading);
		return "abnormalLoading/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String suggestedLoadingTime,
			@RequestParam(required = false) String actualLoadingTime,
			@RequestParam(required = false) String suggestedUnloadingTime,
			@RequestParam(required = false) String newEstimateUnloadingTime,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String flag
	) {
		AbnormalLoading abnormalLoading = new AbnormalLoading();
		abnormalLoading.setId(id);
		abnormalLoading.setRequestId(requestId);
		abnormalLoading.setSuggestedLoadingTime(suggestedLoadingTime);
		abnormalLoading.setActualLoadingTime(actualLoadingTime);
		abnormalLoading.setSuggestedUnloadingTime(suggestedUnloadingTime);
		abnormalLoading.setNewEstimateUnloadingTime(newEstimateUnloadingTime);
		abnormalLoading.setRemarks(remarks);
		abnormalLoading.setFlag(flag);
		AbnormalLoadingDAO abnormalLoadingDAO = new AbnormalLoadingDAO();
		QueryResult queryResult = abnormalLoadingDAO.updateAbnormalLoading(abnormalLoading);
		args = new String[1];
		args[0] = requestId + " - " + suggestedLoadingTime;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/abnormalLoading/edit/" + id;
	}

	@RequestMapping(value = "/delete/{abnormalLoadingId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("abnormalLoadingId") String abnormalLoadingId
	) {
		AbnormalLoadingDAO abnormalLoadingDAO = new AbnormalLoadingDAO();
		AbnormalLoading abnormalLoading = abnormalLoadingDAO.getAbnormalLoading(abnormalLoadingId);
		abnormalLoadingDAO = new AbnormalLoadingDAO();
		QueryResult queryResult = abnormalLoadingDAO.deleteAbnormalLoading(abnormalLoadingId);
		args = new String[1];
		args[0] = abnormalLoading.getRequestId() + " - " + abnormalLoading.getSuggestedLoadingTime();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/abnormalLoading";
	}

	@RequestMapping(value = "/view/{abnormalLoadingId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("abnormalLoadingId") String abnormalLoadingId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/abnormalLoading/viewAbnormalLoadingPdf/" + abnormalLoadingId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/abnormalLoading";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.abnormalLoading");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewAbnormalLoadingPdf/{abnormalLoadingId}", method = RequestMethod.GET)
	public ModelAndView viewAbnormalLoadingPdf(
			Model model, 
			@PathVariable("abnormalLoadingId") String abnormalLoadingId
	) {
		AbnormalLoadingDAO abnormalLoadingDAO = new AbnormalLoadingDAO();
		AbnormalLoading abnormalLoading = abnormalLoadingDAO.getAbnormalLoading(abnormalLoadingId);
		return new ModelAndView("abnormalLoadingPdf", "abnormalLoading", abnormalLoading);
	}
}