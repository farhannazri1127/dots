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
import com.onsemi.dots.model.UslTimelapse;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UslTimelapseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UslTimelapseDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public UslTimelapseDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertUslTimelapse(UslTimelapse uslTimelapse) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_usl_timelapse (process, hour, remarks, created_by, created_date) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, uslTimelapse.getProcess());
            ps.setString(2, uslTimelapse.getHour());
            ps.setString(3, uslTimelapse.getRemarks());
            ps.setString(4, uslTimelapse.getCreatedBy());
            ps.setString(5, uslTimelapse.getCreatedDate());
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

    public QueryResult updateUslTimelapse(UslTimelapse uslTimelapse) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_usl_timelapse SET process = ?, hour = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, uslTimelapse.getProcess());
            ps.setString(2, uslTimelapse.getHour());
            ps.setString(3, uslTimelapse.getRemarks());
            ps.setString(4, uslTimelapse.getId());
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

    public QueryResult deleteUslTimelapse(String uslTimelapseId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_usl_timelapse WHERE id = '" + uslTimelapseId + "'"
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

    public UslTimelapse getUslTimelapse(String uslTimelapseId) {
        String sql = "SELECT * FROM dots_usl_timelapse WHERE id = '" + uslTimelapseId + "'";
        UslTimelapse uslTimelapse = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                uslTimelapse = new UslTimelapse();
                uslTimelapse.setId(rs.getString("id"));
                uslTimelapse.setProcess(rs.getString("process"));
                uslTimelapse.setHour(rs.getString("hour"));
                uslTimelapse.setRemarks(rs.getString("remarks"));
                uslTimelapse.setCreatedBy(rs.getString("created_by"));
                uslTimelapse.setCreatedDate(rs.getString("created_date"));
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
        return uslTimelapse;
    }

    public UslTimelapse getUslTimelapseByProcess(String process) {
        String sql = "SELECT * FROM dots_usl_timelapse WHERE process = '" + process + "'";
        UslTimelapse uslTimelapse = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                uslTimelapse = new UslTimelapse();
                uslTimelapse.setId(rs.getString("id"));
                uslTimelapse.setProcess(rs.getString("process"));
                uslTimelapse.setHour(rs.getString("hour"));
                uslTimelapse.setRemarks(rs.getString("remarks"));
                uslTimelapse.setCreatedBy(rs.getString("created_by"));
                uslTimelapse.setCreatedDate(rs.getString("created_date"));
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
        return uslTimelapse;
    }

    public List<UslTimelapse> getUslTimelapseList() {
        String sql = "SELECT * FROM dots_usl_timelapse ORDER BY id ASC";
        List<UslTimelapse> uslTimelapseList = new ArrayList<UslTimelapse>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UslTimelapse uslTimelapse;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                uslTimelapse = new UslTimelapse();
                uslTimelapse.setId(rs.getString("id"));
                uslTimelapse.setProcess(rs.getString("process"));
                uslTimelapse.setHour(rs.getString("hour"));
                uslTimelapse.setRemarks(rs.getString("remarks"));
                uslTimelapse.setCreatedBy(rs.getString("created_by"));
                uslTimelapse.setCreatedDate(rs.getString("created_date"));
                uslTimelapseList.add(uslTimelapse);
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
        return uslTimelapseList;
    }

    public Integer getCountUslByProcess(String process) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_usl_timelapse WHERE process = '" + process + "'"
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

    public Integer getCountTotalByEventAndMonthAndYear(String event, String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) as countJan FROM dots_request re, dots_lot_penang pe WHERE "
                    + "YEAR(re.shipping_date) = '" + year + "' AND MONTH(re.shipping_date) = '" + month + "' AND re.`event` LIKE '" + event + "%' AND pe.request_id = re.id AND pe.flag in ('0','1')"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countJan");
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

    public Integer getCountFailByEventAndMonthAndYear(String event, String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS totalFail FROM(SELECT "
                    + "(TIMESTAMPDIFF(HOUR, IFNULL(re.shipping_date,NOW()), IFNULL(sh.received_date,NOW())) + "
                    //                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.received_date,NOW()), IFNULL(sh.loading_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.received_date,NOW()), IF(sh2.oldID is not null,IFNULL(sh2.loadDate,NOW()),IFNULL(sh.loading_date,NOW()))) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.unloading_date,NOW()), IFNULL(sh.shipment_date,NOW())) + "
                    + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipment_date,NOW()), IFNULL(sh.closed_date,NOW()))) as totalHourTake "
                    + "FROM dots_request re, dots_lot_penang sh LEFT JOIN (SELECT pee.id AS oldID,pee.loading_date as loadDate FROM dots_lot_penang pee "
                    + "WHERE pee.new_lot_penang_id is not null) AS sh2 ON sh.old_lot_penang_id = sh2.oldID WHERE re.id = sh.request_id AND "
                    + "YEAR(re.shipping_date) = '" + year + "' AND MONTH(re.shipping_date) = '" + month + "' AND re.`event` LIKE '" + event + "%' AND sh.flag in ('0','1')) AS total "
                    + "WHERE total.totalHourTake > 74"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalFail");
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

    public List<UslTimelapse> GetListOfFailedItemByEventAndMonthAndYear(String event, String month, String year) { //timelapse notification ship
        String sql = "SELECT re.id AS reqId, re.rms AS rms, re.`event` AS eventt, re.lot_id AS lot, "
                + "re.intervals AS intervals,sh2.oldID AS oldID, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(re.shipping_date,NOW()), IFNULL(sh.received_date,NOW())) AS shipToReceived, "
                //                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.received_date,NOW()), IFNULL(sh.loading_date,NOW())) AS receivedToLoad, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.received_date,NOW()), IF(sh2.oldID is not null,IFNULL(sh2.loadDate,NOW()),IFNULL(sh.loading_date,NOW()))) AS receivedToLoad, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.unloading_date,NOW()), IFNULL(sh.shipment_date,NOW())) AS unloadToShip, "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipment_date,NOW()), IFNULL(sh.closed_date,NOW())) AS shipToClosed,  "
                + "test.totalHourTake AS totalHourTake,sh.id AS penangId "
                + "FROM (SELECT re.id AS idTest, "
                + "(TIMESTAMPDIFF(HOUR, IFNULL(re.shipping_date,NOW()), IFNULL(sh.received_date,NOW())) + "
                //                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.received_date,NOW()), IFNULL(sh.loading_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.received_date,NOW()), IF(sh3.oldID2 is not null,IFNULL(sh3.loadDate2,NOW()),IFNULL(sh.loading_date,NOW()))) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.unloading_date,NOW()), IFNULL(sh.shipment_date,NOW())) + "
                + "TIMESTAMPDIFF(HOUR, IFNULL(sh.shipment_date,NOW()), IFNULL(sh.closed_date,NOW()))) AS totalHourTake "
                + "FROM dots_request re, dots_lot_penang sh LEFT JOIN (SELECT pee2.id AS oldID2,pee2.loading_date as loadDate2 FROM dots_lot_penang pee2 "
                + "WHERE pee2.new_lot_penang_id is not null) AS sh3 ON sh.old_lot_penang_id = sh3.oldID2 WHERE re.id = sh.request_id AND "
                + "YEAR(re.shipping_date) = '" + year + "' AND MONTH(re.shipping_date) = '" + month + "' AND re.event LIKE '" + event + "%' AND sh.flag in ('0','1')) AS test, "
                + "dots_request re,dots_lot_penang sh LEFT JOIN (SELECT pee.id AS oldID,pee.loading_date as loadDate FROM dots_lot_penang pee "
                + "WHERE pee.new_lot_penang_id is not null) AS sh2 ON sh.old_lot_penang_id = sh2.oldID WHERE re.id = sh.request_id AND "
                + "YEAR(re.shipping_date) = '" + year + "' AND MONTH(re.shipping_date) = '" + month + "' AND re.event LIKE '" + event + "%' AND sh.flag in ('0','1') "
                + "AND test.idTest = re.id AND test.totalHourTake > 74 "
                + "ORDER BY re.rms";
        List<UslTimelapse> whStatusLogList = new ArrayList<UslTimelapse>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            UslTimelapse whStatusLog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whStatusLog = new UslTimelapse();
                whStatusLog.setReqId(rs.getString("reqId"));
                whStatusLog.setRms(rs.getString("rms"));
                whStatusLog.setEvent(rs.getString("eventt"));
                whStatusLog.setLot(rs.getString("lot"));
                whStatusLog.setIntervals(rs.getString("intervals"));
                whStatusLog.setPenangId(rs.getString("penangId"));
                whStatusLog.setOldPenangId(rs.getString("oldID"));
                whStatusLog.setShipToReceived(rs.getString("shipToReceived"));
                whStatusLog.setReceivedToLoad(rs.getString("receivedToLoad"));
                whStatusLog.setUnloadToShip(rs.getString("unloadToShip"));
                whStatusLog.setShipToClosed(rs.getString("shipToClosed"));
                whStatusLog.setTotalUSl(rs.getString("totalHourTake"));
                whStatusLogList.add(whStatusLog);
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
        return whStatusLogList;
    }
}
