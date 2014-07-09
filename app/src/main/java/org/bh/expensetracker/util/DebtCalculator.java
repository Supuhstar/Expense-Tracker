package org.bh.expensetracker.util;

public  class DebtCalculator {
    SalaryType type;

    public double total_debt = 4000;
    public double salary = 2000;
    public double percent_debt_payoff  = 0.2; // This is defined by the slider in tipCalculator
    public double expenditures = 1000; // these are defined by my other class expenses. bills,rent, etc..
    public double money_saved;
    public double amount_towards_debt_payoff;  // = monthly_salary * monthly_percent_debt_payoff;
    public double time_to_pay_off_debt;//(int) (total_debt / monthly_amount_towards_debt_payoff +1);

    public DebtCalculator(SalaryType timeType,double debtToPay,double salaryEarned,double expense)//edit constructor add total_debt and other user data above.
    {
        type = timeType;
        expenditures = expense;
        total_debt = debtToPay;
        salary = salaryEarned;

    }

    public void setType(SalaryType aType)
    {
        type = aType;
    }


    /**
     *
     * @param multiplier one of the following:
     *         {@link org.bh.expensetracker.util.SalaryType#IN_HOURS},
     *         {@link org.bh.expensetracker.util.SalaryType#IN_MONTHS},
     *         {@link org.bh.expensetracker.util.SalaryType#IN_QUARTERS},
     *         {@link org.bh.expensetracker.util.SalaryType#IN_YEARS}
     */
    public void budgetCalculation(double multiplier) //Pay bills before paying debt.
    {
        double budget_AfterBills = (salary - expenditures)/ multiplier;
        amount_towards_debt_payoff = budget_AfterBills * percent_debt_payoff;
        time_to_pay_off_debt = (int) (total_debt/amount_towards_debt_payoff);

        money_saved = budget_AfterBills - amount_towards_debt_payoff;
    }
}