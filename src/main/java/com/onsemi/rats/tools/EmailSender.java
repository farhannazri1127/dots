package com.onsemi.rats.tools;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import com.onsemi.rats.dao.EmailDAO;
import com.onsemi.rats.model.Email;
import com.onsemi.rats.model.User;
import java.util.Arrays;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class EmailSender extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private final String emailTemplate = "resources/email";
    //private final String logoPath = "/resources/public/img/spml_all.png";
//    private final String logoPath = "/resources/img/cdars_logo.png";
    private final String logoPath = "/resources/img/clipart633480.png";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public EmailSender() {
    }

    public void textEmail(final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(env.getProperty("mail.sender"));
                message.setTo(to);
                message.setSubject(subject);
                message.setText(msg);
                mailSender.send(message);
            }
        }).start();
    }

    public void mimeEmail(final ServletContext servletContext, final User user, final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MimeMessagePreparator preparator = new MimeMessagePreparator() {
                    @Override
                    public void prepare(MimeMessage mimeMessage) throws Exception {
                        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                        message.setTo(to);
                        message.setFrom(env.getProperty("mail.sender"));
                        message.setSubject(subject);
                        Map model = new HashMap();
                        model.put("user", user.getFullname());
                        model.put("subject", subject);
                        model.put("message", msg);
                        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                        freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                        String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                        message.setText(text, true);
                    }
                };
                mailSender.send(preparator);
            }
        }).start();
    }

    public void htmlEmail(final ServletContext servletContext, final User user, final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailManyTo(final ServletContext servletContext, final User user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    LOGGER.info("EMAIL PORT.....: " + email.getPort());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator("onsemi/dots-seremban", "relONsemi1"));
//                    htmlEmail.setAuthentication("fg79cj@onsemi.com", "fare1189!!@@##$");
                    LOGGER.info("EMAIL username.....: " + email.getUsername());
                    LOGGER.info("EMAIL password.....: " + email.getPassword());
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailManyToNew(final ServletContext servletContext, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    LOGGER.info("EMAIL PORT.....: " + email.getPort());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator("onsemi/dots-seremban", "relONsemi1"));
//                    htmlEmail.setAuthentication("fg79cj@onsemi.com", "fare1189!!@@##$");
                    LOGGER.info("EMAIL username.....: " + email.getUsername());
                    LOGGER.info("EMAIL password.....: " + email.getPassword());
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
//                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                    LOGGER.info("EMAIL RECIPIENT: " + Arrays.toString(to));
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailManyToNewWithCc(final ServletContext servletContext, final String[] to, final String subject, final String msg, final String[] cc) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    LOGGER.info("EMAIL PORT.....: " + email.getPort());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator("onsemi/dots-seremban", "relONsemi1"));
//                    htmlEmail.setAuthentication("fg79cj@onsemi.com", "fare1189!!@@##$");
                    LOGGER.info("EMAIL username.....: " + email.getUsername());
                    LOGGER.info("EMAIL password.....: " + email.getPassword());
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);
                    htmlEmail.addCc(cc);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
//                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                    LOGGER.info("EMAIL RECIPIENT: " + Arrays.toString(to));
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailWithAttachment(final ServletContext servletContext, final User user, final String[] to, final File file, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);
//                    htmlEmail.setStartTLSEnabled(true);

                    LOGGER.info("port:......" + email.getPort());
                    LOGGER.info("host:......" + email.getHost());

//                    File file = new File("C:\\test.csv");
                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);
//                    htmlEmail.embed(file);
                    htmlEmail.attach(file);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailWithMultipleAttachment(final ServletContext servletContext, final User user, final String[] to, final File[] file, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
//                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
//                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setSSLOnConnect(false);
                    htmlEmail.setDebug(true);
//                    htmlEmail.setStartTLSEnabled(true);

                    LOGGER.info("port:......" + email.getPort());
                    LOGGER.info("host:......" + email.getHost());

//                    File file = new File("C:\\test.csv");
                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    for (File file : file) {
                        htmlEmail.embed(file);
                    }

//                    htmlEmail.embed(file);
                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }
}
