package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.ChamberDAO;
import com.onsemi.dots.model.Chamber;
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
@RequestMapping(value = "/chamber")
@SessionAttributes({"userSession"})public class ChamberController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChamberController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String chamber(
			Model model
	) {
		ChamberDAO chamberDAO = new ChamberDAO();
		List<Chamber> chamberList = chamberDAO.getChamberList();
		model.addAttribute("chamberList", chamberList);
		return "chamber/chamber";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "chamber/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String event,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		Chamber chamber = new Chamber();
		chamber.setEvent(event);
		chamber.setName(name);
		chamber.setRemarks(remarks);
		chamber.setCreatedBy(createdBy);
		chamber.setCreatedDate(createdDate);
		chamber.setModifiedBy(modifiedBy);
		chamber.setModifiedDate(modifiedDate);
		ChamberDAO chamberDAO = new ChamberDAO();
		QueryResult queryResult = chamberDAO.insertChamber(chamber);
		args = new String[1];
		args[0] = event + " - " + name;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("chamber", chamber);
			return "chamber/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/chamber/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{chamberId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("chamberId") String chamberId
	) {
		ChamberDAO chamberDAO = new ChamberDAO();
		Chamber chamber = chamberDAO.getChamber(chamberId);
		model.addAttribute("chamber", chamber);
		return "chamber/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String event,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		Chamber chamber = new Chamber();
		chamber.setId(id);
		chamber.setEvent(event);
		chamber.setName(name);
		chamber.setRemarks(remarks);
		chamber.setCreatedBy(createdBy);
		chamber.setCreatedDate(createdDate);
		chamber.setModifiedBy(modifiedBy);
		chamber.setModifiedDate(modifiedDate);
		ChamberDAO chamberDAO = new ChamberDAO();
		QueryResult queryResult = chamberDAO.updateChamber(chamber);
		args = new String[1];
		args[0] = event + " - " + name;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/chamber/edit/" + id;
	}

	@RequestMapping(value = "/delete/{chamberId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("chamberId") String chamberId
	) {
		ChamberDAO chamberDAO = new ChamberDAO();
		Chamber chamber = chamberDAO.getChamber(chamberId);
		chamberDAO = new ChamberDAO();
		QueryResult queryResult = chamberDAO.deleteChamber(chamberId);
		args = new String[1];
		args[0] = chamber.getEvent() + " - " + chamber.getName();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/chamber";
	}

	@RequestMapping(value = "/view/{chamberId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("chamberId") String chamberId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/chamber/viewChamberPdf/" + chamberId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/chamber";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.chamber");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewChamberPdf/{chamberId}", method = RequestMethod.GET)
	public ModelAndView viewChamberPdf(
			Model model, 
			@PathVariable("chamberId") String chamberId
	) {
		ChamberDAO chamberDAO = new ChamberDAO();
		Chamber chamber = chamberDAO.getChamber(chamberId);
		return new ModelAndView("chamberPdf", "chamber", chamber);
	}
}