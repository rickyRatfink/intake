package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtMetrics;
import com.yada180.sms.domain.CwtModules;

public class CwtModulesDao extends GenericDao {
	
	public CwtModulesDao() {
        super();
    }
	
    public CwtModules find(Long id) throws HibernateException {
    	return (CwtModules) super.findById(CwtModules.class, id);
    }
    public List<CwtModules> findAll() throws HibernateException {
    	return (List<CwtModules>) super.findAll(CwtModules.class);
    }
    public Long save(CwtModules intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtModules intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtModules intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtModules.class);
    }
}
