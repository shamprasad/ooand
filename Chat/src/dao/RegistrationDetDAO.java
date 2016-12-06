package dao;

import entities.*;
import java.util.*;

import javax.persistence.EntityManager;

import org.hibernate.*;


public class RegistrationDetDAO {
	
	public SessionFactory sessionFactory;
	
	public RegistrationDetDAO()
	{
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}
	/* public void listEvents() {
		 
	  try{
		 
		  Session session = this.sessionFactory.getCurrentSession();
		  session.getTransaction().begin();
	       EntityManager entityManager =  session.getEntityManagerFactory().createEntityManager();
		  
	       entityManager.getTransaction().begin();
	       List<UserD> result = entityManager.createQuery( "from UserD", UserD.class ).getResultList();
	       for ( UserD event : result ) {
	           System.out.println( event.getUserName() );
	       }
	       entityManager.getTransaction().commit();
	       entityManager.close();
	       session.getTransaction().commit();
	 }
	        catch(Exception e)
	   	 {		 
	   	System.out.println(e.getMessage());
	   	
	   	}
	   	 finally
	   	 {
	   		 this.sessionFactory.close();
	   	 }
	    }
	    */

	public void insertUser(String uname, String email, String fname, String phone, String pass)
	  {
	 Session session = this.sessionFactory.getCurrentSession();
	 try
	 {
		 Transaction t = session.beginTransaction();
     
     UserD reg = new UserD();
     reg.setUserName(uname);
     Integer tk = (Integer)session.save(reg);
     t.commit();
     insertUserDet(tk,email,fname,phone);
     insertPass(pass,tk);
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
	
	
	public void insertPass(String pass, int id)
	  {
	 Session session = this.sessionFactory.getCurrentSession();
	 try
	 {
		 Transaction t = session.beginTransaction();
   
   AuthenticateId reg = new AuthenticateId();
   reg.setPassword(pass);;
  reg.setUserId(id);
  
  Authenticate a = new Authenticate();
  a.setId(reg);
  session.save(a);
   t.commit();
  
	 }
	 catch(Exception e)
	 {		 
	System.out.println(e.getMessage());
	
	}

}
	
	public void insertUserDet(int k, String ee, String l, String p)
	  {
	 Session session = this.sessionFactory.getCurrentSession();
	 try
	 {
		 
   
  
   Transaction t1 = session.beginTransaction();
   RegDetId r = new RegDetId();
   r.setEmail(ee);
   r.setFullName(l);
r.setLi(k);
r.setPhone(p);
RegDet rr = new RegDet();
rr.setId(r);
session.save(rr);
   t1.commit();
	 }
	 catch(Exception e)
	 {		 
	System.out.println(e.getMessage());
	
	}
	

}
}
