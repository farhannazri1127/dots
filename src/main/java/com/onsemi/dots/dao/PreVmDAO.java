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
import com.onsemi.dots.model.PreVm;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreVmDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreVmDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public PreVmDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertPreVm(PreVm preVm) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_pre_vm (request_id, rms_match, package_match, event_match, interval_match, pcb_event_test_code, chamber_location, quantity_match, vm_mix, vm_mix_remarks, vm_demount, vm_demount_remarks, vm_broken, vm_broken_remarks, remarks, created_by, created_date, cdpa_sample) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, preVm.getRequestId());
            ps.setString(2, preVm.getRmsMatch());
            ps.setString(3, preVm.getPackageMatch());
            ps.setString(4, preVm.getEventMatch());
            ps.setString(5, preVm.getIntervalMatch());
            ps.setString(6, preVm.getPcbEventTestCode());
            ps.setString(7, preVm.getChamberLocation());
            ps.setString(8, preVm.getQuantityMatch());
            ps.setString(9, preVm.getVmMix());
            ps.setString(10, preVm.getVmMixRemarks());
            ps.setString(11, preVm.getVmDemount());
            ps.setString(12, preVm.getVmDemountRemarks());
            ps.setString(13, preVm.getVmBroken());
            ps.setString(14, preVm.getVmBrokenRemarks());
            ps.setString(15, preVm.getRemarks());
            ps.setString(16, preVm.getCreatedBy());
            ps.setString(17, preVm.getCdpaSample());
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

    public QueryResult updatePreVm(PreVm preVm) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_pre_vm SET request_id = ?, rms_match = ?, package_match = ?, event_match = ?, interval_match = ?, pcb_event_test_code = ?, chamber_location = ?, quantity_match = ?, vm_mix = ?, vm_mix_remarks = ?, vm_demount = ?, vm_demount_remarks = ?, vm_broken = ?, vm_broken_remarks = ?, remarks = ?, cdpa_sample = ? WHERE id = ?"
            );
            ps.setString(1, preVm.getRequestId());
            ps.setString(2, preVm.getRmsMatch());
            ps.setString(3, preVm.getPackageMatch());
            ps.setString(4, preVm.getEventMatch());
            ps.setString(5, preVm.getIntervalMatch());
            ps.setString(6, preVm.getPcbEventTestCode());
            ps.setString(7, preVm.getChamberLocation());
            ps.setString(8, preVm.getQuantityMatch());
            ps.setString(9, preVm.getVmMix());
            ps.setString(10, preVm.getVmMixRemarks());
            ps.setString(11, preVm.getVmDemount());
            ps.setString(12, preVm.getVmDemountRemarks());
            ps.setString(13, preVm.getVmBroken());
            ps.setString(14, preVm.getVmBrokenRemarks());
            ps.setString(15, preVm.getRemarks());
//            ps.setString(16, preVm.getCreatedBy());
//            ps.setString(17, preVm.getCreatedDate());
            ps.setString(16, preVm.getCdpaSample());
            ps.setString(17, preVm.getId());
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

    public QueryResult deletePreVm(String preVmId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_pre_vm WHERE id = '" + preVmId + "'"
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

    public PreVm getPreVm(String preVmId) {
        String sql = "SELECT * FROM dots_pre_vm WHERE id = '" + preVmId + "'";
        PreVm preVm = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                preVm = new PreVm();
                preVm.setId(rs.getString("id"));
                preVm.setRequestId(rs.getString("request_id"));
                preVm.setRmsMatch(rs.getString("rms_match"));
                preVm.setPackageMatch(rs.getString("package_match"));
                preVm.setEventMatch(rs.getString("event_match"));
                preVm.setIntervalMatch(rs.getString("interval_match"));
                preVm.setPcbEventTestCode(rs.getString("pcb_event_test_code"));
                preVm.setChamberLocation(rs.getString("chamber_location"));
                preVm.setQuantityMatch(rs.getString("quantity_match"));
                preVm.setVmMix(rs.getString("vm_mix"));
                preVm.setVmMixRemarks(rs.getString("vm_mix_remarks"));
                preVm.setVmDemount(rs.getString("vm_demount"));
                preVm.setVmDemountRemarks(rs.getString("vm_demount_remarks"));
                preVm.setVmBroken(rs.getString("vm_broken"));
                preVm.setVmBrokenRemarks(rs.getString("vm_broken_remarks"));
                preVm.setRemarks(rs.getString("remarks"));
                preVm.setCreatedBy(rs.getString("created_by"));
                preVm.setCreatedDate(rs.getString("created_date"));
                preVm.setCdpaSample(rs.getString("cdpa_sample"));
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
        return preVm;
    }

    public List<PreVm> getPreVmList() {
        String sql = "SELECT * FROM dots_pre_vm ORDER BY id ASC";
        List<PreVm> preVmList = new ArrayList<PreVm>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            PreVm preVm;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                preVm = new PreVm();
                preVm.setId(rs.getString("id"));
                preVm.setRequestId(rs.getString("request_id"));
                preVm.setRmsMatch(rs.getString("rms_match"));
                preVm.setPackageMatch(rs.getString("package_match"));
                preVm.setEventMatch(rs.getString("event_match"));
                preVm.setIntervalMatch(rs.getString("interval_match"));
                preVm.setPcbEventTestCode(rs.getString("pcb_event_test_code"));
                preVm.setChamberLocation(rs.getString("chamber_location"));
                preVm.setQuantityMatch(rs.getString("quantity_match"));
                preVm.setVmMix(rs.getString("vm_mix"));
                preVm.setVmMixRemarks(rs.getString("vm_mix_remarks"));
                preVm.setVmDemount(rs.getString("vm_demount"));
                preVm.setVmDemountRemarks(rs.getString("vm_demount_remarks"));
                preVm.setVmBroken(rs.getString("vm_broken"));
                preVm.setVmBrokenRemarks(rs.getString("vm_broken_remarks"));
                preVm.setRemarks(rs.getString("remarks"));
                preVm.setCreatedBy(rs.getString("created_by"));
                preVm.setCreatedDate(rs.getString("created_date"));
                preVm.setCdpaSample(rs.getString("cdpa_sample"));
                preVmList.add(preVm);
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
        return preVmList;
    }
}
