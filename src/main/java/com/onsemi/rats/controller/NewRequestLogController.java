package com.onsemi.rats.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.rats.dao.NewRequestLogDAO;
import com.onsemi.rats.model.NewRequestLog;
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
@RequestMapping(value = "/newRequestLog")
@SessionAttributes({"userSession"})public class NewRequestLogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewRequestLogController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String newRequestLog(
			Model model
	) {
		NewRequestLogDAO newRequestLogDAO = new NewRequestLogDAO();
		List<NewRequestLog> newRequestLogList = newRequestLogDAO.getNewRequestLogList();
		model.addAttribute("newRequestLogList", newRequestLogList);
		return "newRequestLog/newRequestLog";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "newRequestLog/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String createdBy
	) {
		NewRequestLog newRequestLog = new NewRequestLog();
		newRequestLog.setRequestId(requestId);
		newRequestLog.setStatus(status);
		newRequestLog.setDescription(description);
		newRequestLog.setStatusDate(statusDate);
		newRequestLog.setModule(module);
		newRequestLog.setCreatedBy(createdBy);
		NewRequestLogDAO newRequestLogDAO = new NewRequestLogDAO();
		QueryResult queryResult = newRequestLogDAO.insertNewRequestLog(newRequestLog);
		args = new String[1];
		args[0] = requestId + " - " + status;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("newRequestLog", newRequestLog);
			return "newRequestLog/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/newRequestLog/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{newRequestLogId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("newRequestLogId") String newRequestLogId
	) {
		NewRequestLogDAO newRequestLogDAO = new NewRequestLogDAO();
		NewRequestLog newRequestLog = newRequestLogDAO.getNewRequestLog(newRequestLogId);
		model.addAttribute("newRequestLog", newRequestLog);
		return "newRequestLog/edit";
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
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String createdBy
	) {
		NewRequestLog newRequestLog = new NewRequestLog();
		newRequestLog.setId(id);
		newRequestLog.setRequestId(requestId);
		newRequestLog.setStatus(status);
		newRequestLog.setDescription(description);
		newRequestLog.setStatusDate(statusDate);
		newRequestLog.setModule(module);
		newRequestLog.setCreatedBy(createdBy);
		NewRequestLogDAO newRequestLogDAO = new NewRequestLogDAO();
		QueryResult queryResult = newRequestLogDAO.updateNewRequestLog(newRequestLog);
		args = new String[1];
		args[0] = requestId + " - " + status;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/newRequestLog/edit/" + id;
	}

	@RequestMapping(value = "/delete/{newRequestLogId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("newRequestLogId") String newRequestLogId
	) {
		NewRequestLogDAO newRequestLogDAO = new NewRequestLogDAO();
		NewRequestLog newRequestLog = newRequestLogDAO.getNewRequestLog(newRequestLogId);
		newRequestLogDAO = new NewRequestLogDAO();
		QueryResult queryResult = newRequestLogDAO.deleteNewRequestLog(newRequestLogId);
		args = new String[1];
		args[0] = newRequestLog.getRequestId() + " - " + newRequestLog.getStatus();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/newRequestLog";
	}

	@RequestMapping(value = "/view/{newRequestLogId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("newRequestLogId") String newRequestLogId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/newRequestLog/viewNewRequestLogPdf/" + newRequestLogId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/newRequestLog";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.newRequestLog");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewNewRequestLogPdf/{newRequestLogId}", method = RequestMethod.GET)
	public ModelAndView viewNewRequestLogPdf(
			Model model, 
			@PathVariable("newRequestLogId") String newRequestLogId
	) {
		NewRequestLogDAO newRequestLogDAO = new NewRequestLogDAO();
		NewRequestLog newRequestLog = newRequestLogDAO.getNewRequestLog(newRequestLogId);
		return new ModelAndView("newRequestLogPdf", "newRequestLog", newRequestLog);
	}
}