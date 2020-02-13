package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.SpendingDao;
import model.entities.Spending;

public class SpendingDaoJDBC implements SpendingDao {

	private Connection conn;
	
	public SpendingDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Spending obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO spending " +
				"(Date, Water, Light, SuperMarket, CreditCard, Others)" +
				"VALUES" +
				"(?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getDate());
			st.setDouble(2, obj.getWater());
			st.setDouble(3, obj.getLight());
			st.setDouble(4, obj.getSuperMarket());
			st.setDouble(5, obj.getCreditCard());
			st.setDouble(6, obj.getOthers());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Spending obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE spending " +
				"SET Water = ?, Light = ?, SuperMarket = ?, CreditCard = ?, Others = ? " +
				"WHERE Date = ?");

			st.setDouble(1, obj.getWater());
			st.setDouble(2, obj.getLight());
			st.setDouble(3, obj.getSuperMarket());
			st.setDouble(4, obj.getCreditCard());
			st.setDouble(5, obj.getOthers());
			st.setString(6, obj.getDate());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}	
	}

	@Override
	public void deleteById(String date) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM spending WHERE Date = ?");
			
			st.setString(1, date);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Spending> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
			"SELECT * FROM spending ORDER BY Date");
			rs = st.executeQuery();
			
			List<Spending> list = new ArrayList<>();
			
			while (rs.next()) {
				Spending obj = new Spending();
				obj.setDate(rs.getString("Date"));
				obj.setWater(rs.getDouble("Water"));
				obj.setLight(rs.getDouble("Light"));
				obj.setSuperMarket(rs.getDouble("SuperMarket"));
				obj.setCreditCard(rs.getDouble("CreditCard"));
				obj.setOthers(rs.getDouble("Others"));
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Spending findByDate(String date) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM spending WHERE Date = ?");
			st.setString(1, date);
			rs= st.executeQuery();
			
			if(rs.next()) {
				Spending obj = new Spending();
				obj.setId(rs.getInt("Id"));
				obj.setDate(rs.getString("Date"));
				obj.setWater(rs.getDouble("Water"));
				obj.setLight(rs.getDouble("Light"));
				obj.setSuperMarket(rs.getDouble("SuperMarket"));
				obj.setCreditCard(rs.getDouble("CreditCard"));
				obj.setOthers(rs.getDouble("Others"));
				return obj;
			}
		return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
