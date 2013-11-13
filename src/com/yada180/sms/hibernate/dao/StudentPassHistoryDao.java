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

import com.yada180.sms.domain.IntakeJobSkill;
import com.yada180.sms.domain.StudentPassHistory;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.HibernateUtil;

public class StudentPassHistoryDao {
	private static SessionFactory factory;
	private final static Logger LOGGER = Logger
			.getLogger(StudentPassHistoryDao.class.getName());
	private static Session session;

	public StudentPassHistoryDao() {

		LOGGER.setLevel(Level.INFO);

		try {
			session = HibernateUtil.currentSession();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public StudentPassHistory findById(Long id) {

		StudentPassHistory StudentPassHistory = (StudentPassHistory) session
				.get(StudentPassHistory.class, id);

		return StudentPassHistory;
	}

	public List findByIntakeId(Long id) {

		LOGGER.setLevel(Level.INFO);
		List<IntakeJobSkill> list = new ArrayList<IntakeJobSkill>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			StringBuffer query = new StringBuffer(
					"from StudentPassHistory where intakeId = :intakeId ");
			Query q = session.createQuery(query.toString());
			q.setLong("intakeId", id);
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

	public List listStudentPassHistorys() {
		LOGGER.setLevel(Level.INFO);
		List<StudentPassHistory> list = new ArrayList<StudentPassHistory>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			list = session.createQuery("FROM StudentPassHistory").list();
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

	/* Method to INSERT StudentPassHistory */
	public Long addStudentPassHistory(StudentPassHistory obj) {
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

	/* Method to UPDATE StudentPassHistory */
	public void updateStudentPassHistory(StudentPassHistory obj) {
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			// StudentPassHistory StudentPassHistory =
			// (StudentPassHistory)session.get(StudentPassHistory.class,
			// StudentPassHistoryID);
			// StudentPassHistory.setSalary( salary );
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
	}

	/* Method to DELETE StudentPassHistory */
	public void deleteStudentPassHistory(Long key) {
		Transaction tx = null;

		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			StudentPassHistory obj = (StudentPassHistory) session.get(
					StudentPassHistory.class, key);
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
	}

	public List search(Long key) {

		StringBuffer query = new StringBuffer("from StudentPassHistory");
		query.append(" where intakeId = :intakeId ");
		Query q = session.createQuery(query.toString());
		q.setLong("intakeId", key);

		List list = q.list();
		return list;
	}
}
