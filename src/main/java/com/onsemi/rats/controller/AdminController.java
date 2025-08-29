package com.onsemi.rats.controller;

import com.onsemi.rats.dao.DashboardDAO;
import com.onsemi.rats.dao.LDAPUserDAO;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.rats.dao.MenuDAO;
import com.onsemi.rats.dao.UserDAO;
import com.onsemi.rats.dao.UserGroupAccessDAO;
import com.onsemi.rats.dao.UserGroupDAO;
import com.onsemi.rats.model.Incident;
import com.onsemi.rats.model.JSONResponse;
import com.onsemi.rats.model.LDAPUser;
import com.onsemi.rats.model.Menu;
import com.onsemi.rats.model.NewRequest;
import com.onsemi.rats.tools.QueryResult;
import com.onsemi.rats.model.User;
import com.onsemi.rats.model.UserGroup;
import com.onsemi.rats.model.UserGroupAccess;
import com.onsemi.rats.model.UserSession;
import com.onsemi.rats.tools.SystemUtil;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin")
@SessionAttributes({"userSession"})
@PropertySource("classpath:ldap.properties")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Model model) {

        DashboardDAO dashD = new DashboardDAO();
        Integer newIncident = dashD.getCountStatusIncidentTable("New Incident");
        dashD = new DashboardDAO();
        Integer newRequest = dashD.getCountStatusRequestTable("New Request");
        Integer newCompiled = newIncident + newRequest;

        dashD = new DashboardDAO();
        Integer picAssignedIncident = dashD.getCountStatusIncidentTable("PIC Assigned");
        dashD = new DashboardDAO();
        Integer picAssignedRequest = dashD.getCountStatusRequestTable("PIC Assigned");
        Integer picAssignedCompiled = picAssignedIncident + picAssignedRequest;

        dashD = new DashboardDAO();
        Integer inProcessIncident = dashD.getCountStatusIncidentTable("In Process");
        dashD = new DashboardDAO();
        Integer inProcessRequest = dashD.getCountStatusRequestTable("In Process");
        Integer inProcessCompiled = inProcessIncident + inProcessRequest;

        dashD = new DashboardDAO();
        Integer completeIncident = dashD.getCountStatusIncidentTable("Completed");
        dashD = new DashboardDAO();
        Integer completeRequest = dashD.getCountStatusRequestTable("Completed");
        Integer completeCompiled = completeIncident + completeRequest;

        dashD = new DashboardDAO();
        Integer closeIncident = dashD.getCountStatusIncidentTable("Closed");
        dashD = new DashboardDAO();
        Integer closeRequest = dashD.getCountStatusRequestTable("Closed");
        dashD = new DashboardDAO();
        Integer cancelIncident = dashD.getCountStatusIncidentTable("Cancelled");
        dashD = new DashboardDAO();
        Integer cancelRequest = dashD.getCountStatusRequestTable("Cancelled");
        Integer closeCancelCompiled = closeIncident + closeRequest + cancelIncident + cancelRequest;

        dashD = new DashboardDAO();
        Integer totalIncident = dashD.getCountTotalIncidentTable();
        dashD = new DashboardDAO();
        Integer totalRequest = dashD.getCountTotalRequestTable();
        Integer totalCompiled = totalIncident + totalRequest;

        dashD = new DashboardDAO();
        Integer compTotalIncident = dashD.getCountCompleteTotalIncidentTable();
        dashD = new DashboardDAO();
        Integer compTotalRequest = dashD.getCountCompleteTotalRequestTable();
        Integer compTotalCompiled = compTotalIncident + compTotalRequest;

        float percentNew = 0;
        float percentPic = 0;
        float percentInProcess = 0;
        float percentComplete = 0;
        float percentClosed = 0;

        if (newCompiled != 0 && totalCompiled != 0) {
            percentNew = ((float) newCompiled / totalCompiled) * 100;
        }
        if (picAssignedCompiled != 0 && totalCompiled != 0) {
            percentPic = ((float) picAssignedCompiled / totalCompiled) * 100;
        }
        if (inProcessCompiled != 0 && totalCompiled != 0) {
            percentInProcess = ((float) inProcessCompiled / totalCompiled) * 100;
        }
        if (completeCompiled != 0 && totalCompiled != 0) {
            percentComplete = ((float) completeCompiled / totalCompiled) * 100;
        }
        if (closeCancelCompiled != 0 && totalCompiled != 0) {
            percentClosed = ((float) closeCancelCompiled / totalCompiled) * 100;
        }

//        float percentNew = ((float) newCompiled / totalCompiled) * 100;
//        float percentPic = ((float) picAssignedCompiled / totalCompiled) * 100;
//        float percentInProcess = ((float) inProcessCompiled / totalCompiled) * 100;
//        float percentComplete = ((float) completeCompiled / totalCompiled) * 100;
//        float percentClosed = ((float) closeCancelCompiled / totalCompiled) * 100;
        DecimalFormat df = new DecimalFormat("0.00");
        percentNew = Float.parseFloat(df.format(percentNew));
        percentPic = Float.parseFloat(df.format(percentPic));
        percentInProcess = Float.parseFloat(df.format(percentInProcess));
        percentComplete = Float.parseFloat(df.format(percentComplete));
        percentClosed = Float.parseFloat(df.format(percentClosed));

        dashD = new DashboardDAO();
        Integer IncidentlAtiqah = dashD.getCountByPicIncidentTable("Nor Atiqah Mohd Zahar");
        dashD = new DashboardDAO();
        Integer RequestlAtiqah = dashD.getCountByPicRequestTable("Nor Atiqah Mohd Zahar");
        Integer totalAtiqah = IncidentlAtiqah + RequestlAtiqah;

        dashD = new DashboardDAO();
        Integer IncidentArif = dashD.getCountByPicIncidentTable("Mohd Arif Dzainal Abidin");
        dashD = new DashboardDAO();
        Integer RequestArif = dashD.getCountByPicRequestTable("Mohd Arif Dzainal Abidin");
        Integer totalArif = IncidentArif + RequestArif;

        dashD = new DashboardDAO();
        Integer IncidentFarhan = dashD.getCountByPicIncidentTable("Mohd Farhan Nazri");
        dashD = new DashboardDAO();
        Integer RequestFarhan = dashD.getCountByPicRequestTable("Mohd Farhan Nazri");
        Integer totalFarhan = IncidentFarhan + RequestFarhan;

        dashD = new DashboardDAO();
        Integer perIncidentlAtiqah = dashD.getCountByPicCompleteIncidentTable("Nor Atiqah Mohd Zahar");
        dashD = new DashboardDAO();
        Integer perRequestlAtiqah = dashD.getCountByPicCompleteRequestTable("Nor Atiqah Mohd Zahar");
        Integer compTotalAtiqah = perIncidentlAtiqah + perRequestlAtiqah;

        dashD = new DashboardDAO();
        Integer perIncidentArif = dashD.getCountByPicCompleteIncidentTable("Mohd Arif Dzainal Abidin");
        dashD = new DashboardDAO();
        Integer perRequestArif = dashD.getCountByPicCompleteRequestTable("Mohd Arif Dzainal Abidin");
        Integer compTotalArif = perIncidentArif + perRequestArif;

        dashD = new DashboardDAO();
        Integer perIncidentFarhan = dashD.getCountByPicCompleteIncidentTable("Mohd Farhan Nazri");
        dashD = new DashboardDAO();
        Integer perRequestFarhan = dashD.getCountByPicCompleteRequestTable("Mohd Farhan Nazri");
        Integer compTotalFarhan = perIncidentFarhan + perRequestFarhan;

        float percentAtiqah = 0;
        float percentArif = 0;
        float percentFarhan = 0;
        float percentTotal = 0;

        if (compTotalAtiqah != 0 && totalAtiqah != 0) {
            percentAtiqah = ((float) compTotalAtiqah / totalAtiqah) * 100;
        }
        if (compTotalArif != 0 && totalArif != 0) {
            percentArif = ((float) compTotalArif / totalArif) * 100;
        }
        if (compTotalFarhan != 0 && totalFarhan != 0) {
            percentFarhan = ((float) compTotalFarhan / totalFarhan) * 100;
        }
        if (compTotalCompiled != 0 && totalCompiled != 0) {
            percentTotal = ((float) compTotalCompiled / totalCompiled) * 100;
        }

//        float percentAtiqah = ((float) compTotalAtiqah / totalAtiqah) * 100;
//        float percentArif = ((float) compTotalArif / totalArif) * 100;
//        float percentFarhan = ((float) compTotalFarhan / totalFarhan) * 100;
//        float percentTotal = ((float) compTotalCompiled / totalCompiled) * 100;
        percentAtiqah = Float.parseFloat(df.format(percentAtiqah));
        percentArif = Float.parseFloat(df.format(percentArif));
        percentFarhan = Float.parseFloat(df.format(percentFarhan));
        percentTotal = Float.parseFloat(df.format(percentTotal));

        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);

        dashD = new DashboardDAO();
        Integer totalIncJan = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "1");
        dashD = new DashboardDAO();
        Integer totalReqJan = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "1");
        Integer totalJan = totalIncJan + totalReqJan;
        dashD = new DashboardDAO();
        Integer compIncJan = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "1");
        dashD = new DashboardDAO();
        Integer compReqJan = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "1");
        Integer compJan = compIncJan + compReqJan;

        dashD = new DashboardDAO();
        Integer totalIncFeb = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "2");
        dashD = new DashboardDAO();
        Integer totalReqFeb = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "2");
        Integer totalFeb = totalIncFeb + totalReqFeb;
        dashD = new DashboardDAO();
        Integer compIncFeb = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "2");
        dashD = new DashboardDAO();
        Integer compReqFeb = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "2");
        Integer compFeb = compIncFeb + compReqFeb;

        dashD = new DashboardDAO();
        Integer totalIncMar = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "3");
        dashD = new DashboardDAO();
        Integer totalReqMar = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "3");
        Integer totalMar = totalIncMar + totalReqMar;
        dashD = new DashboardDAO();
        Integer compIncMar = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "3");
        dashD = new DashboardDAO();
        Integer compReqMar = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "3");
        Integer compMar = compIncMar + compReqMar;

        dashD = new DashboardDAO();
        Integer totalIncApr = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "4");
        dashD = new DashboardDAO();
        Integer totalReqApr = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "4");
        Integer totalApr = totalIncApr + totalReqApr;
        dashD = new DashboardDAO();
        Integer compIncApr = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "4");
        dashD = new DashboardDAO();
        Integer compReqApr = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "4");
        Integer compApr = compIncApr + compReqApr;

        dashD = new DashboardDAO();
        Integer totalIncMay = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "5");
        dashD = new DashboardDAO();
        Integer totalReqMay = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "5");
        Integer totalMay = totalIncMay + totalReqMay;
        dashD = new DashboardDAO();
        Integer compIncMay = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "5");
        dashD = new DashboardDAO();
        Integer compReqMay = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "5");
        Integer compMay = compIncMay + compReqMay;

        dashD = new DashboardDAO();
        Integer totalIncJun = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "6");
        dashD = new DashboardDAO();
        Integer totalReqJun = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "6");
        Integer totalJun = totalIncJun + totalReqJun;
        dashD = new DashboardDAO();
        Integer compIncJun = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "6");
        dashD = new DashboardDAO();
        Integer compReqJun = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "6");
        Integer compJun = compIncJun + compReqJun;

        dashD = new DashboardDAO();
        Integer totalIncJul = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "7");
        dashD = new DashboardDAO();
        Integer totalReqJul = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "7");
        Integer totalJul = totalIncJul + totalReqJul;
        dashD = new DashboardDAO();
        Integer compIncJul = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "7");
        dashD = new DashboardDAO();
        Integer compReqJul = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "7");
        Integer compJul = compIncJul + compReqJul;

        dashD = new DashboardDAO();
        Integer totalIncAug = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "8");
        dashD = new DashboardDAO();
        Integer totalReqAug = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "8");
        Integer totalAug = totalIncAug + totalReqAug;
        dashD = new DashboardDAO();
        Integer compIncAug = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "8");
        dashD = new DashboardDAO();
        Integer compReqAug = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "8");
        Integer compAug = compIncAug + compReqAug;

        dashD = new DashboardDAO();
        Integer totalIncSep = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "9");
        dashD = new DashboardDAO();
        Integer totalReqSep = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "9");
        Integer totalSep = totalIncSep + totalReqSep;
        dashD = new DashboardDAO();
        Integer compIncSep = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "9");
        dashD = new DashboardDAO();
        Integer compReqSep = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "9");
        Integer compSep = compIncSep + compReqSep;

        dashD = new DashboardDAO();
        Integer totalIncOct = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "10");
        dashD = new DashboardDAO();
        Integer totalReqOct = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "10");
        Integer totalOct = totalIncOct + totalReqOct;
        dashD = new DashboardDAO();
        Integer compIncOct = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "10");
        dashD = new DashboardDAO();
        Integer compReqOct = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "10");
        Integer compOct = compIncOct + compReqOct;

        dashD = new DashboardDAO();
        Integer totalIncNov = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "11");
        dashD = new DashboardDAO();
        Integer totalReqNov = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "11");
        Integer totalNov = totalIncNov + totalReqNov;
        dashD = new DashboardDAO();
        Integer compIncNov = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "11");
        dashD = new DashboardDAO();
        Integer compReqNov = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "11");
        Integer compNov = compIncNov + compReqNov;

        dashD = new DashboardDAO();
        Integer totalIncDec = dashD.getCountTotalIncidentTableByYearMonth(year.toString(), "12");
        dashD = new DashboardDAO();
        Integer totalReqDec = dashD.getCountTotalRequestTableByYearMonth(year.toString(), "12");
        Integer totalDec = totalIncDec + totalReqDec;
        dashD = new DashboardDAO();
        Integer compIncDec = dashD.getCountTotalIncidentTableByYearMonthComplete(year.toString(), "12");
        dashD = new DashboardDAO();
        Integer compReqDec = dashD.getCountTotalRequestTableByYearMonthComplete(year.toString(), "12");
        Integer compDec = compIncDec + compReqDec;

        model.addAttribute("newCompiled", newCompiled);
        model.addAttribute("picAssignedCompiled", picAssignedCompiled);
        model.addAttribute("inProcessCompiled", inProcessCompiled);
        model.addAttribute("completeCompiled", completeCompiled);
        model.addAttribute("closeCancelCompiled", closeCancelCompiled);

        model.addAttribute("percentNew", percentNew);
        model.addAttribute("percentPic", percentPic);
        model.addAttribute("percentInProcess", percentInProcess);
        model.addAttribute("percentComplete", percentComplete);
        model.addAttribute("percentClosed", percentClosed);

        model.addAttribute("totalAtiqah", totalAtiqah);
        model.addAttribute("totalArif", totalArif);
        model.addAttribute("totalFarhan", totalFarhan);
        model.addAttribute("totalCompiled", totalCompiled);

        model.addAttribute("compTotalAtiqah", compTotalAtiqah);
        model.addAttribute("compTotalArif", compTotalArif);
        model.addAttribute("compTotalFarhan", compTotalFarhan);
        model.addAttribute("compTotalCompiled", compTotalCompiled);

        model.addAttribute("percentAtiqah", percentAtiqah);
        model.addAttribute("percentArif", percentArif);
        model.addAttribute("percentFarhan", percentFarhan);
        model.addAttribute("percentTotal", percentTotal);

        model.addAttribute("totalJan", totalJan);
        model.addAttribute("compJan", compJan);

        model.addAttribute("totalFeb", totalFeb);
        model.addAttribute("compFeb", compFeb);

        model.addAttribute("totalMar", totalMar);
        model.addAttribute("compMar", compMar);

        model.addAttribute("totalApr", totalApr);
        model.addAttribute("compApr", compApr);

        model.addAttribute("totalMay", totalMay);
        model.addAttribute("compMay", compMay);

        model.addAttribute("totalJun", totalJun);
        model.addAttribute("compJun", compJun);

        model.addAttribute("totalJul", totalJul);
        model.addAttribute("compJul", compJul);

        model.addAttribute("totalAug", totalAug);
        model.addAttribute("compAug", compAug);

        model.addAttribute("totalSep", totalSep);
        model.addAttribute("compSep", compSep);

        model.addAttribute("totalOct", totalOct);
        model.addAttribute("compOct", compOct);

        model.addAttribute("totalNov", totalNov);
        model.addAttribute("compNov", compNov);

        model.addAttribute("totalDec", totalDec);
        model.addAttribute("compDec", compDec);

        model.addAttribute("year", year);

        return "admin/dashboard";
//        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newIncident(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "New Incident / Request";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByStatus("New Incident");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByStatus("New Request");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/pic", method = RequestMethod.GET)
    public String pic(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
        String title = "PIC Assigned";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByStatus("PIC Assigned");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByStatus("PIC Assigned");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/inProcess", method = RequestMethod.GET)
    public String inProcess(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "In Process";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByStatus("In Process");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByStatus("In Process");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/completed", method = RequestMethod.GET)
    public String completed(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "Completed";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByStatus("Completed");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByStatus("Completed");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/closeCancel", method = RequestMethod.GET)
    public String closeCancel(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "Closed / Cancelled";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByFlag("1");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByFlag("1");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/atiqah", method = RequestMethod.GET)
    public String atiqah(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "Nor Atiqah Mohd Zahar";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByPic("Nor Atiqah Mohd Zahar");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByPic("Nor Atiqah Mohd Zahar");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/arif", method = RequestMethod.GET)
    public String arif(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "Mohd Arif Dzainal Abidin";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByPic("Mohd Arif Dzainal Abidin");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByPic("Mohd Arif Dzainal Abidin");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/farhan", method = RequestMethod.GET)
    public String farhan(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "Mohd Farhan Nazri";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListByPic("Mohd Farhan Nazri");
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListByPic("Mohd Farhan Nazri");
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public String total(
            Model model,
            @ModelAttribute UserSession userSession
    ) {

        String title = "Total";
        model.addAttribute("title", title);

        DashboardDAO incidentDAO = new DashboardDAO();
        List<Incident> incidentList = incidentDAO.getIncidentListTotal();
        model.addAttribute("incidentList", incidentList);

        incidentDAO = new DashboardDAO();
        List<NewRequest> requestList = incidentDAO.getRequestListTotal();
        model.addAttribute("requestList", requestList);
        return "admin/ticket";
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(
            Model model,
            @RequestParam(required = false) String selectedGroup
    ) {
        selectedGroup = SystemUtil.nullToEmptyString(selectedGroup);
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        List<LDAPUser> ldapUserList = ldapUserDAO.listByGroupId(selectedGroup);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(selectedGroup);
        model.addAttribute("userList", ldapUserList);
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("selectedGroup", selectedGroup);
        return "admin/ldap_user";
    }

    @RequestMapping(value = "/user/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String userAdd(
            Model model,
            @RequestParam(required = false) String loginId
    ) {
        LOGGER.info("Login Id: " + loginId);
        List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();

        if (loginId != null) {
            //Start Retrieve LDAP Users
            Hashtable h = new Hashtable();
            h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

            DirContext ctx = null;
            NamingEnumeration results = null;

            try {
                ctx = new InitialDirContext(h);
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid"};
                controls.setReturningAttributes(attrIDs);
                //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
                //Onsemi
                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);

                while (results.hasMore()) {
                    SearchResult searchResult = (SearchResult) results.next();
                    Attributes attributes = searchResult.getAttributes();

                    LDAPUser user = new LDAPUser();

                    Enumeration e = attributes.getIDs();
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        if (key.equalsIgnoreCase("givenName")) {
                            user.setFirstname(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("sn")) {
                            user.setLastname(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("title")) {
                            user.setTitle(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("cn")) {
                            user.setLoginId(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("mail")) {
                            user.setEmail(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("oncid")) {
                            user.setOncid(attributes.get(key).get().toString());
                        }
                        if (key.equalsIgnoreCase("onoraclelocation")) {
                            user.setLocation(attributes.get(key).get().toString());
                        }
                    }

                    ldapUserList.add(user);
                }
            } catch (NamingException e) {
                LOGGER.error(e.getMessage());
            } finally {
                if (results != null) {
                    try {
                        results.close();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }
                if (ctx != null) {
                    try {
                        ctx.close();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            //End Retrieve LDAP Users
        }

        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        model.addAttribute("userList", ldapUserList);
        return "admin/ldap_user_add";
    }

    @RequestMapping(value = "/user/loginid/{loginId}", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse userLoginId(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        JSONResponse response = new JSONResponse();
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        LDAPUser ldapUser = ldapUserDAO.getByLoginId(loginId);
        if (ldapUser.getFirstname() == null) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage("User not registered!");
            response.setResult(ldapUser);
        } else {
            response.setStatus(Boolean.TRUE);
            response.setStatusMessage("User already registered!");
            response.setResult(ldapUser);
        }
        return response;
    }

    @RequestMapping(value = "/user/ldap/save", method = RequestMethod.POST)
    public @ResponseBody
    JSONResponse userLDAPSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String oncid,
            @RequestParam(required = false) String groupId
    ) {

        JSONResponse response = new JSONResponse();
        LDAPUser ldapUser = new LDAPUser();
        ldapUser.setLoginId(loginId);
        ldapUser.setOncid(oncid);
        ldapUser.setFirstname(firstname);
        ldapUser.setLastname(lastname);
        ldapUser.setEmail(email);
        ldapUser.setTitle(title);
        ldapUser.setGroupId(groupId);
        ldapUser.setCreatedBy(userSession.getId());
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        QueryResult queryResult = ldapUserDAO.insert(ldapUser);
        if (queryResult.getResult() <= 0) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage(queryResult.getErrorMessage());
            response.setResult(ldapUser);
        } else {
            response.setStatus(Boolean.TRUE);
            response.setStatusMessage("User added!");
            response.setResult(ldapUser);
        }
        return response;
    }

    @RequestMapping(value = "/user/sync/{loginId}", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse userSync(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request,
            @PathVariable("loginId") String loginId
    ) {
        //Start Retrieve LDAP Users
        Hashtable h = new Hashtable();
        h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        h.put(Context.PROVIDER_URL, env.getProperty("ldap.url"));

        DirContext ctx = null;
        NamingEnumeration results = null;
        LDAPUser ldapUser = new LDAPUser();

        try {
            ctx = new InitialDirContext(h);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid"};
            controls.setReturningAttributes(attrIDs);
            //Local
//            results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
            //Onsemi
            results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);

            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();

                Enumeration e = attributes.getIDs();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    if (key.equalsIgnoreCase("givenName")) {
                        ldapUser.setFirstname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("sn")) {
                        ldapUser.setLastname(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("title")) {
                        ldapUser.setTitle(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("cn")) {
                        ldapUser.setLoginId(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("mail")) {
                        ldapUser.setEmail(attributes.get(key).get().toString());
                    }
                    if (key.equalsIgnoreCase("oncid")) {
                        ldapUser.setOncid(attributes.get(key).get().toString());
                    }
                }
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        //End Retrieve LDAP Users

        JSONResponse response = new JSONResponse();
        if (ldapUser.getFirstname() == null) {
            response.setStatus(Boolean.FALSE);
            response.setStatusMessage("Unable to retrieve user info from LDAP for " + loginId + "!");
            response.setResult(ldapUser);
        } else {
            LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
            LDAPUser dbLdapUser = ldapUserDAO.getByLoginId(loginId);
            if (dbLdapUser.getFirstname() == null) {
                response.setStatus(Boolean.FALSE);
                response.setStatusMessage("Unable to retrieve user info from Database for " + loginId + "!");
                response.setResult(ldapUser);
            } else {
                LDAPUser updateUser = new LDAPUser();
                updateUser.setId(dbLdapUser.getId());
                updateUser.setOncid(ldapUser.getOncid());
                updateUser.setFirstname(ldapUser.getFirstname());
                updateUser.setLastname(ldapUser.getLastname());
                updateUser.setEmail(ldapUser.getEmail());
                updateUser.setTitle(ldapUser.getTitle());
                updateUser.setGroupId(dbLdapUser.getGroupId());
                updateUser.setModifiedBy(userSession.getId());
                QueryResult queryResult = ldapUserDAO.update(updateUser);
                if (queryResult.getResult() <= 0) {
                    response.setStatus(Boolean.FALSE);
                    response.setStatusMessage(queryResult.getErrorMessage());
                    response.setResult(ldapUser);
                } else {
                    response.setStatus(Boolean.TRUE);
                    response.setStatusMessage("User updated!");
                    response.setResult(updateUser);
                }
            }
        }
        return response;
    }

    @RequestMapping(value = "/user/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByLoginId(loginId);
        if (user == null) {
            user = new User();
            user.setLoginId(loginId);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setGroupId(groupId);
            user.setCreatedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.insertUser(user);
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("admin.label.user.save.error", args, locale));
                model.addAttribute("loginId", loginId);
                model.addAttribute("fullname", fullname);
                model.addAttribute("email", email);
                UserGroupDAO userGroupDAO = new UserGroupDAO();
                List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
                model.addAttribute("userGroupList", userGroupList);
                return "admin/user_add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.save.success", args, locale));
                return "redirect:/admin/user/edit/" + queryResult.getGeneratedKey();
            }
        } else {
            args = new String[1];
            args[0] = loginId;
            model.addAttribute("error", messageSource.getMessage("general.label.exist.success", args, locale));
            model.addAttribute("loginId", loginId);
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            List<UserGroup> userGroupList = userGroupDAO.getGroupList(groupId);
            model.addAttribute("userGroupList", userGroupList);
            return "admin/user_add";
        }
    }

    @RequestMapping(value = "/user/edit/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userEdit(
            Model model,
            @PathVariable("userId") String userId
    ) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(userId);
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList(user.getGroupId());
        model.addAttribute("user", user);
        model.addAttribute("userGroupList", userGroupList);
        return "admin/user_edit";
    }

    @RequestMapping(value = "/user/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String userUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String isActive
    ) {
        User user = new User();
        user.setId(userId);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setIsActive(isActive);
        user.setModifiedBy(userSession.getId());
        user.setGroupId(groupId);

        UserDAO userDAO = new UserDAO();
        QueryResult queryResult = userDAO.updateUser(user);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.update.error", args, locale));
        }
        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/password", method = {RequestMethod.GET, RequestMethod.POST})
    public String userPassword(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmPassword
    ) {
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.getUser(userId);
        if (BCrypt.checkpw(currentPassword, currentUser.getPassword())) {
            User user = new User();
            user.setId(userId);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            user.setModifiedBy(userSession.getId());
            userDAO = new UserDAO();
            QueryResult queryResult = userDAO.updatePassword(user);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.password.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.password.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.current_password.error", args, locale));
        }

        return "redirect:/admin/user/edit/" + userId;
    }

    @RequestMapping(value = "/user/delete/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("userId") String userId
    ) {
        LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
        QueryResult queryResult = ldapUserDAO.delete(userId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.user.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.user.delete.error", args, locale));
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String group(
            Model model
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        List<UserGroup> userGroupList = userGroupDAO.getGroupList("");
        model.addAttribute("userGroupList", userGroupList);
        return "admin/group";
    }

    @RequestMapping(value = "/group/menu/{groupId}", method = RequestMethod.GET)
    public String groupMenu(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupAccessDAO userGroupAccessDAO = new UserGroupAccessDAO();
        List<UserGroupAccess> userGroupAccessList = userGroupAccessDAO.getUserGroupAccess(groupId);
        model.addAttribute("userGroupAccessList", userGroupAccessList);
        model.addAttribute("groupId", groupId);
        return "admin/group_menu";
    }

    @RequestMapping(value = "/group/menu/save", method = {RequestMethod.POST})
    public String groupMenuSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String[] groupAccess
    ) {
        groupAccess = SystemUtil.nullToEmptyString(groupAccess);
        UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
        //should use batch insert for performance
        QueryResult addQueryResult = new QueryResult();
        addQueryResult.setResult(0);
        for (String access : groupAccess) {
            UserGroupAccessDAO addUserGroupAccessDAO = new UserGroupAccessDAO();
            addQueryResult = addUserGroupAccessDAO.addAccess(groupId, access);
        }
        QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccess(groupId, groupAccess);
        int result = addQueryResult.getResult() + remQueryResult.getResult();
        if (result != 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.access.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.access.error", args, locale));
        }
        return "redirect:/admin/group/menu/" + groupId;
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String groupAdd(Model model) {
        return "admin/group_add";
    }

    @RequestMapping(value = "/group/edit/{groupId}", method = RequestMethod.GET)
    public String group_edit(
            Model model,
            @PathVariable("groupId") String groupId
    ) {
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        UserGroup userGroup = userGroupDAO.getGroup(groupId);
        model.addAttribute("userGroup", userGroup);
        return "admin/group_edit";
    }

    @RequestMapping(value = "/group/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setCreatedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.insertGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.save.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.save.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + queryResult.getGeneratedKey();
    }

    @RequestMapping(value = "/group/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupUpdate(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String groupId,
            @RequestParam(required = false) String groupCode,
            @RequestParam(required = false) String groupName
    ) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(groupId);
        userGroup.setCode(groupCode);
        userGroup.setName(groupName);
        userGroup.setModifiedBy(userSession.getId());
        UserGroupDAO userGroupDAO = new UserGroupDAO();
        QueryResult queryResult = userGroupDAO.updateGroup(userGroup);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.update.error", args, locale));
        }
        return "redirect:/admin/group/edit/" + groupId;
    }

    @RequestMapping(value = "/group/delete/{groupId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String groupDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("groupId") String groupId
    ) {
        UserDAO userDAO = new UserDAO();
        int userCount = userDAO.getCountByGroupId(groupId);
        if (userCount == 0) {
            UserGroupDAO userGroupDAO = new UserGroupDAO();
            QueryResult queryResult = userGroupDAO.deleteGroup(groupId);
            UserGroupAccessDAO removeUserGroupAccessDAO = new UserGroupAccessDAO();
            QueryResult remQueryResult = removeUserGroupAccessDAO.removeAccessByGroupId(groupId);
            int result = queryResult.getResult() + remQueryResult.getResult();
            if (result != 0) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("admin.label.group.delete.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.error", args, locale));
            }
        } else {
            args = new String[]{Integer.toString(userCount)};
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("admin.label.group.delete.have_user.error", args, locale));
        }
        return "redirect:/admin/group";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(
            Model model
    ) {
        MenuDAO menuDAO = new MenuDAO();
        List<Menu> parentMenuList = menuDAO.getMenuList("0");
        String tbody = "<tbody>";
        String menuOption = "";
        for (int i = 0; i < parentMenuList.size(); i++) {
            Menu parentMenu = parentMenuList.get(i);
            tbody += "<tr><td>&nbsp;</td><td><i class='fa " + parentMenu.getIcon() + "'></i>&nbsp;" + parentMenu.getName() + "</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
            menuOption += "<option value='" + parentMenu.getId() + "'>" + parentMenu.getName() + "</option>";
            List<Menu> childMenuList = menuDAO.getMenuList(parentMenu.getCode());
            if (!childMenuList.isEmpty()) {
                for (int j = 0; j < childMenuList.size(); j++) {
                    Menu childMenu = childMenuList.get(j);
                    tbody += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td><i class='fa fa-minus'></i>&nbsp;" + childMenu.getName() + "</td><td>&nbsp;</td></tr>";
                    menuOption += "<option value='" + childMenu.getId() + "'>&nbsp;&nbsp;&nbsp;&nbsp;" + childMenu.getName() + "</option>";
                }
            }
        }
        tbody += "</tbody>";
        model.addAttribute("tbody", tbody);
        model.addAttribute("menuOption", menuOption);
        return "admin/menu";
    }

}
