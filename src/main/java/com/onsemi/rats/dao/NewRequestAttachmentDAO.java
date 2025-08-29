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
import com.onsemi.rats.model.NewRequestAttachment;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewRequestAttachmentDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewRequestAttachmentDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public NewRequestAttachmentDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertNewRequestAttachment(NewRequestAttachment newRequestAttachment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_new_request_attachment (request_id, filename, attachment, description, created_by, created_date) VALUES (?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, newRequestAttachment.getRequestId());
            ps.setString(2, newRequestAttachment.getFilename());
            ps.setString(3, newRequestAttachment.getAttachment());
            ps.setString(4, newRequestAttachment.getDescription());
            ps.setString(5, newRequestAttachment.getCreatedBy());
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

    public QueryResult updateNewRequestAttachment(NewRequestAttachment newRequestAttachment) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_new_request_attachment SET request_id = ?, filename = ?, attachment = ?, description = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, newRequestAttachment.getRequestId());
            ps.setString(2, newRequestAttachment.getFilename());
            ps.setString(3, newRequestAttachment.getAttachment());
            ps.setString(4, newRequestAttachment.getDescription());
            ps.setString(5, newRequestAttachment.getCreatedBy());
            ps.setString(6, newRequestAttachment.getCreatedDate());
            ps.setString(7, newRequestAttachment.getId());
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

    public QueryResult deleteNewRequestAttachment(String newRequestAttachmentId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_new_request_attachment WHERE id = '" + newRequestAttachmentId + "'"
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

    public NewRequestAttachment getNewRequestAttachment(String newRequestAttachmentId) {
        String sql = "SELECT * FROM rats_new_request_attachment WHERE id = '" + newRequestAttachmentId + "'";
        NewRequestAttachment newRequestAttachment = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequestAttachment = new NewRequestAttachment();
                newRequestAttachment.setId(rs.getString("id"));
                newRequestAttachment.setRequestId(rs.getString("request_id"));
                newRequestAttachment.setFilename(rs.getString("filename"));
                newRequestAttachment.setAttachment(rs.getString("attachment"));
                newRequestAttachment.setDescription(rs.getString("description"));
                newRequestAttachment.setCreatedBy(rs.getString("created_by"));
                newRequestAttachment.setCreatedDate(rs.getString("created_date"));
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
        return newRequestAttachment;
    }

    public List<NewRequestAttachment> getNewRequestAttachmentList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request_attachment ORDER BY id ASC";
        List<NewRequestAttachment> newRequestAttachmentList = new ArrayList<NewRequestAttachment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequestAttachment newRequestAttachment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequestAttachment = new NewRequestAttachment();
                newRequestAttachment.setId(rs.getString("id"));
                newRequestAttachment.setRequestId(rs.getString("request_id"));
                newRequestAttachment.setFilename(rs.getString("filename"));
                newRequestAttachment.setAttachment(rs.getString("attachment"));
                newRequestAttachment.setDescription(rs.getString("description"));
                newRequestAttachment.setCreatedBy(rs.getString("created_by"));
                newRequestAttachment.setCreatedDate(rs.getString("createdDate"));
                newRequestAttachmentList.add(newRequestAttachment);
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
        return newRequestAttachmentList;
    }

    public List<NewRequestAttachment> getNewRequestAttachmentListByReqId(String requestId) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_new_request_attachment WHERE request_id = '" + requestId + "' ORDER BY id DESC";
        List<NewRequestAttachment> newRequestAttachmentList = new ArrayList<NewRequestAttachment>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            NewRequestAttachment newRequestAttachment;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newRequestAttachment = new NewRequestAttachment();
                newRequestAttachment.setId(rs.getString("id"));
                newRequestAttachment.setRequestId(rs.getString("request_id"));
                newRequestAttachment.setFilename(rs.getString("filename"));
                newRequestAttachment.setAttachment(rs.getString("attachment"));
                newRequestAttachment.setDescription(rs.getString("description"));
                newRequestAttachment.setCreatedBy(rs.getString("created_by"));
                newRequestAttachment.setCreatedDate(rs.getString("createdDate"));
                newRequestAttachmentList.add(newRequestAttachment);
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
        return newRequestAttachmentList;
    }
}
