package com.onsemi.dots.controller;

import com.onsemi.dots.dao.ChamberDAO;
import com.onsemi.dots.dao.LogDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.dots.dao.LotPenangDAO;
import com.onsemi.dots.dao.ParameterDetailsDAO;
import com.onsemi.dots.dao.RequestDAO;
import com.onsemi.dots.model.Chamber;
import com.onsemi.dots.model.Log;
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.model.ParameterDetails;
import com.onsemi.dots.model.Request;
import com.onsemi.dots.model.UserSession;
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
@RequestMapping(value = "/query")
@SessionAttributes({"userSession"})
public class QueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/view/{requestId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("requestId") String requestId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/query/viewQueryPdf/" + requestId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/query";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Details Information for Lot in Penang");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewQueryPdf/{requestId}", method = RequestMethod.GET)
    public ModelAndView viewLotPenangPdf(
            Model model,
            @PathVariable("requestId") String requestId
    ) {
        LotPenangDAO lotPenangDAO = new LotPenangDAO();
        LotPenang lotPenang = lotPenangDAO.getLotPenangforQueryByReqId(requestId);

        LogDAO statusD = new LogDAO();
        List<Log> Log = statusD.getLogListByRequestId(requestId);
        model.addAttribute("Log", Log);

        return new ModelAndView("dotsQueryPdf", "lotPenang", lotPenang);
    }

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String rms_event,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) String device,
            @RequestParam(required = false) String packages,
            @RequestParam(required = false) String chamber,
            @RequestParam(required = false) String loadingDate,
            @RequestParam(required = false) String unloadingDate,
            @RequestParam(required = false) String shipToPenang,
            @RequestParam(required = false) String penangReceived,
            @RequestParam(required = false) String penangShipment,
            @RequestParam(required = false) String relReceived,
            @RequestParam(required = false) String gts,
            @RequestParam(required = false) String doNumber,
            @RequestParam(required = false) String status
    ) {
        String query = "";
        int count = 0;

        if (rms_event != null) {
            if (!("").equals(rms_event)) {
                count++;
                if (count == 1) {
                    query = " re.rms_event = \'" + rms_event + "\' ";
                } else if (count > 1) {
                    query = query + " AND re.rms_event = \'" + rms_event + "\' ";
                }
            }
        }

        if (rms != null) {
            if (!rms.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.rms = \'" + rms + "\' ";
                } else if (count > 1) {
                    query = query + " AND re.rms = \'" + rms + "\' ";
                }
            }
        }

        if (event != null) {
            if (!event.equals("")) {
                count++;
                if (count == 1) {
                    if ("TC AND HTSL".equals(event)) {
                        query = " re.event LIKE '%' ";
                    } else {
                        query = " re.event LIKE '" + event + "%' ";
                    }

                } else if (count > 1) {
                    if ("TC AND HTSL".equals(event)) {
                        query = query + " AND re.event LIKE '%' ";
                    } else {
                        query = query + " AND re.event LIKE '" + event + "%' ";
                    }

                }
            }
        }

        if (interval != null) {
            if (!interval.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.intervals = \'" + interval + "\' ";
                } else if (count > 1) {
                    query = query + " AND re.intervals = \'" + interval + "\' ";
                }
            }
        }

        if (device != null) {
            if (!("").equals(device)) {
                count++;
                if (count == 1) {
                    query = " re.device = \'" + device + "\' ";
                } else if (count > 1) {
                    query = query + " AND re.device = \'" + device + "\' ";
                }
            }
        }

        if (packages != null) {
            if (!packages.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.package = \'" + packages + "\' ";
                } else if (count > 1) {
                    query = query + " AND re.package = \'" + packages + "\' ";
                }
            }
        }

        if (chamber != null) {
            if (!chamber.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.chamber_id LIKE '" + chamber + "%'";
                } else if (count > 1) {
                    query = query + " AND re.chamber_id LIKE '" + chamber + "%'";
                }
            }
        }

        if (loadingDate != null) {
            if (!loadingDate.equals("")) {
                count++;
                if (count == 1) {
                    query = " lo.loading_date LIKE '" + loadingDate + "%'";
                } else if (count > 1) {
                    query = query + " AND lo.loading_date LIKE '" + loadingDate + "%'";
                }
            }
        }

        if (unloadingDate != null) {
            if (!unloadingDate.equals("")) {
                count++;
                if (count == 1) {
                    query = " lo.unloading_date LIKE '" + unloadingDate + "%'";
                } else if (count > 1) {
                    query = query + " AND lo.unloading_date LIKE '" + unloadingDate + "%'";
                }
            }
        }

        if (shipToPenang != null) {
            if (!shipToPenang.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.shipping_date LIKE '" + shipToPenang + "%'";
                } else if (count > 1) {
                    query = query + " AND lo.shipping_date LIKE '" + shipToPenang + "%'";
                }
            }
        }

        if (penangReceived != null) {
            if (!penangReceived.equals("")) {
                count++;
                if (count == 1) {
                    query = " lo.received_date LIKE '" + penangReceived + "%'";
                } else if (count > 1) {
                    query = query + " AND lo.received_date LIKE '" + penangReceived + "%'";
                }
            }
        }

        if (penangShipment != null) {
            if (!penangShipment.equals("")) {
                count++;
                if (count == 1) {
                    query = " lo.shipment_date LIKE '" + penangShipment + "%'";
                } else if (count > 1) {
                    query = query + " AND lo.shipment_date LIKE '" + penangShipment + "%'";
                }
            }
        }

        if (relReceived != null) {
            if (!relReceived.equals("")) {
                count++;
                if (count == 1) {
                    query = " lo.closed_date LIKE '" + relReceived + "%'";
                } else if (count > 1) {
                    query = query + " AND lo.closed_date LIKE '" + relReceived + "%'";
                }
            }
        }

        if (gts != null) {
            if (!gts.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.gts LIKE '" + gts + "%'";
                } else if (count > 1) {
                    query = query + " AND re.gts LIKE '" + gts + "%'";
                }
            }
        }

        if (doNumber != null) {
            if (!doNumber.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.do_number LIKE '" + doNumber + "%'";
                } else if (count > 1) {
                    query = query + " AND re.do_number LIKE '" + doNumber + "%'";
                }
            }
        }

        if (status != null) {
            if (!status.equals("")) {
                count++;
                if (count == 1) {
                    query = " re.status LIKE '" + status + "%'";
                } else if (count > 1) {
                    query = query + " AND re.status LIKE '" + status + "%'";
                }
            }
        }

        System.out.println("Query: " + query);
        LotPenangDAO wh = new LotPenangDAO();
        List<LotPenang> retrieveQueryList = wh.getLotPenangforQuery(query);
        model.addAttribute("retrieveQueryList", retrieveQueryList);
        ChamberDAO chamberD = new ChamberDAO();
        List<Chamber> chamberList = chamberD.getChamberList();
        model.addAttribute("chamberList", chamberList);
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> eventList = sDAO.getGroupParameterDetailList("", "018");
        model.addAttribute("eventList", eventList);
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> statusList = sDAO.getGroupParameterDetailList("", "019");
        model.addAttribute("statusList", statusList);

        return "lotPenang/query";
    }

}
