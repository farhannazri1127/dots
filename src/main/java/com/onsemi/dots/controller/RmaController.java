package com.onsemi.dots.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.RmaDAO;
import com.onsemi.dots.model.Rma;
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
@RequestMapping(value = "/rma")
@SessionAttributes({"userSession"})public class RmaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RmaController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String rma(
			Model model
	) {
		RmaDAO rmaDAO = new RmaDAO();
		List<Rma> rmaList = rmaDAO.getRmaList();
		model.addAttribute("rmaList", rmaList);
		return "rma/rma";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "rma/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String rmaRemarks1,
			@RequestParam(required = false) String rmaDate1,
			@RequestParam(required = false) String rmaDispo1,
			@RequestParam(required = false) String rmaDispo1Remarks,
			@RequestParam(required = false) String rmaDispo1Date,
			@RequestParam(required = false) String rmaDispo1By,
			@RequestParam(required = false) String rmaRemarks2,
			@RequestParam(required = false) String rmaDate2,
			@RequestParam(required = false) String rmaDispo2,
			@RequestParam(required = false) String rmaDispo2Remarks,
			@RequestParam(required = false) String rmaDispo2Date,
			@RequestParam(required = false) String rmaDispo2By,
			@RequestParam(required = false) String flag
	) {
		Rma rma = new Rma();
		rma.setRequestId(requestId);
		rma.setRmaRemarks1(rmaRemarks1);
		rma.setRmaDate1(rmaDate1);
		rma.setRmaDispo1(rmaDispo1);
		rma.setRmaDispo1Remarks(rmaDispo1Remarks);
		rma.setRmaDispo1Date(rmaDispo1Date);
		rma.setRmaDispo1By(rmaDispo1By);
		rma.setRmaRemarks2(rmaRemarks2);
		rma.setRmaDate2(rmaDate2);
		rma.setRmaDispo2(rmaDispo2);
		rma.setRmaDispo2Remarks(rmaDispo2Remarks);
		rma.setRmaDispo2Date(rmaDispo2Date);
		rma.setRmaDispo2By(rmaDispo2By);
		rma.setFlag(flag);
		RmaDAO rmaDAO = new RmaDAO();
		QueryResult queryResult = rmaDAO.insertRma(rma);
		args = new String[1];
		args[0] = requestId + " - " + rmaRemarks1;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("rma", rma);
			return "rma/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/rma/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{rmaId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("rmaId") String rmaId
	) {
		RmaDAO rmaDAO = new RmaDAO();
		Rma rma = rmaDAO.getRma(rmaId);
		model.addAttribute("rma", rma);
		return "rma/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String rmaRemarks1,
			@RequestParam(required = false) String rmaDate1,
			@RequestParam(required = false) String rmaDispo1,
			@RequestParam(required = false) String rmaDispo1Remarks,
			@RequestParam(required = false) String rmaDispo1Date,
			@RequestParam(required = false) String rmaDispo1By,
			@RequestParam(required = false) String rmaRemarks2,
			@RequestParam(required = false) String rmaDate2,
			@RequestParam(required = false) String rmaDispo2,
			@RequestParam(required = false) String rmaDispo2Remarks,
			@RequestParam(required = false) String rmaDispo2Date,
			@RequestParam(required = false) String rmaDispo2By,
			@RequestParam(required = false) String flag
	) {
		Rma rma = new Rma();
		rma.setId(id);
		rma.setRequestId(requestId);
		rma.setRmaRemarks1(rmaRemarks1);
		rma.setRmaDate1(rmaDate1);
		rma.setRmaDispo1(rmaDispo1);
		rma.setRmaDispo1Remarks(rmaDispo1Remarks);
		rma.setRmaDispo1Date(rmaDispo1Date);
		rma.setRmaDispo1By(rmaDispo1By);
		rma.setRmaRemarks2(rmaRemarks2);
		rma.setRmaDate2(rmaDate2);
		rma.setRmaDispo2(rmaDispo2);
		rma.setRmaDispo2Remarks(rmaDispo2Remarks);
		rma.setRmaDispo2Date(rmaDispo2Date);
		rma.setRmaDispo2By(rmaDispo2By);
		rma.setFlag(flag);
		RmaDAO rmaDAO = new RmaDAO();
		QueryResult queryResult = rmaDAO.updateRma(rma);
		args = new String[1];
		args[0] = requestId + " - " + rmaRemarks1;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/rma/edit/" + id;
	}

	@RequestMapping(value = "/delete/{rmaId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("rmaId") String rmaId
	) {
		RmaDAO rmaDAO = new RmaDAO();
		Rma rma = rmaDAO.getRma(rmaId);
		rmaDAO = new RmaDAO();
		QueryResult queryResult = rmaDAO.deleteRma(rmaId);
		args = new String[1];
		args[0] = rma.getRequestId() + " - " + rma.getRmaRemarks1();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/rma";
	}

	@RequestMapping(value = "/view/{rmaId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("rmaId") String rmaId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/rma/viewRmaPdf/" + rmaId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/rma";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.rma");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewRmaPdf/{rmaId}", method = RequestMethod.GET)
	public ModelAndView viewRmaPdf(
			Model model, 
			@PathVariable("rmaId") String rmaId
	) {
		RmaDAO rmaDAO = new RmaDAO();
		Rma rma = rmaDAO.getRma(rmaId);
		return new ModelAndView("rmaPdf", "rma", rma);
	}
}