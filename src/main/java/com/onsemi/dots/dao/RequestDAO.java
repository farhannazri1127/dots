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
import com.onsemi.dots.model.Request;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRequest(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_request (rms, event, lot_id, device, package, intervals, quantity, gts, remarks, status, shipping_date, created_by, created_date, modified_by, modified_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, request.getRms());
            ps.setString(2, request.getEvent());
            ps.setString(3, request.getLotId());
            ps.setString(4, request.getDevice());
            ps.setString(5, request.getPackages());
            ps.setString(6, request.getInterval());
            ps.setString(7, request.getQuantity());
            ps.setString(8, request.getGts());
            ps.setString(9, request.getRemarks());
            ps.setString(10, request.getStatus());
            ps.setString(11, request.getShippingDate());
            ps.setString(12, request.getCreatedBy());
            ps.setString(13, request.getCreatedDate());
            ps.setString(14, request.getModifiedBy());
            ps.setString(15, request.getModifiedDate());
            ps.setString(16, request.getFlag());
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

    public QueryResult insertRequestFromFtp(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_request (rms, event, lot_id, device, package, status, created_by, created_date, "
                    + "flag, intervals, quantity, estimate_loading_date, estimate_unloading_date, rms_event, expected_condition) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, request.getRms());
            ps.setString(2, request.getEvent());
            ps.setString(3, request.getLotId());
            ps.setString(4, request.getDevice());
            ps.setString(5, request.getPackages());
            ps.setString(6, request.getStatus());
            ps.setString(7, request.getCreatedBy());
            ps.setString(8, request.getFlag());
            ps.setString(9, request.getInterval());
            ps.setString(10, request.getQuantity());
            ps.setString(11, request.getLoadingDate());
            ps.setString(12, request.getUnloadingDate());
            ps.setString(13, request.getRmsEvent());
            ps.setString(14, request.getExpectedTestCondition());
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

    public QueryResult insertRequestWithRmsIdFromFtp(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO dots_request (rms, event, lot_id, device, package, status, created_by, created_date, "
                    + "flag, intervals, quantity, estimate_loading_date, estimate_unloading_date, rms_event, expected_condition, rms_id) "
                    + "VALUES (?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, request.getRms());
            ps.setString(2, request.getEvent());
            ps.setString(3, request.getLotId());
            ps.setString(4, request.getDevice());
            ps.setString(5, request.getPackages());
            ps.setString(6, request.getStatus());
            ps.setString(7, request.getCreatedBy());
            ps.setString(8, request.getFlag());
            ps.setString(9, request.getInterval());
            ps.setString(10, request.getQuantity());
            ps.setString(11, request.getLoadingDate());
            ps.setString(12, request.getUnloadingDate());
            ps.setString(13, request.getRmsEvent());
            ps.setString(14, request.getExpectedTestCondition());
            ps.setString(15, request.getRmsId());
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

    public QueryResult updateRequest(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET rms = ?, event = ?, lot_id = ?, device = ?, package = ?, intervals = ?, quantity = ?, gts = ?, remarks = ?, status = ?, shipping_date = ?, created_by = ?, created_date = ?, modified_by = ?, modified_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, request.getRms());
            ps.setString(2, request.getEvent());
            ps.setString(3, request.getLotId());
            ps.setString(4, request.getDevice());
            ps.setString(5, request.getPackages());
            ps.setString(6, request.getInterval());
            ps.setString(7, request.getQuantity());
            ps.setString(8, request.getGts());
            ps.setString(9, request.getRemarks());
            ps.setString(10, request.getStatus());
            ps.setString(11, request.getShippingDate());
            ps.setString(12, request.getCreatedBy());
            ps.setString(13, request.getCreatedDate());
            ps.setString(14, request.getModifiedBy());
            ps.setString(15, request.getModifiedDate());
            ps.setString(16, request.getFlag());
            ps.setString(17, request.getId());
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

    public QueryResult updateRequestLoadingAndUnloadingDateFTP(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET estimate_loading_date = ?, estimate_unloading_date = ? ,modified_by = ?, modified_date = NOW() WHERE rms = ? AND lot_id = ? "
                    + "AND event = ? and intervals = ?"
            );

            ps.setString(1, request.getLoadingDate());
            ps.setString(2, request.getUnloadingDate());
            ps.setString(3, request.getModifiedBy());
            ps.setString(4, request.getRms());
            ps.setString(5, request.getLotId());
            ps.setString(6, request.getEvent());
            ps.setString(7, request.getInterval());
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

    public QueryResult updateRequestRmsIdFTP(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET rms_id = ?, modified_date = NOW() WHERE id = ?"
            );

            ps.setString(1, request.getRmsId());
            ps.setString(2, request.getId());
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

    public QueryResult updateRequestExpectedConditionFTP(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET expected_condition = ? , modified_by = ?, modified_date = NOW() WHERE rms = ? AND lot_id = ? "
                    + "AND event = ? and intervals = ?"
            );

            ps.setString(1, request.getExpectedTestCondition());
            ps.setString(2, request.getModifiedBy());
            ps.setString(3, request.getRms());
            ps.setString(4, request.getLotId());
            ps.setString(5, request.getEvent());
            ps.setString(6, request.getInterval());
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

    public QueryResult updateRequestforOtherDetails(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    //                    "UPDATE dots_request SET intervals = ?, quantity = ?, remarks = ?, status = ?, modified_by = ?, modified_date = NOW(), flag = ?,"
                    //                    + "chamber_id = ?, chamber_level = ?, test_condition = ?, estimate_loading_date = ?, estimate_unloading_date = ?,rms_event = ? WHERE id = ?"
                    "UPDATE dots_request SET intervals = ?, quantity = ?, remarks = ?, status = ?, modified_by = ?, modified_date = NOW(), flag = ?,"
                    + "chamber_id = ?, chamber_level = ?, test_condition = ?, rms_event = ? WHERE id = ?"
            );
            ps.setString(1, request.getInterval());
            ps.setString(2, request.getQuantity());
            ps.setString(3, request.getRemarks());
            ps.setString(4, request.getStatus());
            ps.setString(5, request.getModifiedBy());
            ps.setString(6, request.getFlag());
            ps.setString(7, request.getChamber());
            ps.setString(8, request.getChamberLocation());
            ps.setString(9, request.getTestCondition());
            ps.setString(10, request.getRmsEvent());
//            ps.setString(11, request.getMultiplier()); //new
            ps.setString(11, request.getId());
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

    public QueryResult updateRequestStatusToDO(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, modified_by = ?, modified_date = NOW(), flag = ?, do_number = ? WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getModifiedBy());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getDoNumber());
            ps.setString(5, request.getId());
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

    public QueryResult updateRequestforShipDateAndGts(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    //                    "UPDATE dots_request SET shipping_date = ?, gts = ?, status = ?, modified_by = ?, modified_date = NOW(), flag = ? WHERE id = ?"
                    "UPDATE dots_request re, dots_final_request fi SET re.gts = ?, re.status = ?, re.shipping_date = ?, re.modified_by = ?, re.modified_date = NOW(), re.flag = ?"
                    + " WHERE re.id = fi.request_id AND re.status <> 'Ship to Penang'"
            );
            ps.setString(1, request.getGts());
            ps.setString(2, request.getStatus());
            ps.setString(3, request.getShippingDate());
            ps.setString(4, request.getModifiedBy());
            ps.setString(5, request.getFlag());

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

    public QueryResult updateRequestforCancelShipment(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET shipping_date = NULL, gts = NULL, status = 'Pending Shipment', modified_by = ?, modified_date = NOW(), flag = ?, do_number = NULL WHERE id = ?"
            );

            ps.setString(1, request.getModifiedBy());
            ps.setString(2, request.getFlag());
            ps.setString(3, request.getId());
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

    public QueryResult updateRequestDoNumber(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET do_number = ? WHERE id = ? AND flag = '0'"
            );

            ps.setString(1, request.getDoNumber());
            ps.setString(2, request.getId());
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

    public QueryResult updateRequestforDelete(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET chamber_id = NULL, chamber_level = NULL, test_condition = NULL, remarks = NULL, shipping_date = NULL, gts = NULL, status = 'New', modified_by = ?, modified_date = NOW(), flag = ?, do_number = NULL, pre_vm_id = NULL WHERE id = ?"
            );

            ps.setString(1, request.getModifiedBy());
            ps.setString(2, request.getFlag());
            ps.setString(3, request.getId());
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

    public QueryResult updateRequestStatus(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getModifiedBy());
            ps.setString(3, request.getId());
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

    public QueryResult updateRequestStatusAndFlag(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getFlag());
            ps.setString(3, request.getId());
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

    public QueryResult updateRequestStatusAndFlagWithModifedBy(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, flag = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getFlag());
            ps.setString(3, request.getModifiedBy());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestBreakInterval(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, modified_by = ?, modified_date = NOW(), flag = ?, old_request_id = ?, chamber_id = ?,"
                    + "chamber_level = ?, test_condition = ?, gts = ?, do_number = ?, shipping_date = ?, quantity = ? WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getModifiedBy());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getOldRequestId());
            ps.setString(5, request.getChamber());
            ps.setString(6, request.getChamberLocation());
            ps.setString(7, request.getTestCondition());
            ps.setString(8, request.getGts());
            ps.setString(9, request.getDoNumber());
            ps.setString(10, request.getShippingDate());
            ps.setString(11, request.getQuantity());
            ps.setString(12, request.getId());
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

    public QueryResult updateRequestCancelInterval(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, modified_by = ?, modified_date = NOW(), flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getModifiedBy());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestNewInterval(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET modified_by = ?, modified_date = NOW(), new_request_id = ?, flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getModifiedBy());
            ps.setString(2, request.getNewRequestId());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestWhenClosed(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET status = ?, modified_by = ?, modified_date = NOW(), flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getStatus());
            ps.setString(2, request.getModifiedBy());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestWithDisposition(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET disposition_id = ?, modified_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getDispositionId());
            ps.setString(2, request.getStatus());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestPreVmId(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET pre_vm_id = ?, modified_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getPreVmId());
            ps.setString(2, request.getStatus());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestPostVmId(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dots_request SET post_vm_id = ?, modified_date = NOW(), status = ?, flag = ? WHERE id = ?"
            );

            ps.setString(1, request.getPostVmId());
            ps.setString(2, request.getStatus());
            ps.setString(3, request.getFlag());
            ps.setString(4, request.getId());
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

    public QueryResult deleteRequest(String requestId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dots_request WHERE id = '" + requestId + "'"
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

    public Request getRequest(String requestId) {
        String sql = "SELECT *,DATE_FORMAT(estimate_loading_date,'%Y-%m-%d') AS loading_date_view,DATE_FORMAT(estimate_unloading_date,'%Y-%m-%d') AS unloading_date_view FROM dots_request WHERE id = '" + requestId + "'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setLoadingDateView(rs.getString("loading_date_view"));
                request.setUnloadingDateView(rs.getString("unloading_date_view"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
                request.setRmsId(rs.getString("rms_id"));
                request.setPreVmId(rs.getString("pre_vm_id"));
                request.setPostVmId(rs.getString("post_vm_id"));
//                request.setMultiplier(rs.getString("multiplier"));
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
        return request;
    }

    public Request getRequestWithViewDate(String requestId) {
        String sql = "SELECT *,DATE_FORMAT(estimate_loading_date,'%d-%m-%Y %h:%i %p') AS loading_date_view,DATE_FORMAT(estimate_unloading_date,'%d-%m-%Y %h:%i %ps') AS unloading_date_view FROM dots_request WHERE id = '" + requestId + "'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setLoadingDateView(rs.getString("loading_date_view"));
                request.setUnloadingDateView(rs.getString("unloading_date_view"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
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
        return request;
    }

    public Request getRequestByRmsLotEvent(String rms, String lot, String event) {
        String sql = "SELECT *,DATE_FORMAT(estimate_loading_date,'%Y-%m-%d') AS loading_date_view,DATE_FORMAT(estimate_unloading_date,'%Y-%m-%d') AS unloading_date_view FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setLoadingDateView(rs.getString("loading_date_view"));
                request.setUnloadingDateView(rs.getString("unloading_date_view"));
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
        return request;
    }

    public Request getRequestByRmsLotEventIntervalForFtp(String rms, String lot, String event, String interval) {
        String sql = "SELECT * FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "' AND intervals = '" + interval + "'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
//                request.setUnloadingDateView(rs.getString("unloading_date_view"));
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
        return request;
    }

    public Request getRequestByRmsLotEventIntervalRmsIdForFtp(String rms, String lot, String event, String interval, String rmsId) {
        String sql = "SELECT * FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "' AND intervals = '" + interval + "'"
                + " AND rms_id = '" + rmsId + "' AND status != 'Cancel Interval'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
                request.setRmsId(rs.getString("rms_id"));
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
        return request;
    }

    public Request getRequestByRmsLotEventInterval(String rmsEvent, String intervals) {
        String sql = "SELECT *,DATE_FORMAT(estimate_loading_date,'%Y-%m-%d') AS loading_date_view,DATE_FORMAT(estimate_unloading_date,'%Y-%m-%d') AS unloading_date_view FROM dots_request WHERE rms_event = '" + rmsEvent + "' AND intervals = '" + intervals + "'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setLoadingDateView(rs.getString("loading_date_view"));
                request.setUnloadingDateView(rs.getString("unloading_date_view"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
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
        return request;
    }

    public Request getRequestByRmsLotEventIntervalFlag0(String rmsEvent, String intervals) {
        String sql = "SELECT *,DATE_FORMAT(estimate_loading_date,'%Y-%m-%d') AS loading_date_view,DATE_FORMAT(estimate_unloading_date,'%Y-%m-%d') AS unloading_date_view FROM dots_request "
                + "WHERE rms_event = '" + rmsEvent + "' AND intervals = '" + intervals + "' AND flag = '0'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setLoadingDateView(rs.getString("loading_date_view"));
                request.setUnloadingDateView(rs.getString("unloading_date_view"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
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
        return request;
    }

    public Request getRequestWithDisposition(String requestId) {
        String sql = "SELECT re.*,do.*,DATE_FORMAT(re.estimate_loading_date,'%d-%m-%Y %h:%i %p') AS loading_date_view,"
                + "DATE_FORMAT(re.estimate_unloading_date,'%d-%m-%Y %h:%i %p') AS unloading_date_view "
                + "FROM dots_request re "
                + "LEFT JOIN dots_disposition do ON re.disposition_id = do.id "
                + "WHERE re.id = '" + requestId + "'";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("re.id"));
                request.setRms(rs.getString("re.rms"));
                request.setEvent(rs.getString("re.event"));
                request.setLotId(rs.getString("re.lot_id"));
                request.setDevice(rs.getString("re.device"));
                request.setPackages(rs.getString("re.package"));
                request.setInterval(rs.getString("re.intervals"));
                request.setQuantity(rs.getString("re.quantity"));
                request.setStatus(rs.getString("re.status"));
                request.setModifiedBy(rs.getString("re.modified_by"));
                request.setModifiedDate(rs.getString("re.modified_date"));
                request.setFlag(rs.getString("re.flag"));
                request.setExpectedTestCondition(rs.getString("re.expected_condition"));
                request.setLoadingDate(rs.getString("re.estimate_loading_date"));
                request.setUnloadingDate(rs.getString("re.estimate_unloading_date"));
                request.setRmsEvent(rs.getString("re.rms_event"));
                request.setLoadingDateView(rs.getString("loading_date_view"));
                request.setUnloadingDateView(rs.getString("unloading_date_view"));
                request.setDispositionId(rs.getString("re.disposition_id"));
                request.setDispoId(rs.getString("do.id"));
                request.setDispoReqId(rs.getString("do.request_id"));
                request.setDisposition(rs.getString("do.disposition"));
                request.setDispoRemarks(rs.getString("do.remarks"));
                request.setDispoBy(rs.getString("do.dispo_by"));
                request.setDispoDate(rs.getString("do.dispo_date"));
                request.setDispoFlag(rs.getString("do.flag"));
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
        return request;
    }

    public List<Request> getRequestList() {
        String sql = "SELECT * FROM dots_request ORDER BY id ASC";
        List<Request> requestList = new ArrayList<Request>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Request request;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                requestList.add(request);
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
        return requestList;
    }

    public List<Request> getRequestListForStatusPending() {
//        String sql = "SELECT * FROM dots_request WHERE status = 'Pending Shipment' ORDER BY id DESC";
        String sql = "SELECT * FROM dots_request WHERE status IN ('Pending Shipment','Pending Pre VM') ORDER BY id DESC";
        List<Request> requestList = new ArrayList<Request>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Request request;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                request.setPreVmId(rs.getString("pre_vm_id"));
                requestList.add(request);
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
        return requestList;
    }

    public List<Request> getRequestListForQueryCheck(String query) {
        String sql = "SELECT * FROM dots_request WHERE status = 'New' AND " + query + " ORDER BY id DESC";
        List<Request> requestList = new ArrayList<Request>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Request request;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setGts(rs.getString("gts"));
                request.setRemarks(rs.getString("remarks"));
                request.setStatus(rs.getString("status"));
                request.setShippingDate(rs.getString("shipping_date"));
                request.setCreatedBy(rs.getString("created_by"));
                request.setCreatedDate(rs.getString("created_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setChamber(rs.getString("chamber_id"));
                request.setChamberLocation(rs.getString("chamber_level"));
                request.setTestCondition(rs.getString("test_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setDoNumber(rs.getString("do_number"));
                requestList.add(request);
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
        return requestList;
    }

    //list previous interval
    public List<Request> getRequestListForPreviousInterval(String query) {
        String sql = "SELECT re.*,do.disposition,do.remarks FROM dots_request re "
                + "LEFT JOIN dots_disposition do ON re.disposition_id = do.id "
                + "WHERE re.status IN('New','Cancel Interval','Hold for MRB') "
                + "AND re.flag in ('0','3') "
                + "AND " + query + " ORDER BY intervals ASC";
        List<Request> requestList = new ArrayList<Request>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Request request;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setRms(rs.getString("rms"));
                request.setEvent(rs.getString("event"));
                request.setLotId(rs.getString("lot_id"));
                request.setDevice(rs.getString("device"));
                request.setPackages(rs.getString("package"));
                request.setInterval(rs.getString("intervals"));
                request.setQuantity(rs.getString("quantity"));
                request.setStatus(rs.getString("status"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setFlag(rs.getString("flag"));
                request.setExpectedTestCondition(rs.getString("expected_condition"));
                request.setLoadingDate(rs.getString("estimate_loading_date"));
                request.setUnloadingDate(rs.getString("estimate_unloading_date"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setRequestorEmail(rs.getString("requestor_email"));
                request.setDispositionId(rs.getString("disposition_id"));
                request.setDisposition(rs.getString("do.disposition"));
                request.setDispoRemarks(rs.getString("do.remarks"));
                requestList.add(request);
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
        return requestList;
    }

    public Integer getCountRequestRmsLotEvent(String rms, String lot, String event) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "'"
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

    public Integer getCountRequestRmsLotEventInterval(String rmsEvent, String interval) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_request WHERE rms_event = '" + rmsEvent + "' AND intervals = '" + interval + "'"
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

    public Integer getCountRequestRmsLotEventInterval(String rms, String lot, String event, String interval) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "' AND intervals = '" + interval + "'"
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

    public Integer getCountRequestRmsLotEventIntervalFlag0(String rms, String lot, String event, String interval) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "' AND intervals = '" + interval + "'"
                    + " AND flag = '0'"
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

    public Integer getCountRequestRmsLotEventIntervalRmsId(String rms, String lot, String event, String interval, String rmsId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_request WHERE rms = '" + rms + "' AND lot_id = '" + lot + "' AND event = '" + event + "' AND intervals = '" + interval + "'"
                    + " AND rms_id = '" + rmsId + "' AND status != 'Cancel Interval'"
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

    public Integer getCountRequestID(String requestId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM dots_request WHERE id = '" + requestId + "'"
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

    public Integer getCountPreviousInterval(String rmsEvent, String intervals) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count from dots_request "
                    + "WHERE CAST(intervals AS UNSIGNED) < '" + intervals + "' "
                    + "AND rms_event = '" + rmsEvent + "' "
                    + "AND status = 'New'"
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
