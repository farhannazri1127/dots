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
import com.onsemi.rats.model.Log;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public LogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertLog(Log log) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_log (request_id, status, status_date, module, created_by) VALUES (?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, log.getRequestId());
            ps.setString(2, log.getStatus());
            ps.setString(3, log.getModule());
            ps.setString(4, log.getCreatedBy());
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

    public QueryResult updateLog(Log log) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_log SET request_id = ?, status = ?, status_date = ?, module = ?, created_by = ? WHERE id = ?"
            );
            ps.setString(1, log.getRequestId());
            ps.setString(2, log.getStatus());
            ps.setString(3, log.getStatusDate());
            ps.setString(4, log.getModule());
            ps.setString(5, log.getCreatedBy());
            ps.setString(6, log.getId());
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

    public QueryResult deleteLog(String logId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_log WHERE id = '" + logId + "'"
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

    public Log getLog(String logId) {
        String sql = "SELECT * FROM dots_log WHERE id = '" + logId + "'";
        Log log = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setStatus(rs.getString("status"));
                log.setStatusDate(rs.getString("status_date"));
                log.setModule(rs.getString("module"));
                log.setCreatedBy(rs.getString("created_by"));
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
        return log;
    }
    
    public Log getLogByRequestId(String requestId) {
        String sql = "SELECT * FROM dots_log WHERE request_id = '" + requestId + "'";
        Log log = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setStatus(rs.getString("status"));
                log.setStatusDate(rs.getString("status_date"));
                log.setModule(rs.getString("module"));
                log.setCreatedBy(rs.getString("created_by"));
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
        return log;
    }

    public List<Log> getLogList() {
        String sql = "SELECT * FROM dots_log ORDER BY id ASC";
        List<Log> logList = new ArrayList<Log>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Log log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setStatus(rs.getString("status"));
                log.setStatusDate(rs.getString("status_date"));
                log.setModule(rs.getString("module"));
                log.setCreatedBy(rs.getString("created_by"));
                logList.add(log);
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
        return logList;
    }
    
    public List<Log> getLogListByRequestId(String requestId) {
        String sql = "SELECT * FROM dots_log WHERE request_id = '" + requestId + "' ORDER BY status_date DESC";
        List<Log> logList = new ArrayList<Log>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Log log;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setId(rs.getString("id"));
                log.setRequestId(rs.getString("request_id"));
                log.setStatus(rs.getString("status"));
                log.setStatusDate(rs.getString("status_date"));
                log.setModule(rs.getString("module"));
                log.setCreatedBy(rs.getString("created_by"));
                logList.add(log);
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
        return logList;
    }
}
