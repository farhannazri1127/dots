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
import com.onsemi.dots.model.DispoEmailList;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispoEmailListDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispoEmailListDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public DispoEmailListDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertDispoEmailList(DispoEmailList dispoEmailList) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_dispo_email_list (dist_list, oncid, name, email, remarks, flag) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, dispoEmailList.getDistList());
            ps.setString(2, dispoEmailList.getOncid());
            ps.setString(3, dispoEmailList.getName());
            ps.setString(4, dispoEmailList.getEmail());
            ps.setString(5, dispoEmailList.getRemarks());
            ps.setString(6, dispoEmailList.getFlag());
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

    public QueryResult updateDispoEmailList(DispoEmailList dispoEmailList) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_dispo_email_list SET dist_list = ?, oncid = ?, name = ?, email = ?, remarks = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, dispoEmailList.getDistList());
            ps.setString(2, dispoEmailList.getOncid());
            ps.setString(3, dispoEmailList.getName());
            ps.setString(4, dispoEmailList.getEmail());
            ps.setString(5, dispoEmailList.getRemarks());
            ps.setString(6, dispoEmailList.getFlag());
            ps.setString(7, dispoEmailList.getId());
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

    public QueryResult deleteDispoEmailList(String dispoEmailListId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_dispo_email_list WHERE id = '" + dispoEmailListId + "'"
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

    public DispoEmailList getDispoEmailList(String dispoEmailListId) {
        String sql = "SELECT * FROM dots_dispo_email_list WHERE id = '" + dispoEmailListId + "'";
        DispoEmailList dispoEmailList = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dispoEmailList = new DispoEmailList();
                dispoEmailList.setId(rs.getString("id"));
                dispoEmailList.setDistList(rs.getString("dist_list"));
                dispoEmailList.setOncid(rs.getString("oncid"));
                dispoEmailList.setName(rs.getString("name"));
                dispoEmailList.setEmail(rs.getString("email"));
                dispoEmailList.setRemarks(rs.getString("remarks"));
                dispoEmailList.setFlag(rs.getString("flag"));
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
        return dispoEmailList;
    }

    public List<DispoEmailList> getDispoEmailListList() {
        String sql = "SELECT * FROM dots_dispo_email_list ORDER BY id ASC";
        List<DispoEmailList> dispoEmailListList = new ArrayList<DispoEmailList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            DispoEmailList dispoEmailList;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dispoEmailList = new DispoEmailList();
                dispoEmailList.setId(rs.getString("id"));
                dispoEmailList.setDistList(rs.getString("dist_list"));
                dispoEmailList.setOncid(rs.getString("oncid"));
                dispoEmailList.setName(rs.getString("name"));
                dispoEmailList.setEmail(rs.getString("email"));
                dispoEmailList.setRemarks(rs.getString("remarks"));
                dispoEmailList.setFlag(rs.getString("flag"));
                dispoEmailListList.add(dispoEmailList);
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
        return dispoEmailListList;
    }

    public List<DispoEmailList> getDispoEmailListOnly() {
        String sql = "SELECT email FROM dots_dispo_email_list ORDER BY id ASC";
        List<DispoEmailList> dispoEmailListList = new ArrayList<DispoEmailList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            DispoEmailList dispoEmailList;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dispoEmailList = new DispoEmailList();
                dispoEmailList.setEmail(rs.getString("email"));
                dispoEmailListList.add(dispoEmailList);
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
        return dispoEmailListList;
    }

    public List<DispoEmailList> getDispoEmailListApproverOnly() {
        String sql = "SELECT email FROM dots_dispo_email_list "
                + "WHERE dist_list LIKE '%approver%' "
                + "ORDER BY id ASC";
        List<DispoEmailList> dispoEmailListList = new ArrayList<DispoEmailList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            DispoEmailList dispoEmailList;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dispoEmailList = new DispoEmailList();
                dispoEmailList.setEmail(rs.getString("email"));
                dispoEmailListList.add(dispoEmailList);
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
        return dispoEmailListList;
    }
    
    public List<DispoEmailList> getDispoEmailListManagementOnly() {
        String sql = "SELECT email FROM dots_dispo_email_list "
                + "WHERE dist_list LIKE '%Management%' "
                + "ORDER BY id ASC";
        List<DispoEmailList> dispoEmailListList = new ArrayList<DispoEmailList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            DispoEmailList dispoEmailList;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dispoEmailList = new DispoEmailList();
                dispoEmailList.setEmail(rs.getString("email"));
                dispoEmailListList.add(dispoEmailList);
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
        return dispoEmailListList;
    }

    public Integer getCountTask(String job) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_dispo_email_list WHERE dist_list = '" + job + "'"
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
}
