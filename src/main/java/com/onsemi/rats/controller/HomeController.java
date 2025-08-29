package com.onsemi.rats.controller;

import com.onsemi.rats.dao.LDAPUserDAO;
import com.onsemi.rats.dao.UserGroupDAO;
import com.onsemi.rats.model.LDAPUser;
import com.onsemi.rats.model.UserGroup;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.onsemi.rats.model.UserSession;
import com.onsemi.rats.tools.QueryResult;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("userSession")
@PropertySource("classpath:ldap.properties")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String selectedProgram
    ) {
        HttpSession currentSession = request.getSession();
        UserSession userSession = (UserSession) currentSession.getAttribute("userSession");

        if (userSession != null) {
            String groupId = userSession.getGroup();
            model.addAttribute("groupId", groupId);
//            System.out.println("++groupId : " + groupId);
            List<LDAPUser> ldapUserList = new ArrayList<LDAPUser>();

            //save to user table if not registered yet
            if ("0".equals(groupId)) {
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
                    String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid", "onoraclelocation"};
                    controls.setReturningAttributes(attrIDs);
                    //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
                    //Onsemi
//                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
                    results = ctx.search("ou=ONSemi", "(cn=" + userSession.getLoginId() + ")", controls);

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
//                            System.out.println("onoraclelocation: " + attributes.get(key).get().toString());
//                        LOGGER.info("onoraclelocation: " + attributes.get(key).get().toString());
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

                for (int i = 0; i < ldapUserList.size(); i++) {

                    LDAPUser ldap = new LDAPUser();
                    ldap.setLoginId(ldapUserList.get(i).getLoginId());
                    ldap.setOncid(ldapUserList.get(i).getOncid());
                    ldap.setFirstname(ldapUserList.get(i).getFirstname());
                    ldap.setLastname(ldapUserList.get(i).getLastname());
                    ldap.setEmail(ldapUserList.get(i).getEmail());
                    ldap.setTitle(ldapUserList.get(i).getTitle());
                    ldap.setLocation(ldapUserList.get(i).getLocation());
                    ldap.setGroupId("2");
                    LDAPUserDAO ldapUserDAO = new LDAPUserDAO();
                    QueryResult queryResult = ldapUserDAO.insert(ldap);
//                    System.out.println("++queryResult : " + queryResult.getGeneratedKey());
                    return "redirect:/";
                }

            }

            //Anything for Dashboard
            return "home/index";
        } else {
            return "home/index";
        }
    }

//    @RequestMapping(value = "/error", method = RequestMethod.GET)
//    public String loginError(RedirectAttributes redirectAttrs, Locale locale) {
//        redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.login.error", args, locale));
//        return "redirect:/";
//    }
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String loginError(
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            Locale locale) {
        LOGGER.info("Login LDAP Error!");
        LOGGER.info(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString());
        redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.login.error", args, locale));
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttrs, Locale locale) {
        redirectAttrs.addFlashAttribute("logout", messageSource.getMessage("general.label.logout", args, locale));
        return "redirect:/";
    }

//    @RequestMapping(value = "/register", method = {RequestMethod.GET})
//    public String register(Model model, HttpServletRequest request) {
//        return "home/register";
//    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        System.out.println("register!!!!!: ");
        return "home/register";
    }

    @RequestMapping(value = "/register/add", method = RequestMethod.POST)
    public String userAdd(
            Model model,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String loginId
    ) {
        System.out.println("++user++");
        System.out.println("Login Id: " + loginId);
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
                String[] attrIDs = {"givenname", "sn", "title", "cn", "mail", "oncid", "onoraclelocation"};
                controls.setReturningAttributes(attrIDs);
                //Local
//                results = ctx.search("ou=Users", "(cn=" + loginId + ")", controls);
                //Onsemi
//                results = ctx.search("ou=Seremban,ou=ONSemi", "(cn=" + loginId + ")", controls);
                results = ctx.search("ou=ONSemi", "(cn=" + loginId + ")", controls);

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
                        System.out.println("onoraclelocation: " + attributes.get(key).get().toString());
//                        LOGGER.info("onoraclelocation: " + attributes.get(key).get().toString());
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
        return "redirect:/";
    }
}
