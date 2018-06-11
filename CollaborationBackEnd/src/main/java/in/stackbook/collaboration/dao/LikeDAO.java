package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Like;

public interface LikeDAO {
	
	public boolean save(Like like);
	
	public boolean update(Like like);
	
	public boolean delete(int like_id);
	
	public Like get(int like_id);
	
	public Like get(char reference_table, int table_id, String email_id);
	
	public List<Like> listLikesForTableID(char reference_table, int table_id);
	
	public List<Like> listDislikesForTableID(char reference_table, int table_id);

}
