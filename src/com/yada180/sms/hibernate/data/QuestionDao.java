package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.Question;

public class QuestionDao extends GenericDao {
	
	public QuestionDao() {
        super();
    }
	
    public Question find(Long id) throws HibernateException {
    	return (Question) super.findById(Question.class, id);
    }
    public Long save(Question intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(Question intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(Question intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(Question.class);
    }
}