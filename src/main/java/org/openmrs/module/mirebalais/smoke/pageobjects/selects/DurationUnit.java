package org.openmrs.module.mirebalais.smoke.pageobjects.selects;

public enum DurationUnit {
	DAYS(1), WEEKS(2), MONTHS(3), HOURS(4);
	
	private int index;
	
	DurationUnit(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
