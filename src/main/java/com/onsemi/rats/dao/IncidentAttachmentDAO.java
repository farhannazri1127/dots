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
import com.onsemi.rats.model.IncidentAttachment;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncidentAttachmentDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentAttachmentDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IncidentAttachmentDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIncidentAttachment(IncidentAttachment incidentAttachment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_incident_attachment (incident_id, attachment, description, created_by, created_date, filename) VALUES (?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, incidentAttachment.getIncidentId());
            ps.setString(2, incidentAttachment.getAttachment());
            ps.setString(3, incidentAttachment.getDescription());
            ps.setString(4, incidentAttachment.getCreatedBy());
            ps.setString(5, incidentAttachment.getFilename());
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

    public QueryResult updateIncidentAttachment(IncidentAttachment incidentAttachment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_incident_attachment SET incident_id = ?, attachment = ?, description = ?, created_by = ?, filename = ? WHERE id = ?"
            );
            ps.setString(1, incidentAttachment.getIncidentId());
            ps.setString(2, incidentAttachment.getAttachment());
            ps.setString(3, incidentAttachment.getDescription());
            ps.setString(4, incidentAttachment.getCreatedBy());
            ps.setString(5, incidentAttachment.getFilename());
            ps.setString(6, incidentAttachment.getId());
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

    public QueryResult deleteIncidentAttachment(String incidentAttachmentId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_incident_attachment WHERE id = '" + incidentAttachmentId + "'"
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

    public IncidentAttachment getIncidentAttachment(String id) {
        String sql = "SELECT * FROM rats_incident_attachment WHERE id = '" + id + "'";
        IncidentAttachment incidentAttachment = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentAttachment = new IncidentAttachment();
                incidentAttachment.setId(rs.getString("id"));
                incidentAttachment.setIncidentId(rs.getString("incident_id"));
                incidentAttachment.setAttachment(rs.getString("attachment"));
                incidentAttachment.setDescription(rs.getString("description"));
                incidentAttachment.setCreatedBy(rs.getString("created_by"));
                incidentAttachment.setFilename(rs.getString("filename"));
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
        return incidentAttachment;
    }

    public List<IncidentAttachment> getIncidentAttachmentList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_incident_attachment ORDER BY id ASC";
        List<IncidentAttachment> incidentAttachmentList = new ArrayList<IncidentAttachment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IncidentAttachment incidentAttachment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentAttachment = new IncidentAttachment();
                incidentAttachment.setId(rs.getString("id"));
                incidentAttachment.setIncidentId(rs.getString("incident_id"));
                incidentAttachment.setAttachment(rs.getString("attachment"));
                incidentAttachment.setDescription(rs.getString("description"));
                incidentAttachment.setCreatedBy(rs.getString("created_by"));
                incidentAttachment.setCreatedDate(rs.getString("createdDate"));
                incidentAttachment.setFilename(rs.getString("filename"));
                incidentAttachmentList.add(incidentAttachment);
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
        return incidentAttachmentList;
    }

    public List<IncidentAttachment> getIncidentAttachmentListByIncidentId(String incidentId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate "
                + "FROM rats_incident_attachment WHERE incident_id = '" + incidentId + "' ORDER BY id DESC";
        List<IncidentAttachment> incidentAttachmentList = new ArrayList<IncidentAttachment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IncidentAttachment incidentAttachment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentAttachment = new IncidentAttachment();
                incidentAttachment.setId(rs.getString("id"));
                incidentAttachment.setIncidentId(rs.getString("incident_id"));
                incidentAttachment.setAttachment(rs.getString("attachment"));
                incidentAttachment.setDescription(rs.getString("description"));
                incidentAttachment.setCreatedBy(rs.getString("created_by"));
                incidentAttachment.setCreatedDate(rs.getString("createdDate"));
                incidentAttachment.setFilename(rs.getString("filename"));
                incidentAttachmentList.add(incidentAttachment);
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
        return incidentAttachmentList;
    }

    public List<IncidentAttachment> getIncidentAttachmentListById(String id) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate "
                + "FROM rats_incident_attachment WHERE id = '" + id + "' ORDER BY id DESC";
        List<IncidentAttachment> incidentAttachmentList = new ArrayList<IncidentAttachment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IncidentAttachment incidentAttachment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentAttachment = new IncidentAttachment();
                incidentAttachment.setId(rs.getString("id"));
                incidentAttachment.setIncidentId(rs.getString("incident_id"));
                incidentAttachment.setAttachment(rs.getString("attachment"));
                incidentAttachment.setDescription(rs.getString("description"));
                incidentAttachment.setCreatedBy(rs.getString("created_by"));
                incidentAttachment.setCreatedDate(rs.getString("createdDate"));
                incidentAttachment.setFilename(rs.getString("filename"));
                incidentAttachmentList.add(incidentAttachment);
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
        return incidentAttachmentList;
    }
}
