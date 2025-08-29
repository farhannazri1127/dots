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
import com.onsemi.rats.model.IncidentLog;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncidentLogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentLogDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IncidentLogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertIncidentLog(IncidentLog incidentLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_incident_log (incident_id, status, description, status_date, module, created_by) VALUES (?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, incidentLog.getIncidentId());
            ps.setString(2, incidentLog.getStatus());
            ps.setString(3, incidentLog.getDescription());
            ps.setString(4, incidentLog.getModule());
            ps.setString(5, incidentLog.getCreatedBy());
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

    public QueryResult updateIncidentLog(IncidentLog incidentLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_incident_log SET incident_id = ?, status = ?, description = ?, status_date = ?, module = ?, created_by = ? WHERE id = ?"
            );
            ps.setString(1, incidentLog.getIncidentId());
            ps.setString(2, incidentLog.getStatus());
            ps.setString(3, incidentLog.getDescription());
            ps.setString(4, incidentLog.getStatusDate());
            ps.setString(5, incidentLog.getModule());
            ps.setString(6, incidentLog.getCreatedBy());
            ps.setString(7, incidentLog.getId());
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

    public QueryResult deleteIncidentLog(String incidentLogId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_incident_log WHERE id = '" + incidentLogId + "'"
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

    public IncidentLog getIncidentLog(String incidentLogId) {
        String sql = "SELECT * FROM rats_incident_log WHERE id = '" + incidentLogId + "'";
        IncidentLog incidentLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentLog = new IncidentLog();
                incidentLog.setId(rs.getString("id"));
                incidentLog.setIncidentId(rs.getString("incident_id"));
                incidentLog.setStatus(rs.getString("status"));
                incidentLog.setDescription(rs.getString("description"));
                incidentLog.setStatusDate(rs.getString("status_date"));
                incidentLog.setModule(rs.getString("module"));
                incidentLog.setCreatedBy(rs.getString("created_by"));
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
        return incidentLog;
    }

    public List<IncidentLog> getIncidentLogList() {
        String sql = "SELECT *, DATE_FORMAT(status_date,'%d %M %Y %h:%i %p') AS statusDate FROM rats_incident_log ORDER BY id ASC";
        List<IncidentLog> incidentLogList = new ArrayList<IncidentLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IncidentLog incidentLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentLog = new IncidentLog();
                incidentLog.setId(rs.getString("id"));
                incidentLog.setIncidentId(rs.getString("incident_id"));
                incidentLog.setStatus(rs.getString("status"));
                incidentLog.setDescription(rs.getString("description"));
                incidentLog.setStatusDate(rs.getString("statusDate"));
                incidentLog.setModule(rs.getString("module"));
                incidentLog.setCreatedBy(rs.getString("created_by"));
                incidentLogList.add(incidentLog);
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
        return incidentLogList;
    }

    public List<IncidentLog> getIncidentLogListByIncidentId(String incidentId) {
        String sql = "SELECT *, DATE_FORMAT(status_date,'%d %M %Y %h:%i %p') AS statusDate "
                + "FROM rats_incident_log WHERE incident_id = '" + incidentId + "' ORDER BY id DESC";
        List<IncidentLog> incidentLogList = new ArrayList<IncidentLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IncidentLog incidentLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incidentLog = new IncidentLog();
                incidentLog.setId(rs.getString("id"));
                incidentLog.setIncidentId(rs.getString("incident_id"));
                incidentLog.setStatus(rs.getString("status"));
                incidentLog.setDescription(rs.getString("description"));
                incidentLog.setStatusDate(rs.getString("statusDate"));
                incidentLog.setModule(rs.getString("module"));
                incidentLog.setCreatedBy(rs.getString("created_by"));
                incidentLogList.add(incidentLog);
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
        return incidentLogList;
    }
}
