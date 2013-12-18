package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.Intake;

public class IntakeDao extends GenericDao {
	
	public IntakeDao() {
        super();
    }
	
    public Intake find(Long id) throws HibernateException {
    	return (Intake) super.findById(Intake.class, id);
    }
    public Long save(Intake intake) throws HibernateException {
    	return (Long) super.save(intake);
    }
    public void update(Intake intake) throws HibernateException {
    	super.update(intake);
    }
    public void delete(Intake intake) throws HibernateException {
    	super.delete(intake);
    }
    public List list() throws HibernateException {
    	return super.findAll(Intake.class);
    }
    public List search(String entryDate, String exitDate, String lastname,
			String firstname, String ssn, String dob, String farm, String ged, String archived, String status) throws HibernateException {
    	return super.search( entryDate,  exitDate,  lastname,firstname,  ssn,  dob,  farm,  ged,  archived,  status);
    }
    public List searchApplications(String entryDate, String exitDate,
			String lastname, String firstname, String ssn, String dob,String status, String farm) throws HibernateException {
    	return super.searchApplications( entryDate,  exitDate,  lastname,firstname,  ssn,  dob,  status, farm);
    }
    public List listClass(String classNumber, String farm) throws HibernateException {
    	return super.listClass(classNumber,farm);
    }
    
    
}