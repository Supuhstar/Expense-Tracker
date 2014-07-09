package org.bh.expensetracker.util;

/**
 * Calculates the debt and related information
 *
 * @author Kyli Rouge of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-07-08
 */
public class DebtCalculator {
    // in
    public double total_debt = 4000;
    public double monthly_salary = 2000;
    public double monthly_percent_debt_payoff  = 0.2; // This is defined by the slider in tipCalculator
    public double monthly_expenditures = 1000; // these are defined by my other class expenses. bills,rent, etc..

    //out
    public double money_saved;
    public double monthly_amount_towards_debt_payoff;  // = monthly_salary * monthly_percent_debt_payoff;
    public double number_of_months_to_pay_off_debt;//(int) (total_debt / monthly_amount_towards_debt_payoff +1);



    public void budgetCalculation() //Pay bills before paying debt.
    {
        double budget_AfterBills = monthly_salary - monthly_expenditures;
        monthly_amount_towards_debt_payoff = budget_AfterBills * monthly_percent_debt_payoff;
        number_of_months_to_pay_off_debt = total_debt / monthly_amount_towards_debt_payoff;

        money_saved = budget_AfterBills - monthly_amount_towards_debt_payoff; // This is the money saved per month
        if(total_debt <= 0)
        {
            //Debt is payed off do something.or dont need
        }
    }
}
