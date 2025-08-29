package com.onsemi.rats.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.rats.dao.IncidentLogDAO;
import com.onsemi.rats.model.IncidentLog;
import com.onsemi.rats.model.UserSession;
import com.onsemi.rats.tools.QueryResult;
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
@RequestMapping(value = "/incidentLog")
@SessionAttributes({"userSession"})public class IncidentLogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncidentLogController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String incidentLog(
			Model model
	) {
		IncidentLogDAO incidentLogDAO = new IncidentLogDAO();
		List<IncidentLog> incidentLogList = incidentLogDAO.getIncidentLogList();
		model.addAttribute("incidentLogList", incidentLogList);
		return "incidentLog/incidentLog";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "incidentLog/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String incidentId,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String createdBy
	) {
		IncidentLog incidentLog = new IncidentLog();
		incidentLog.setIncidentId(incidentId);
		incidentLog.setStatus(status);
		incidentLog.setDescription(description);
		incidentLog.setStatusDate(statusDate);
		incidentLog.setModule(module);
		incidentLog.setCreatedBy(createdBy);
		IncidentLogDAO incidentLogDAO = new IncidentLogDAO();
		QueryResult queryResult = incidentLogDAO.insertIncidentLog(incidentLog);
		args = new String[1];
		args[0] = incidentId + " - " + status;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("incidentLog", incidentLog);
			return "incidentLog/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/incidentLog/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{incidentLogId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("incidentLogId") String incidentLogId
	) {
		IncidentLogDAO incidentLogDAO = new IncidentLogDAO();
		IncidentLog incidentLog = incidentLogDAO.getIncidentLog(incidentLogId);
		model.addAttribute("incidentLog", incidentLog);
		return "incidentLog/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String incidentId,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String createdBy
	) {
		IncidentLog incidentLog = new IncidentLog();
		incidentLog.setId(id);
		incidentLog.setIncidentId(incidentId);
		incidentLog.setStatus(status);
		incidentLog.setDescription(description);
		incidentLog.setStatusDate(statusDate);
		incidentLog.setModule(module);
		incidentLog.setCreatedBy(createdBy);
		IncidentLogDAO incidentLogDAO = new IncidentLogDAO();
		QueryResult queryResult = incidentLogDAO.updateIncidentLog(incidentLog);
		args = new String[1];
		args[0] = incidentId + " - " + status;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/incidentLog/edit/" + id;
	}

	@RequestMapping(value = "/delete/{incidentLogId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("incidentLogId") String incidentLogId
	) {
		IncidentLogDAO incidentLogDAO = new IncidentLogDAO();
		IncidentLog incidentLog = incidentLogDAO.getIncidentLog(incidentLogId);
		incidentLogDAO = new IncidentLogDAO();
		QueryResult queryResult = incidentLogDAO.deleteIncidentLog(incidentLogId);
		args = new String[1];
		args[0] = incidentLog.getIncidentId() + " - " + incidentLog.getStatus();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/incidentLog";
	}

	@RequestMapping(value = "/view/{incidentLogId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("incidentLogId") String incidentLogId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/incidentLog/viewIncidentLogPdf/" + incidentLogId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/incidentLog";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.incidentLog");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewIncidentLogPdf/{incidentLogId}", method = RequestMethod.GET)
	public ModelAndView viewIncidentLogPdf(
			Model model, 
			@PathVariable("incidentLogId") String incidentLogId
	) {
		IncidentLogDAO incidentLogDAO = new IncidentLogDAO();
		IncidentLog incidentLog = incidentLogDAO.getIncidentLog(incidentLogId);
		return new ModelAndView("incidentLogPdf", "incidentLog", incidentLog);
	}
}