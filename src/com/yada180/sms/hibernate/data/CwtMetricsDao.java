package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtMetrics;
import com.yada180.sms.domain.CwtProgram;

public class CwtMetricsDao extends GenericDao {
	
	public CwtMetricsDao() {
        super();
    }
	
    public CwtMetrics find(Long id) throws HibernateException {
    	return (CwtMetrics) super.findById(CwtMetrics.class, id);
    }
    public List<CwtMetrics> findAll() throws HibernateException {
    	return (List<CwtMetrics>) super.findAll(CwtMetrics.class);
    }
    public Long save(CwtMetrics intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtMetrics intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtMetrics intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtMetrics.class);
    }
}
