package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.SystemUser;

public class SystemUserDao extends GenericDao {
	
	public SystemUserDao() {
        super();
    }
	
    public SystemUser find(Long id) throws HibernateException {
    	return (SystemUser) super.findById(SystemUser.class, id);
    }
    public Long save(SystemUser intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(SystemUser intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(SystemUser intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(SystemUser.class);
    }
    public SystemUser authenticate(String username, String password) throws HibernateException {
    	return (SystemUser) super.authenticate(username, password);
    }
}
