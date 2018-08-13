package com.onsemi.dots.dao;

import com.onsemi.dots.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.dots.model.Disposition;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispositionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispositionDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public DispositionDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertDisposition(Disposition disposition) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_disposition (request_id, remarks, disposition, dispo_by, dispo_date, flag) VALUES (?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, disposition.getRequestId());
            ps.setString(2, disposition.getRemarks());
            ps.setString(3, disposition.getDisposition());
            ps.setString(4, disposition.getDispoBy());
            ps.setString(5, disposition.getFlag());
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

    public QueryResult updateDisposition(Disposition disposition) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_disposition SET request_id = ?, remarks = ?, disposition = ?, dispo_by = ?, dispo_date = NOW(), flag = ? WHERE id = ?"
            );
            ps.setString(1, disposition.getRequestId());
            ps.setString(2, disposition.getRemarks());
            ps.setString(3, disposition.getDisposition());
            ps.setString(4, disposition.getDispoBy());
            ps.setString(5, disposition.getFlag());
            ps.setString(6, disposition.getId());
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

    public QueryResult deleteDisposition(String dispositionId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_disposition WHERE id = '" + dispositionId + "'"
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

    public Disposition getDisposition(String dispositionId) {
        String sql = "SELECT * FROM dots_disposition WHERE id = '" + dispositionId + "'";
        Disposition disposition = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                disposition = new Disposition();
                disposition.setId(rs.getString("id"));
                disposition.setRequestId(rs.getString("request_id"));
                disposition.setRemarks(rs.getString("remarks"));
                disposition.setDisposition(rs.getString("disposition"));
                disposition.setDispoBy(rs.getString("dispo_by"));
                disposition.setDispoDate(rs.getString("dispo_date"));
                disposition.setFlag(rs.getString("flag"));
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
        return disposition;
    }

    public List<Disposition> getDispositionList() {
        String sql = "SELECT * FROM dots_disposition ORDER BY id ASC";
        List<Disposition> dispositionList = new ArrayList<Disposition>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Disposition disposition;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                disposition = new Disposition();
                disposition.setId(rs.getString("id"));
                disposition.setRequestId(rs.getString("request_id"));
                disposition.setRemarks(rs.getString("remarks"));
                disposition.setDisposition(rs.getString("disposition"));
                disposition.setDispoBy(rs.getString("dispo_by"));
                disposition.setDispoDate(rs.getString("dispo_date"));
                disposition.setFlag(rs.getString("flag"));
                dispositionList.add(disposition);
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
        return dispositionList;
    }
}
