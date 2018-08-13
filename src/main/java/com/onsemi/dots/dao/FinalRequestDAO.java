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
import com.onsemi.dots.model.FinalRequest;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinalRequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinalRequestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public FinalRequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertFinalRequest(FinalRequest finalRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_final_request (request_id, gts, status, shipping_date, created_by, created_date, modified_by, modified_date, flag) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, finalRequest.getRequestId());
            ps.setString(2, finalRequest.getGts());
            ps.setString(3, finalRequest.getStatus());
            ps.setString(4, finalRequest.getShippingDate());
            ps.setString(5, finalRequest.getCreatedBy());
            ps.setString(6, finalRequest.getCreatedDate());
            ps.setString(7, finalRequest.getModifiedBy());
            ps.setString(8, finalRequest.getModifiedDate());
            ps.setString(9, finalRequest.getFlag());
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

    public QueryResult insertFinalRequestFromRequest(FinalRequest finalRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_final_request (request_id, status, created_by, created_date, flag) VALUES (?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, finalRequest.getRequestId());
            ps.setString(2, finalRequest.getStatus());
            ps.setString(3, finalRequest.getCreatedBy());
            ps.setString(4, finalRequest.getFlag());
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

    public QueryResult updateFinalRequest(FinalRequest finalRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_final_request SET request_id = ?, gts = ?, status = ?, shipping_date = ?, created_by = ?, created_date = ?, modified_by = ?, modified_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, finalRequest.getRequestId());
            ps.setString(2, finalRequest.getGts());
            ps.setString(3, finalRequest.getStatus());
            ps.setString(4, finalRequest.getShippingDate());
            ps.setString(5, finalRequest.getCreatedBy());
            ps.setString(6, finalRequest.getCreatedDate());
            ps.setString(7, finalRequest.getModifiedBy());
            ps.setString(8, finalRequest.getModifiedDate());
            ps.setString(9, finalRequest.getFlag());
            ps.setString(10, finalRequest.getId());
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

    public QueryResult updateFinalRequestGtsAndShipDate(FinalRequest finalRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_final_request SET gts = ?, status = ?, shipping_date = ?, modified_by = ?, modified_date = NOW() WHERE flag = '0'"
            );
            ps.setString(1, finalRequest.getGts());
            ps.setString(2, finalRequest.getStatus());
            ps.setString(3, finalRequest.getShippingDate());
            ps.setString(4, finalRequest.getModifiedBy());
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

    public QueryResult updateFinalRequestDoNumber(FinalRequest finalRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_final_request SET do_number = ? WHERE id = ? AND flag = '0'"
            );
            ps.setString(1, finalRequest.getDoNumber());
            ps.setString(2, finalRequest.getId());
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

    public QueryResult updateFinalRequestAndRequestTableShipPenangFlag1() {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_final_request f, dots_request r SET f.status = 'Ship to Penang', f.flag = '1', r.`status` = 'Ship to Penang' WHERE r.id = f.request_id"
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

    public QueryResult deleteFinalRequest(String finalRequestId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_final_request WHERE id = '" + finalRequestId + "'"
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

    public QueryResult deleteFinalRequestAfterShip() {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_final_request WHERE flag = '1'"
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

    public FinalRequest getFinalRequest(String finalRequestId) {
        String sql = "SELECT * FROM dots_final_request WHERE id = '" + finalRequestId + "'";
        FinalRequest finalRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                finalRequest = new FinalRequest();
                finalRequest.setId(rs.getString("id"));
                finalRequest.setRequestId(rs.getString("request_id"));
                finalRequest.setGts(rs.getString("gts"));
                finalRequest.setStatus(rs.getString("status"));
                finalRequest.setShippingDate(rs.getString("shipping_date"));
                finalRequest.setCreatedBy(rs.getString("created_by"));
                finalRequest.setCreatedDate(rs.getString("created_date"));
                finalRequest.setModifiedBy(rs.getString("modified_by"));
                finalRequest.setModifiedDate(rs.getString("modified_date"));
                finalRequest.setFlag(rs.getString("flag"));
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
        return finalRequest;
    }

    public List<FinalRequest> getFinalRequestList() {
        String sql = "SELECT * FROM dots_final_request ORDER BY id ASC";
        List<FinalRequest> finalRequestList = new ArrayList<FinalRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FinalRequest finalRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                finalRequest = new FinalRequest();
                finalRequest.setId(rs.getString("id"));
                finalRequest.setRequestId(rs.getString("request_id"));
                finalRequest.setGts(rs.getString("gts"));
                finalRequest.setStatus(rs.getString("status"));
                finalRequest.setShippingDate(rs.getString("shipping_date"));
                finalRequest.setCreatedBy(rs.getString("created_by"));
                finalRequest.setCreatedDate(rs.getString("created_date"));
                finalRequest.setModifiedBy(rs.getString("modified_by"));
                finalRequest.setModifiedDate(rs.getString("modified_date"));
                finalRequest.setFlag(rs.getString("flag"));
                finalRequestList.add(finalRequest);
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
        return finalRequestList;
    }

    public List<FinalRequest> getFinalRequestListDoNumberNotLikeFlag0(String date) {
        String sql = "SELECT * FROM dots_final_request WHERE flag = '0' AND (do_number NOT LIKE '" + date + "%' OR do_number IS NULL) ORDER BY id ASC";
        List<FinalRequest> finalRequestList = new ArrayList<FinalRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FinalRequest finalRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                finalRequest = new FinalRequest();
                finalRequest.setId(rs.getString("id"));
                finalRequest.setRequestId(rs.getString("request_id"));
                finalRequest.setGts(rs.getString("gts"));
                finalRequest.setStatus(rs.getString("status"));
                finalRequest.setShippingDate(rs.getString("shipping_date"));
                finalRequest.setCreatedBy(rs.getString("created_by"));
                finalRequest.setCreatedDate(rs.getString("created_date"));
                finalRequest.setModifiedBy(rs.getString("modified_by"));
                finalRequest.setModifiedDate(rs.getString("modified_date"));
                finalRequest.setFlag(rs.getString("flag"));
                finalRequestList.add(finalRequest);
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
        return finalRequestList;
    }

    public List<FinalRequest> getFinalRequestListWithRequestData() {
        String sql = "SELECT re.*, fi.*, DATE_FORMAT(fi.shipping_date,'%d-%M-%Y') AS shipping_date_view FROM dots_final_request fi, dots_request re "
                + "WHERE fi.request_id = re.id "
                + "ORDER BY fi.id DESC";
        List<FinalRequest> finalRequestList = new ArrayList<FinalRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FinalRequest finalRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                finalRequest = new FinalRequest();
                finalRequest.setId(rs.getString("fi.id"));
                finalRequest.setRequestId(rs.getString("fi.request_id"));
                finalRequest.setGts(rs.getString("fi.gts"));
                finalRequest.setStatus(rs.getString("fi.status"));
                finalRequest.setDoNumber(rs.getString("fi.do_number"));
                finalRequest.setShippingDate(rs.getString("fi.shipping_date"));
                finalRequest.setCreatedBy(rs.getString("fi.created_by"));
                finalRequest.setCreatedDate(rs.getString("fi.created_date"));
                finalRequest.setModifiedBy(rs.getString("fi.modified_by"));
                finalRequest.setModifiedDate(rs.getString("fi.modified_date"));
                finalRequest.setFlag(rs.getString("fi.flag"));
                finalRequest.setShippingDateView(rs.getString("shipping_date_view"));

                //from request
                finalRequest.setRms(rs.getString("re.rms"));
                finalRequest.setEvent(rs.getString("re.event"));
                finalRequest.setLotId(rs.getString("re.lot_id"));
                finalRequest.setPackages(rs.getString("re.package"));
                finalRequest.setInterval(rs.getString("re.intervals"));
                finalRequest.setQuantity(rs.getString("re.quantity"));
                finalRequest.setRemarks(rs.getString("re.remarks"));
                finalRequest.setDevice(rs.getString("re.device"));
                finalRequest.setRmsEvent(rs.getString("re.rms_event"));
                finalRequestList.add(finalRequest);
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
        return finalRequestList;
    }

    public List<FinalRequest> getFinalRequestListWithRequestDataforDoPdf() {
        String sql = "SELECT re.*, fi.*, DATE_FORMAT(fi.shipping_date,'%d-%M-%Y') AS shipping_date_view FROM dots_final_request fi, dots_request re "
                + "WHERE fi.request_id = re.id AND fi.flag = '0' "
                + "ORDER BY fi.id DESC";
        List<FinalRequest> finalRequestList = new ArrayList<FinalRequest>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FinalRequest finalRequest;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                finalRequest = new FinalRequest();
                finalRequest.setId(rs.getString("fi.id"));
                finalRequest.setRequestId(rs.getString("fi.request_id"));
                finalRequest.setGts(rs.getString("fi.gts"));
                finalRequest.setStatus(rs.getString("fi.status"));
                finalRequest.setDoNumber(rs.getString("fi.do_number"));
                finalRequest.setShippingDate(rs.getString("fi.shipping_date"));
                finalRequest.setCreatedBy(rs.getString("fi.created_by"));
                finalRequest.setCreatedDate(rs.getString("fi.created_date"));
                finalRequest.setModifiedBy(rs.getString("fi.modified_by"));
                finalRequest.setModifiedDate(rs.getString("fi.modified_date"));
                finalRequest.setFlag(rs.getString("fi.flag"));
                finalRequest.setShippingDateView(rs.getString("shipping_date_view"));

                //from request
                finalRequest.setRms(rs.getString("re.rms"));
                finalRequest.setEvent(rs.getString("re.event"));
                finalRequest.setLotId(rs.getString("re.lot_id"));
                finalRequest.setPackages(rs.getString("re.package"));
                finalRequest.setInterval(rs.getString("re.intervals"));
                finalRequest.setQuantity(rs.getString("re.quantity"));
                finalRequest.setRemarks(rs.getString("re.remarks"));
                finalRequest.setDevice(rs.getString("re.device"));
                finalRequest.setRmsEvent(rs.getString("re.rms_event"));
                finalRequestList.add(finalRequest);
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
        return finalRequestList;
    }

    public Integer getCountRequestId(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_final_request WHERE request_id = '" + id + "'"
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

    public Integer getCountGts() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(DISTINCT IFNULL(gts,1)) AS count FROM dots_final_request"
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

    public FinalRequest getGtsAndShipmentDate() {
//        String sql = "SELECT DISTINCT gts as gts, DATE_FORMAT(shipping_date,'%d-%M-%Y') AS shipping_date_view FROM dots_final_request WHERE flag = '0'";
        String sql = "SELECT DISTINCT gts as gts, DATE_FORMAT(shipping_date,'%d-%M-%Y') AS shipping_date_view FROM dots_final_request";
        FinalRequest finalRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                finalRequest = new FinalRequest();
                finalRequest.setGts(rs.getString("gts"));
                finalRequest.setShippingDate(rs.getString("shipping_date_view"));
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
        return finalRequest;
    }

    public Integer getCountAll() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_final_request"
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

    public Integer getCountAllFlag0() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_final_request WHERE flag = '0'"
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

    public String getMaxDoNumber(String date) {
        String doNumberSeq = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    //                    "SELECT IFNULL(MAX(do_number),0) AS do_number_seq FROM dots_final_request WHERE do_number LIKE '" + date + "%'"
                    "SELECT IFNULL(MAX(do_number),0) AS do_number_seq FROM dots_lot_penang WHERE do_number LIKE '" + date + "%'"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doNumberSeq = rs.getString("do_number_seq");
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
        return doNumberSeq;
    }
}
