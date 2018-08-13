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
import com.onsemi.dots.model.TestCondition;
import com.onsemi.dots.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestConditionDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestConditionDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public TestConditionDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertTestCondition(TestCondition testCondition) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO dots_test_condition (name, condition, remarks, created_by, created_date, modified_by, modified_date) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, testCondition.getName());
			ps.setString(2, testCondition.getCondition());
			ps.setString(3, testCondition.getRemarks());
			ps.setString(4, testCondition.getCreatedBy());
			ps.setString(5, testCondition.getCreatedDate());
			ps.setString(6, testCondition.getModifiedBy());
			ps.setString(7, testCondition.getModifiedDate());
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

	public QueryResult updateTestCondition(TestCondition testCondition) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE dots_test_condition SET name = ?, condition = ?, remarks = ?, created_by = ?, created_date = ?, modified_by = ?, modified_date = ? WHERE id = ?"
			);
			ps.setString(1, testCondition.getName());
			ps.setString(2, testCondition.getCondition());
			ps.setString(3, testCondition.getRemarks());
			ps.setString(4, testCondition.getCreatedBy());
			ps.setString(5, testCondition.getCreatedDate());
			ps.setString(6, testCondition.getModifiedBy());
			ps.setString(7, testCondition.getModifiedDate());
			ps.setString(8, testCondition.getId());
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

	public QueryResult deleteTestCondition(String testConditionId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM dots_test_condition WHERE id = '" + testConditionId + "'"
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

	public TestCondition getTestCondition(String testConditionId) {
		String sql = "SELECT * FROM dots_test_condition WHERE id = '" + testConditionId + "'";
		TestCondition testCondition = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				testCondition = new TestCondition();
				testCondition.setId(rs.getString("id"));
				testCondition.setName(rs.getString("name"));
				testCondition.setCondition(rs.getString("condition"));
				testCondition.setRemarks(rs.getString("remarks"));
				testCondition.setCreatedBy(rs.getString("created_by"));
				testCondition.setCreatedDate(rs.getString("created_date"));
				testCondition.setModifiedBy(rs.getString("modified_by"));
				testCondition.setModifiedDate(rs.getString("modified_date"));
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
		return testCondition;
	}
        
        public TestCondition getTestConditionByName(String Name) {
		String sql = "SELECT * FROM dots_test_condition WHERE name = '" + Name + "'";
		TestCondition testCondition = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				testCondition = new TestCondition();
				testCondition.setId(rs.getString("id"));
				testCondition.setName(rs.getString("name"));
				testCondition.setCondition(rs.getString("condition"));
				testCondition.setRemarks(rs.getString("remarks"));
				testCondition.setCreatedBy(rs.getString("created_by"));
				testCondition.setCreatedDate(rs.getString("created_date"));
				testCondition.setModifiedBy(rs.getString("modified_by"));
				testCondition.setModifiedDate(rs.getString("modified_date"));
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
		return testCondition;
	}
        
        public TestCondition getTestConditionByCondition(String condition) {
		String sql = "SELECT * FROM dots_test_condition WHERE `condition` = '" + condition + "'";
		TestCondition testCondition = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				testCondition = new TestCondition();
				testCondition.setId(rs.getString("id"));
				testCondition.setName(rs.getString("name"));
				testCondition.setCondition(rs.getString("condition"));
				testCondition.setRemarks(rs.getString("remarks"));
				testCondition.setCreatedBy(rs.getString("created_by"));
				testCondition.setCreatedDate(rs.getString("created_date"));
				testCondition.setModifiedBy(rs.getString("modified_by"));
				testCondition.setModifiedDate(rs.getString("modified_date"));
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
		return testCondition;
	}

	public List<TestCondition> getTestConditionList() {
		String sql = "SELECT * FROM dots_test_condition ORDER BY name ASC";
		List<TestCondition> testConditionList = new ArrayList<TestCondition>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			TestCondition testCondition;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				testCondition = new TestCondition();
				testCondition.setId(rs.getString("id"));
				testCondition.setName(rs.getString("name"));
				testCondition.setCondition(rs.getString("condition"));
				testCondition.setRemarks(rs.getString("remarks"));
				testCondition.setCreatedBy(rs.getString("created_by"));
				testCondition.setCreatedDate(rs.getString("created_date"));
				testCondition.setModifiedBy(rs.getString("modified_by"));
				testCondition.setModifiedDate(rs.getString("modified_date"));
				testConditionList.add(testCondition);
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
		return testConditionList;
	}
        
        public List<TestCondition> getTestConditionListByName(String name) {
		String sql = "SELECT *,IF(name=\"" + name + "\",\"selected=''\",\"\") AS selected FROM dots_test_condition ORDER BY name ASC";
		List<TestCondition> testConditionList = new ArrayList<TestCondition>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			TestCondition testCondition;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				testCondition = new TestCondition();
				testCondition.setId(rs.getString("id"));
				testCondition.setName(rs.getString("name"));
				testCondition.setCondition(rs.getString("condition"));
				testCondition.setRemarks(rs.getString("remarks"));
				testCondition.setCreatedBy(rs.getString("created_by"));
				testCondition.setCreatedDate(rs.getString("created_date"));
				testCondition.setModifiedBy(rs.getString("modified_by"));
				testCondition.setModifiedDate(rs.getString("modified_date"));
                                testCondition.setSelected(rs.getString("selected"));
				testConditionList.add(testCondition);
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
		return testConditionList;
	}
        
        public List<TestCondition> getTestConditionListByCondition(String condition) {
		String sql = "SELECT *,IF(`condition`=\"" + condition + "\",\"selected=''\",\"\") AS selected FROM dots_test_condition ORDER BY name ASC";
		List<TestCondition> testConditionList = new ArrayList<TestCondition>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			TestCondition testCondition;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				testCondition = new TestCondition();
				testCondition.setId(rs.getString("id"));
				testCondition.setName(rs.getString("name"));
				testCondition.setCondition(rs.getString("condition"));
				testCondition.setRemarks(rs.getString("remarks"));
				testCondition.setCreatedBy(rs.getString("created_by"));
				testCondition.setCreatedDate(rs.getString("created_date"));
				testCondition.setModifiedBy(rs.getString("modified_by"));
				testCondition.setModifiedDate(rs.getString("modified_date"));
                                testCondition.setSelected(rs.getString("selected"));
				testConditionList.add(testCondition);
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
		return testConditionList;
	}
        
}