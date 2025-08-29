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
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncidentDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IncidentDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIncident(Incident incident) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_incident (category, sub_category, title, description, urgency, user, site, assignee, status, created_date, flag, ticket_no, user_email, user_id, cc) VALUES (?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, incident.getCategory());
            ps.setString(2, incident.getSubCategory());
            ps.setString(3, incident.getTitle());
            ps.setString(4, incident.getDescription());
            ps.setString(5, incident.getUrgency());
            ps.setString(6, incident.getUser());
            ps.setString(7, incident.getSite());
            ps.setString(8, incident.getAssignee());
            ps.setString(9, incident.getStatus());
            ps.setString(10, incident.getFlag());
            ps.setString(11, incident.getTicketNo());
            ps.setString(12, incident.getUserEmail());
            ps.setString(13, incident.getUserId());
            ps.setString(14, incident.getCc());
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
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
        return queryResult;
    }

    public QueryResult updateIncident(Incident incident) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_incident SET category = ?, sub_category = ?, title = ?, description = ?, urgency = ?, user = ?, site = ?, assignee = ?, status = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, incident.getCategory());
            ps.setString(2, incident.getSubCategory());
            ps.setString(3, incident.getTitle());
            ps.setString(4, incident.getDescription());
            ps.setString(5, incident.getUrgency());
            ps.setString(6, incident.getUser());
            ps.setString(7, incident.getSite());
            ps.setString(8, incident.getAssignee());
            ps.setString(9, incident.getStatus());
            ps.setString(10, incident.getCreatedDate());
            ps.setString(11, incident.getFlag());
            ps.setString(12, incident.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
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
        return queryResult;
    }

    public QueryResult updateIncidentLatest(Incident incident) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_incident SET assignee = ?, eta = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, incident.getAssignee());
            ps.setString(2, incident.getEta());
            ps.setString(3, incident.getStatus());
            ps.setString(4, incident.getFlag());
            ps.setString(5, incident.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
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
        return queryResult;
    }

    public QueryResult updateIncidentNo(Incident incident) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_incident SET ticket_no = ? WHERE id = ?"
            );
            ps.setString(1, incident.getTicketNo());
            ps.setString(2, incident.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
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
        return queryResult;
    }

    public QueryResult updatePicAndEta(Incident incident) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_incident SET assignee = ?, eta = ? WHERE id = ?"
            );
            ps.setString(1, incident.getAssignee());
            ps.setString(2, incident.getEta());
            ps.setString(3, incident.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
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
        return queryResult;
    }

    public QueryResult deleteIncident(String incidentId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_incident WHERE id = '" + incidentId + "'"
            );
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
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
        return queryResult;
    }

    public Incident getIncident(String incidentId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident WHERE id = '" + incidentId + "'";
        Incident incident = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
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
                incident.setTicketNo(rs.getString("ticket_no"));
                incident.setEta(rs.getString("eta"));
                incident.setUserEmail(rs.getString("user_email"));
                incident.setUserId(rs.getString("user_id"));
                incident.setCc(rs.getString("cc"));
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
        return incident;
    }

    public List<Incident> getIncidentList() {
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
                incident.setCc(rs.getString("cc"));
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

    public List<Incident> getIncidentListByUser(String userId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident WHERE user_id = '" + userId + "' ORDER BY id DESC";
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
                incident.setCc(rs.getString("cc"));
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
}
