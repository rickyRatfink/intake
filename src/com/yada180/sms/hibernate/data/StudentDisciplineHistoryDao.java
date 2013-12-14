package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.StudentDisciplineHistory;

public class StudentDisciplineHistoryDao extends GenericDao {
	
	public StudentDisciplineHistoryDao() {
        super();
    }
	
    public StudentDisciplineHistory find(Long id) throws HibernateException {
    	return (StudentDisciplineHistory) super.findById(StudentDisciplineHistory.class, id);
    }
    public Long save(StudentDisciplineHistory intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(StudentDisciplineHistory intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(StudentDisciplineHistory intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(StudentDisciplineHistory.class);
    }
}
