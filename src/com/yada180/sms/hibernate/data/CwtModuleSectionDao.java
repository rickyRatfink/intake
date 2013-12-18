package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtMetrics;
import com.yada180.sms.domain.CwtModuleSection;

public class CwtModuleSectionDao extends GenericDao {
	
	public CwtModuleSectionDao() {
        super();
    }
	
    public CwtModuleSection find(Long id) throws HibernateException {
    	return (CwtModuleSection) super.findById(CwtModuleSection.class, id);
    }
    public List<CwtModuleSection> findAll() throws HibernateException {
    	return (List<CwtModuleSection>) super.findAll(CwtModuleSection.class);
    }
    public Long save(CwtModuleSection intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtModuleSection intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtModuleSection intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtModuleSection.class);
    }
}
