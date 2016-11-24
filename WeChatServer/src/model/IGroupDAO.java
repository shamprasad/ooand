package model;

import java.util.List;

interface IGroupDAO {
	void save(Group group);
	
	List<Group> list();
	
	List<Contact> list(int groupId);
}
