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
import com.onsemi.rats.model.NewRequest;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewRequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewRequestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public NewRequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertNewRequest(NewRequest newRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_new_request (ticket_no, category, sub_category, title, description, urgency, user, site, user_id, user_email, assignee, eta, status, created_date, flag, cc) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, newRequest.getTicketNo());
            ps.setString(2, newRequest.getCategory());
            ps.setString(3, newRequest.getSubCategory());
            ps.setString(4, newRequest.getTitle());
            ps.setString(5, newRequest.getDescription());
            ps.setString(6, newRequest.getUrgency());
            ps.setString(7, newRequest.getUser());
            ps.setString(8, newRequest.getSite());
            ps.setString(9, newRequest.getUserId());
            ps.setString(10, newRequest.getUserEmail());
            ps.setString(11, newRequest.getAssignee());
            ps.setString(12, newRequest.getEta());
            ps.setString(13, newRequest.getStatus());
            ps.setString(14, newRequest.getFlag());
            ps.setString(15, newRequest.getCc());
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

    public QueryResult updateNewRequest(NewRequest newRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_new_request SET ticket_no = ?, category = ?, sub_category = ?, title = ?, description = ?, urgency = ?, user = ?, site = ?, user_id = ?, user_email = ?, assignee = ?, eta = ?, status = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, newRequest.getTicketNo());
            ps.setString(2, newRequest.getCategory());
            ps.setString(3, newRequest.getSubCategory());
            ps.setString(4, newRequest.getTitle());
            ps.setString(5, newRequest.getDescription());
            ps.setString(6, newRequest.getUrgency());
            ps.setString(7, newRequest.getUser());
            ps.setString(8, newRequest.getSite());
            ps.setString(9, newRequest.getUserId());
            ps.setString(10, newRequest.getUserEmail());
            ps.setString(11, newRequest.getAssignee());
            ps.setString(12, newRequest.getEta());
            ps.setString(13, newRequest.getStatus());
            ps.setString(14, newRequest.getCreatedDate());
            ps.setString(15, newRequest.getFlag());
            ps.setString(16, newRequest.getId());
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

    public QueryResult updateNewRequestLatest(NewRequest newRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_new_request SET assignee = ?, eta = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, newRequest.getAssignee());
            ps.setString(2, newRequest.getEta());
            ps.setString(3, newRequest.getStatus());
            ps.setString(4, newRequest.getFlag());
            ps.setString(5, newRequest.getId());
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

    public QueryResult updateRequestNo(NewRequest newRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_new_request SET ticket_no = ? WHERE id = ?"
            );
            ps.setString(1, newRequest.getTicketNo());
            ps.setString(2, newRequest.getId());
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

    public QueryResult updatePicAndEta(NewRequest newRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_new_request SET assignee = ?, eta = ? WHERE id = ?"
            );
            ps.setString(1, newRequest.getAssignee());
            ps.setString(2, newRequest.getEta());
            ps.setString(3, newRequest.getId());
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

    public QueryResult deleteNewRequest(String newRequestId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_new_request WHERE id = '" + newRequestId + "'"
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

    public NewRequest getNewRequest(String newRequestId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request WHERE id = '" + newRequestId + "'";
        NewRequest newRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setCc(rs.getString("cc"));
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
        return newRequest;
    }

    public List<NewRequest> getNewRequestList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request ORDER BY id DESC";
        List<NewRequest> newRequestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setCc(rs.getString("cc"));
                newRequestList.add(newRequest);
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
        return newRequestList;
    }

    public List<NewRequest> getNewRequestListByUserId(String userId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request WHERE user_id = '" + userId + "' ORDER BY id DESC";
        List<NewRequest> newRequestList = new ArrayList<NewRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequest newRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequest = new NewRequest();
                newRequest.setId(rs.getString("id"));
                newRequest.setTicketNo(rs.getString("ticket_no"));
                newRequest.setCategory(rs.getString("category"));
                newRequest.setSubCategory(rs.getString("sub_category"));
                newRequest.setTitle(rs.getString("title"));
                newRequest.setDescription(rs.getString("description"));
                newRequest.setUrgency(rs.getString("urgency"));
                newRequest.setUser(rs.getString("user"));
                newRequest.setSite(rs.getString("site"));
                newRequest.setUserId(rs.getString("user_id"));
                newRequest.setUserEmail(rs.getString("user_email"));
                newRequest.setAssignee(rs.getString("assignee"));
                newRequest.setEta(rs.getString("eta"));
                newRequest.setStatus(rs.getString("status"));
                newRequest.setCreatedDate(rs.getString("createdDate"));
                newRequest.setFlag(rs.getString("flag"));
                newRequest.setCc(rs.getString("cc"));
                newRequestList.add(newRequest);
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
        return newRequestList;
    }
}
