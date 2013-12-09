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
import com.yada180.sms.domain.CwtProgramMetric;
import com.yada180.sms.domain.IntakeMedicalCondition;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.HibernateUtil;

public class CwtProgramMetricDao {
	private static SessionFactory factory;
	private final static Logger LOGGER = Logger
			.getLogger(CwtProgramMetricDao.class.getName());
	private static Session session;

	public CwtProgramMetricDao() {

		LOGGER.setLevel(Level.INFO);

		try {
			session = HibernateUtil.currentSession();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public CwtProgramMetric findById(Long id) {

		CwtProgramMetric CwtProgramMetric = (CwtProgramMetric) session.get(
				CwtProgramMetric.class, id);

		return CwtProgramMetric;
	}

	public List listCwtProgramMetrics() {
		LOGGER.setLevel(Level.INFO);
		List<CwtProgramMetric> list = new ArrayList<CwtProgramMetric>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			list = session.createQuery("FROM CwtProgramMetric").list();
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

	/* Method to INSERT CwtProgramMetric */
	public Long addCwtProgramMetric(CwtProgramMetric obj) {
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

	/* Method to UPDATE CwtProgramMetric */
	public void updateCwtProgramMetric(CwtProgramMetric obj) {
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			CwtProgramMetric t = (CwtProgramMetric) session.get(CwtProgramMetric.class, obj.getProgramMetricId());
			if (t != null) { 
			session.evict(t); 
			} 
			// CwtProgramMetric CwtProgramMetric =
			// (CwtProgramMetric)session.get(CwtProgramMetric.class,
			// CwtProgramMetricID);
			// CwtProgramMetric.setSalary( salary );
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
	}

	/* Method to DELETE CwtProgramMetric */
	public void deleteCwtProgramMetric(Long key) {
		Transaction tx = null;

		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			CwtProgramMetric obj = (CwtProgramMetric) session.get(
					CwtProgramMetric.class, key);
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
		List<CwtProgramMetric> list = new ArrayList<CwtProgramMetric>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			StringBuffer query = new StringBuffer(
					"from CwtProgramMetric where metricId = :metricId ");
			Query q = session.createQuery(query.toString());
			q.setLong("metricId", id);
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
