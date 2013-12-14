package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtProgram;

public class CwtProgramDao extends GenericDao {
	
	public CwtProgramDao() {
        super();
    }
	
    public CwtProgram find(Long id) throws HibernateException {
    	return (CwtProgram) super.findById(CwtProgram.class, id);
    }
    public Long save(CwtProgram intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(CwtProgram intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(CwtProgram intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(CwtProgram.class);
    }
}
