package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Bulletin;

public interface BulletinDAO {
	
	public boolean save(Bulletin bulletin);
	
	public boolean update(Bulletin bulletin);

	public boolean delete(int bulletin_id);

	public Bulletin get(int bulletin_id);
	
	public List<Bulletin> list();
	
	public List<Integer> listAllID();
	
	public List<String> listAllTitle();

}
