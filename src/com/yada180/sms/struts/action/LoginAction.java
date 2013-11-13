package com.yada180.sms.struts.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.yada180.sms.application.Constants;
import com.yada180.sms.domain.ErrorMessage;
import com.yada180.sms.domain.SystemUser;
import com.yada180.sms.domain.UserAuthorizedSession;
import com.yada180.sms.hibernate.dao.SystemUserDao;
import com.yada180.sms.hibernate.dao.UserAuthorizedSessionDao;
import com.yada180.sms.struts.form.LoginForm;
import com.yada180.sms.util.HtmlDropDownBuilder;
import com.yada180.sms.util.Validator;


public class LoginAction extends Action {
	
	private final static Logger LOGGER = Logger.getLogger(LoginAction.class.getName());
	private final static HtmlDropDownBuilder html = new HtmlDropDownBuilder();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		LOGGER.setLevel(Level.INFO);

		 HttpSession session = request.getSession(true);
		 
		 try {
			 
		 String action = request.getParameter("action");
		 LoginForm loginForm = (LoginForm)form;
		 
		 if ("logout".equals(action)) {
			 loginForm.setSystemUser(null);
			 session.invalidate();
			 return mapping.findForward(Constants.LOGOUT);
		 }
		 //SystemUserHome userDao = new SystemUserHome();
		 SystemUserDao userDao = new SystemUserDao();
		 UserAuthorizedSessionDao sessionDao = new UserAuthorizedSessionDao();
		 UserAuthorizedSession sessionObj = new UserAuthorizedSession();
		 
		 ActionRedirect redirect = null;
		 
		 LOGGER.log(Level.INFO, "In login action..."+loginForm.getSystemUser().getUsername());
		 SystemUser user=null;
		 if ("autologin".equals(action)) {
			 String sessionKey = request.getParameter("sessionKey");
			 UserAuthorizedSession userSession = sessionDao.findById(sessionKey);
			 if (userSession==null)
				 return mapping.findForward(Constants.GLOBAL_LOGIN);
			 
			 String sessionTime = userSession.getLoginTime();
			 //check if sessionTime is less than X seconds, if so, change to inactive redirect back to login
			 
			 //login and store user in session
			 user = userDao.findById(userSession.getUserId());
			 if ("Intake".equals(user.getGroup())) {
				 loginForm.setSystemUser(user);
				 userSession.setActive("No");
				 sessionDao.updateUserAuthorizedSession(userSession);
				 session.setAttribute("system_user", user);
				 return mapping.findForward(Constants.SUCCESS);
			 } else  {
				 userSession.setActive("No");
				 sessionDao.updateUserAuthorizedSession(userSession);
				 return mapping.findForward(Constants.GLOBAL_LOGIN);
			}
		 }
		 else if ("Login".equals(action)) {
			 boolean valid = this.validate(loginForm);
			 if (!valid)
				 return mapping.findForward(Constants.LOGIN);
			 
			 user = userDao.authenticate(loginForm.getSystemUser().getUsername(), loginForm.getSystemUser().getPassword());
			 if (user!=null) {
				 if ("Intake".equals(user.getGroup())) {
					 html.refresh(session);
					 loginForm.setSystemUser(user);
					 session.setAttribute("system_user", user);
					 return mapping.findForward(Constants.SUCCESS);
				 } else {
					 List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
					 ActionErrors errors = new ActionErrors();
					 messages.add(new ErrorMessage("Access denied. This user is not authorized to view this application.",""));
					 loginForm.setMessages(messages); 	 
					return mapping.findForward(Constants.LOGIN);
				 }
			 } else
			 {
				 loginForm.getSystemUser().setUsername(null);
				 loginForm.getSystemUser().setPassword(null);
				 return mapping.findForward(Constants.FAILURE);
			 }
			 
		 }
		 else if ("ManageUsers".equals(action)) {
			 loginForm.setUserList(userDao.listSystemUsers());
		 	 return mapping.findForward(Constants.MANAGE_USERS);
		 } else if ("Create".equals(action)) 
			 	return mapping.findForward(Constants.CREATE_USER);
		 else if ("Edit".equals(action)) { 
			 	String key = request.getParameter("id");
			 	loginForm.setUser(userDao.findById(new Long(key+"")));
			 	return mapping.findForward(Constants.CREATE_USER);
		 }
		 else if ("Delete".equals(action)) {
			 	userDao.deleteSystemUser(loginForm.getUser().getUserId());
			 	loginForm.setUserList(userDao.listSystemUsers());
			 	return mapping.findForward(Constants.MANAGE_USERS);
		 }
		 else if ("Save".equals(action)) { 
			 SystemUser loggedInUser = (SystemUser)session.getAttribute("system_user");
			 if (loginForm.getUser().getUserId()==null) {
				loginForm.getUser().setCreatedBy(loggedInUser.getUsername());
				loginForm.getUser().setCreationDate(Validator.getEpoch()+"");
			 	userDao.addSystemUser(loginForm.getUser());
			 }
			 else {				
				loginForm.getUser().setLastUpdatedBy(loggedInUser.getUsername());
				loginForm.getUser().setLastUpdatedDate(Validator.getEpoch()+"");
				userDao.updateSystemUser(loginForm.getUser());
			 }
			 	loginForm.setUserList(userDao.listSystemUsers());
			 	return mapping.findForward(Constants.MANAGE_USERS);
		 }
		 return mapping.findForward(Constants.LOGIN);
		}
			catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);			
				session.setAttribute("SYSTEM_ERROR", sw.toString());
				e.printStackTrace();
				return mapping.findForward(Constants.ERROR);
			}
		 //return mapping.findForward(Constants.GLOBAL_LOGIN);
	}
	
	public boolean validate(LoginForm loginForm) {
		  List<ErrorMessage> messages = new ArrayList<ErrorMessage>();
		  ActionErrors errors = new ActionErrors();
		  
		  if ((loginForm.getSystemUser().getUsername()==null) || (loginForm.getSystemUser().getUsername().length() < 1)) 
		     	messages.add(new ErrorMessage("username is required",""));
		  if ((loginForm.getSystemUser().getPassword()==null) || (loginForm.getSystemUser().getPassword().length() < 1)) 
			    messages.add(new ErrorMessage("password is required",""));
		  
		  if (messages.size()>0) {
			  loginForm.setMessages(messages); 	 
			  return false;
		  }
		  else
			  return true;
		}
	
}
