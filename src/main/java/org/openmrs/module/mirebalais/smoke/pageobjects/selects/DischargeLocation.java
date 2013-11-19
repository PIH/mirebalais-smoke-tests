package org.openmrs.module.mirebalais.smoke.pageobjects.selects;

public enum DischargeLocation {
    MIREBALAIS_HOSPITAL(16);

    private int index;

    DischargeLocation(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
