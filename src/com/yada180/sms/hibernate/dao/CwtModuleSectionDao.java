package com.yada180.sms.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.yada180.sms.domain.CwtModuleSection;
import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.IntakeMedicalCondition;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.HibernateUtil;

public class CwtModuleSectionDao {
	private static SessionFactory factory;
	private final static Logger LOGGER = Logger
			.getLogger(CwtModuleSectionDao.class.getName());
	private static Session session;

	public CwtModuleSectionDao() {

		LOGGER.setLevel(Level.INFO);

		try {
			session = HibernateUtil.currentSession();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public CwtModuleSection findById(Long id) {

		CwtModuleSection CwtModuleSection = (CwtModuleSection) session.get(
				CwtModuleSection.class, id);

		return CwtModuleSection;
	}

	public List listCwtModuleSections() {
		LOGGER.setLevel(Level.INFO);
		List<CwtModuleSection> list = new ArrayList<CwtModuleSection>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			list = session.createQuery("FROM CwtModuleSection").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			
		}
		return list;
	}

	/* Method to INSERT CwtModuleSection */
	public Long addCwtModuleSection(CwtModuleSection obj) {
		Transaction tx = null;
		Long key = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			key = (Long) session.save(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
		return key;
	}

	/* Method to UPDATE CwtModuleSection */
	public void updateCwtModuleSection(CwtModuleSection obj) {
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			// CwtModuleSection CwtModuleSection =
			// (CwtModuleSection)session.get(CwtModuleSection.class,
			// CwtModuleSectionID);
			// CwtModuleSection.setSalary( salary );
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
	}

	/* Method to DELETE CwtModuleSection */
	public void deleteCwtModuleSection(Long key) {
		Transaction tx = null;

		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			CwtModuleSection obj = (CwtModuleSection) session.get(
					CwtModuleSection.class, key);
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
	}

	public List findByMetricId(Long id) {

		LOGGER.setLevel(Level.INFO);
		List<CwtModuleSection> list = new ArrayList<CwtModuleSection>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			CwtModuleSection t = (CwtModuleSection) session.get(CwtModuleSection.class, id);
			if (t != null) { 
			session.evict(t); 
			} 
			StringBuffer query = new StringBuffer(
					"from CwtModuleSection where moduleId = :moduleId ");
			Query q = session.createQuery(query.toString());
			q.setLong("moduleId", id);
			list = q.list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			
		}
		return list;
	}

}
