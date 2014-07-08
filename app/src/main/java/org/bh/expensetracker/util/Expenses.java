package org.bh.expensetracker.util;

/**
 * Represents the 5 classes of expense
 *
 * @author Ben Leggiero of Blue Husky Programming and Ian Jensen
 * @version 1.0.0
 * @since 2014-07-08
 */
public enum Expenses {
    BILLS(),
    FOOD(),
    RENT(),
    TRANSPORTATION(),
    OTHER();

    private float expenseAmount;

    private Expenses() {
        expenseAmount = 0;
    }

    public float getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(float newExpenseAmount) {
        expenseAmount = newExpenseAmount;
    }

    public float calculateTotal() {
        float total = 0;
        for (Expenses expense : values())
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