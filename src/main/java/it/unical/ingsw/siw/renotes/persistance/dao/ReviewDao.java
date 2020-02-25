package it.unical.ingsw.siw.renotes.persistance.dao;

import it.unical.ingsw.siw.renotes.model.Review;

public interface ReviewDao {

	public void save(Review review); //Insert -Create
	public Review findByPrimaryKey(Integer id);
	public void update(Review review);
	public void delete(Review review);
}
