package model;

import java.util.List;
import java.lang.UnsupportedOperationException;

public class GroupDAO implements IGroupDAO {
	public void save(Group group)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<Group> list()
	{
		throw new UnsupportedOperationException();
	}
	
	public List<Contact> list(int groupId)
	{
		throw new UnsupportedOperationException();
	}
}
