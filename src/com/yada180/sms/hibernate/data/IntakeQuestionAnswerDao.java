package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.IntakeQuestionAnswer;

public class IntakeQuestionAnswerDao extends GenericDao {
	
	public IntakeQuestionAnswerDao() {
        super();
    }
	
    public IntakeQuestionAnswer find(Long id) throws HibernateException {
    	return (IntakeQuestionAnswer) super.findById(IntakeQuestionAnswer.class, id);
    }
    public Long save(IntakeQuestionAnswer intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(IntakeQuestionAnswer intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(IntakeQuestionAnswer intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(IntakeQuestionAnswer.class);
    }
}
