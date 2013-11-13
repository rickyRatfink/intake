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
import com.yada180.sms.domain.StudentDisciplineHistory;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.hibernate.HibernateUtil;

public class StudentDisciplineHistoryDao {
	private static SessionFactory factory;
	private final static Logger LOGGER = Logger
			.getLogger(StudentDisciplineHistoryDao.class.getName());
	private static Session session;

	public StudentDisciplineHistoryDao() {

		LOGGER.setLevel(Level.INFO);

		try {
			session = HibernateUtil.currentSession();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public StudentDisciplineHistory findById(Integer id) {

		StudentDisciplineHistory StudentDisciplineHistory = (StudentDisciplineHistory) session.get(
				StudentDisciplineHistory.class, id);

		return StudentDisciplineHistory;
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
					"from StudentDisciplineHistory where intakeId = :intakeId ");
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

	public List listStudentDisciplineHistorys() {
		LOGGER.setLevel(Level.INFO);
		List<StudentDisciplineHistory> list = new ArrayList<StudentDisciplineHistory>();
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			tx.begin();
			list = session.createQuery("FROM StudentDisciplineHistory").list();
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

	/* Method to INSERT StudentDisciplineHistory */
	public Long addStudentDisciplineHistory(StudentDisciplineHistory obj) {
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

	/* Method to UPDATE StudentDisciplineHistory */
	public void updateStudentDisciplineHistory(StudentDisciplineHistory obj) {
		Transaction tx = null;
		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			// StudentDisciplineHistory StudentDisciplineHistory =
			// (StudentDisciplineHistory)session.get(StudentDisciplineHistory.class,
			// StudentDisciplineHistoryID);
			// StudentDisciplineHistory.setSalary( salary );
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			
		}
	}

	/* Method to DELETE StudentDisciplineHistory */
	public void deleteStudentDisciplineHistory(Long key) {
		Transaction tx = null;

		try {

			session = HibernateUtil.currentSession();
			tx = session.beginTransaction();
			StudentDisciplineHistory obj = (StudentDisciplineHistory) session.get(
					StudentDisciplineHistory.class, key);
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

		StringBuffer query = new StringBuffer("from StudentDisciplineHistory");
		query.append(" where intakeId = :intakeId ");
		Query q = session.createQuery(query.toString());
		q.setLong("intakeId", key);

		List list = q.list();
		return list;
	}
}
