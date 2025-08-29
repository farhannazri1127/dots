package com.onsemi.rats.dao;

import com.onsemi.rats.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.rats.model.Incident;
import com.onsemi.rats.model.NewRequest;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public DashboardDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public Integer getCountStatusIncidentTable(String status) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc WHERE inc.`status` = '" + status + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountStatusRequestTable(String status) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request req WHERE req.`status` = '" + status + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountByPicRequestTable(String pic) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request req WHERE req.assignee = '" + pic + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountByPicCompleteRequestTable(String pic) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request req WHERE req.assignee = '" + pic + "' AND req.flag = '1'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountByPicIncidentTable(String pic) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc WHERE inc.assignee = '" + pic + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountByPicCompleteIncidentTable(String pic) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc WHERE inc.assignee = '" + pic + "' AND inc.flag = '1'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountTotalIncidentTable() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountCompleteTotalIncidentTable() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc WHERE inc.flag = '1'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountTotalRequestTable() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request req"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountCompleteTotalRequestTable() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request req WHERE req.flag = '1'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountTotalIncidentTableByYearMonth(String year, String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc WHERE YEAR(inc.created_date) = '" + year + "' AND MONTH(inc.created_date) = '" + month + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountTotalIncidentTableByYearMonthComplete(String year, String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_incident inc "
                    + "WHERE YEAR(inc.created_date) = '" + year + "' AND MONTH(inc.created_date) = '" + month + "' "
                    + "AND inc.flag = '1'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountTotalRequestTableByYearMonth(String year, String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request inc WHERE YEAR(inc.created_date) = '" + year + "' AND MONTH(inc.created_date) = '" + month + "'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountTotalRequestTableByYearMonthComplete(String year, String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rats_new_request inc "
                    + "WHERE YEAR(inc.created_date) = '" + year + "' AND MONTH(inc.created_date) = '" + month + "' "
                    + "AND inc.flag = '1'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public List<Incident> getIncidentListByStatus(String status) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident WHERE status = '" + status + "' ORDER BY id DESC";
        List<Incident> incidentList = new ArrayList<Incident>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Incident incident;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incident = new Incident();
                incident.setId(rs.getString("id"));
                incident.setCategory(rs.getString("category"));
                incident.setSubCategory(rs.getString("sub_category"));
                incident.setTitle(rs.getString("title"));
                incident.setDescription(rs.getString("description"));
                incident.setUrgency(rs.getString("urgency"));
                incident.setUser(rs.getString("user"));
                incident.setSite(rs.getString("site"));
                incident.setAssignee(rs.getString("assignee"));
                incident.setStatus(rs.getString("status"));
                incident.setCreatedDate(rs.getString("createdDate"));
                incident.setFlag(rs.getString("flag"));
                incident.setEta(rs.getString("eta"));
                incident.setUserEmail(rs.getString("user_email"));
                incident.setUserId(rs.getString("user_id"));
                incident.setTicketNo(rs.getString("ticket_no"));
                incidentList.add(incident);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return incidentList;
    }

    public List<NewRequest> getRequestListByStatus(String status) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request WHERE status = '" + status + "' ORDER BY id DESC";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public List<Incident> getIncidentListByFlag(String flag) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident WHERE flag = '" + flag + "' ORDER BY id DESC";
        List<Incident> incidentList = new ArrayList<Incident>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Incident incident;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incident = new Incident();
                incident.setId(rs.getString("id"));
                incident.setCategory(rs.getString("category"));
                incident.setSubCategory(rs.getString("sub_category"));
                incident.setTitle(rs.getString("title"));
                incident.setDescription(rs.getString("description"));
                incident.setUrgency(rs.getString("urgency"));
                incident.setUser(rs.getString("user"));
                incident.setSite(rs.getString("site"));
                incident.setAssignee(rs.getString("assignee"));
                incident.setStatus(rs.getString("status"));
                incident.setCreatedDate(rs.getString("createdDate"));
                incident.setFlag(rs.getString("flag"));
                incident.setEta(rs.getString("eta"));
                incident.setUserEmail(rs.getString("user_email"));
                incident.setUserId(rs.getString("user_id"));
                incident.setTicketNo(rs.getString("ticket_no"));
                incidentList.add(incident);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return incidentList;
    }

    public List<NewRequest> getRequestListByFlag(String flag) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request WHERE flag = '" + flag + "' ORDER BY id DESC";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public List<Incident> getIncidentListByPic(String pic) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident WHERE assignee = '" + pic + "' ORDER BY id DESC";
        List<Incident> incidentList = new ArrayList<Incident>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Incident incident;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incident = new Incident();
                incident.setId(rs.getString("id"));
                incident.setCategory(rs.getString("category"));
                incident.setSubCategory(rs.getString("sub_category"));
                incident.setTitle(rs.getString("title"));
                incident.setDescription(rs.getString("description"));
                incident.setUrgency(rs.getString("urgency"));
                incident.setUser(rs.getString("user"));
                incident.setSite(rs.getString("site"));
                incident.setAssignee(rs.getString("assignee"));
                incident.setStatus(rs.getString("status"));
                incident.setCreatedDate(rs.getString("createdDate"));
                incident.setFlag(rs.getString("flag"));
                incident.setEta(rs.getString("eta"));
                incident.setUserEmail(rs.getString("user_email"));
                incident.setUserId(rs.getString("user_id"));
                incident.setTicketNo(rs.getString("ticket_no"));
                incidentList.add(incident);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return incidentList;
    }

    public List<NewRequest> getRequestListByPic(String pic) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request WHERE assignee = '" + pic + "' ORDER BY id DESC";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public List<Incident> getIncidentListTotal() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident ORDER BY id DESC";
        List<Incident> incidentList = new ArrayList<Incident>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Incident incident;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incident = new Incident();
                incident.setId(rs.getString("id"));
                incident.setCategory(rs.getString("category"));
                incident.setSubCategory(rs.getString("sub_category"));
                incident.setTitle(rs.getString("title"));
                incident.setDescription(rs.getString("description"));
                incident.setUrgency(rs.getString("urgency"));
                incident.setUser(rs.getString("user"));
                incident.setSite(rs.getString("site"));
                incident.setAssignee(rs.getString("assignee"));
                incident.setStatus(rs.getString("status"));
                incident.setCreatedDate(rs.getString("createdDate"));
                incident.setFlag(rs.getString("flag"));
                incident.setEta(rs.getString("eta"));
                incident.setUserEmail(rs.getString("user_email"));
                incident.setUserId(rs.getString("user_id"));
                incident.setTicketNo(rs.getString("ticket_no"));
                incidentList.add(incident);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return incidentList;
    }

    public List<NewRequest> getRequestListTotal() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request ORDER BY id DESC";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public List<NewRequest> getQuery(String query) {
        String sql = query;
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                newRequest.setTicket(rs.getString("ticket"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public List<NewRequest> getUserForQuery() {
        String sql = "SELECT user FROM rats_incident UNION SELECT user FROM rats_new_request ORDER BY user";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setUser(rs.getString("user"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public List<NewRequest> getSiteForQuery() {
        String sql = "SELECT site FROM rats_incident UNION SELECT site FROM rats_new_request ORDER BY site";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setSite(rs.getString("site"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

    public Integer getCountOverdueEtaRequest() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as count FROM rats_new_request re WHERE re.eta <= NOW() AND re.flag = '0'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public Integer getCountOverdueEtaIncident() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as count FROM rats_incident re WHERE re.eta <= NOW() AND re.flag = '0'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public List<NewRequest> getListOverdueETA() {
        String sql = "SELECT *, SUBSTRING(inc.ticket_no,1,1) AS ticket, DATE_FORMAT(inc.created_date,'%d-%M-%Y %h:%i %p') AS createdDate "
                + "FROM rats_incident inc WHERE inc.eta <= NOW() AND inc.flag = '0' "
                + "UNION ALL "
                + "SELECT *, SUBSTRING(inc.ticket_no,1,1) AS ticket,DATE_FORMAT(inc.created_date,'%d-%M-%Y %h:%i %p') AS createdDate "
                + "FROM rats_new_request inc WHERE inc.eta <= NOW() AND inc.flag = '0' ORDER BY eta";
        List<NewRequest> requestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                newRequest.setTicket(rs.getString("ticket"));
                requestList.add(newRequest);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return requestList;
    }

}
