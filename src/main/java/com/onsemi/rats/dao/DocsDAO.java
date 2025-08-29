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
import com.onsemi.rats.model.Docs;
import com.onsemi.rats.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocsDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public DocsDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertDocs(Docs docs) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rats_docs (type, application, site, name, path, created_date, upload_by, flag) VALUES (?,?,?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, docs.getType());
            ps.setString(2, docs.getApplication());
            ps.setString(3, docs.getSite());
            ps.setString(4, docs.getName());
            ps.setString(5, docs.getPath());
            ps.setString(6, docs.getUploadBy());
            ps.setString(7, docs.getFlag());
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

    public QueryResult updateDocs(Docs docs) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_docs SET type = ?, application = ?, site = ?, name = ?, path = ?, created_date = ?, upload_by = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, docs.getType());
            ps.setString(2, docs.getApplication());
            ps.setString(3, docs.getSite());
            ps.setString(4, docs.getName());
            ps.setString(5, docs.getPath());
            ps.setString(6, docs.getCreatedDate());
            ps.setString(7, docs.getUploadBy());
            ps.setString(8, docs.getFlag());
            ps.setString(9, docs.getId());
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

    public QueryResult updateDocsDetail(Docs docs) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rats_docs SET name = ?, path = ? WHERE id = ?"
            );
            ps.setString(1, docs.getName());
            ps.setString(2, docs.getPath());
            ps.setString(3, docs.getId());
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

    public QueryResult deleteDocs(String docsId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rats_docs WHERE id = '" + docsId + "'"
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

    public Docs getDocs(String docsId) {
        String sql = "SELECT * FROM rats_docs WHERE id = '" + docsId + "'";
        Docs docs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                docs = new Docs();
                docs.setId(rs.getString("id"));
                docs.setType(rs.getString("type"));
                docs.setApplication(rs.getString("application"));
                docs.setSite(rs.getString("site"));
                docs.setName(rs.getString("name"));
                docs.setPath(rs.getString("path"));
                docs.setCreatedDate(rs.getString("created_date"));
                docs.setUploadBy(rs.getString("upload_by"));
                docs.setFlag(rs.getString("flag"));
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
        return docs;
    }

    public List<Docs> getDocsList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM rats_docs ORDER BY site ASC";
        List<Docs> docsList = new ArrayList<Docs>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Docs docs;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                docs = new Docs();
                docs.setId(rs.getString("id"));
                docs.setType(rs.getString("type"));
                docs.setApplication(rs.getString("application"));
                docs.setSite(rs.getString("site"));
                docs.setName(rs.getString("name"));
                docs.setPath(rs.getString("path"));
                docs.setCreatedDate(rs.getString("createdDate"));
                docs.setUploadBy(rs.getString("upload_by"));
                docs.setFlag(rs.getString("flag"));
                docsList.add(docs);
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
        return docsList;
    }
}
