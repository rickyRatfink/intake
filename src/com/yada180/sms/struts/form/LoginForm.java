package com.yada180.sms.struts.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.yada180.sms.domain.ErrorMessage;
import com.yada180.sms.domain.SystemUser;

public class LoginForm  extends ActionForm {

	private SystemUser user = new SystemUser();
	private SystemUser systemUser = new SystemUser();
	private List<SystemUser> userList = new ArrayList<SystemUser>();
	private List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
    private String messageType;
	
	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	
	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public List<ErrorMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ErrorMessage> messages) {
		this.messages = messages;
	}

	public List<SystemUser> getUserList() {
		return userList;
	}

	public void setUserList(List<SystemUser> userList) {
		this.userList = userList;
	}

	/*
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		  ActionErrors errors = new ActionErrors();
		  if ((this.systemUser.getUsername()==null) || (this.systemUser.getUsername().length() < 1)) 
		      errors.add("",new ActionMessage("errors.required","username"));
		  if ((this.systemUser.getPassword()==null) || (this.systemUser.getPassword().length() < 1)) 
		      errors.add("", new ActionMessage("errors.required","password"));
		  
		  	 
		  return errors;
		}
		*/
}
