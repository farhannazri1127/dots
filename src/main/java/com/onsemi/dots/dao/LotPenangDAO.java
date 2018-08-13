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
import com.onsemi.dots.model.LotPenang;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotPenangDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotPenangDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public LotPenangDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertLotPenang(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_lot_penang (do_number, request_id, rms_event, chamber_id, chamber_level, loading_date, loading_remarks, test_condition, loading_by, unloading_date, unloading_remarks, unloading_by, status, received_quantity, received_date, received_by, shipment_by, shipment_date, created_by, created_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, lotPenang.getDoNumber());
            ps.setString(2, lotPenang.getRequestId());
            ps.setString(3, lotPenang.getRmsEvent());
            ps.setString(4, lotPenang.getChamberId());
            ps.setString(5, lotPenang.getChamberLevel());
            ps.setString(6, lotPenang.getLoadingDate());
            ps.setString(7, lotPenang.getLoadingRemarks());
            ps.setString(8, lotPenang.getTestCondition());
            ps.setString(9, lotPenang.getLoadingBy());
            ps.setString(10, lotPenang.getUnloadingDate());
            ps.setString(11, lotPenang.getUnloadingRemarks());
            ps.setString(12, lotPenang.getUnloadingBy());
            ps.setString(13, lotPenang.getStatus());
            ps.setString(14, lotPenang.getReceivedQuantity());
            ps.setString(15, lotPenang.getReceivedDate());
            ps.setString(16, lotPenang.getReceivedBy());
            ps.setString(17, lotPenang.getShipmentBy());
            ps.setString(18, lotPenang.getShipmentDate());
            ps.setString(19, lotPenang.getCreatedBy());
            ps.setString(20, lotPenang.getCreatedDate());
            ps.setString(21, lotPenang.getFlag());
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

    public QueryResult insertLotPenangBreakInterval(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_lot_penang (do_number, request_id, rms_event, chamber_id, chamber_level, loading_date, loading_remarks, "
                    + "test_condition, loading_by, status, received_quantity, received_date, received_by, received_verification_status, received_verification_date,"
                    + "received_mix_status, received_mix_remarks, received_demount_status, received_demount_remarks, received_broken_status, received_broken_remarks,"
                    + "created_by, created_date, flag, old_lot_penang_id,trip_ticket_path,pre_vm_date,pots_notify, interval_remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, lotPenang.getDoNumber());
            ps.setString(2, lotPenang.getRequestId());
            ps.setString(3, lotPenang.getRmsEvent());
            ps.setString(4, lotPenang.getChamberId());
            ps.setString(5, lotPenang.getChamberLevel());
            ps.setString(6, lotPenang.getLoadingDate());
            ps.setString(7, lotPenang.getLoadingRemarks());
            ps.setString(8, lotPenang.getTestCondition());
            ps.setString(9, lotPenang.getLoadingBy());
            ps.setString(10, lotPenang.getStatus());
            ps.setString(11, lotPenang.getReceivedQuantity());
            ps.setString(12, lotPenang.getReceivedDate());
            ps.setString(13, lotPenang.getReceivedBy());
            ps.setString(14, lotPenang.getReceivedVerificationStatus());
            ps.setString(15, lotPenang.getReceivedVerificationDate());
            ps.setString(16, lotPenang.getReceivedMixStatus());
            ps.setString(17, lotPenang.getReceivedMixRemarks());
            ps.setString(18, lotPenang.getReceivedDemountStatus());
            ps.setString(19, lotPenang.getReceivedDemountRemarks());
            ps.setString(20, lotPenang.getReceivedBrokenStatus());
            ps.setString(21, lotPenang.getReceivedBrokenRemarks());
            ps.setString(22, lotPenang.getCreatedBy());
            ps.setString(23, lotPenang.getFlag());
            ps.setString(24, lotPenang.getOldLotPenangId());
            ps.setString(25, lotPenang.getTripTicketPath());
            ps.setString(26, lotPenang.getPreVmDate());
            ps.setString(27, lotPenang.getPotsNotify());
            ps.setString(28, lotPenang.getIntervalRemarks());
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

    public QueryResult insertLotPenangNewInterval(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_lot_penang (do_number, request_id, rms_event, status, received_quantity, received_date, received_by, received_verification_status, received_verification_date,"
                    + "received_mix_status, received_mix_remarks, received_demount_status, received_demount_remarks, received_broken_status, received_broken_remarks,"
                    + "created_by, created_date, flag, old_lot_penang_id,trip_ticket_path,pre_vm_date,pots_notify, interval_remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, lotPenang.getDoNumber());
            ps.setString(2, lotPenang.getRequestId());
            ps.setString(3, lotPenang.getRmsEvent());
            ps.setString(4, lotPenang.getStatus());
            ps.setString(5, lotPenang.getReceivedQuantity());
            ps.setString(6, lotPenang.getReceivedDate());
            ps.setString(7, lotPenang.getReceivedBy());
            ps.setString(8, lotPenang.getReceivedVerificationStatus());
            ps.setString(9, lotPenang.getReceivedVerificationDate());
            ps.setString(10, lotPenang.getReceivedMixStatus());
            ps.setString(11, lotPenang.getReceivedMixRemarks());
            ps.setString(12, lotPenang.getReceivedDemountStatus());
            ps.setString(13, lotPenang.getReceivedDemountRemarks());
            ps.setString(14, lotPenang.getReceivedBrokenStatus());
            ps.setString(15, lotPenang.getReceivedBrokenRemarks());
            ps.setString(16, lotPenang.getCreatedBy());
            ps.setString(17, lotPenang.getFlag());
            ps.setString(18, lotPenang.getOldLotPenangId());
            ps.setString(19, lotPenang.getTripTicketPath());
            ps.setString(20, lotPenang.getPreVmDate());
            ps.setString(21, lotPenang.getPotsNotify());
            ps.setString(22, lotPenang.getIntervalRemarks());
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

    public QueryResult insertLotPenangWhenShip(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_lot_penang (do_number, request_id, rms_event, status, created_by, created_date, flag) VALUES (?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, lotPenang.getDoNumber());
            ps.setString(2, lotPenang.getRequestId());
            ps.setString(3, lotPenang.getRmsEvent());
            ps.setString(4, lotPenang.getStatus());
            ps.setString(5, lotPenang.getCreatedBy());
            ps.setString(6, lotPenang.getFlag());
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

    public QueryResult updateLotPenang(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET do_number = ?, request_id = ?, rms_event = ?, chamber_id = ?, chamber_level = ?, loading_date = ?, loading_remarks = ?, test_condition = ?, loading_by = ?, unloading_date = ?, unloading_remarks = ?, unloading_by = ?, status = ?, received_quantity = ?, received_date = ?, received_by = ?, shipment_by = ?, shipment_date = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, lotPenang.getDoNumber());
            ps.setString(2, lotPenang.getRequestId());
            ps.setString(3, lotPenang.getRmsEvent());
            ps.setString(4, lotPenang.getChamberId());
            ps.setString(5, lotPenang.getChamberLevel());
            ps.setString(6, lotPenang.getLoadingDate());
            ps.setString(7, lotPenang.getLoadingRemarks());
            ps.setString(8, lotPenang.getTestCondition());
            ps.setString(9, lotPenang.getLoadingBy());
            ps.setString(10, lotPenang.getUnloadingDate());
            ps.setString(11, lotPenang.getUnloadingRemarks());
            ps.setString(12, lotPenang.getUnloadingBy());
            ps.setString(13, lotPenang.getStatus());
            ps.setString(14, lotPenang.getReceivedQuantity());
            ps.setString(15, lotPenang.getReceivedDate());
            ps.setString(16, lotPenang.getReceivedBy());
            ps.setString(17, lotPenang.getShipmentBy());
            ps.setString(18, lotPenang.getShipmentDate());
            ps.setString(19, lotPenang.getCreatedBy());
            ps.setString(20, lotPenang.getCreatedDate());
            ps.setString(21, lotPenang.getFlag());
            ps.setString(22, lotPenang.getId());
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

    public QueryResult updateLotPenangWhenClosed(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET request_id = ?, status = ?, closed_verification = ?, closed_by = ?, closed_date = NOW(), flag = ? WHERE id = ?"
            );
            ps.setString(1, lotPenang.getRequestId());
            ps.setString(2, lotPenang.getStatus());
            ps.setString(3, lotPenang.getClosedVerification());
            ps.setString(4, lotPenang.getClosedBy());
            ps.setString(5, lotPenang.getFlag());
            ps.setString(6, lotPenang.getId());
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

    public QueryResult updateLotPenangWhenBreakInterval(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET request_id = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, lotPenang.getRequestId());
            ps.setString(2, lotPenang.getStatus());
            ps.setString(3, lotPenang.getFlag());
            ps.setString(4, lotPenang.getId());
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

    public QueryResult updateLotPenangforStatusAndFlag(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getFlag());
            ps.setString(3, lotPenang.getId());
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

    public QueryResult updateLotPenangWhenCancelInterval(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, flag = ?, pots_notify = ? WHERE id = ?"
            );
            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getFlag());
            ps.setString(3, lotPenang.getPotsNotify());
            ps.setString(4, lotPenang.getId());
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

    public QueryResult updateLotPenangWhenNewInterval(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET request_id = ?, new_lot_penang_id = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, lotPenang.getRequestId());
            ps.setString(2, lotPenang.getNewLotPenangId());
            ps.setString(3, lotPenang.getFlag());
            ps.setString(4, lotPenang.getId());
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

    public QueryResult updateLotPenangWhenPenangReceived(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, received_date = ?, received_quantity = ?, received_by = ?, received_verification_status = ?,"
                    + "received_verification_date = ?, received_mix_status = ?, received_mix_remarks = ?, received_demount_status = ?, "
                    + "received_demount_remarks = ?, received_broken_status = ?, received_broken_remarks = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getReceivedDate());
            ps.setString(3, lotPenang.getReceivedQuantity());
            ps.setString(4, lotPenang.getReceivedBy());
            ps.setString(5, lotPenang.getReceivedVerificationStatus());
            ps.setString(6, lotPenang.getReceivedVerificationDate());
            ps.setString(7, lotPenang.getReceivedMixStatus());
            ps.setString(8, lotPenang.getReceivedMixRemarks());
            ps.setString(9, lotPenang.getReceivedDemountStatus());
            ps.setString(10, lotPenang.getReceivedDemountRemarks());
            ps.setString(11, lotPenang.getReceivedBrokenStatus());
            ps.setString(12, lotPenang.getReceivedBrokenRemarks());
            ps.setString(13, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenStatusReceiving(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, received_date = ?, received_by = ?, received_verification_status = ?,"
                    + "received_verification_date = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getReceivedDate());
            ps.setString(3, lotPenang.getReceivedBy());
            ps.setString(4, lotPenang.getReceivedVerificationStatus());
            ps.setString(5, lotPenang.getReceivedVerificationDate());
            ps.setString(6, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenStatusPreLoadingVM(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, received_quantity = ?, received_mix_status = ?, received_mix_remarks = ?, received_demount_status = ?, "
                    + "received_demount_remarks = ?, received_broken_status = ?, received_broken_remarks = ?, pre_vm_date = ?, received_date = ?, received_by = ?, received_verification_status = ?, "
                    + "received_verification_date = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getReceivedQuantity());
            ps.setString(3, lotPenang.getReceivedMixStatus());
            ps.setString(4, lotPenang.getReceivedMixRemarks());
            ps.setString(5, lotPenang.getReceivedDemountStatus());
            ps.setString(6, lotPenang.getReceivedDemountRemarks());
            ps.setString(7, lotPenang.getReceivedBrokenStatus());
            ps.setString(8, lotPenang.getReceivedBrokenRemarks());
            ps.setString(9, lotPenang.getPreVmDate());
            ps.setString(10, lotPenang.getReceivedDate());
            ps.setString(11, lotPenang.getReceivedBy());
            ps.setString(12, lotPenang.getReceivedVerificationStatus());
            ps.setString(13, lotPenang.getReceivedVerificationDate());
            ps.setString(14, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenLoading(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, chamber_id = ?, chamber_level = ?, test_condition = ?, loading_remarks = ?,"
                    + " loading_date = ?, loading_by = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getChamberId());
            ps.setString(3, lotPenang.getChamberLevel());
            ps.setString(4, lotPenang.getTestCondition());
            ps.setString(5, lotPenang.getLoadingRemarks());
            ps.setString(6, lotPenang.getLoadingDate());
            ps.setString(7, lotPenang.getLoadingBy());
            ps.setString(8, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenUnloading(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, unloading_date = ?, unloading_remarks = ?, unloading_by = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getUnloadingDate());
            ps.setString(3, lotPenang.getUnloadingRemarks());
            ps.setString(4, lotPenang.getUnloadingBy());
            ps.setString(5, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenUnloadingWithVM(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, unloading_date = ?, unloading_remarks = ?, unloading_by = ? ,"
                    + "unloading_mix_status = ?, unloading_mix_remarks = ?, unloading_demount_status = ?, "
                    + "unloading_demount_remarks = ?, unloading_broken_status = ?, unloading_broken_remarks = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getUnloadingDate());
            ps.setString(3, lotPenang.getUnloadingRemarks());
            ps.setString(4, lotPenang.getUnloadingBy());
            ps.setString(5, lotPenang.getUnloadingMixStatus());
            ps.setString(6, lotPenang.getUnloadingMixRemarks());
            ps.setString(7, lotPenang.getUnloadingDemountStatus());
            ps.setString(8, lotPenang.getUnloadingDemountRemarks());
            ps.setString(9, lotPenang.getUnloadingBrokenStatus());
            ps.setString(10, lotPenang.getUnloadingBrokenRemarks());
            ps.setString(11, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenPostLoadingVM(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, unloading_mix_status = ?, unloading_mix_remarks = ?, unloading_demount_status = ?, "
                    + "unloading_demount_remarks = ?, unloading_broken_status = ?, unloading_broken_remarks = ?, unloading_quantity = ?, post_vm_date = ?, "
                    + "unloading_date = ?, unloading_remarks = ?, unloading_by = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getUnloadingMixStatus());
            ps.setString(3, lotPenang.getUnloadingMixRemarks());
            ps.setString(4, lotPenang.getUnloadingDemountStatus());
            ps.setString(5, lotPenang.getUnloadingDemountRemarks());
            ps.setString(6, lotPenang.getUnloadingBrokenStatus());
            ps.setString(7, lotPenang.getUnloadingBrokenRemarks());
            ps.setString(8, lotPenang.getUnloadingQty());
            ps.setString(9, lotPenang.getPostVmDate());
            ps.setString(10, lotPenang.getUnloadingDate());
            ps.setString(11, lotPenang.getUnloadingRemarks());
            ps.setString(12, lotPenang.getUnloadingBy());
            ps.setString(13, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenShipToRel(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET status = ?, shipment_date = ?, shipment_by = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getStatus());
            ps.setString(2, lotPenang.getShipmentDate());
            ps.setString(3, lotPenang.getShipmentBy());
            ps.setString(4, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenPotsNotify(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET pots_notify = ?, pots_notify_date = ?, pots_notify_by = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getPotsNotify());
            ps.setString(2, lotPenang.getPotsNotifyDate());
            ps.setString(3, lotPenang.getPotsNotifyBy());
            ps.setString(4, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWhenPotsNotifyForCancelInterval(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET pots_notify = ?, pots_notify_date = ?, pots_notify_by = ?, flag = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getPotsNotify());
            ps.setString(2, lotPenang.getPotsNotifyDate());
            ps.setString(3, lotPenang.getPotsNotifyBy());
            ps.setString(4, lotPenang.getFlag());
            ps.setString(5, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangChangeTripTicket(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET trip_ticket_path = ? WHERE id = ?"
            );

            ps.setString(1, lotPenang.getTripTicketPath());
            ps.setString(2, lotPenang.getId());
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

    public QueryResult updateLotPenangRmaIdWithRequestId(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET rma_id = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getRmaId());
            ps.setString(2, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangAbnormalIdWithRequestId(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET abnormal_id = ? WHERE request_id = ?"
            );

            ps.setString(1, lotPenang.getAbnormalId());
            ps.setString(2, lotPenang.getRequestId());
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

    public QueryResult updateLotPenangWithPostVmId(LotPenang lotPenang) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_lot_penang SET post_vm_id = ?, status = ? WHERE id = ?"
            );

            ps.setString(1, lotPenang.getPostVmId());
            ps.setString(2, lotPenang.getStatus());
            ps.setString(3, lotPenang.getId());
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

    public QueryResult deleteLotPenang(String lotPenangId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_lot_penang WHERE id = '" + lotPenangId + "'"
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

    public LotPenang getLotPenang(String lotPenangId) {
        String sql = "SELECT * FROM dots_lot_penang WHERE id = '" + lotPenangId + "'";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setChamberId(rs.getString("chamber_id"));
                lotPenang.setChamberLevel(rs.getString("chamber_level"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("loading_remarks"));
                lotPenang.setTestCondition(rs.getString("test_condition"));
                lotPenang.setLoadingBy(rs.getString("loading_by"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("unloading_by"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setReceivedQuantity(rs.getString("received_quantity"));
                lotPenang.setReceivedDate(rs.getString("received_date"));
                lotPenang.setReceivedBy(rs.getString("received_by"));
                lotPenang.setShipmentBy(rs.getString("shipment_by"));
                lotPenang.setShipmentDate(rs.getString("shipment_date"));
                lotPenang.setCreatedBy(rs.getString("created_by"));
                lotPenang.setCreatedDate(rs.getString("created_date"));
                lotPenang.setFlag(rs.getString("flag"));
                lotPenang.setClosedBy(rs.getString("closed_by"));
                lotPenang.setClosedVerification(rs.getString("closed_verification"));
                lotPenang.setClosedDate(rs.getString("closed_date"));
                lotPenang.setReceivedVerificationStatus(rs.getString("received_verification_status"));
                lotPenang.setReceivedVerificationDate(rs.getString("received_verification_date"));
                lotPenang.setReceivedMixStatus(rs.getString("received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("received_broken_remarks"));
                lotPenang.setPreVmDate(rs.getString("pre_vm_date"));
                lotPenang.setPostVmDate(rs.getString("post_vm_date"));
                lotPenang.setUnloadingQty(rs.getString("unloading_quantity"));
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
        return lotPenang;
    }

    public LotPenang getLotPenangWithTripticketPath(String lotPenangId) {
        String sql = "SELECT * FROM dots_lot_penang WHERE id = '" + lotPenangId + "'";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setChamberId(rs.getString("chamber_id"));
                lotPenang.setChamberLevel(rs.getString("chamber_level"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("loading_remarks"));
                lotPenang.setTestCondition(rs.getString("test_condition"));
                lotPenang.setLoadingBy(rs.getString("loading_by"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("unloading_by"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setReceivedQuantity(rs.getString("received_quantity"));
                lotPenang.setReceivedDate(rs.getString("received_date"));
                lotPenang.setReceivedBy(rs.getString("received_by"));
                lotPenang.setShipmentBy(rs.getString("shipment_by"));
                lotPenang.setShipmentDate(rs.getString("shipment_date"));
                lotPenang.setCreatedBy(rs.getString("created_by"));
                lotPenang.setCreatedDate(rs.getString("created_date"));
                lotPenang.setFlag(rs.getString("flag"));
                lotPenang.setClosedBy(rs.getString("closed_by"));
                lotPenang.setClosedVerification(rs.getString("closed_verification"));
                lotPenang.setClosedDate(rs.getString("closed_date"));
                lotPenang.setReceivedVerificationStatus(rs.getString("received_verification_status"));
                lotPenang.setReceivedVerificationDate(rs.getString("received_verification_date"));
                lotPenang.setReceivedMixStatus(rs.getString("received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("received_broken_remarks"));
                lotPenang.setTripTicketPath(rs.getString("trip_ticket_path"));
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
        return lotPenang;
    }

    public LotPenang getLotPenangByRequestId(String requestId) {
        String sql = "SELECT * FROM dots_lot_penang WHERE request_id = '" + requestId + "'";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setChamberId(rs.getString("chamber_id"));
                lotPenang.setChamberLevel(rs.getString("chamber_level"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("loading_remarks"));
                lotPenang.setTestCondition(rs.getString("test_condition"));
                lotPenang.setLoadingBy(rs.getString("loading_by"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("unloading_by"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setReceivedQuantity(rs.getString("received_quantity"));
                lotPenang.setReceivedDate(rs.getString("received_date"));
                lotPenang.setReceivedBy(rs.getString("received_by"));
                lotPenang.setShipmentBy(rs.getString("shipment_by"));
                lotPenang.setShipmentDate(rs.getString("shipment_date"));
                lotPenang.setCreatedBy(rs.getString("created_by"));
                lotPenang.setCreatedDate(rs.getString("created_date"));
                lotPenang.setFlag(rs.getString("flag"));
                lotPenang.setClosedBy(rs.getString("closed_by"));
                lotPenang.setClosedVerification(rs.getString("closed_verification"));
                lotPenang.setClosedDate(rs.getString("closed_date"));
                lotPenang.setReceivedVerificationDate(rs.getString("received_verification_date"));
                lotPenang.setReceivedVerificationStatus(rs.getString("received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("unloading_broken_remarks"));
                lotPenang.setPreVmDate(rs.getString("pre_vm_date"));
                lotPenang.setPostVmDate(rs.getString("post_vm_date"));
                lotPenang.setUnloadingQty(rs.getString("unloading_quantity"));
                lotPenang.setNewLotPenangId(rs.getString("new_lot_penang_id"));
                lotPenang.setOldLotPenangId(rs.getString("old_lot_penang_id"));
                lotPenang.setPotsNotify(rs.getString("pots_notify"));
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
        return lotPenang;
    }

    public LotPenang getLotPenangByRequestIdWithFlag0(String requestId) {
        String sql = "SELECT * FROM dots_lot_penang WHERE request_id = '" + requestId + "' AND flag = '0'";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setChamberId(rs.getString("chamber_id"));
                lotPenang.setChamberLevel(rs.getString("chamber_level"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("loading_remarks"));
                lotPenang.setTestCondition(rs.getString("test_condition"));
                lotPenang.setLoadingBy(rs.getString("loading_by"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("unloading_by"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setReceivedQuantity(rs.getString("received_quantity"));
                lotPenang.setReceivedDate(rs.getString("received_date"));
                lotPenang.setReceivedBy(rs.getString("received_by"));
                lotPenang.setShipmentBy(rs.getString("shipment_by"));
                lotPenang.setShipmentDate(rs.getString("shipment_date"));
                lotPenang.setCreatedBy(rs.getString("created_by"));
                lotPenang.setCreatedDate(rs.getString("created_date"));
                lotPenang.setFlag(rs.getString("flag"));
                lotPenang.setClosedBy(rs.getString("closed_by"));
                lotPenang.setClosedVerification(rs.getString("closed_verification"));
                lotPenang.setClosedDate(rs.getString("closed_date"));
                lotPenang.setReceivedVerificationDate(rs.getString("received_verification_date"));
                lotPenang.setReceivedVerificationStatus(rs.getString("received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("unloading_broken_remarks"));
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
        return lotPenang;
    }

    public LotPenang getLotPenangWithRequestTable(String lotPenangId) {
        String sql = "SELECT lo.*, re.*, "
                + "DATE_FORMAT(re.shipping_date,'%d-%M-%Y') AS shipping_date_rel_view, "
                + "DATE_FORMAT(lo.loading_date,'%d-%M-%Y %h:%i %p' ) AS loading_date_view, "
                + "DATE_FORMAT(lo.unloading_date,'%d-%M-%Y %h:%i %p') AS unloading_date_view , "
                + "DATE_FORMAT(lo.received_date,'%d-%M-%Y %h:%i %p') AS received_date_view,"
                + "DATE_FORMAT(lo.shipment_date,'%d-%M-%Y %h:%i %p') AS shipment_date_view, "
                + "DATE_FORMAT(re.estimate_loading_date,'%d-%M-%Y') AS estimate_loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d-%M-%Y') AS estimate_uloading_date_view,"
                + "DATE_FORMAT(lo.closed_date,'%d-%M-%Y %h:%i %p') AS closed_date_view, "
                + "DATE_FORMAT(lo.received_verification_date,'%d-%M-%Y %h:%i %p') AS received_verification_date_view, "
                + "DATE_FORMAT(lo.pre_vm_date,'%d-%M-%Y %h:%i %p') AS pre_vm_date_view, "
                + "DATE_FORMAT(lo.post_vm_date,'%d-%M-%Y %h:%i %p' ) AS post_vm_date_view "
                + "FROM dots_lot_penang lo, dots_request re "
                + "WHERE re.id = lo.request_id AND lo.id = '" + lotPenangId + "' "
                + "ORDER BY lo.id DESC";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
                lotPenang.setRequestId(rs.getString("lo.request_id"));
                lotPenang.setRmsEvent(rs.getString("lo.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("lo.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
                lotPenang.setClosedDateView(rs.getString("closed_date_view"));
                lotPenang.setReceivedVerificationDateView(rs.getString("received_verification_date_view"));
                lotPenang.setReceivedVerificationStatus(rs.getString("lo.received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("lo.received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("lo.received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("lo.received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("lo.received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("lo.received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("lo.received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("lo.unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("lo.unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("lo.unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("lo.unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("lo.unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("lo.unloading_broken_remarks"));
                lotPenang.setPreVmDate(rs.getString("lo.pre_vm_date"));
                lotPenang.setPreVmDateView(rs.getString("pre_vm_date_view"));
                lotPenang.setUnloadingQty(rs.getString("lo.unloading_quantity"));
                lotPenang.setPostVmDate(rs.getString("lo.post_vm_date"));
                lotPenang.setTripTicketPath(rs.getString("lo.trip_ticket_path"));
                lotPenang.setPostVmDateView(rs.getString("post_vm_date_view"));
                lotPenang.setPotsNotify(rs.getString("lo.pots_notify"));
                lotPenang.setOldLotPenangId(rs.getString("lo.old_lot_penang_id"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.expected_condition"));
                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
                lotPenang.setShipmentDateView(rs.getString("shipment_date_view"));
                lotPenang.setShipmentFromRelView(rs.getString("shipping_date_rel_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setReceivedDateView(rs.getString("received_date_view"));

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
        return lotPenang;
    }

    public LotPenang getLotPenangWithRequestTableAndRmaTable(String lotPenangId) {
        String sql = "SELECT lo.*, re.*, "
                + "rm.rma_remarks1,"
                + "rm.rma_dispo1,"
                + "rm.rma_dispo1_remarks,"
                + "rm.rma_dispo1_by,"
                + "rm.rma_remarks2,"
                + "rm.rma_dispo2,"
                + "rm.rma_dispo2_remarks,"
                + "rm.rma_dispo2_by,"
                + "rm.flag as rm_flag,"
                + "rm.rma_count,"
                + "DATE_FORMAT(re.shipping_date,'%d-%M-%Y') AS shipping_date_rel_view, "
                + "DATE_FORMAT(lo.loading_date,'%d-%M-%Y %h:%i %p' ) AS loading_date_view, "
                + "DATE_FORMAT(lo.unloading_date,'%d-%M-%Y %h:%i %p') AS unloading_date_view , "
                + "DATE_FORMAT(lo.received_date,'%d-%M-%Y %h:%i %p') AS received_date_view,"
                + "DATE_FORMAT(lo.shipment_date,'%d-%M-%Y %h:%i %p') AS shipment_date_view, "
                + "DATE_FORMAT(re.estimate_loading_date,'%d-%M-%Y') AS estimate_loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d-%M-%Y') AS estimate_uloading_date_view,"
                + "DATE_FORMAT(lo.closed_date,'%d-%M-%Y %h:%i %p') AS closed_date_view, "
                + "DATE_FORMAT(lo.received_verification_date,'%d-%M-%Y %h:%i %p') AS received_verification_date_view, "
                + "DATE_FORMAT(lo.pre_vm_date,'%d-%M-%Y %h:%i %p') AS pre_vm_date_view, "
                + "DATE_FORMAT(lo.post_vm_date,'%d-%M-%Y %h:%i %p' ) AS post_vm_date_view, "
                + "DATE_FORMAT(rm.rma_date1,'%d-%M-%Y %h:%i %p' ) AS rma_date_1_view, "
                + "DATE_FORMAT(rm.rma_dispo1_date,'%d-%M-%Y %h:%i %p' ) AS rma_dispo1_date_view, "
                + "DATE_FORMAT(rm.rma_date2,'%d-%M-%Y %h:%i %p' ) AS rma_date_2_view, "
                + "DATE_FORMAT(rm.rma_dispo2_date,'%d-%M-%Y %h:%i %p' ) AS rma_dispo2_date_view "
                + "FROM dots_lot_penang lo LEFT JOIN dots_rma rm ON rm.id = lo.rma_id , dots_request re "
                + "WHERE re.id = lo.request_id AND lo.id = '" + lotPenangId + "' "
                + "ORDER BY lo.id DESC";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
                lotPenang.setRequestId(rs.getString("lo.request_id"));
                lotPenang.setRmsEvent(rs.getString("lo.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("lo.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
                lotPenang.setClosedDateView(rs.getString("closed_date_view"));
                lotPenang.setReceivedVerificationDateView(rs.getString("received_verification_date_view"));
                lotPenang.setReceivedVerificationStatus(rs.getString("lo.received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("lo.received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("lo.received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("lo.received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("lo.received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("lo.received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("lo.received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("lo.unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("lo.unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("lo.unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("lo.unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("lo.unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("lo.unloading_broken_remarks"));
                lotPenang.setPreVmDate(rs.getString("lo.pre_vm_date"));
                lotPenang.setPreVmDateView(rs.getString("pre_vm_date_view"));
                lotPenang.setUnloadingQty(rs.getString("lo.unloading_quantity"));
                lotPenang.setPostVmDate(rs.getString("lo.post_vm_date"));
                lotPenang.setTripTicketPath(rs.getString("lo.trip_ticket_path"));
                lotPenang.setPostVmDateView(rs.getString("post_vm_date_view"));
                lotPenang.setPotsNotify(rs.getString("lo.pots_notify"));
                lotPenang.setOldLotPenangId(rs.getString("lo.old_lot_penang_id"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.expected_condition"));
                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
                lotPenang.setShipmentDateView(rs.getString("shipment_date_view"));
                lotPenang.setShipmentFromRelView(rs.getString("shipping_date_rel_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setReceivedDateView(rs.getString("received_date_view"));
//                lotPenang.setMultiplier(rs.getString("re.multiplier"));

                //rma
                lotPenang.setRmaRemarks1(rs.getString("rm.rma_remarks1"));
                lotPenang.setRmaDispo1(rs.getString("rm.rma_dispo1"));
                lotPenang.setRmaDispo1Remarks(rs.getString("rm.rma_dispo1_remarks"));
                lotPenang.setRmaDispo1By(rs.getString("rm.rma_dispo1_by"));
                lotPenang.setRmaRemarks2(rs.getString("rm.rma_remarks2"));
                lotPenang.setRmaDispo2(rs.getString("rm.rma_dispo2"));
                lotPenang.setRmaDispo2Remarks(rs.getString("rm.rma_dispo2_remarks"));
                lotPenang.setRmaDispo2By(rs.getString("rm.rma_dispo2_by"));
                lotPenang.setRmaFlag(rs.getString("rm_flag"));
                lotPenang.setRmaDate1View(rs.getString("rma_date_1_view"));
                lotPenang.setRmaDate2View(rs.getString("rma_date_2_view"));
                lotPenang.setRmaDispo1DateView(rs.getString("rma_dispo1_date_view"));
                lotPenang.setRmaDispo2DateView(rs.getString("rma_dispo2_date_view"));
                lotPenang.setRmaId(rs.getString("lo.rma_id"));
                lotPenang.setRmaCount(rs.getString("rm.rma_count"));

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
        return lotPenang;
    }

    public LotPenang getLotPenangWithRequestTableByRequestIDWithFlag0(String requestId) {
        String sql = "SELECT lo.*, re.*, "
                + "DATE_FORMAT(re.shipping_date,'%d-%M-%Y') AS shipping_date_rel_view, "
                + "DATE_FORMAT(lo.loading_date,'%d-%M-%Y %h:%i %p' ) AS loading_date_view, "
                + "DATE_FORMAT(lo.unloading_date,'%d-%M-%Y %h:%i %p') AS unloading_date_view , "
                + "DATE_FORMAT(lo.received_date,'%d-%M-%Y %h:%i %p') AS received_date_view,"
                + "DATE_FORMAT(lo.shipment_date,'%d-%M-%Y %h:%i %p') AS shipment_date_view, "
                + "DATE_FORMAT(re.estimate_loading_date,'%d-%M-%Y') AS estimate_loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d-%M-%Y') AS estimate_uloading_date_view,"
                + "DATE_FORMAT(lo.closed_date,'%d-%M-%Y %h:%i %p') AS closed_date_view, "
                + "DATE_FORMAT(lo.received_verification_date,'%d-%M-%Y %h:%i %p') AS received_verification_date_view "
                + "FROM dots_lot_penang lo, dots_request re "
                + "WHERE re.id = lo.request_id AND lo.request_id = '" + requestId + "' AND lo.flag = '0' "
                + "ORDER BY lo.id DESC";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
                lotPenang.setRequestId(rs.getString("lo.request_id"));
                lotPenang.setRmsEvent(rs.getString("lo.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("lo.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
                lotPenang.setClosedDateView(rs.getString("closed_date_view"));
                lotPenang.setReceivedVerificationDateView(rs.getString("received_verification_date_view"));
                lotPenang.setReceivedVerificationStatus(rs.getString("lo.received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("lo.received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("lo.received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("lo.received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("lo.received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("lo.received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("lo.received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("lo.unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("lo.unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("lo.unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("lo.unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("lo.unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("lo.unloading_broken_remarks"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.expected_condition"));
                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
                lotPenang.setShipmentDateView(rs.getString("shipment_date_view"));
                lotPenang.setShipmentFromRelView(rs.getString("shipping_date_rel_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setReceivedDateView(rs.getString("received_date_view"));
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
        return lotPenang;
    }

    public LotPenang getLotPenangforQueryByReqId(String requestId) {
        String sql = "SELECT re.*, lo.*,"
                + "DATE_FORMAT(re.shipping_date,'%d %M %Y %h:%i %p') AS rel_shipping_date_view,"
                + "DATE_FORMAT(lo.received_date,'%d %M %Y %h:%i %p') AS penang_received_date_view,"
                + "DATE_FORMAT(lo.loading_date,'%d %M %Y %h:%i %p') AS loading_date_view,"
                + "DATE_FORMAT(lo.unloading_date,'%d %M %Y %h:%i %p') AS unloading_date_view,"
                + "DATE_FORMAT(re.estimate_loading_date,'%d %M %Y %h:%i %p') AS estimate_loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d %M %Y %h:%i %p') AS estimate_unloading_date_view,"
                + "DATE_FORMAT(lo.shipment_date,'%d %M %Y %h:%i %p') AS penang_shipping_date_view,"
                + "DATE_FORMAT(lo.pre_vm_date,'%d %M %Y %h:%i %p') AS pre_vm_date_view,"
                + "DATE_FORMAT(lo.post_vm_date,'%d %M %Y %h:%i %p') AS post_vm_date_view,"
                + "DATE_FORMAT(lo.closed_date,'%d %M %Y %h:%i %p') AS rel_received_date_view "
                + "FROM dots_request re LEFT JOIN dots_lot_penang lo ON lo.request_id = re.id "
                + "WHERE re.id = '" + requestId + "' ORDER BY re.id ASC";
        LotPenang lotPenang = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
                lotPenang.setRequestId(rs.getString("re.id"));
                lotPenang.setRmsEvent(rs.getString("re.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("re.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
                lotPenang.setClosedDateView(rs.getString("rel_received_date_view"));
                lotPenang.setPreVmDate(rs.getString("lo.pre_vm_date"));
                lotPenang.setPostVmDate(rs.getString("lo.post_vm_date"));
                lotPenang.setPreVmDateView(rs.getString("pre_vm_date_view"));
                lotPenang.setPostVmDateView(rs.getString("post_vm_date_view"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.test_condition"));
                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_unloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
                lotPenang.setShipmentDateView(rs.getString("penang_shipping_date_view"));
                lotPenang.setShipmentFromRelView(rs.getString("rel_shipping_date_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setReceivedDateView(rs.getString("penang_received_date_view"));
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
        return lotPenang;
    }

    public List<LotPenang> getLotPenangList() {
        String sql = "SELECT * FROM dots_lot_penang ORDER BY id ASC";
        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LotPenang lotPenang;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setChamberId(rs.getString("chamber_id"));
                lotPenang.setChamberLevel(rs.getString("chamber_level"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("loading_remarks"));
                lotPenang.setTestCondition(rs.getString("test_condition"));
                lotPenang.setLoadingBy(rs.getString("loading_by"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("unloading_by"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setReceivedQuantity(rs.getString("received_quantity"));
                lotPenang.setReceivedDate(rs.getString("received_date"));
                lotPenang.setReceivedBy(rs.getString("received_by"));
                lotPenang.setShipmentBy(rs.getString("shipment_by"));
                lotPenang.setShipmentDate(rs.getString("shipment_date"));
                lotPenang.setCreatedBy(rs.getString("created_by"));
                lotPenang.setCreatedDate(rs.getString("created_date"));
                lotPenang.setFlag(rs.getString("flag"));
                lotPenangList.add(lotPenang);
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
        return lotPenangList;
    }

    public List<LotPenang> getLotPenangListWithRequestTable() {
        String sql = "SELECT "
                + "lo.*, "
                + "re.*, "
                + "DATE_FORMAT(re.shipping_date,'%d-%M-%Y') AS shipping_date_rel_view, DATE_FORMAT(lo.loading_date,'%d-%M-%Y') AS loading_date_view,"
                + " DATE_FORMAT(lo.unloading_date,'%d-%M-%Y') AS unloading_date_view , "
                + "DATE_FORMAT(lo.received_date,'%d-%M-%Y') AS received_date_view,"
                + "DATE_FORMAT(lo.shipment_date,'%d-%M-%Y') AS shipment_date_view, "
                + "DATE_FORMAT(re.estimate_loading_date,'%d-%M-%Y') AS estimate_loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d-%M-%Y') AS estimate_uloading_date_view, "
                + "DATE_FORMAT(lo.received_verification_date,'%d-%M-%Y') AS received_verification_date_view "
                + "FROM dots_lot_penang lo, dots_request re "
                + "WHERE re.id = lo.request_id AND lo.flag = '0' AND lo.status <> 'Closed' "
                + "ORDER BY lo.id DESC";
        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LotPenang lotPenang;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
                lotPenang.setRequestId(rs.getString("lo.request_id"));
                lotPenang.setRmsEvent(rs.getString("lo.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("lo.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
                lotPenang.setReceivedVerificationDateView(rs.getString("received_verification_date_view"));
                lotPenang.setReceivedVerificationStatus(rs.getString("lo.received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("lo.received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("lo.received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("lo.received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("lo.received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("lo.received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("lo.received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("lo.unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("lo.unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("lo.unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("lo.unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("lo.unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("lo.unloading_broken_remarks"));
                lotPenang.setPotsNotify(rs.getString("lo.pots_notify"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.test_condition"));
                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
                lotPenang.setShipmentDateView(rs.getString("shipment_date_view"));
                lotPenang.setShipmentFromRelView(rs.getString("shipping_date_rel_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setReceivedDateView(rs.getString("received_date_view"));
                lotPenangList.add(lotPenang);
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
        return lotPenangList;
    }

    public List<LotPenang> getLotPenangListWithRequestTableAndRmaTable() {
        String sql = "SELECT lo.*, re.*, "
                + "rm.rma_remarks1,"
                + "rm.rma_dispo1,"
                //                + "rm.rma_dispo1_remarks,"
                //                + "rm.rma_dispo1_by,"
                + "rm.rma_remarks2,"
                + "rm.rma_dispo2,"
                //                + "rm.rma_dispo2_remarks,"
                //                + "rm.rma_dispo2_by,"
                + "rm.flag as rm_flag,"
                + "rm.rma_count,"
                //                + "DATE_FORMAT(re.shipping_date,'%d-%M-%Y') AS shipping_date_rel_view, "
                + "DATE_FORMAT(lo.loading_date,'%d-%M-%Y %h:%i %p' ) AS loading_date_view, "
                + "DATE_FORMAT(lo.unloading_date,'%d-%M-%Y %h:%i %p') AS unloading_date_view , "
                //                + "DATE_FORMAT(lo.received_date,'%d-%M-%Y %h:%i %p') AS received_date_view,"
                //                + "DATE_FORMAT(lo.shipment_date,'%d-%M-%Y %h:%i %p') AS shipment_date_view, "
                + "DATE_FORMAT(re.estimate_loading_date,'%d-%M-%Y') AS estimate_loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d-%M-%Y') AS estimate_uloading_date_view "
                //                + "DATE_FORMAT(lo.closed_date,'%d-%M-%Y %h:%i %p') AS closed_date_view, "
                //                + "DATE_FORMAT(lo.received_verification_date,'%d-%M-%Y %h:%i %p') AS received_verification_date_view, "
                //                + "DATE_FORMAT(lo.pre_vm_date,'%d-%M-%Y %h:%i %p') AS pre_vm_date_view, "
                //                + "DATE_FORMAT(lo.post_vm_date,'%d-%M-%Y %h:%i %p' ) AS post_vm_date_view, "
                //                + "DATE_FORMAT(rm.rma_date1,'%d-%M-%Y %h:%i %p' ) AS rma_date_1_view, "
                //                + "DATE_FORMAT(rm.rma_dispo1_date,'%d-%M-%Y %h:%i %p' ) AS rma_dispo1_date_view, "
                //                + "DATE_FORMAT(rm.rma_date2,'%d-%M-%Y %h:%i %p' ) AS rma_date_2_view, "
                //                + "DATE_FORMAT(rm.rma_dispo2_date,'%d-%M-%Y %h:%i %p' ) AS rma_dispo2_date_view "
                + "FROM dots_lot_penang lo LEFT JOIN dots_rma rm ON rm.id = lo.rma_id , dots_request re "
                + "WHERE re.id = lo.request_id AND lo.flag = '0' AND lo.status <> 'Closed'"
                + "ORDER BY lo.id DESC";
        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LotPenang lotPenang;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
                lotPenang.setRequestId(rs.getString("lo.request_id"));
                lotPenang.setRmsEvent(rs.getString("lo.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("lo.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
//                lotPenang.setClosedDateView(rs.getString("closed_date_view"));
//                lotPenang.setReceivedVerificationDateView(rs.getString("received_verification_date_view"));
                lotPenang.setReceivedVerificationStatus(rs.getString("lo.received_verification_status"));
                lotPenang.setReceivedMixStatus(rs.getString("lo.received_mix_status"));
                lotPenang.setReceivedMixRemarks(rs.getString("lo.received_mix_remarks"));
                lotPenang.setReceivedDemountStatus(rs.getString("lo.received_demount_status"));
                lotPenang.setReceivedDemountRemarks(rs.getString("lo.received_demount_remarks"));
                lotPenang.setReceivedBrokenStatus(rs.getString("lo.received_broken_status"));
                lotPenang.setReceivedBrokenRemarks(rs.getString("lo.received_broken_remarks"));
                lotPenang.setUnloadingMixStatus(rs.getString("lo.unloading_mix_status"));
                lotPenang.setUnloadingMixRemarks(rs.getString("lo.unloading_mix_remarks"));
                lotPenang.setUnloadingDemountStatus(rs.getString("lo.unloading_demount_status"));
                lotPenang.setUnloadingDemountRemarks(rs.getString("lo.unloading_demount_remarks"));
                lotPenang.setUnloadingBrokenStatus(rs.getString("lo.unloading_broken_status"));
                lotPenang.setUnloadingBrokenRemarks(rs.getString("lo.unloading_broken_remarks"));
                lotPenang.setPreVmDate(rs.getString("lo.pre_vm_date"));
//                lotPenang.setPreVmDateView(rs.getString("pre_vm_date_view"));
                lotPenang.setUnloadingQty(rs.getString("lo.unloading_quantity"));
                lotPenang.setPostVmDate(rs.getString("lo.post_vm_date"));
                lotPenang.setTripTicketPath(rs.getString("lo.trip_ticket_path"));
//                lotPenang.setPostVmDateView(rs.getString("post_vm_date_view"));
                lotPenang.setPotsNotify(rs.getString("lo.pots_notify"));
                lotPenang.setOldLotPenangId(rs.getString("lo.old_lot_penang_id"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.expected_condition"));
                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
//                lotPenang.setShipmentDateView(rs.getString("shipment_date_view"));
//                lotPenang.setShipmentFromRelView(rs.getString("shipping_date_rel_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
//                lotPenang.setReceivedDateView(rs.getString("received_date_view"));

                //rma
                lotPenang.setRmaRemarks1(rs.getString("rm.rma_remarks1"));
                lotPenang.setRmaDispo1(rs.getString("rm.rma_dispo1"));
//                lotPenang.setRmaDispo1Remarks(rs.getString("rm.rma_dispo1_remarks"));
//                lotPenang.setRmaDispo1By(rs.getString("rm.rma_dispo1_by"));
                lotPenang.setRmaRemarks2(rs.getString("rm.rma_remarks2"));
                lotPenang.setRmaDispo2(rs.getString("rm.rma_dispo2"));
//                lotPenang.setRmaDispo2Remarks(rs.getString("rm.rma_dispo2_remarks"));
//                lotPenang.setRmaDispo2By(rs.getString("rm.rma_dispo2_by"));
                lotPenang.setRmaFlag(rs.getString("rm_flag"));
//                lotPenang.setRmaDate1View(rs.getString("rma_date_1_view"));
//                lotPenang.setRmaDate2View(rs.getString("rma_date_2_view"));
//                lotPenang.setRmaDispo1DateView(rs.getString("rma_dispo1_date_view"));
//                lotPenang.setRmaDispo2DateView(rs.getString("rma_dispo2_date_view"));
                lotPenang.setRmaId(rs.getString("lo.rma_id"));
                lotPenang.setRmaCount(rs.getString("rm.rma_count"));
                lotPenang.setPostVmId(rs.getString("lo.post_vm_id"));
                lotPenangList.add(lotPenang);
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
        return lotPenangList;
    }

    // left join
//    public List<LotPenang> getLotPenangforQuery(String query) {
//        String sql = "SELECT re.*, lo.*,"
//                + "DATE_FORMAT(re.shipping_date,'%d %M %Y %h:%i %p') AS rel_shipping_date_view,"
//                + "DATE_FORMAT(lo.received_date,'%d %M %Y %h:%i %p') AS penang_received_date_view,"
//                + "DATE_FORMAT(lo.loading_date,'%d %M %Y %h:%i %p') AS loading_date_view,"
//                + "DATE_FORMAT(lo.unloading_date,'%d %M %Y %h:%i %p') AS unloading_date_view,"
//                + "DATE_FORMAT(lo.shipment_date,'%d %M %Y %h:%i %p') AS penang_shipping_date_view,"
//                + "DATE_FORMAT(lo.closed_date,'%d %M %Y %h:%i %p') AS rel_received_date_view "
//                + "FROM dots_request re LEFT JOIN dots_lot_penang lo ON lo.request_id = re.id "
//                + "WHERE " + query + " ORDER BY re.id ASC";
//        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            LotPenang lotPenang;
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                lotPenang = new LotPenang();
//                lotPenang.setId(rs.getString("lo.id"));
//                lotPenang.setDoNumber(rs.getString("lo.do_number"));
////                lotPenang.setRequestId(rs.getString("lo.request_id"));
//                lotPenang.setRequestId(rs.getString("re.id"));
//                lotPenang.setRmsEvent(rs.getString("re.rms_event"));
//                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
//                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
//                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
//                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
//                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
//                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
//                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
//                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
//                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
//                lotPenang.setStatus(rs.getString("re.status"));
//                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
//                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
//                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
//                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
//                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
//                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
//                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
//                lotPenang.setFlag(rs.getString("lo.flag"));
//                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
//                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
//                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
//
//                lotPenang.setRms(rs.getString("re.rms"));
//                lotPenang.setLot(rs.getString("re.lot_id"));
//                lotPenang.setEvent(rs.getString("re.event"));
//                lotPenang.setDevice(rs.getString("re.device"));
//                lotPenang.setPackages(rs.getString("re.package"));
//                lotPenang.setInterval(rs.getString("re.intervals"));
//                lotPenang.setGts(rs.getString("re.gts"));
//                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
//                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
//                lotPenang.setExpectedCondition(rs.getString("re.test_condition"));
////                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
////                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
//                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
//                lotPenang.setShipmentDateView(rs.getString("penang_shipping_date_view"));
//                lotPenang.setShipmentFromRelView(rs.getString("rel_shipping_date_view"));
//                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
//                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
//                lotPenang.setReceivedDateView(rs.getString("penang_received_date_view"));
//                lotPenangList.add(lotPenang);
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return lotPenangList;
//    }
//    display lotpenang only
    public List<LotPenang> getLotPenangforQuery(String query) {
        String sql = "SELECT re.*, lo.*,"
                + "DATE_FORMAT(re.shipping_date,'%d %M %Y %h:%i %p') AS rel_shipping_date_view,"
                + "DATE_FORMAT(lo.received_date,'%d %M %Y %h:%i %p') AS penang_received_date_view,"
                + "DATE_FORMAT(lo.loading_date,'%d %M %Y %h:%i %p') AS loading_date_view,"
                + "DATE_FORMAT(lo.unloading_date,'%d %M %Y %h:%i %p') AS unloading_date_view,"
                + "DATE_FORMAT(lo.shipment_date,'%d %M %Y %h:%i %p') AS penang_shipping_date_view,"
                + "DATE_FORMAT(lo.pre_vm_date,'%d %M %Y %h:%i %p') AS pre_vm_date_view,"
                + "DATE_FORMAT(lo.post_vm_date,'%d %M %Y %h:%i %p') AS post_vm_date_view,"
                + "DATE_FORMAT(lo.closed_date,'%d %M %Y %h:%i %p') AS rel_received_date_view "
                //                + "FROM dots_request re LEFT JOIN dots_lot_penang lo ON lo.request_id = re.id "
                //                + "WHERE " + query + " ORDER BY re.id ASC";
                + "FROM dots_request re, dots_lot_penang lo "
                + "WHERE lo.request_id = re.id AND " + query + " ORDER BY re.event, re.rms_event, re.intervals DESC";
        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LotPenang lotPenang;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("lo.id"));
                lotPenang.setDoNumber(rs.getString("lo.do_number"));
//                lotPenang.setRequestId(rs.getString("lo.request_id"));
                lotPenang.setRequestId(rs.getString("re.id"));
                lotPenang.setRmsEvent(rs.getString("re.rms_event"));
                lotPenang.setChamberId(rs.getString("lo.chamber_id"));
                lotPenang.setChamberLevel(rs.getString("lo.chamber_level"));
                lotPenang.setLoadingDate(rs.getString("lo.loading_date"));
                lotPenang.setLoadingRemarks(rs.getString("lo.loading_remarks"));
                lotPenang.setTestCondition(rs.getString("lo.test_condition"));
                lotPenang.setLoadingBy(rs.getString("lo.loading_by"));
                lotPenang.setUnloadingDate(rs.getString("lo.unloading_date"));
                lotPenang.setUnloadingRemarks(rs.getString("lo.unloading_remarks"));
                lotPenang.setUnloadingBy(rs.getString("lo.unloading_by"));
                lotPenang.setStatus(rs.getString("re.status"));
                lotPenang.setReceivedQuantity(rs.getString("lo.received_quantity"));
                lotPenang.setReceivedDate(rs.getString("lo.received_date"));
                lotPenang.setReceivedBy(rs.getString("lo.received_by"));
                lotPenang.setShipmentBy(rs.getString("lo.shipment_by"));
                lotPenang.setShipmentDate(rs.getString("lo.shipment_date"));
                lotPenang.setCreatedBy(rs.getString("lo.created_by"));
                lotPenang.setCreatedDate(rs.getString("lo.created_date"));
                lotPenang.setFlag(rs.getString("lo.flag"));
                lotPenang.setClosedBy(rs.getString("lo.closed_by"));
                lotPenang.setClosedVerification(rs.getString("lo.closed_verification"));
                lotPenang.setClosedDate(rs.getString("lo.closed_date"));
                lotPenang.setPreVmDate(rs.getString("lo.pre_vm_date"));
                lotPenang.setPostVmDate(rs.getString("lo.post_vm_date"));
                lotPenang.setPreVmDateView(rs.getString("pre_vm_date_view"));
                lotPenang.setPostVmDateView(rs.getString("post_vm_date_view"));

                lotPenang.setRms(rs.getString("re.rms"));
                lotPenang.setLot(rs.getString("re.lot_id"));
                lotPenang.setEvent(rs.getString("re.event"));
                lotPenang.setDevice(rs.getString("re.device"));
                lotPenang.setPackages(rs.getString("re.package"));
                lotPenang.setInterval(rs.getString("re.intervals"));
                lotPenang.setGts(rs.getString("re.gts"));
                lotPenang.setExpectedChamber(rs.getString("re.chamber_id"));
                lotPenang.setExpectedChamberLevel(rs.getString("re.chamber_level"));
                lotPenang.setExpectedCondition(rs.getString("re.test_condition"));
//                lotPenang.setExpectedLoadingDate(rs.getString("estimate_loading_date_view"));
//                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_uloading_date_view"));
                lotPenang.setExpectedQuantity(rs.getString("re.quantity"));
                lotPenang.setShipmentDateView(rs.getString("penang_shipping_date_view"));
                lotPenang.setShipmentFromRelView(rs.getString("rel_shipping_date_view"));
                lotPenang.setLoadingDateView(rs.getString("loading_date_view"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setReceivedDateView(rs.getString("penang_received_date_view"));
                lotPenangList.add(lotPenang);
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
        return lotPenangList;
    }

    public Integer getCountId(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_lot_penang WHERE id = '" + id + "'"
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
                    "SELECT COUNT(*) AS count FROM dots_lot_penang WHERE request_id = '" + requestId + "'"
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

    public Integer getCountRequestIdWithFlag0(String requestId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_lot_penang WHERE request_id = '" + requestId + "' AND flag = '0'"
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

    public List<LotPenang> getLotPenangListForTimelapseReport() {
        String sql = "SELECT re.id AS id, "
                + "re.request_id AS request_id, "
                + "re.rms_event AS rms_event, "
                + "re.do_number AS do_number, "
                + "sh.intervals AS intervals, "
                + "re.status AS status, "
                + "sh.device AS device, "
                + "sh.package AS packages, "
                + "re.unloading_date AS unloading_date, "
                + "sh.estimate_unloading_date AS estimate_unloading_date, "
                + "NOW() AS now_date, "
                + "DATE_FORMAT(NOW(),'%d %M %Y %h:%i %p') AS now_date_view, "
                + "DATE_FORMAT(sh.shipping_date,'%d %M %Y %h:%i %p') AS ship_to_penang, "
                + "DATE_FORMAT(re.received_date,'%d %M %Y %h:%i %p') AS penang_received_date, "
                + "DATE_FORMAT(sh.estimate_unloading_date,'%d %M %Y %h:%i %p') AS estimate_unloading_date_view, "
                + "DATE_FORMAT(re.loading_date,'%d %M %Y %h:%i %p') AS loading_date, "
                + "DATE_FORMAT(re.unloading_date,'%d %M %Y %h:%i %p') AS unloading_date_view, "
                + "DATE_FORMAT(re.shipment_date,'%d %M %Y %h:%i %p') AS ship_to_rel, "
                + "DATE_FORMAT(re.closed_date,'%d %M %Y %h:%i %p') AS closed_date, "
                //ship to received
                + "HOUR(TIMEDIFF(sh.shipping_date,re.received_date)) AS ship_to_received_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(sh.shipping_date,re.received_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(sh.shipping_date,re.received_date)), 24), ' hours, ', MINUTE(TIMEDIFF(sh.shipping_date,re.received_date)), ' mins, ', SECOND(TIMEDIFF(sh.shipping_date,re.received_date)), ' secs') AS ship_to_received, "
                + "HOUR(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))) AS ship_to_received_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))), ' secs') AS ship_to_received_temp, "
                //received to load
                + "HOUR(TIMEDIFF(re.received_date,re.loading_date)) AS received_to_load_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.received_date,re.loading_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.received_date,re.loading_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.received_date,re.loading_date)), ' mins, ', SECOND(TIMEDIFF(re.received_date,re.loading_date)), ' secs') AS received_to_load, "
                + "HOUR(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))) AS received_to_load_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))), ' secs') AS received_to_load_temp, "
                //unload to ship
                + "HOUR(TIMEDIFF(re.unloading_date,re.shipment_date)) AS unload_to_ship_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.unloading_date,re.shipment_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.unloading_date,re.shipment_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.unloading_date,re.shipment_date)), ' mins, ', SECOND(TIMEDIFF(re.unloading_date,re.shipment_date)), ' secs') AS unload_to_ship, "
                + "HOUR(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))) AS unload_to_ship_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))), ' secs') AS unload_to_ship_temp, "
                //ship to close
                + "HOUR(TIMEDIFF(re.shipment_date,re.closed_date)) AS ship_to_close_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.shipment_date,re.closed_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.shipment_date,re.closed_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.shipment_date,re.closed_date)), ' mins, ', SECOND(TIMEDIFF(re.shipment_date,re.closed_date)), ' secs') AS ship_to_close, "
                + "HOUR(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))) AS ship_to_close_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))), ' secs') AS ship_to_close_temp, "
                //actual vs estimate unloading
                + "HOUR(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)) AS estimate_unloading_actual_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)), ' mins, ', SECOND(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)), ' secs') AS estimate_unloading_actual, "
                + "HOUR(TIMEDIFF(sh.estimate_unloading_date,IFNULL(re.unloading_date,NOW()))) AS estimate_unloading_actual_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)), 24), ' hours, ', MINUTE(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)), ' mins, ', SECOND(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)), ' secs') AS estimate_unloading_actual_temp "
                + "FROM dots_lot_penang re "
                + "LEFT JOIN dots_request sh ON sh.id = re.request_id "
                + "WHERE re.flag = '0' "
                + "ORDER BY sh.rms ASC";
        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LotPenang lotPenang;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setInterval(rs.getString("intervals"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setDevice(rs.getString("device"));
                lotPenang.setPackages(rs.getString("packages"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_unloading_date"));
                lotPenang.setExpectedUnloadingDateView(rs.getString("estimate_unloading_date_view"));
                lotPenang.setNowDate(rs.getString("now_date"));
                lotPenang.setNowDateView(rs.getString("now_date_view"));
                lotPenang.setShipmentFromRel(rs.getString("ship_to_penang"));
                lotPenang.setReceivedDate(rs.getString("penang_received_date"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setShipmentDate(rs.getString("ship_to_rel"));
                lotPenang.setClosedDate(rs.getString("closed_date"));
                //ship to penang received
                lotPenang.setShipToReceived24(rs.getString("ship_to_received_24"));
                lotPenang.setShipToReceivedDetail(rs.getString("ship_to_received"));
                lotPenang.setShipToReceivedIfNull24(rs.getString("ship_to_received_temp_24"));
                lotPenang.setShipToReceivedIfNullDetail(rs.getString("ship_to_received_temp"));
                //received to loading       
                lotPenang.setReceivedToLoad24(rs.getString("received_to_load_24"));
                lotPenang.setReceivedToLoadDetail(rs.getString("received_to_load"));
                lotPenang.setReceivedToLoadIfNull24(rs.getString("received_to_load_temp_24"));
                lotPenang.setReceivedToLoadIfNullDetail(rs.getString("received_to_load_temp"));
                //unload to ship
                lotPenang.setUnloadToShip24(rs.getString("unload_to_ship_24"));
                lotPenang.setUnloadToShipDetail(rs.getString("unload_to_ship"));
                lotPenang.setUnloadToShipIfNull24(rs.getString("unload_to_ship_temp_24"));
                lotPenang.setUnloadToShipIfNullDetail(rs.getString("unload_to_ship_temp"));
                //ship to close
                lotPenang.setShipToClosed24(rs.getString("ship_to_close_24"));
                lotPenang.setShipToClosedDetail(rs.getString("ship_to_close"));
                lotPenang.setShipToClosedIfNull24(rs.getString("ship_to_close_temp_24"));
                lotPenang.setShipToClosedIfNullDetail(rs.getString("ship_to_close_temp"));
                //actual vs estimate unloading
                lotPenang.setActualVsEstimateUnloading24(rs.getString("estimate_unloading_actual_24"));
                lotPenang.setActualVsEstimateUnloadingDetail(rs.getString("estimate_unloading_actual"));
                lotPenang.setActualVsEstimateUnloadingIfNull24(rs.getString("estimate_unloading_actual_temp_24"));
                lotPenang.setActualVsEstimateUnloadingIfNullDetail(rs.getString("estimate_unloading_actual_temp"));
                lotPenangList.add(lotPenang);
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
        return lotPenangList;
    }

    public List<LotPenang> getLotPenangListForTimelapseReportForAllLot() {
        String sql = "SELECT re.id AS id, "
                + "re.request_id AS request_id, "
                + "re.rms_event AS rms_event, "
                + "re.do_number AS do_number, "
                + "sh.intervals AS intervals, "
                + "re.status AS status, "
                + "sh.device AS device, "
                + "sh.package AS packages, "
                + "re.unloading_date AS unloading_date, "
                + "sh.estimate_unloading_date AS estimate_unloading_date, "
                + "NOW() AS now_date, "
                + "DATE_FORMAT(NOW(),'%d %M %Y %h:%i %p') AS now_date_view, "
                + "DATE_FORMAT(sh.shipping_date,'%d %M %Y %h:%i %p') AS ship_to_penang, "
                + "DATE_FORMAT(re.received_date,'%d %M %Y %h:%i %p') AS penang_received_date, "
                + "DATE_FORMAT(sh.estimate_unloading_date,'%d %M %Y %h:%i %p') AS estimate_unloading_date_view, "
                + "DATE_FORMAT(re.loading_date,'%d %M %Y %h:%i %p') AS loading_date, "
                + "DATE_FORMAT(re.unloading_date,'%d %M %Y %h:%i %p') AS unloading_date_view, "
                + "DATE_FORMAT(re.shipment_date,'%d %M %Y %h:%i %p') AS ship_to_rel, "
                + "DATE_FORMAT(re.closed_date,'%d %M %Y %h:%i %p') AS closed_date, "
                //ship to received
                + "HOUR(TIMEDIFF(sh.shipping_date,re.received_date)) AS ship_to_received_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(sh.shipping_date,re.received_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(sh.shipping_date,re.received_date)), 24), ' hours, ', MINUTE(TIMEDIFF(sh.shipping_date,re.received_date)), ' mins, ', SECOND(TIMEDIFF(sh.shipping_date,re.received_date)), ' secs') AS ship_to_received, "
                + "HOUR(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))) AS ship_to_received_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(sh.shipping_date,IFNULL(re.received_date,NOW()))), ' secs') AS ship_to_received_temp, "
                //received to load
                + "HOUR(TIMEDIFF(re.received_date,re.loading_date)) AS received_to_load_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.received_date,re.loading_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.received_date,re.loading_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.received_date,re.loading_date)), ' mins, ', SECOND(TIMEDIFF(re.received_date,re.loading_date)), ' secs') AS received_to_load, "
                + "HOUR(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))) AS received_to_load_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.received_date,IFNULL(re.loading_date,NOW()))), ' secs') AS received_to_load_temp, "
                //unload to ship
                + "HOUR(TIMEDIFF(re.unloading_date,re.shipment_date)) AS unload_to_ship_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.unloading_date,re.shipment_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.unloading_date,re.shipment_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.unloading_date,re.shipment_date)), ' mins, ', SECOND(TIMEDIFF(re.unloading_date,re.shipment_date)), ' secs') AS unload_to_ship, "
                + "HOUR(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))) AS unload_to_ship_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.unloading_date,IFNULL(re.shipment_date,NOW()))), ' secs') AS unload_to_ship_temp, "
                //ship to close
                + "HOUR(TIMEDIFF(re.shipment_date,re.closed_date)) AS ship_to_close_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.shipment_date,re.closed_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.shipment_date,re.closed_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.shipment_date,re.closed_date)), ' mins, ', SECOND(TIMEDIFF(re.shipment_date,re.closed_date)), ' secs') AS ship_to_close, "
                + "HOUR(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))) AS ship_to_close_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))), 24), ' hours, ', MINUTE(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))), ' mins, ', SECOND(TIMEDIFF(re.shipment_date,IFNULL(re.closed_date,NOW()))), ' secs') AS ship_to_close_temp, "
                //actual vs estimate unloading
                + "HOUR(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)) AS estimate_unloading_actual_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)), 24), ' hours, ', MINUTE(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)), ' mins, ', SECOND(TIMEDIFF(re.unloading_date,sh.estimate_unloading_date)), ' secs') AS estimate_unloading_actual, "
                + "HOUR(TIMEDIFF(sh.estimate_unloading_date,IFNULL(re.unloading_date,NOW()))) AS estimate_unloading_actual_temp_24, "
                + "CONCAT(FLOOR(HOUR(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)) / 24), ' days, ', MOD(HOUR(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)), 24), ' hours, ', MINUTE(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)), ' mins, ', SECOND(TIMEDIFF(IFNULL(re.unloading_date,NOW()),sh.estimate_unloading_date)), ' secs') AS estimate_unloading_actual_temp "
                + "FROM dots_lot_penang re "
                + "LEFT JOIN dots_request sh ON sh.id = re.request_id "
                //                + "WHERE re.flag = '0' "
                + "ORDER BY sh.rms ASC";
        List<LotPenang> lotPenangList = new ArrayList<LotPenang>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            LotPenang lotPenang;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lotPenang = new LotPenang();
                lotPenang.setId(rs.getString("id"));
                lotPenang.setRequestId(rs.getString("request_id"));
                lotPenang.setRmsEvent(rs.getString("rms_event"));
                lotPenang.setDoNumber(rs.getString("do_number"));
                lotPenang.setInterval(rs.getString("intervals"));
                lotPenang.setStatus(rs.getString("status"));
                lotPenang.setDevice(rs.getString("device"));
                lotPenang.setPackages(rs.getString("packages"));
                lotPenang.setUnloadingDate(rs.getString("unloading_date"));
                lotPenang.setExpectedUnloadingDate(rs.getString("estimate_unloading_date"));
                lotPenang.setExpectedUnloadingDateView(rs.getString("estimate_unloading_date_view"));
                lotPenang.setNowDate(rs.getString("now_date"));
                lotPenang.setNowDateView(rs.getString("now_date_view"));
                lotPenang.setShipmentFromRel(rs.getString("ship_to_penang"));
                lotPenang.setReceivedDate(rs.getString("penang_received_date"));
                lotPenang.setLoadingDate(rs.getString("loading_date"));
                lotPenang.setUnloadingDateView(rs.getString("unloading_date_view"));
                lotPenang.setShipmentDate(rs.getString("ship_to_rel"));
                lotPenang.setClosedDate(rs.getString("closed_date"));
                //ship to penang received
                lotPenang.setShipToReceived24(rs.getString("ship_to_received_24"));
                lotPenang.setShipToReceivedDetail(rs.getString("ship_to_received"));
                lotPenang.setShipToReceivedIfNull24(rs.getString("ship_to_received_temp_24"));
                lotPenang.setShipToReceivedIfNullDetail(rs.getString("ship_to_received_temp"));
                //received to loading       
                lotPenang.setReceivedToLoad24(rs.getString("received_to_load_24"));
                lotPenang.setReceivedToLoadDetail(rs.getString("received_to_load"));
                lotPenang.setReceivedToLoadIfNull24(rs.getString("received_to_load_temp_24"));
                lotPenang.setReceivedToLoadIfNullDetail(rs.getString("received_to_load_temp"));
                //unload to ship
                lotPenang.setUnloadToShip24(rs.getString("unload_to_ship_24"));
                lotPenang.setUnloadToShipDetail(rs.getString("unload_to_ship"));
                lotPenang.setUnloadToShipIfNull24(rs.getString("unload_to_ship_temp_24"));
                lotPenang.setUnloadToShipIfNullDetail(rs.getString("unload_to_ship_temp"));
                //ship to close
                lotPenang.setShipToClosed24(rs.getString("ship_to_close_24"));
                lotPenang.setShipToClosedDetail(rs.getString("ship_to_close"));
                lotPenang.setShipToClosedIfNull24(rs.getString("ship_to_close_temp_24"));
                lotPenang.setShipToClosedIfNullDetail(rs.getString("ship_to_close_temp"));
                //actual vs estimate unloading
                lotPenang.setActualVsEstimateUnloading24(rs.getString("estimate_unloading_actual_24"));
                lotPenang.setActualVsEstimateUnloadingDetail(rs.getString("estimate_unloading_actual"));
                lotPenang.setActualVsEstimateUnloadingIfNull24(rs.getString("estimate_unloading_actual_temp_24"));
                lotPenang.setActualVsEstimateUnloadingIfNullDetail(rs.getString("estimate_unloading_actual_temp"));
                lotPenangList.add(lotPenang);
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
        return lotPenangList;
    }
}
