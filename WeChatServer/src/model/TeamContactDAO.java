package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TeamContactDAO implements ITeamContactDAO{
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public void save(TeamContact teamContact)
	{
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(teamContact);
		tx.commit();
		session.close();
	}
	
	public List<TeamContact> list()
	{
		Session session = this.sessionFactory.openSession();
		List<TeamContact> teamContactList = session.createQuery("from TeamContact").list();
		session.close();
		return teamContactList;		
	}
	
	public List<Contact> listContact(int teamId)
	{
		Session session = this.sessionFactory.openSession();
		List<Contact> contactList = session.createQuery(
				"select distinct c from Contact c, TeamContact tc where c.id = tc.contactId and tc.teamId = :teamId ")
				.setParameter("teamId", teamId)
				.list();
		session.close();
		return contactList;			
	}
}
