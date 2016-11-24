package model;

import java.util.List;
import java.lang.UnsupportedOperationException;

public class ContactDAO implements IContactDAO {
	public void save(Contact contact){
		
	}
	
	public List<Contact> list(){
		throw new UnsupportedOperationException();
	}
	
	public List<Contact> list(int groupId) {
		throw new UnsupportedOperationException();
	}
}
