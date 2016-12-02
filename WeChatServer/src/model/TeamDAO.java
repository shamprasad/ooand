package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TeamDAO implements ITeamDAO {
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public void save(Team group)
	{
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(group);
		tx.commit();
		session.close();
	}
	
	public List<Team> list()
	{
		Session session = this.sessionFactory.openSession();
		List<Team> groupList = session.createQuery("from Team").list();
		session.close();
		return groupList;
	}
	
	public List<Contact> list(int groupId)
	{
		Session session = this.sessionFactory.openSession();
		List<Contact> contactList = session.createQuery("select * from Team").list();
		session.close();
		return contactList;		
	}
}
