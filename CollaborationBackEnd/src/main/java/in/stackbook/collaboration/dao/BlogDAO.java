package in.stackbook.collaboration.dao;

import java.util.List;

import in.stackbook.collaboration.model.Blog;
import in.stackbook.collaboration.model.BlogComment;

public interface BlogDAO {
	
	public boolean save(Blog blog);
	
	public boolean update(Blog blog);
	
	public boolean delete(int blog_id);
	
	public Blog get(int blog_id);
	
	//public String getBlogTitle(int blog_id);
	
	public List<Blog> list();
	
	public List<Blog> list(char approved);
	
	public List<Blog> list(String email_id);
	
	//public List<Integer> listID();
	
	//public List<String> listTitle();
				
	
	//Blog Comments
	
	public boolean save(BlogComment blogComment);
	
	public boolean update(BlogComment blogComment);

	public boolean deleteBlogComment(int b_comment_id);
	
	public BlogComment getBlogComment(int b_comment_id);
			
	public List<BlogComment> list(int blog_id);
	
	public List<BlogComment> list(int blog_id, String email_id);
	
	public List<BlogComment> listAllCommentsFrom(String email_id);
		
	//public List<BlogComment> listTheFirstXCommentsOn(int blog_id);
	

}
