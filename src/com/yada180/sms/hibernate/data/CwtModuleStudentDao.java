package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtModuleStudent;

public class CwtModuleStudentDao extends GenericDao {
	
	public CwtModuleStudentDao() {
        super();
    }
	
    public CwtModuleStudent find(Long id) throws HibernateException {
    	return (CwtModuleStudent) super.findById(CwtModuleStudent.class, id);
    }
    public Long save(CwtModuleStudent intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtModuleStudent intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtModuleStudent intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtModuleStudent.class);
    }
}
