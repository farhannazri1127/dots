package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.UslTimelapseDAO;
import com.onsemi.dots.model.UslTimelapse;
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
@RequestMapping(value = "/admin/uslTimelapse")
@SessionAttributes({"userSession"})public class UslTimelapseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UslTimelapseController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String uslTimelapse(
			Model model
	) {
		UslTimelapseDAO uslTimelapseDAO = new UslTimelapseDAO();
		List<UslTimelapse> uslTimelapseList = uslTimelapseDAO.getUslTimelapseList();
		model.addAttribute("uslTimelapseList", uslTimelapseList);
		return "uslTimelapse/uslTimelapse";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "uslTimelapse/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String process,
			@RequestParam(required = false) String hour,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate
	) {
		UslTimelapse uslTimelapse = new UslTimelapse();
		uslTimelapse.setProcess(process);
		uslTimelapse.setHour(hour);
		uslTimelapse.setRemarks(remarks);
		uslTimelapse.setCreatedBy(createdBy);
		uslTimelapse.setCreatedDate(createdDate);
		UslTimelapseDAO uslTimelapseDAO = new UslTimelapseDAO();
		QueryResult queryResult = uslTimelapseDAO.insertUslTimelapse(uslTimelapse);
		args = new String[1];
		args[0] = process + " - " + hour;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("uslTimelapse", uslTimelapse);
			return "uslTimelapse/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/admin/uslTimelapse/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{uslTimelapseId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("uslTimelapseId") String uslTimelapseId
	) {
		UslTimelapseDAO uslTimelapseDAO = new UslTimelapseDAO();
		UslTimelapse uslTimelapse = uslTimelapseDAO.getUslTimelapse(uslTimelapseId);
		model.addAttribute("uslTimelapse", uslTimelapse);
		return "uslTimelapse/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String process,
			@RequestParam(required = false) String hour,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate
	) {
		UslTimelapse uslTimelapse = new UslTimelapse();
		uslTimelapse.setId(id);
		uslTimelapse.setProcess(process);
		uslTimelapse.setHour(hour);
		uslTimelapse.setRemarks(remarks);
		uslTimelapse.setCreatedBy(createdBy);
		uslTimelapse.setCreatedDate(createdDate);
		UslTimelapseDAO uslTimelapseDAO = new UslTimelapseDAO();
		QueryResult queryResult = uslTimelapseDAO.updateUslTimelapse(uslTimelapse);
		args = new String[1];
		args[0] = process + " - " + hour;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/admin/uslTimelapse/edit/" + id;
	}

	@RequestMapping(value = "/delete/{uslTimelapseId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("uslTimelapseId") String uslTimelapseId
	) {
		UslTimelapseDAO uslTimelapseDAO = new UslTimelapseDAO();
		UslTimelapse uslTimelapse = uslTimelapseDAO.getUslTimelapse(uslTimelapseId);
		uslTimelapseDAO = new UslTimelapseDAO();
		QueryResult queryResult = uslTimelapseDAO.deleteUslTimelapse(uslTimelapseId);
		args = new String[1];
		args[0] = uslTimelapse.getProcess() + " - " + uslTimelapse.getHour();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/admin/uslTimelapse";
	}

	@RequestMapping(value = "/view/{uslTimelapseId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("uslTimelapseId") String uslTimelapseId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/uslTimelapse/viewUslTimelapsePdf/" + uslTimelapseId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/uslTimelapse";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.uslTimelapse");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewUslTimelapsePdf/{uslTimelapseId}", method = RequestMethod.GET)
	public ModelAndView viewUslTimelapsePdf(
			Model model, 
			@PathVariable("uslTimelapseId") String uslTimelapseId
	) {
		UslTimelapseDAO uslTimelapseDAO = new UslTimelapseDAO();
		UslTimelapse uslTimelapse = uslTimelapseDAO.getUslTimelapse(uslTimelapseId);
		return new ModelAndView("uslTimelapsePdf", "uslTimelapse", uslTimelapse);
	}
}