package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Forum;
import in.stackbook.collaboration.model.ForumComment;

public interface ForumDAO {
	
	public boolean save(Forum forum);
	
	public boolean update(Forum forum);

	public boolean delete(int forum_id);

	public Forum get(int forum_id);
	
	public String getTitle(int forum_id);
	
	public List<Forum> list();
	
	public List<Integer> listByLevel(int forum_level);
	
	//public List<Integer> listByViewAccess(int view_access);
	
	//public List<Integer> listByPostAccess(int new_post_access);
		
	public List<Integer> listPendingApprovalForums();
	
	//Forum Comments
	
	public boolean save(ForumComment forumComment);
	
	public boolean update(ForumComment forumComment);

	public boolean deleteForumComment(int f_comment_id);

	public ForumComment getForumComment(int f_comment_id);
	
	public List<ForumComment> list(int forum_id);	
	
		
}
