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
import com.onsemi.dots.model.AbnormalLoading;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbnormalLoadingDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbnormalLoadingDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public AbnormalLoadingDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertAbnormalLoading(AbnormalLoading abnormalLoading) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_abnormal_loading (request_id, suggested_loading_time, actual_loading_time, suggested_unloading_time, new_estimate_unloading_time, remarks, flag) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, abnormalLoading.getRequestId());
            ps.setString(2, abnormalLoading.getSuggestedLoadingTime());
            ps.setString(3, abnormalLoading.getActualLoadingTime());
            ps.setString(4, abnormalLoading.getSuggestedUnloadingTime());
            ps.setString(5, abnormalLoading.getNewEstimateUnloadingTime());
            ps.setString(6, abnormalLoading.getRemarks());
            ps.setString(7, abnormalLoading.getFlag());
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

    public QueryResult updateAbnormalLoading(AbnormalLoading abnormalLoading) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_abnormal_loading SET request_id = ?, suggested_loading_time = ?, actual_loading_time = ?, suggested_unloading_time = ?, new_estimate_unloading_time = ?, remarks = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, abnormalLoading.getRequestId());
            ps.setString(2, abnormalLoading.getSuggestedLoadingTime());
            ps.setString(3, abnormalLoading.getActualLoadingTime());
            ps.setString(4, abnormalLoading.getSuggestedUnloadingTime());
            ps.setString(5, abnormalLoading.getNewEstimateUnloadingTime());
            ps.setString(6, abnormalLoading.getRemarks());
            ps.setString(7, abnormalLoading.getFlag());
            ps.setString(8, abnormalLoading.getId());
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

    public QueryResult deleteAbnormalLoading(String abnormalLoadingId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_abnormal_loading WHERE id = '" + abnormalLoadingId + "'"
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

    public AbnormalLoading getAbnormalLoading(String abnormalLoadingId) {
        String sql = "SELECT * FROM dots_abnormal_loading WHERE id = '" + abnormalLoadingId + "'";
        AbnormalLoading abnormalLoading = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abnormalLoading = new AbnormalLoading();
                abnormalLoading.setId(rs.getString("id"));
                abnormalLoading.setRequestId(rs.getString("request_id"));
                abnormalLoading.setSuggestedLoadingTime(rs.getString("suggested_loading_time"));
                abnormalLoading.setActualLoadingTime(rs.getString("actual_loading_time"));
                abnormalLoading.setSuggestedUnloadingTime(rs.getString("suggested_unloading_time"));
                abnormalLoading.setNewEstimateUnloadingTime(rs.getString("new_estimate_unloading_time"));
                abnormalLoading.setRemarks(rs.getString("remarks"));
                abnormalLoading.setFlag(rs.getString("flag"));
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
        return abnormalLoading;
    }

    public List<AbnormalLoading> getAbnormalLoadingList() {
        String sql = "SELECT * FROM dots_abnormal_loading ORDER BY id ASC";
        List<AbnormalLoading> abnormalLoadingList = new ArrayList<AbnormalLoading>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            AbnormalLoading abnormalLoading;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abnormalLoading = new AbnormalLoading();
                abnormalLoading.setId(rs.getString("id"));
                abnormalLoading.setRequestId(rs.getString("request_id"));
                abnormalLoading.setSuggestedLoadingTime(rs.getString("suggested_loading_time"));
                abnormalLoading.setActualLoadingTime(rs.getString("actual_loading_time"));
                abnormalLoading.setSuggestedUnloadingTime(rs.getString("suggested_unloading_time"));
                abnormalLoading.setNewEstimateUnloadingTime(rs.getString("new_estimate_unloading_time"));
                abnormalLoading.setRemarks(rs.getString("remarks"));
                abnormalLoading.setFlag(rs.getString("flag"));
                abnormalLoadingList.add(abnormalLoading);
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
        return abnormalLoadingList;
    }

    public List<AbnormalLoading> getAbnormalLoadingListJoinWithLotPenang() {
        String sql = "SELECT ab.*, req.id, req.rms_event, req.intervals, req.quantity, req.gts, req.do_number, "
                + "lot.id, lot.request_id, lot.received_date, lot.chamber_id, lot.status, "
                + "DATE_FORMAT(ab.suggested_loading_time,'%d %M %Y %h:%i %p') AS suggested_loading_time_view, "
                + "DATE_FORMAT(ab.actual_loading_time,'%d %M %Y %h:%i %p') AS actual_loading_time_view, "
                + "DATE_FORMAT(ab.suggested_unloading_time,'%d %M %Y %h:%i %p') AS suggested_unloading_time_view, "
                + "DATE_FORMAT(ab.new_estimate_unloading_time,'%d %M %Y %h:%i %p') AS new_estimate_unloading_time_view, "
                + "DATE_FORMAT(lot.received_date,'%d %M %Y %h:%i %p') AS received_date_view "
                + "FROM dots_lot_penang lot, dots_request req, dots_abnormal_loading ab "
                + "WHERE lot.request_id = req.id AND lot.request_id = ab.request_id "
                + "AND lot.status != 'Closed' "
                + "ORDER BY actual_loading_time DESC";
        List<AbnormalLoading> abnormalLoadingList = new ArrayList<AbnormalLoading>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            AbnormalLoading abnormalLoading;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abnormalLoading = new AbnormalLoading();
                abnormalLoading.setId(rs.getString("ab.id"));
                abnormalLoading.setRequestId(rs.getString("ab.request_id"));
                abnormalLoading.setSuggestedLoadingTime(rs.getString("ab.suggested_loading_time"));
                abnormalLoading.setActualLoadingTime(rs.getString("ab.actual_loading_time"));
                abnormalLoading.setSuggestedUnloadingTime(rs.getString("ab.suggested_unloading_time"));
                abnormalLoading.setNewEstimateUnloadingTime(rs.getString("ab.new_estimate_unloading_time"));
                abnormalLoading.setRemarks(rs.getString("ab.remarks"));
                abnormalLoading.setFlag(rs.getString("ab.flag"));
                 abnormalLoading.setSuggestedLoadingTimeView(rs.getString("suggested_loading_time_view"));
                abnormalLoading.setActualLoadingTimeView(rs.getString("actual_loading_time_view"));
                abnormalLoading.setSuggestedUnloadingTimeView(rs.getString("suggested_unloading_time_view"));
                abnormalLoading.setNewEstimateUnloadingTimeView(rs.getString("new_estimate_unloading_time_view"));
                abnormalLoading.setChamber(rs.getString("lot.chamber_id"));
                abnormalLoading.setDoNumber(rs.getString("req.do_number"));
                abnormalLoading.setGts(rs.getString("req.gts"));
                abnormalLoading.setIntervals(rs.getString("req.intervals"));
                abnormalLoading.setQuantity(rs.getString("req.quantity"));
                abnormalLoading.setReceivedDateView(rs.getString("new_estimate_unloading_time_view"));
                abnormalLoading.setRmsEvent(rs.getString("req.rms_event"));
                abnormalLoading.setStatus(rs.getString("lot.status"));
                abnormalLoadingList.add(abnormalLoading);
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
        return abnormalLoadingList;
    }
    
    public Integer getCountRequestId(String requestId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_abnormal_loading WHERE request_id = '" + requestId + "'"
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
