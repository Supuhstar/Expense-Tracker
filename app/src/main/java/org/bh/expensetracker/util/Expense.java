package org.bh.expensetracker.util;

/**
 * Represents the 5 classes of expense
 *
 * @author Ben Leggiero of Blue Husky Programming and Ian Jensen
 * @version 1.1.0
 * @since 2014-07-08
 */
public enum Expense {
    BILLS(),
    FOOD(),
    HOUSING(),
    TRANSIT(),
    OTHER();

    private double expenseAmount;

    private Expense() {
        expenseAmount = 0;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double newExpenseAmount) {
        expenseAmount = newExpenseAmount;
    }

    public static double calculateTotal() {
        float total = 0;
        for (Expense expense : values())
            total += expense.getExpenseAmount();
        return total;
    }

    /**
     * Returns the same thing as {@link #name()}, but with only the first letter capitalized.
     *
     * @return the same thing as {@link #name()}, but with only the first letter capitalized.
     * @see #name()
     */
    @Override
    public String toString() {
        String name = name().toUpperCase();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}