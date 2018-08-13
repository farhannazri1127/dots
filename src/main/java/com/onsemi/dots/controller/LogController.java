package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.LogDAO;
import com.onsemi.dots.model.Log;
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
@RequestMapping(value = "/log")
@SessionAttributes({"userSession"})public class LogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String log(
			Model model
	) {
		LogDAO logDAO = new LogDAO();
		List<Log> logList = logDAO.getLogList();
		model.addAttribute("logList", logList);
		return "log/log";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "log/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String createdBy
	) {
		Log log = new Log();
		log.setRequestId(requestId);
		log.setStatus(status);
		log.setStatusDate(statusDate);
		log.setModule(module);
		log.setCreatedBy(createdBy);
		LogDAO logDAO = new LogDAO();
		QueryResult queryResult = logDAO.insertLog(log);
		args = new String[1];
		args[0] = requestId + " - " + status;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("log", log);
			return "log/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/log/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{logId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("logId") String logId
	) {
		LogDAO logDAO = new LogDAO();
		Log log = logDAO.getLog(logId);
		model.addAttribute("log", log);
		return "log/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String createdBy
	) {
		Log log = new Log();
		log.setId(id);
		log.setRequestId(requestId);
		log.setStatus(status);
		log.setStatusDate(statusDate);
		log.setModule(module);
		log.setCreatedBy(createdBy);
		LogDAO logDAO = new LogDAO();
		QueryResult queryResult = logDAO.updateLog(log);
		args = new String[1];
		args[0] = requestId + " - " + status;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/log/edit/" + id;
	}

	@RequestMapping(value = "/delete/{logId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("logId") String logId
	) {
		LogDAO logDAO = new LogDAO();
		Log log = logDAO.getLog(logId);
		logDAO = new LogDAO();
		QueryResult queryResult = logDAO.deleteLog(logId);
		args = new String[1];
		args[0] = log.getRequestId() + " - " + log.getStatus();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/log";
	}

	@RequestMapping(value = "/view/{logId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("logId") String logId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/log/viewLogPdf/" + logId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/log";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.log");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewLogPdf/{logId}", method = RequestMethod.GET)
	public ModelAndView viewLogPdf(
			Model model, 
			@PathVariable("logId") String logId
	) {
		LogDAO logDAO = new LogDAO();
		Log log = logDAO.getLog(logId);
		return new ModelAndView("logPdf", "log", log);
	}
}