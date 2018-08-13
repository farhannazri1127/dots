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
import com.onsemi.dots.model.Rma;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmaDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmaDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RmaDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRma(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_rma (request_id, rma_remarks1, rma_date1, rma_dispo1, rma_dispo1_remarks, rma_dispo1_date, rma_dispo1_by, rma_remarks2, rma_date2, rma_dispo2, rma_dispo2_remarks, rma_dispo2_date, rma_dispo2_by, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rma.getRequestId());
            ps.setString(2, rma.getRmaRemarks1());
            ps.setString(3, rma.getRmaDate1());
            ps.setString(4, rma.getRmaDispo1());
            ps.setString(5, rma.getRmaDispo1Remarks());
            ps.setString(6, rma.getRmaDispo1Date());
            ps.setString(7, rma.getRmaDispo1By());
            ps.setString(8, rma.getRmaRemarks2());
            ps.setString(9, rma.getRmaDate2());
            ps.setString(10, rma.getRmaDispo2());
            ps.setString(11, rma.getRmaDispo2Remarks());
            ps.setString(12, rma.getRmaDispo2Date());
            ps.setString(13, rma.getRmaDispo2By());
            ps.setString(14, rma.getFlag());
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

    public QueryResult insertRmaIfFlag1Csv(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_rma (request_id, rma_remarks1, rma_date1, flag, rma_count) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rma.getRequestId());
            ps.setString(2, rma.getRmaRemarks1());
            ps.setString(3, rma.getRmaDate1());
            ps.setString(4, rma.getFlag());
            ps.setString(5, rma.getRmaCount());
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

    public QueryResult insertRmaIfFlag2Csv(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_rma (request_id, rma_remarks1, rma_date1, rma_remarks2, rma_date2, flag,rma_count) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rma.getRequestId());
            ps.setString(2, rma.getRmaRemarks1());
            ps.setString(3, rma.getRmaDate1());
            ps.setString(4, rma.getRmaRemarks2());
            ps.setString(5, rma.getRmaDate2());;
            ps.setString(6, rma.getFlag());
            ps.setString(7, rma.getRmaCount());
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

    public QueryResult updateRma(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_rma SET request_id = ?, rma_remarks1 = ?, rma_date1 = ?, rma_dispo1 = ?, rma_dispo1_remarks = ?, rma_dispo1_date = ?, rma_dispo1_by = ?, rma_remarks2 = ?, rma_date2 = ?, rma_dispo2 = ?, rma_dispo2_remarks = ?, rma_dispo2_date = ?, rma_dispo2_by = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rma.getRequestId());
            ps.setString(2, rma.getRmaRemarks1());
            ps.setString(3, rma.getRmaDate1());
            ps.setString(4, rma.getRmaDispo1());
            ps.setString(5, rma.getRmaDispo1Remarks());
            ps.setString(6, rma.getRmaDispo1Date());
            ps.setString(7, rma.getRmaDispo1By());
            ps.setString(8, rma.getRmaRemarks2());
            ps.setString(9, rma.getRmaDate2());
            ps.setString(10, rma.getRmaDispo2());
            ps.setString(11, rma.getRmaDispo2Remarks());
            ps.setString(12, rma.getRmaDispo2Date());
            ps.setString(13, rma.getRmaDispo2By());
            ps.setString(14, rma.getFlag());
            ps.setString(15, rma.getId());
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

    public QueryResult updateRmaifFlag1(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_rma SET rma_dispo1 = ?, rma_dispo1_remarks = ?, rma_dispo1_date = ?, rma_dispo1_by = ?, rma_count = ? WHERE id = ?"
            );
            ps.setString(1, rma.getRmaDispo1());
            ps.setString(2, rma.getRmaDispo1Remarks());
            ps.setString(3, rma.getRmaDispo1Date());
            ps.setString(4, rma.getRmaDispo1By());
            ps.setString(5, rma.getRmaCount());
            ps.setString(6, rma.getId());
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

    public QueryResult updateRmaifFlag1Csv(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_rma SET request_id = ?, rma_remarks1 = ?, rma_date1 = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rma.getRequestId());
            ps.setString(2, rma.getRmaRemarks1());
            ps.setString(3, rma.getRmaDate1());
            ps.setString(4, rma.getFlag());
            ps.setString(5, rma.getId());
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

    public QueryResult updateRmaifFlag2Csv(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_rma SET request_id = ?, rma_remarks1 = ?, rma_date1 = ?, rma_remarks2 = ?, rma_date2 = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rma.getRequestId());
            ps.setString(2, rma.getRmaRemarks1());
            ps.setString(3, rma.getRmaDate1());
            ps.setString(4, rma.getRmaRemarks2());
            ps.setString(5, rma.getRmaDate2());
            ps.setString(6, rma.getFlag());
            ps.setString(7, rma.getId());
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

    public QueryResult updateRmaifFlag2(Rma rma) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_rma SET rma_dispo2 = ?, rma_dispo2_remarks = ?, rma_dispo2_date = ?, rma_dispo2_by = ?, rma_count = ? WHERE id = ?"
            );

            ps.setString(1, rma.getRmaDispo2());
            ps.setString(2, rma.getRmaDispo2Remarks());
            ps.setString(3, rma.getRmaDispo2Date());
            ps.setString(4, rma.getRmaDispo2By());
            ps.setString(5, rma.getRmaCount());
            ps.setString(6, rma.getId());
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

    public QueryResult deleteRma(String rmaId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_rma WHERE id = '" + rmaId + "'"
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

    public Rma getRma(String rmaId) {
        String sql = "SELECT * FROM dots_rma WHERE id = '" + rmaId + "'";
        Rma rma = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rma = new Rma();
                rma.setId(rs.getString("id"));
                rma.setRequestId(rs.getString("request_id"));
                rma.setRmaRemarks1(rs.getString("rma_remarks1"));
                rma.setRmaDate1(rs.getString("rma_date1"));
                rma.setRmaDispo1(rs.getString("rma_dispo1"));
                rma.setRmaDispo1Remarks(rs.getString("rma_dispo1_remarks"));
                rma.setRmaDispo1Date(rs.getString("rma_dispo1_date"));
                rma.setRmaDispo1By(rs.getString("rma_dispo1_by"));
                rma.setRmaRemarks2(rs.getString("rma_remarks2"));
                rma.setRmaDate2(rs.getString("rma_date2"));
                rma.setRmaDispo2(rs.getString("rma_dispo2"));
                rma.setRmaDispo2Remarks(rs.getString("rma_dispo2_remarks"));
                rma.setRmaDispo2Date(rs.getString("rma_dispo2_date"));
                rma.setRmaDispo2By(rs.getString("rma_dispo2_by"));
                rma.setFlag(rs.getString("flag"));
                rma.setRmaCount(rs.getString("rma_count"));
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
        return rma;
    }

    public Rma getRmaByRequestId(String requestId) {
        String sql = "SELECT * FROM dots_rma WHERE request_id = '" + requestId + "'";
        Rma rma = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rma = new Rma();
                rma.setId(rs.getString("id"));
                rma.setRequestId(rs.getString("request_id"));
                rma.setRmaRemarks1(rs.getString("rma_remarks1"));
                rma.setRmaDate1(rs.getString("rma_date1"));
                rma.setRmaDispo1(rs.getString("rma_dispo1"));
                rma.setRmaDispo1Remarks(rs.getString("rma_dispo1_remarks"));
                rma.setRmaDispo1Date(rs.getString("rma_dispo1_date"));
                rma.setRmaDispo1By(rs.getString("rma_dispo1_by"));
                rma.setRmaRemarks2(rs.getString("rma_remarks2"));
                rma.setRmaDate2(rs.getString("rma_date2"));
                rma.setRmaDispo2(rs.getString("rma_dispo2"));
                rma.setRmaDispo2Remarks(rs.getString("rma_dispo2_remarks"));
                rma.setRmaDispo2Date(rs.getString("rma_dispo2_date"));
                rma.setRmaDispo2By(rs.getString("rma_dispo2_by"));
                rma.setFlag(rs.getString("flag"));
                rma.setRmaCount(rs.getString("rma_count"));
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
        return rma;
    }

    public List<Rma> getRmaList() {
        String sql = "SELECT * FROM dots_rma ORDER BY id ASC";
        List<Rma> rmaList = new ArrayList<Rma>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Rma rma;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rma = new Rma();
                rma.setId(rs.getString("id"));
                rma.setRequestId(rs.getString("request_id"));
                rma.setRmaRemarks1(rs.getString("rma_remarks1"));
                rma.setRmaDate1(rs.getString("rma_date1"));
                rma.setRmaDispo1(rs.getString("rma_dispo1"));
                rma.setRmaDispo1Remarks(rs.getString("rma_dispo1_remarks"));
                rma.setRmaDispo1Date(rs.getString("rma_dispo1_date"));
                rma.setRmaDispo1By(rs.getString("rma_dispo1_by"));
                rma.setRmaRemarks2(rs.getString("rma_remarks2"));
                rma.setRmaDate2(rs.getString("rma_date2"));
                rma.setRmaDispo2(rs.getString("rma_dispo2"));
                rma.setRmaDispo2Remarks(rs.getString("rma_dispo2_remarks"));
                rma.setRmaDispo2Date(rs.getString("rma_dispo2_date"));
                rma.setRmaDispo2By(rs.getString("rma_dispo2_by"));
                rma.setFlag(rs.getString("flag"));
                rmaList.add(rma);
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
        return rmaList;
    }

    public Integer getCountRmaId(String rmaId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_rma WHERE id = '" + rmaId + "'"
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

    public Integer getCountRequestId(String requestId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_rma WHERE request_id = '" + requestId + "'"
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
