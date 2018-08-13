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
import com.onsemi.dots.model.Chamber;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChamberDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChamberDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ChamberDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertChamber(Chamber chamber) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_chamber (event, name, remarks, created_by, created_date, modified_by, modified_date) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, chamber.getEvent());
            ps.setString(2, chamber.getName());
            ps.setString(3, chamber.getRemarks());
            ps.setString(4, chamber.getCreatedBy());
            ps.setString(5, chamber.getCreatedDate());
            ps.setString(6, chamber.getModifiedBy());
            ps.setString(7, chamber.getModifiedDate());
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

    public QueryResult updateChamber(Chamber chamber) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_chamber SET event = ?, name = ?, remarks = ?, created_by = ?, created_date = ?, modified_by = ?, modified_date = ? WHERE id = ?"
            );
            ps.setString(1, chamber.getEvent());
            ps.setString(2, chamber.getName());
            ps.setString(3, chamber.getRemarks());
            ps.setString(4, chamber.getCreatedBy());
            ps.setString(5, chamber.getCreatedDate());
            ps.setString(6, chamber.getModifiedBy());
            ps.setString(7, chamber.getModifiedDate());
            ps.setString(8, chamber.getId());
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

    public QueryResult deleteChamber(String chamberId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_chamber WHERE id = '" + chamberId + "'"
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

    public Chamber getChamber(String chamberId) {
        String sql = "SELECT * FROM dots_chamber WHERE id = '" + chamberId + "'";
        Chamber chamber = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chamber = new Chamber();
                chamber.setId(rs.getString("id"));
                chamber.setEvent(rs.getString("event"));
                chamber.setName(rs.getString("name"));
                chamber.setRemarks(rs.getString("remarks"));
                chamber.setCreatedBy(rs.getString("created_by"));
                chamber.setCreatedDate(rs.getString("created_date"));
                chamber.setModifiedBy(rs.getString("modified_by"));
                chamber.setModifiedDate(rs.getString("modified_date"));
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
        return chamber;
    }

    public List<Chamber> getChamberList() {
        String sql = "SELECT * FROM dots_chamber ORDER BY name ASC";
        List<Chamber> chamberList = new ArrayList<Chamber>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Chamber chamber;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chamber = new Chamber();
                chamber.setId(rs.getString("id"));
                chamber.setEvent(rs.getString("event"));
                chamber.setName(rs.getString("name"));
                chamber.setRemarks(rs.getString("remarks"));
                chamber.setCreatedBy(rs.getString("created_by"));
                chamber.setCreatedDate(rs.getString("created_date"));
                chamber.setModifiedBy(rs.getString("modified_by"));
                chamber.setModifiedDate(rs.getString("modified_date"));
                chamberList.add(chamber);
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
        return chamberList;
    }

    public List<Chamber> getChamberListByChamberNameAndEvent(String name, String event) {
        String sql = "SELECT *,IF(name=\"" + name + "\",\"selected=''\",\"\") AS selected FROM dots_chamber "
                + "WHERE event LIKE'" + event + "%' ORDER BY name ASC";
//          + "WHERE event = '" + event + "' ORDER BY name ASC";
        List<Chamber> chamberList = new ArrayList<Chamber>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Chamber chamber;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chamber = new Chamber();
                chamber.setId(rs.getString("id"));
                chamber.setEvent(rs.getString("event"));
                chamber.setName(rs.getString("name"));
                chamber.setRemarks(rs.getString("remarks"));
                chamber.setCreatedBy(rs.getString("created_by"));
                chamber.setCreatedDate(rs.getString("created_date"));
                chamber.setModifiedBy(rs.getString("modified_by"));
                chamber.setModifiedDate(rs.getString("modified_date"));
                chamber.setSelected(rs.getString("selected"));
                chamberList.add(chamber);
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
        return chamberList;
    }
}
