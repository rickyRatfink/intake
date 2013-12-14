package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtJob;

public class CwtJobDao extends GenericDao {
	
	public CwtJobDao() {
        super();
    }
	
    public CwtJob find(Long id) throws HibernateException {
    	return (CwtJob) super.findById(CwtJob.class, id);
    }
    public Long save(CwtJob intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtJob intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtJob intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtJob.class);
    }
}
