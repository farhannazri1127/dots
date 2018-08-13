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
import com.onsemi.dots.model.PostVm;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostVmDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostVmDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public PostVmDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertPostVm(PostVm postVm) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_post_vm (request_id, rms_match, package_match, event_match, interval_match, chamber_location, quantity_match, pcb_event_test_code, vm_mix, vm_mix_remarks, vm_demount, vm_demount_remarks, vm_broken, vm_broken_remarks, remarks, created_by, created_date,lot_penang_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, postVm.getRequestId());
            ps.setString(2, postVm.getRmsMatch());
            ps.setString(3, postVm.getPackageMatch());
            ps.setString(4, postVm.getEventMatch());
            ps.setString(5, postVm.getIntervalMatch());
            ps.setString(6, postVm.getChamberLocation());
            ps.setString(7, postVm.getQuantityMatch());
            ps.setString(8, postVm.getPcbEventTestCode());
            ps.setString(9, postVm.getVmMix());
            ps.setString(10, postVm.getVmMixRemarks());
            ps.setString(11, postVm.getVmDemount());
            ps.setString(12, postVm.getVmDemountRemarks());
            ps.setString(13, postVm.getVmBroken());
            ps.setString(14, postVm.getVmBrokenRemarks());
            ps.setString(15, postVm.getRemarks());
            ps.setString(16, postVm.getCreatedBy());
            ps.setString(17, postVm.getLotPenangId());
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

    public QueryResult updatePostVm(PostVm postVm) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_post_vm SET request_id = ?, rms_match = ?, package_match = ?, event_match = ?, interval_match = ?, chamber_location = ?, quantity_match = ?, pcb_event_test_code = ?, vm_mix = ?, vm_mix_remarks = ?, vm_demount = ?, vm_demount_remarks = ?, vm_broken = ?, vm_broken_remarks = ?, remarks = ?, lot_penang_id = ? WHERE id = ?"
            );
            ps.setString(1, postVm.getRequestId());
            ps.setString(2, postVm.getRmsMatch());
            ps.setString(3, postVm.getPackageMatch());
            ps.setString(4, postVm.getEventMatch());
            ps.setString(5, postVm.getIntervalMatch());
            ps.setString(6, postVm.getChamberLocation());
            ps.setString(7, postVm.getQuantityMatch());
            ps.setString(8, postVm.getPcbEventTestCode());
            ps.setString(9, postVm.getVmMix());
            ps.setString(10, postVm.getVmMixRemarks());
            ps.setString(11, postVm.getVmDemount());
            ps.setString(12, postVm.getVmDemountRemarks());
            ps.setString(13, postVm.getVmBroken());
            ps.setString(14, postVm.getVmBrokenRemarks());
            ps.setString(15, postVm.getRemarks());
            ps.setString(16, postVm.getLotPenangId());
            ps.setString(17, postVm.getId());
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

    public QueryResult deletePostVm(String postVmId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_post_vm WHERE id = '" + postVmId + "'"
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

    public PostVm getPostVm(String postVmId) {
        String sql = "SELECT * FROM dots_post_vm WHERE id = '" + postVmId + "'";
        PostVm postVm = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                postVm = new PostVm();
                postVm.setId(rs.getString("id"));
                postVm.setRequestId(rs.getString("request_id"));
                postVm.setLotPenangId(rs.getString("lot_penang_id"));
                postVm.setRmsMatch(rs.getString("rms_match"));
                postVm.setPackageMatch(rs.getString("package_match"));
                postVm.setEventMatch(rs.getString("event_match"));
                postVm.setIntervalMatch(rs.getString("interval_match"));
                postVm.setChamberLocation(rs.getString("chamber_location"));
                postVm.setQuantityMatch(rs.getString("quantity_match"));
                postVm.setPcbEventTestCode(rs.getString("pcb_event_test_code"));
                postVm.setVmMix(rs.getString("vm_mix"));
                postVm.setVmMixRemarks(rs.getString("vm_mix_remarks"));
                postVm.setVmDemount(rs.getString("vm_demount"));
                postVm.setVmDemountRemarks(rs.getString("vm_demount_remarks"));
                postVm.setVmBroken(rs.getString("vm_broken"));
                postVm.setVmBrokenRemarks(rs.getString("vm_broken_remarks"));
                postVm.setRemarks(rs.getString("remarks"));
                postVm.setCreatedBy(rs.getString("created_by"));
                postVm.setCreatedDate(rs.getString("created_date"));
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
        return postVm;
    }

    public List<PostVm> getPostVmList() {
        String sql = "SELECT * FROM dots_post_vm ORDER BY id ASC";
        List<PostVm> postVmList = new ArrayList<PostVm>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            PostVm postVm;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                postVm = new PostVm();
                postVm.setId(rs.getString("id"));
                postVm.setRequestId(rs.getString("request_id"));
                postVm.setLotPenangId(rs.getString("lot_penang_id"));
                postVm.setRmsMatch(rs.getString("rms_match"));
                postVm.setPackageMatch(rs.getString("package_match"));
                postVm.setEventMatch(rs.getString("event_match"));
                postVm.setIntervalMatch(rs.getString("interval_match"));
                postVm.setChamberLocation(rs.getString("chamber_location"));
                postVm.setQuantityMatch(rs.getString("quantity_match"));
                postVm.setPcbEventTestCode(rs.getString("pcb_event_test_code"));
                postVm.setVmMix(rs.getString("vm_mix"));
                postVm.setVmMixRemarks(rs.getString("vm_mix_remarks"));
                postVm.setVmDemount(rs.getString("vm_demount"));
                postVm.setVmDemountRemarks(rs.getString("vm_demount_remarks"));
                postVm.setVmBroken(rs.getString("vm_broken"));
                postVm.setVmBrokenRemarks(rs.getString("vm_broken_remarks"));
                postVm.setRemarks(rs.getString("remarks"));
                postVm.setCreatedBy(rs.getString("created_by"));
                postVm.setCreatedDate(rs.getString("created_date"));
                postVmList.add(postVm);
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
        return postVmList;
    }
}
