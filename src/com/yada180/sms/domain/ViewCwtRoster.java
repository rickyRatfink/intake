package com.yada180.sms.domain;

public class ViewCwtRoster implements java.io.Serializable {

	private Long sectionId;
	private Long moduleId;
	private String moduleName;
	private String rosterDate;
	private String farmBase;
	private String archivedFlag;
	
	
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getRosterDate() {
		return rosterDate;
	}
	public void setRosterDate(String rosterDate) {
		this.rosterDate = rosterDate;
	}
	public String getFarmBase() {
		return farmBase;
	}
	public void setFarmBase(String farmBase) {
		this.farmBase = farmBase;
	}
	public String getArchivedFlag() {
		return archivedFlag;
	}
	public void setArchivedFlag(String archivedFlag) {
		this.archivedFlag = archivedFlag;
	}
	
	
}
