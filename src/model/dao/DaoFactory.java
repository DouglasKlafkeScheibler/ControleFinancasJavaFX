package model.dao;

import db.DB;

import model.dao.impl.SpendingDaoJDBC;

public class DaoFactory {

	public static SpendingDao createSpendingDao() {
		return new SpendingDaoJDBC(DB.getConnection());
	}
}
