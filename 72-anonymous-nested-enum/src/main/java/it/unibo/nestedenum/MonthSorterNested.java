package it.unibo.nestedenum;

import java.util.Comparator;
//import java.util.Locale;
//import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    private static final int SHORT_MONTH = 28;
    private static final int REGULAR_MONTH = 30;
    private static final int LONG_MONTH = 31;

    public static enum Month {

        JANUARY("January",LONG_MONTH),
        FEBRUARY("February",SHORT_MONTH),
        MARCH("March",LONG_MONTH),
        APRIL("April",REGULAR_MONTH),
        MAY("May",LONG_MONTH),
        JUNE("June",REGULAR_MONTH),
        JULY("July",LONG_MONTH),
        AUGUST("August",LONG_MONTH),
        SEPTEMER("September",REGULAR_MONTH),
        OCTOBER("October",LONG_MONTH),
        NOVEMBER("November",REGULAR_MONTH),
        DECEMBER("December",LONG_MONTH);

        private final String actualName;
        private final int days;

        private Month(final String actualName, final int days){
            this.actualName = actualName;
            this.days = days;
        }

        public String getActualName() {
            return this.actualName;
        }

        public int getDays() {
            return this.days;
        }

        public static Month fromString(final String month) {
            int equalMonth = 0;
            Month result = null;
            for(final Month name: Month.values()) {
                if (name.getActualName().toLowerCase().startsWith(month.toLowerCase())) {
                    result = name;
                    equalMonth++;
                }
            }
            if (equalMonth == 1) {
                return result;
            }
            throw new IllegalArgumentException("Such possible month found: "+equalMonth);
        }



    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    class SortByMonthOrder implements Comparator<String> {

        public int compare(String s1, String s2) throws IllegalArgumentException {
            return Integer.compare(Month.fromString(s1).ordinal(), Month.fromString(s2).ordinal());
        }
    }

    class SortByDate implements Comparator<String> {

        public int compare(String s1, String s2) throws IllegalArgumentException {
            return Integer.compare(Month.fromString(s1).getDays(), Month.fromString(s2).getDays());
        }
    }
}
