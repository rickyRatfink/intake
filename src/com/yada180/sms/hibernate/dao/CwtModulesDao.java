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

import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.Intake;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.HibernateUtil;

public class CwtModulesDao {
	private static SessionFactory factory;
	private final static Logger LOGGER = Logger.getLogger(CwtModulesDao.class
			.getName());
	private static Session session;

	public CwtModulesDao() {

		LOGGER.setLevel(Level.INFO);

		try {
			session = HibernateUtil.currentSession();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public CwtModules findById(Long id) {

		CwtModules CwtModules = (CwtModules) session.get(CwtModules.class, id);

		return CwtModules;
	}

	public List listCwtModuless() {
		LOGGER.setLevel(Level.INFO);
		List<CwtModules> list = new ArrayList<CwtModules>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			list = session.createQuery("FROM CwtModules").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} 
		return list;
	}

	/* Method to INSERT CwtModules */
	public Long addCwtModules(CwtModules obj) {
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
		} 
		return key;
	}

	/* Method to UPDATE CwtModules */
	public void updateCwtModules(CwtModules obj) {
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			CwtModules t = (CwtModules) session.get(CwtModules.class, obj.getModuleId());
			if (t != null) { 
			session.evict(t); 
			} 
			// CwtModules CwtModules =
			// (CwtModules)session.get(CwtModules.class, CwtModulesID);
			// CwtModules.setSalary( salary );
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
	}

	/* Method to DELETE CwtModules */
	public void deleteCwtModules(Long key) {
		Transaction tx = null;

		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			CwtModules obj = (CwtModules) session.get(CwtModules.class, key);
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
	}

}
