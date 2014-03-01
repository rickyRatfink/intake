package com.yada180.sms.hibernate.data;

import java.util.List;

import org.hibernate.HibernateException;

import com.yada180.sms.domain.CwtRoster;
import com.yada180.sms.domain.CwtRosterNotes;

public class CwtRosterNotesDao extends GenericDao {

	public CwtRosterNotesDao() {
		super();
	}

	public CwtRosterNotes find(Long id) throws HibernateException {
		return (CwtRosterNotes) super.findById(CwtRosterNotes.class, id);
	}
	
	public CwtRosterNotes findBySectionIdRosterDate(Long sectionId,String rosterDate) throws HibernateException {
		return (CwtRosterNotes) super.findBySectionIdRosterDate(CwtRosterNotes.class, sectionId, rosterDate);
	}

	public Long save(CwtRosterNotes notes) throws HibernateException {
		return (Long) super.save(notes);
	}

	public void update(CwtRosterNotes notes) throws HibernateException {
		super.update(notes);
	}

	public void delete(CwtRosterNotes notes) throws HibernateException {
		super.delete(notes);
	}

	public List list() throws HibernateException {
		return super.findAll(CwtRosterNotes.class);
	}
}
