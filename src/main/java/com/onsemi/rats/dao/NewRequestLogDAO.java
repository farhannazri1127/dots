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
import com.onsemi.rats.model.NewRequestLog;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewRequestLogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewRequestLogDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public NewRequestLogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertNewRequestLog(NewRequestLog newRequestLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_new_request_log (request_id, status, description, status_date, module, created_by) VALUES (?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, newRequestLog.getRequestId());
            ps.setString(2, newRequestLog.getStatus());
            ps.setString(3, newRequestLog.getDescription());
            ps.setString(4, newRequestLog.getModule());
            ps.setString(5, newRequestLog.getCreatedBy());
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

    public QueryResult updateNewRequestLog(NewRequestLog newRequestLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_new_request_log SET request_id = ?, status = ?, description = ?, status_date = ?, module = ?, created_by = ? WHERE id = ?"
            );
            ps.setString(1, newRequestLog.getRequestId());
            ps.setString(2, newRequestLog.getStatus());
            ps.setString(3, newRequestLog.getDescription());
            ps.setString(4, newRequestLog.getStatusDate());
            ps.setString(5, newRequestLog.getModule());
            ps.setString(6, newRequestLog.getCreatedBy());
            ps.setString(7, newRequestLog.getId());
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

    public QueryResult deleteNewRequestLog(String newRequestLogId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_new_request_log WHERE id = '" + newRequestLogId + "'"
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

    public NewRequestLog getNewRequestLog(String newRequestLogId) {
        String sql = "SELECT * FROM rats_new_request_log WHERE id = '" + newRequestLogId + "'";
        NewRequestLog newRequestLog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequestLog = new NewRequestLog();
                newRequestLog.setId(rs.getString("id"));
                newRequestLog.setRequestId(rs.getString("request_id"));
                newRequestLog.setStatus(rs.getString("status"));
                newRequestLog.setDescription(rs.getString("description"));
                newRequestLog.setStatusDate(rs.getString("status_date"));
                newRequestLog.setModule(rs.getString("module"));
                newRequestLog.setCreatedBy(rs.getString("created_by"));
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
        return newRequestLog;
    }

    public List<NewRequestLog> getNewRequestLogList() {
        String sql = "SELECT * FROM rats_new_request_log ORDER BY id ASC";
        List<NewRequestLog> newRequestLogList = new ArrayList<NewRequestLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequestLog newRequestLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequestLog = new NewRequestLog();
                newRequestLog.setId(rs.getString("id"));
                newRequestLog.setRequestId(rs.getString("request_id"));
                newRequestLog.setStatus(rs.getString("status"));
                newRequestLog.setDescription(rs.getString("description"));
                newRequestLog.setStatusDate(rs.getString("status_date"));
                newRequestLog.setModule(rs.getString("module"));
                newRequestLog.setCreatedBy(rs.getString("created_by"));
                newRequestLogList.add(newRequestLog);
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
        return newRequestLogList;
    }

    public List<NewRequestLog> getNewRequestLogListByReqId(String requestId) {
        String sql = "SELECT *,DATE_FORMAT(status_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request_log WHERE request_id = '" + requestId + "' ORDER BY id DESC";
        List<NewRequestLog> newRequestLogList = new ArrayList<NewRequestLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequestLog newRequestLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequestLog = new NewRequestLog();
                newRequestLog.setId(rs.getString("id"));
                newRequestLog.setRequestId(rs.getString("request_id"));
                newRequestLog.setStatus(rs.getString("status"));
                newRequestLog.setDescription(rs.getString("description"));
                newRequestLog.setStatusDate(rs.getString("createdDate"));
                newRequestLog.setModule(rs.getString("module"));
                newRequestLog.setCreatedBy(rs.getString("created_by"));
                newRequestLogList.add(newRequestLog);
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
        return newRequestLogList;
    }
}
