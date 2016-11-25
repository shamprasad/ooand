package model;

import java.util.List;

public interface ITeamContactDAO {
	void save(TeamContact teamContact);
	
	List<TeamContact> list();
	
	List<Contact> listContact(int teamId);
}
