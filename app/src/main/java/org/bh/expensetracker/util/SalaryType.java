package org.bh.expensetracker.util;

/**
 * Represents the 4 types of salaries.
 *
 * @author Kyli Rouge of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-07-08
 */
public enum SalaryType
{
    //      Y D          H
    HOURLY((1/365.24219)/24), // because it's repeating and long
    //      Y  M
    MONTHLY(1d/12d), // because it's repeating
    QUARTERLY(.25),
    YEARLY(1d)
    ;

    public final double IN_HOURS;
    public final double IN_MONTHS;
    public final double IN_QUARTERS;
    public final double IN_YEARS;
    private SalaryType(double years)
    {
            /*switch (this)
            {
                // this works for all cases, but the rest are included for accuracy and speed
                default:*/
        IN_HOURS = years * 365.24219 * 24;
        IN_MONTHS = years * 12;
        IN_QUARTERS = years * 4;
        IN_YEARS = years;/*
                    break;

                case HOURLY:
                    IN_HOURS = 1;
                    IN_MONTHS = years * 12;
                    IN_QUARTERS = years * 4;
                    IN_YEARS = years;
                    break;
                case MONTHLY:
                    IN_HOURS = years * 365.24219 * 24;
                    IN_MONTHS = 1;
                    IN_QUARTERS = years * 4;
                    IN_YEARS = years;
                    break;
                case QUARTERLY:
                    IN_HOURS = years * 4 * 365.24219 * 24;
                    IN_MONTHS = 3;
                    IN_QUARTERS = 1;
                    IN_YEARS = .25;
                    break;
                case YEARLY:
                    IN_HOURS = 365.24219 * 24;
                    IN_MONTHS = 12;
                    IN_QUARTERS = 4;
                    IN_YEARS = 1;
                    break;
            }*/
    }

    /**
     * Returns the same thing as {@link #name()}, but with only the first letter capitalized.
     *
     * @return the same thing as {@link #name()}, but with only the first letter capitalized.
     *
     * @see #name()
     */
    @Override
    public String toString() {
        String name = name().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }

    public String toNoun() {
        String s = toString();
        return s.substring(0, s.length() - 2);
    }
}
