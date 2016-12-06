package dao;

import entities.*;
import java.util.*;

import javax.persistence.EntityManager;

import org.hibernate.*;

public class UserContactDao {

	public SessionFactory sessionFactory;

	public UserContactDao()
	{
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}

	public void insertUserContact(String user1, String user2)
	{
		UserDao ud = new UserDao();
		int u1 = ud.getUId(user1);
		int u2 = ud.getUId(user2);
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			Transaction t = session.beginTransaction();

			UserContactId u = new UserContactId();

			u.setUserContactId(u2);
			u.setUserId(u1);

			UserContact a = new UserContact();
			a.setId(u);
			session.save(a);
			//t.commit();

		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}

	}

	public void insertUserContactG(String user1, String user2)
	{
		UserDao ud = new UserDao();

		int u2 = ud.getUId(user2);
		UserGroupsDao ud1 = new UserGroupsDao();
		int u1 = ud1.listgid(user1);
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			Transaction t = session.beginTransaction();

			GroupusersId u = new GroupusersId();

			u.setGroupId(u1);;
			u.setUserId(u2);;

			Groupusers a = new Groupusers();
			a.setId(u);
			session.save(a);
			//t.commit();

		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}

	}

	public List<UserContact> getUsersuser( int id) {

		List<UserContact> result = null;

		try{

			Session session;

			if(this.sessionFactory.isClosed() == false)
			{
				session = this.sessionFactory.getCurrentSession();
			}
			else
			{
				this.sessionFactory = HibernateUtil.getSessionFactory();
				session = this.sessionFactory.getCurrentSession();
			}
			if(!session.getTransaction().isActive())
			{
				session.getTransaction().begin();
			}
			EntityManager entityManager =  session.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();
			result = entityManager.createQuery( "from UserContact where userId = " + id + " or userContactId = " + id, UserContact.class ).getResultList();	       
			entityManager.getTransaction().commit();
			//entityManager.close();
			session.getTransaction().commit();



		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}
		finally
		{
			//this.sessionFactory.close();
		}

		return result;
	}

	public String[] toStringUsersUser(String user)
	{
		int id = 0;
		UserDao ud = new UserDao();
		id = ud.getUId(user);
		List<UserContact> result1 = getUsersuser(id);
		int[] id1 = new int[result1.size()];
		for(int i = 0;i<id1.length;i++)
		{
			id1[i] = result1.get(i).getId().getUserId() == id ? result1.get(i).getId().getUserContactId(): result1.get(i).getId().getUserId();

		}
		UserDao ud1 = new UserDao();
		return ud1.getUsername(id1);

	}
}
