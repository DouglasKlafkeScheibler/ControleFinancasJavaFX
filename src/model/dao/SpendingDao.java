package model.dao;

import java.util.List;

import model.entities.Spending;

public interface SpendingDao {

	void insert(Spending obj);
	void update(Spending obj);
	void deleteById(String date);
	Spending findByDate(String date);
	List<Spending> findAll();	
}
