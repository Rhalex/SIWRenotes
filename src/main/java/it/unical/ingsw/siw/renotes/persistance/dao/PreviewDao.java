package it.unical.ingsw.siw.renotes.persistance.dao;

import it.unical.ingsw.siw.renotes.model.Preview;

public interface PreviewDao {

	public void save(Preview preview); //Insert -Create
	public Preview findByPrimaryKey(Integer id);
	public void update(Preview preview);
	public void delete(Preview preview);
	
}
