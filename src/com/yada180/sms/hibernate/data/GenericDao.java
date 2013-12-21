package com.yada180.sms.hibernate.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.IntakeMedicalCondition;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.HibernateUtil;
import com.yada180.sms.hibernate.data.HibernateFactory;

public class GenericDao {

	private static SessionFactory factory;
	private final static Logger LOGGER = Logger.getLogger(IntakeDao.class
			.getName());
	private static Session session;

	public GenericDao() {
		LOGGER.setLevel(Level.SEVERE);		
	}
	
	
	public Object findById(Class c,Long id) {
		Object obj=null;
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			obj = session.get(c, id);
			session.getTransaction().commit();
			session.flush();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			session.close();
		}
		return obj;
	}
	
	
	public Long save(Object obj) {
		Long key = null;
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			key = (Long) session.save(obj);
			session.getTransaction().commit();
			session.flush();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			session.close();
		}
		return key;
	}
	
	public void update(Object obj) {
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			session.update(obj);
			session.getTransaction().commit();
			session.flush();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			session.close();
		}
	}
	
	public void delete(Object obj) {
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			session.delete(obj);
			session.getTransaction().commit();
			session.flush();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			session.close();
		}
	}
	
	 protected List findAll(Class clazz) {
	        List objects = null;
	        try {
	        	session = HibernateFactory.openSession();
				session.beginTransaction();
				Query query = session.createQuery("from " + clazz.getName());
	            if ("com.yada180.sms.domain.CwtJob".equals(clazz.getName().toString()))
	            	query = session.createQuery("from " + clazz.getName()+" order by title");
	            if ("com.yada180.sms.domain.CwtSupervisor".equals(clazz.getName().toString()))
	            	query = session.createQuery("from " + clazz.getName()+" order by firstname");
	            		
	            objects = query.list();
	            session.getTransaction().commit();
	            session.flush();
	        } catch (HibernateException e) {
	        	session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
	        return objects;
	    }
	 
	 protected List findAllByFarm(Class clazz, String farm) {
	        List objects = null;
	        try {
	        	session = HibernateFactory.openSession();
				session.beginTransaction();
	            Query query = session.createQuery("from " + clazz.getName() + " where farmBase = :farmBase ");
	            query.setString("farmBase", farm);
	            objects = query.list();
	            session.getTransaction().commit();
	            session.flush();
	        } catch (HibernateException e) {
	        	session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
	        return objects;
	    }
	
		public List search(String entryDate, String exitDate, String lastname,
				String firstname, String ssn, String dob, String farm, String ged, String archived, String status) {

			StringBuffer query = new StringBuffer("from Intake where 1=1 ");
			
			if (lastname != null && lastname.length() > 0)
				query.append(" and lastname = :lastname ");
			if (firstname != null && firstname.length() > 0)
				query.append(" and firstname = :firstname ");
			if (ssn != null && ssn.length() > 0)
				query.append(" and ssn = :ssn ");
			if (dob != null && dob.length() > 0)
				query.append(" and dob = :dob ");
			if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
				query.append(" and farmBase = :farmBase ");
			if (ged != null && ged.length() > 0)
				query.append(" and needGed = :needGed ");
			if (archived != null && archived.length() > 0)
				query.append(" and archivedFlag = :archivedFlag ");
			if (status != null && status.length() > 0)
				query.append(" and intakeStatus = :intakeStatus ");
			query.append(" Order by lastname, firstname");
			
			
			List list = null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();
				Query q = session.createQuery(query.toString());
				q.setMaxResults(200);
				if (lastname != null && lastname.length() > 0)
					q.setString("lastname", lastname);
				if (firstname != null && firstname.length() > 0)
					q.setString("firstname", firstname);
				if (ssn != null && ssn.length() > 0)
					q.setString("ssn", ssn);
				if (dob != null && dob.length() > 0)
					q.setString("dob", dob);
				if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
					q.setString("farmBase", farm);
				if (ged != null && ged.length() > 0)
					q.setString("needGed", ged);
				if (archived != null && archived.length() > 0)
					q.setString("archivedFlag", archived);
				if (status != null && status.length() > 0)
					q.setString("intakeStatus", status);
				
				list = q.list();
				session.getTransaction().commit();
				session.flush();
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();			
			}
			return list;
		}

		
		public List searchApplications(String entryDate, String exitDate,
				String lastname, String firstname, String ssn, String dob,
				String status, String farm) {

			StringBuffer query = new StringBuffer("from Intake where 1=1 ");
			if (lastname != null && lastname.length() > 0)
				query.append(" and lastname = :lastname ");
			if (firstname != null && firstname.length() > 0)
				query.append(" and firstname = :firstname ");
			if (ssn != null && ssn.length() > 0)
				query.append(" and ssn = :ssn ");
			if (dob != null && dob.length() > 0)
				query.append(" and dob = :dob ");
			if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
				query.append(" and farmBase = :farmBase ");

			query.append(" and applicationStatus = :applicationStatus order by creation_date desc ");

			List list = null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();
				Query q = session.createQuery(query.toString());
				q.setMaxResults(200);
				
				if (lastname != null && lastname.length() > 0)
					q.setString("lastname", lastname);
				if (firstname != null && firstname.length() > 0)
					q.setString("firstname", firstname);
				if (ssn != null && ssn.length() > 0)
					q.setString("ssn", ssn);
				if (dob != null && dob.length() > 0)
					q.setString("dob", dob);
				if (status != null && status.length() > 0)
					q.setString("applicationStatus", status);
				else
					q.setString("applicationStatus", "Pending");

				if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
					q.setString("farmBase", farm);

				list = q.list();
				
				session.getTransaction().commit();
				session.flush();
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
			return list;
		}

		public List listClass(String classNumber, String farm) {

			List<Intake> list = new ArrayList<Intake>();
			Transaction tx = null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();

				StringBuffer query = new StringBuffer("from Intake where 1=1 ");
				query.append(" and class_ = :class_ ");
				query.append(" and farmBase = :farmBase ");
				query.append(" and archivedFlag = :archivedFlag ");
				query.append(" and intakeStatus = :intakeStatus order by entryDate asc ");
				Query q = session.createQuery(query.toString());
				q.setString("class_", classNumber);
				q.setString("farmBase", farm);
				q.setString("archivedFlag", "No");
				q.setString("intakeStatus", "In Program");
				list = q.list();
				
				session.getTransaction().commit();
				session.flush();
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
			return list;
		}
		
		public List listByStatus(String status, String farm) {

			List<Intake> list = new ArrayList<Intake>();
			Transaction tx = null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();

				StringBuffer query = new StringBuffer("from Intake where ");
				query.append(" intakeStatus = :intakeStatus ");
				query.append(" and farmBase = :farmBase ");
				query.append(" and archivedFlag = :archivedFlag ");
				Query q = session.createQuery(query.toString());
				
				q.setString("farmBase", farm);
				q.setString("archivedFlag", "No");
				q.setString("intakeStatus", status);
				list = q.list();
				
				session.getTransaction().commit();
				session.flush();
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
			return list;
		}

		public List findByObjectId(Class c, String objectIdName, Long id) {

			LOGGER.setLevel(Level.INFO);
			List<Object> list = new ArrayList<Object>();
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from "+c.getName()+" where "+objectIdName+" = :"+objectIdName);
				Query q = session.createQuery(query.toString());
				q.setLong(objectIdName, id);
				list = q.list();
				session.getTransaction().commit();
				session.flush();
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
			return list;
		}
		
		public List findByIntakeId(Class c, Long id) {

			LOGGER.setLevel(Level.INFO);
			List<Object> list = new ArrayList<Object>();
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from "+c.getName()+" where intakeId = :intakeId ");
				Query q = session.createQuery(query.toString());
				q.setLong("intakeId", id);
				list = q.list();
				session.getTransaction().commit();
				session.flush();
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}
			return list;
		}
		
		public SystemUser authenticate(String username, String password) {
			SystemUser user=null;
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				Query q = session
						.createQuery("from SystemUser where username = :username and password = :password");
				q.setString("username", username);
				q.setString("password", password);

				List result = q.list();
				if (result.size() > 0)
					user = (SystemUser) result.get(0);

				session.getTransaction().commit();
				session.flush();
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();
			}

			return user;
		}
}
