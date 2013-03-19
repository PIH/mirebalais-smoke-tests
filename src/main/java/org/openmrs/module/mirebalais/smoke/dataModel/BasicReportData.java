package org.openmrs.module.mirebalais.smoke.dataModel;

public class BasicReportData {

	private Integer openVisits;
	private Integer registrationToday;

	public BasicReportData(Integer openVisits, Integer registrationToday) {
		this.openVisits = openVisits;
		this.registrationToday = registrationToday;
	}

	public Integer getOpenVisits() {
		return openVisits;
	}

	public Integer getRegistrationToday() {
		return registrationToday;
	}

}
