package dao;
import entities.*;
import java.util.*;

import javax.persistence.EntityManager;

import org.hibernate.*;

public class UserDao {
	
public SessionFactory sessionFactory;
	
	public UserDao()
	{
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}
	 
	public int listEvents(String userN, String Pass) {
		 int resultR = 0;
	  try{
		 
		  Session session = this.sessionFactory.getCurrentSession();
		  session.getTransaction().begin();
	       EntityManager entityManager =  session.getEntityManagerFactory().createEntityManager();
		  
	       entityManager.getTransaction().begin();
	       UserD result = entityManager.createQuery( "from UserD where userName = '" + userN + "'", UserD.class ).getSingleResult();	       
	       entityManager.getTransaction().commit();
	       //entityManager.close();
	       session.getTransaction().commit();
	       if(listPassEvents(result.getUserId(),Pass)>0)
		      {
		    	  resultR=1;
		      }
	 }
	        catch(Exception e)
	   	 {		 
	   	System.out.println(e.getMessage());
	   	
	   	}
	   	 finally
	   	 {
	   		// this.sessionFactory.close();
	   	 }
	  return resultR;
	    }
	
	public int listPassEvents(int id, String Pass) {
		 int resultR = 0;
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
	       Authenticate result = entityManager.createQuery( "from Authenticate where password = '" + Pass + "' and userid = " + id, Authenticate.class ).getSingleResult();	       
	       entityManager.getTransaction().commit();
	       //entityManager.close();
	       session.getTransaction().commit();
	       if(result.getId().getUserId()>0)
		      {
		    	  resultR=1;
		      }
	 }
	        catch(Exception e)
	   	 {		 
	   	System.out.println(e.getMessage());
	   	
	   	}
	   	 finally
	   	 {
	   		 //this.sessionFactory.close();
	   	 }
	  return resultR;
	    }

	public int getUId(String userN) {
		 int resultR = 0;
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
	       UserD result = entityManager.createQuery( "from UserD where userName = '" + userN + "'", UserD.class ).getSingleResult();	       
	       entityManager.getTransaction().commit();
	       //entityManager.close();
	       session.getTransaction().commit();
	       resultR = result.getUserId();
		    
	 }
	        catch(Exception e)
	   	 {		 
	   	System.out.println(e.getMessage());
	   	
	   	}
	   	 finally
	   	 {
	   		 //this.sessionFactory.close();
	   	 }
	  return resultR;
	    }
	
	public String[] getUsername(int[] id) {
		if(id.length>0)
		{
		 String userN = "0,";
		 for(int i = 0;i<id.length;i++)
		 {
			 if(i!=id.length-1)
			 {
			 userN = userN + id[i] + ",";
			 }
			 else
			 {
				 userN = userN + id[i] ; 
			 }
		 }
		 String k[] = null;
		
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
	       List<UserD> result = entityManager.createQuery( "from UserD where userId in (" + userN + ")", UserD.class ).getResultList();	       
	       entityManager.getTransaction().commit();
	       //entityManager.close();
	       session.getTransaction().commit();
	       k = new String[result.size()];
	       for(int i = 0;i<k.length;i++)
			 {
			 k[i] = result.get(i).getUserName();
			 }
		    
	 }
	        catch(Exception e)
	   	 {		 
	   	System.out.println(e.getMessage());
	   	
	   	}
	   	 finally
	   	 {
	   		 //this.sessionFactory.close();
	   	 }
	  return k;
		}
		else return null;
	    }

	public List<UserD> getUsers(String userN) {
		List<UserD> result = null;
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
	       result = entityManager.createQuery( "from UserD where userName like '%" + userN + "%'", UserD.class ).getResultList();	       
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
	   		// this.sessionFactory.close();
	   	 }
	  
	  return result;
	    }

	public String[] toStringSearchUser(String user)
{
	List<UserD> result1 = getUsers(user);
	String[] s = new String[result1.size()];
	
	for(int i =0;i<result1.size();i++ )
	{
		s[i] = result1.get(i).getUserName();
	}
	return s;
	
}

}
