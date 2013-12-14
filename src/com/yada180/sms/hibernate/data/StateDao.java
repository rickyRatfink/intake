package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.State;

public class StateDao extends GenericDao {
	
	public StateDao() {
        super();
    }
	
    public State find(Long id) throws HibernateException {
    	return (State) super.findById(State.class, id);
    }
    public Long save(State intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(State intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(State intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(State.class);
    }
}
