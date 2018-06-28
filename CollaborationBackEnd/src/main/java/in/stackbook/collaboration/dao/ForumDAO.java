package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Forum;
import in.stackbook.collaboration.model.ForumComment;

public interface ForumDAO {
	
	public boolean save(Forum forum);
	
	public boolean update(Forum forum);

	public boolean delete(int forum_id);

	public Forum get(int forum_id);
	
	//public String getTitle(int forum_id);
	
	public List<Forum> list();
	
	public List<Forum> listByApproval(char approved);
	
	public List<Forum> listByArchive(char archived);
		
	public List<Forum> listByLevel(int forum_level);
	
	public List<Forum> listAllForParentID(int parent_id);
		
	public List<Forum> listByNewPostAccess(int new_post_access);
	
	public List<Forum> listByViewAccess(int view_access);
	
	public List<Forum> listByCommentAccess(int comment_access);
				
	public List<Forum> listPendingApprovalForums();
	
	public List<Forum> listApprovedForumsInCurrentLevelByParentID(int forum_level, int parent_id);
	
	
	//Forum Comments
	
	
	public boolean save(ForumComment forumComment);
	
	public boolean update(ForumComment forumComment);

	public boolean deleteForumComment(int f_comment_id);

	public ForumComment getForumComment(int f_comment_id);
	
	public List<ForumComment> list(int forum_id);
	
	public List<ForumComment> list(int forum_id, String email_id);
	
	public List<ForumComment> list(String email_id);
	
		
}
