package org.openmrs.module.mirebalais.smoke.pageobjects.selects;

public enum DurationUnit {
	DAYS(1), HOURS(2), MONTHS(3), MINUTES(4), SECONDS(5), WEEKS(6), YEARS(7);
	
	private int index;
	
	DurationUnit(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
