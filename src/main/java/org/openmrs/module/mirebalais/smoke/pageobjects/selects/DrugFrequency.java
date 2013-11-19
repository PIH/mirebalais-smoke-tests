package org.openmrs.module.mirebalais.smoke.pageobjects.selects;

public enum DrugFrequency {
    ONCE_A_DAY(1),TWICE_A_DAY(2),THREE_TIMES_A_DAY(3), FOUR_TIMES_A_DAY(4), FIVE_TIMES_A_DAY(5),SIX_TIMES_A_DAY(6),
    SEVEN_TIMES_A_DAY(7), EIGHT_TIMES_A_DAY(8),NINE_TIMES_A_DAY(9),IN_THE_MORNING(10), AT_NIGHT(11),WHEN_REQUIRED(12),
    IMMEDIATELY(13), EVERY_TWO_HOURS(14), EVERY_THREE_HOURS(15),EVERY_FOUR_HOURS(16),EVERY_SIX_HOURS(17),
    EVERY_TWELVE_HOURS(18), OTHER(19);

    private int index;

    DrugFrequency(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
