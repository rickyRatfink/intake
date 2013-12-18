package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtModules;
import com.yada180.sms.domain.CwtRoster;

public class CwtRosterDao extends GenericDao {
	
	public CwtRosterDao() {
        super();
    }
	
    public CwtRoster find(Long id) throws HibernateException {
    	return (CwtRoster) super.findById(CwtRoster.class, id);
    }
    public List<CwtRoster> findAll() throws HibernateException {
    	return (List<CwtRoster>) super.findAll(CwtRoster.class);
    }
    public Long save(CwtRoster intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtRoster intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtRoster intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtRoster.class);
    }
}
