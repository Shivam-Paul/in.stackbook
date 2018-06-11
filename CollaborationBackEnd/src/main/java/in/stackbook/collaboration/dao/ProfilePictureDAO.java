package in.stackbook.collaboration.dao;

import in.stackbook.collaboration.model.ProfilePicture;

public interface ProfilePictureDAO {
	
	public boolean save(ProfilePicture profilePicture);
	
	public ProfilePicture get(String email_id);
	
	public boolean update(ProfilePicture profilePicture);
	
	public boolean delete(String email_id);

}
