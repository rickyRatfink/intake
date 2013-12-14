package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CourseRotationHistory;

public class CourseRotationHistoryDao extends GenericDao {
	
	public CourseRotationHistoryDao() {
        super();
    }
	
    public CourseRotationHistory find(Long id) throws HibernateException {
    	return (CourseRotationHistory) super.findById(CourseRotationHistory.class, id);
    }
    public Long save(CourseRotationHistory intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CourseRotationHistory intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CourseRotationHistory intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CourseRotationHistory.class);
    }
}
