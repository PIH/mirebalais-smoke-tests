package org.openmrs.module.mirebalais.smoke.pageobjects.selects;

public enum TypeOfPrescrition {

    DISCHARGE(1), INPATIENT_HOSPITALIZATION(2);

    private int index;

    TypeOfPrescrition(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
