package com.yada180.sms.hibernate.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.SystemUser;

public class GenericDao {

	private static SessionFactory factory;
	private final static Logger LOGGER = Logger.getLogger(IntakeDao.class
			.getName());
	
	public GenericDao() {
		LOGGER.setLevel(Level.SEVERE);		
	}
	
	
	public Object findById(Class c,Long id) {
		Object obj=null;
		Session session=null;
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			obj = session.get(c, id);
			if (session.isOpen()) {
				session.flush();
				session.getTransaction().commit();	
			}
		} catch (Exception e) {
			if (session.isOpen())
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			if (session.isOpen())
			session.close();
		}
		return obj;
	}
	
	
	public Long save(Object obj) {
		Long key = null;
		Session session=null;
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			key = (Long) session.save(obj);
			if (session.isOpen()) {
				session.flush();
				session.getTransaction().commit();	
			}
			
		} catch (HibernateException e) {
			if (session.isOpen())
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			if (session.isOpen())
			session.close();
		}
		return key;
	}
	
	public void update(Object obj) {
		Session session=null;
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			session.update(obj);
			if (session.isOpen()) {
				session.flush();
				session.getTransaction().commit();	
			}
		} catch (HibernateException e) {
			if (session.isOpen())
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			if (session.isOpen())
			session.close();
		}
	}
	
	public void delete(Object obj) {
		Session session=null;
		try {
			session = HibernateFactory.openSession();
			session.beginTransaction();
			session.delete(obj);
			if (session.isOpen()) {
				session.flush();
				session.getTransaction().commit();	
			}
		} catch (HibernateException e) {
			if (session.isOpen())
			session.getTransaction().rollback();
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			if (session.isOpen())
			session.close();
		}
	}
	
	 protected List findAll(Class clazz) {
	        List objects = null;
	        Session session=null;
	        try {
	        	session = HibernateFactory.openSession();
				session.beginTransaction();
				Query query = session.createQuery("from " + clazz.getName());
	            if ("com.yada180.sms.domain.CwtJob".equals(clazz.getName().toString()))
	            	query = session.createQuery("from " + clazz.getName()+" order by title");
	            if ("com.yada180.sms.domain.CwtSupervisor".equals(clazz.getName().toString()))
	            	query = session.createQuery("from " + clazz.getName()+" order by firstname");
	            if ("com.yada180.sms.domain.CwtModuleSection".equals(clazz.getName().toString()))
	            	query = session.createQuery("from " + clazz.getName()+" where status='Active' order by farmBase, moduleOfferingId ");
	            
	            
	            objects = query.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
	        } catch (HibernateException e) {
	        	if (session.isOpen())
	        	session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
	        return objects;
	    }
	 
	 protected List filterSection(Class clazz, String farm, Long moduleId) {
	        List objects = null;
	        Session session=null;
	        try {
	        	session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer q = new StringBuffer(" from "+ clazz.getName()+" where 1=1 ");
				if (farm!=null&&farm.length()>0)
					q.append(" and farmBase = :farmBase");
				if (moduleId!=null&&moduleId>0)
					q.append(" and moduleId = :moduleId");
				Query query = session.createQuery(q.toString());
				
				if (farm!=null&&farm.length()>0)
					query.setString("farmBase", farm);
				if (moduleId!=null&&moduleId>0)
					query.setLong("moduleId", moduleId);
				
	            objects = query.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
	        } catch (HibernateException e) {
	        	if (session.isOpen())
	        	session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
	        return objects;
	    }
	 
	 protected List findAllByFarm(Class clazz, String farm) {
	        List objects = null;
	        Session session=null;
	        try {
	        	session = HibernateFactory.openSession();
				session.beginTransaction();
	            Query query = session.createQuery("from " + clazz.getName() + " where farmBase = :farmBase ");
	            query.setString("farmBase", farm);
	            objects = query.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
	        } catch (HibernateException e) {
	        	if (session.isOpen())
	        	session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
	        return objects;
	    }
	
		public List search(String entryDate, String exitDate, String lastname,
				String firstname, String ssn, String dob, String farm, String ged, String archived, String status, 
				String currentClass, Long jobId, Long supervisorId, String driverFlag) {

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
			if (currentClass != null && currentClass.length() > 0)
				query.append(" and class_ = :class_ ");
			if (jobId != null && jobId!=0 )
				query.append(" and jobId = :jobId ");
			if (supervisorId != null && supervisorId != 0)
				query.append(" and supervisorId = :supervisorId ");
			if (driverFlag != null && driverFlag.length() > 0)
				query.append(" and dlFlag = :dlFlag ");
			query.append(" Order by lastname, firstname");
			
		
			List list = null;
			Session session=null;
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
				if (currentClass != null && currentClass.length() > 0)
					q.setString("class_", currentClass);
				if (jobId != null && jobId != 0)
					q.setLong("jobId", jobId);
				if (supervisorId != null && supervisorId != 0)
					q.setLong("supervisorId", supervisorId);
				if (driverFlag != null && driverFlag.length() > 0)
					q.setString("dlFlag", driverFlag);				
				
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (HibernateException e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();			
			}
			return list;
		}

		
		public List searchApplications(String entryDate, String exitDate,
				String lastname, String firstname, String ssn, String dob,
				String status, String driverFlag, String gedFlag, String farm) {

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
			if (driverFlag != null && driverFlag.length() > 0)
				query.append(" and dlFlag = :dlFlag ");
			if (gedFlag != null && gedFlag.length() > 0)
				query.append(" and needGed = :needGed ");
			if (status != null && status.length() > 0 && !status.equals("ALL"))
				query.append(" and applicationStatus = :applicationStatus");

			query.append(" order by creation_date desc");
			List list = null;
			Session session=null;
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
				if (status != null && status.length() > 0 && !status.equals("ALL"))
					q.setString("applicationStatus", status);
				if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
					q.setString("farmBase", farm);
				if (driverFlag != null && driverFlag.length() > 0)
					q.setString("dlFlag",driverFlag);
				if (gedFlag != null && gedFlag.length() > 0)
					q.setString("needGed",gedFlag);

				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (HibernateException e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list;
		}

		public List listClass(String classNumber, String farm) {

			List<Intake> list = new ArrayList<Intake>();
			Transaction tx = null;
			Session session=null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();

				StringBuffer query = new StringBuffer("from Intake where 1=1 ");
				query.append(" and class_ = :class_ ");
				query.append(" and farmBase = :farmBase ");
				query.append(" and archivedFlag = :archivedFlag ");
				query.append(" and intakeStatus = :intakeStatus ORDER BY STR_TO_DATE(entry_date, '%m/%d/%Y') asc ");
				Query q = session.createQuery(query.toString());
				q.setString("class_", classNumber);
				q.setString("farmBase", farm);
				q.setString("archivedFlag", "No");
				q.setString("intakeStatus", "In Program");
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (HibernateException e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list;
		}
		
		public List listByStatus(String status, String farm) {

			List<Intake> list = new ArrayList<Intake>();
			Transaction tx = null;
			Session session=null;
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
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (HibernateException e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list;
		}

		public List findByObjectId(Class c, String objectIdName, Long id) {
			
			LOGGER.setLevel(Level.INFO);
			List<Object> list = new ArrayList<Object>();
			Session session=null;
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from "+c.getName()+" where "+objectIdName+" = :"+objectIdName);
				
				 if ("com.yada180.sms.domain.CwtRoster".equals(c.getName().toString())) 
		            query.append(" and archivedFlag='No' ");		           
				 
				Query q = session.createQuery(query.toString());
				q.setLong(objectIdName, id);
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (Exception e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list;
		}
		
		public List findRosterHistoryByIntakeId(Long id) {
			
			LOGGER.setLevel(Level.INFO);
			List<Object> list = new ArrayList<Object>();
			Session session=null;
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from CwtRoster where intakeId = :intakeId");
				Query q = session.createQuery(query.toString());
				q.setLong("intakeId", id);
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (Exception e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list;
		}
		

		
		public boolean findByIntakeIdAndObjectId(Class c, String objectIdName, Long intakeId, Long id) {

			LOGGER.setLevel(Level.INFO);
			List<Object> list = new ArrayList<Object>();
			boolean match=false;
			Session session=null;
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from "+c.getName()+" where "+objectIdName+" = :"+objectIdName+" and intakeId = :intakeId");
				Query q = session.createQuery(query.toString());
				
				q.setLong(objectIdName, id);
				q.setLong("intakeId", intakeId);
				list = q.list();
				if (list.size()>0)
					match=true;
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (Exception e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return match;
		}
		
		public Object findByObjectIdOnLikeClause(Class c, String objectName, String value) {

			LOGGER.setLevel(Level.INFO);
			Session session=null;
			List<Object> list = new ArrayList<Object>();
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from "+c.getName()+" where "+objectName+" LIKE :"+objectName);
				Query q = session.createQuery(query.toString());
				q.setParameter(objectName, "%"+value+"%");
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (Exception e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list.get(0);
		}
		
		public List findByIntakeId(Class c, Long id) {

			LOGGER.setLevel(Level.INFO);
			Session session=null;
			List<Object> list = new ArrayList<Object>();
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				StringBuffer query = new StringBuffer(
						"from "+c.getName()+" where intakeId = :intakeId ");
				Query q = session.createQuery(query.toString());
				q.setLong("intakeId", id);
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (Exception e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}
			return list;
		}
		
		public SystemUser authenticate(String username, String password) {
			SystemUser user=null;
			Session session=null;
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
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (Exception e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();
			}

			return user;
		}
		
		public List searchPass(String passDate) {

			StringBuffer query = new StringBuffer("from StudentPassHistory where 1=1 ");
			Session session=null;
			if (passDate != null && passDate.length() > 0)
				query.append(" and passDate = :passDate ");
			
			List list = null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();
				Query q = session.createQuery(query.toString());
				q.setMaxResults(200);
				if (passDate != null && passDate.length() > 0)
					q.setString("passDate", passDate);
				
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (HibernateException e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();			
			}
			return list;
		}
		
		public List searchRosterList(String farm) {
			Session session=null;
			StringBuffer query = new StringBuffer("from Intake where 1=1 ");
			query.append(" and intakeStatus = :intakeStatus ");
			query.append(" and archivedFlag = :archivedFlag ");
			if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
				query.append(" and farmBase = :farmBase ");
			query.append(" and class_ in ('Orientation','1','2','3','4','5','6','Fresh Start','SLS','Intern') order by firstname, lastname ");
			
			List list = null;
			try {

				session = HibernateFactory.openSession();
				session.beginTransaction();
				Query q = session.createQuery(query.toString());
				q.setMaxResults(200);
				if (farm != null && farm.length() > 0 && !farm.equals("ALL"))
					q.setString("farmBase", farm);	
				q.setString("intakeStatus", "In Program");
				q.setString("archivedFlag", "No");
				
				list = q.list();
				if (session.isOpen()) {
					session.flush();
					session.getTransaction().commit();	
				}
			} catch (HibernateException e) {
				if (session.isOpen())
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				if (session.isOpen())
				session.close();			
			}
			return list;
		}
		
		
		public List findIntakeModules(Long id) {
			Session session=null;
			StringBuffer query = new StringBuffer(" select distinct module_id from cwt_roster where intake_id= :intakeId ");
			
			List list = null;
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				SQLQuery q = session.createSQLQuery(query.toString());
				q.setParameter("intakeId", id);
				list = q.list();				
				session.flush();
				session.getTransaction().commit();	
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();			
			}
			return list;
		}
		
		public List findIntakePrograms(Long id) {
			Session session=null;
			StringBuffer query = new StringBuffer(" select distinct(cwt_program.program_id) from  ");
				query.append("cwt_roster inner join cwt_modules ");
				query.append("on cwt_roster.module_id=cwt_modules.module_id ");
				query.append("inner join cwt_program ");
				query.append("on cwt_program.program_id=cwt_modules.program_id  ");
				query.append("where cwt_roster.intake_id= :intakeId ");
			
			List list = null;
			try {
				session = HibernateFactory.openSession();
				session.beginTransaction();
				SQLQuery q = session.createSQLQuery(query.toString());
				q.setParameter("intakeId", id);
				list = q.list();				
				session.flush();
				session.getTransaction().commit();	
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				e.printStackTrace();
				throw new HibernateException(e);
			} finally {
				session.close();			
			}
			return list;
		}

}
