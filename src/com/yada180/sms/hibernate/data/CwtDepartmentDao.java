package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtDepartment;

public class CwtDepartmentDao extends GenericDao {
	
	public CwtDepartmentDao() {
        super();
    }
	
    public CwtDepartment find(Long id) throws HibernateException {
    	return (CwtDepartment) super.findById(CwtDepartment.class, id);
    }
    public Long save(CwtDepartment intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtDepartment intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtDepartment intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtDepartment.class);
    }
}
