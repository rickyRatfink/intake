package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtSupervisor;

public class CwtSupervisorDao extends GenericDao {
	
	public CwtSupervisorDao() {
        super();
    }
	
    public CwtSupervisor find(Long id) throws HibernateException {
    	return (CwtSupervisor) super.findById(CwtSupervisor.class, id);
    }
    public Long save(CwtSupervisor intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtSupervisor intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtSupervisor intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtSupervisor.class);
    }
}
