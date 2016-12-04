package model;

import java.util.List;

public interface IContactDAO {

	boolean exists(String name);

	void save(Contact contact);

	Contact get(String name);

	List<Contact> list(); 
	
	List<Contact> list(int groupId);

	List<Contact> listByUser(int userId);

}