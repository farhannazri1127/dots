package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.TestConditionDAO;
import com.onsemi.dots.model.TestCondition;
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
@RequestMapping(value = "/testCondition")
@SessionAttributes({"userSession"})public class TestConditionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestConditionController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String testCondition(
			Model model
	) {
		TestConditionDAO testConditionDAO = new TestConditionDAO();
		List<TestCondition> testConditionList = testConditionDAO.getTestConditionList();
		model.addAttribute("testConditionList", testConditionList);
		return "testCondition/testCondition";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "testCondition/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String condition,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		TestCondition testCondition = new TestCondition();
		testCondition.setName(name);
		testCondition.setCondition(condition);
		testCondition.setRemarks(remarks);
		testCondition.setCreatedBy(createdBy);
		testCondition.setCreatedDate(createdDate);
		testCondition.setModifiedBy(modifiedBy);
		testCondition.setModifiedDate(modifiedDate);
		TestConditionDAO testConditionDAO = new TestConditionDAO();
		QueryResult queryResult = testConditionDAO.insertTestCondition(testCondition);
		args = new String[1];
		args[0] = name + " - " + condition;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("testCondition", testCondition);
			return "testCondition/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/testCondition/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{testConditionId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("testConditionId") String testConditionId
	) {
		TestConditionDAO testConditionDAO = new TestConditionDAO();
		TestCondition testCondition = testConditionDAO.getTestCondition(testConditionId);
		model.addAttribute("testCondition", testCondition);
		return "testCondition/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String condition,
			@RequestParam(required = false) String remarks,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		TestCondition testCondition = new TestCondition();
		testCondition.setId(id);
		testCondition.setName(name);
		testCondition.setCondition(condition);
		testCondition.setRemarks(remarks);
		testCondition.setCreatedBy(createdBy);
		testCondition.setCreatedDate(createdDate);
		testCondition.setModifiedBy(modifiedBy);
		testCondition.setModifiedDate(modifiedDate);
		TestConditionDAO testConditionDAO = new TestConditionDAO();
		QueryResult queryResult = testConditionDAO.updateTestCondition(testCondition);
		args = new String[1];
		args[0] = name + " - " + condition;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/testCondition/edit/" + id;
	}

	@RequestMapping(value = "/delete/{testConditionId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("testConditionId") String testConditionId
	) {
		TestConditionDAO testConditionDAO = new TestConditionDAO();
		TestCondition testCondition = testConditionDAO.getTestCondition(testConditionId);
		testConditionDAO = new TestConditionDAO();
		QueryResult queryResult = testConditionDAO.deleteTestCondition(testConditionId);
		args = new String[1];
		args[0] = testCondition.getName() + " - " + testCondition.getCondition();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/testCondition";
	}

	@RequestMapping(value = "/view/{testConditionId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("testConditionId") String testConditionId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/testCondition/viewTestConditionPdf/" + testConditionId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/testCondition";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.testCondition");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewTestConditionPdf/{testConditionId}", method = RequestMethod.GET)
	public ModelAndView viewTestConditionPdf(
			Model model, 
			@PathVariable("testConditionId") String testConditionId
	) {
		TestConditionDAO testConditionDAO = new TestConditionDAO();
		TestCondition testCondition = testConditionDAO.getTestCondition(testConditionId);
		return new ModelAndView("testConditionPdf", "testCondition", testCondition);
	}
}