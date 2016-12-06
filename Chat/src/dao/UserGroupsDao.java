package dao;
import entities.*;
import java.util.*;

import javax.persistence.EntityManager;

import org.hibernate.*;

public class UserGroupsDao {

	public SessionFactory sessionFactory;

	public UserGroupsDao()
	{
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}

	public void insertUser(String uname, String gname)
	{
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

		try
		{
			Transaction t = null;
			if(!session.getTransaction().isActive())
			{
				t= session.beginTransaction();
			}

			UserGroups reg = new UserGroups();
			reg.setGroupName(gname);
			reg.setGroupOwner(uname);
			session.save(reg);
			//t.commit();
			UserContactDao d = new UserContactDao();
			d.insertUserContactG(gname, uname);
		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}
		finally
		{
			// this.sessionFactory.close();
		}

	}

	public int listgid(String userN) {
		UserGroups result = null;
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
			result = entityManager.createQuery( "from UserGroups where GroupName = '" + userN + "'", UserGroups.class ).getSingleResult();	       
			entityManager.getTransaction().commit();
			session.getTransaction().commit();

		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}
		finally
		{
			// this.sessionFactory.close();
		}
		return result.getGroupId();
	}

	public List<UserGroups> listEvents(String userN) {
		List<UserGroups> result = null;
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
			result = entityManager.createQuery( "from UserGroups where GroupOwner = '" + userN + "'", UserGroups.class ).getResultList();	       
			entityManager.getTransaction().commit();
			session.getTransaction().commit();

		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}
		finally
		{
			// this.sessionFactory.close();
		}
		return result;
	}

	public String[] toStringUserGroups(String user)
	{
		List<UserGroups> result1 = listEvents(user);
		String[] s = new String[result1.size()];

		for(int i =0;i<result1.size();i++ )
		{
			s[i] = result1.get(i).getGroupName();
		}
		return s;

	}

	public List<Groupusers> listGEvents(int id) {
		List<Groupusers> result = null;
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
			result = entityManager.createQuery( "from Groupusers where groupId = '" + id + "'", Groupusers.class ).getResultList();	       
			entityManager.getTransaction().commit();
			session.getTransaction().commit();

		}
		catch(Exception e)
		{		 
			System.out.println(e.getMessage());

		}
		finally
		{
			// this.sessionFactory.close();
		}
		return result;
	}


	public String[] toStringgroupusers(String user)
	{
		int id = listgid(user);
		List<Groupusers> result1 = listGEvents(id);
		int[] id1 = new int[result1.size()];
		for(int i = 0;i<id1.length;i++)
		{
			id1[i] = result1.get(i).getId().getUserId();

		}

		UserDao ud1 = new UserDao();
		return ud1.getUsername(id1);

	}
}
