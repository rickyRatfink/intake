package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtJobMetric;

public class CwtJobMetricDao extends GenericDao {
	
	public CwtJobMetricDao() {
        super();
    }
	
    public CwtJobMetric find(Long id) throws HibernateException {
    	return (CwtJobMetric) super.findById(CwtJobMetric.class, id);
    }
    public Long save(CwtJobMetric intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtJobMetric intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtJobMetric intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtJobMetric.class);
    }
}
