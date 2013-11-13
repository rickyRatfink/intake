package com.yada180.sms.domain;

import java.io.Serializable;

public class CwtMaster implements Serializable {
  
	private CwtProgram program = new CwtProgram();
	private CwtMetrics metric = new CwtMetrics();
	private CwtModules module = new CwtModules();
	private CwtModuleSection section = new CwtModuleSection();
	private CwtSupervisor supervisor = new CwtSupervisor();
	private CwtRoster roster = new CwtRoster();
	private CwtDepartment cwtDepartment = new CwtDepartment();
	private CwtJob cwtJob = new CwtJob();
	private CwtSupervisor cwtSupervisor = new CwtSupervisor();
	private Intake intake = new Intake();
	
	
	
	public CwtRoster getRoster() {
		return roster;
	}
	public void setRoster(CwtRoster roster) {
		this.roster = roster;
	}
	public CwtDepartment getCwtDepartment() {
		return cwtDepartment;
	}
	public void setCwtDepartment(CwtDepartment cwtDepartment) {
		this.cwtDepartment = cwtDepartment;
	}
	public CwtJob getCwtJob() {
		return cwtJob;
	}
	public void setCwtJob(CwtJob cwtJob) {
		this.cwtJob = cwtJob;
	}
	public CwtSupervisor getCwtSupervisor() {
		return cwtSupervisor;
	}
	public void setCwtSupervisor(CwtSupervisor cwtSupervisor) {
		this.cwtSupervisor = cwtSupervisor;
	}
	public Intake getIntake() {
		return intake;
	}
	public void setIntake(Intake intake) {
		this.intake = intake;
	}
	public CwtProgram getProgram() {
		return program;
	}
	public void setProgram(CwtProgram program) {
		this.program = program;
	}
	public CwtMetrics getMetric() {
		return metric;
	}
	public void setMetric(CwtMetrics metric) {
		this.metric = metric;
	}
	public CwtModules getModule() {
		return module;
	}
	public void setModule(CwtModules module) {
		this.module = module;
	}
	public CwtModuleSection getSection() {
		return section;
	}
	public void setSection(CwtModuleSection section) {
		this.section = section;
	}
	public CwtSupervisor getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(CwtSupervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	
	
	
}
