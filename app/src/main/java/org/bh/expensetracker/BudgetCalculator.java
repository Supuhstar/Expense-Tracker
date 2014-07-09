package org.bh.expensetracker;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.bh.expensetracker.util.DebtCalculator;
import org.bh.expensetracker.util.Expense;
import org.bh.expensetracker.util.SalaryType;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.bh.expensetracker.Convenience.extractDouble;

/**
 * Made for ExpenseTracker by and copyrighted to Blue Husky Programming, ©2014 GPLv3.<hr/>
 *
 * @author Kyli Rouge of Blue Husky Programming
 * @since 2014-07-07
 * @version 1.0.0
 */
 public class BudgetCalculator extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        // constants used when saving/restoring state
        private static final String SALARY            = "SAL";
        private static final String SALARY_TYPE_INDEX = "SAL_TYPE_IDX";

        protected double salary;       // Bill as entered by the user
        protected int salaryTypeIndex; // Custom tip % as set by user

        private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("\u00A4#,###,###,##0.00");

        protected SalaryType currentSalary;
        protected ArrayList<ExpenseIO> expenseIOs = new ArrayList<ExpenseIO>();

        private EditText salaryEditText;
        private Spinner salarySpinner;
        private EditText debtEditText;
        private SeekBar debtPayoffSeekBar;
        private TextView debtPayoffPercentTextView;

        private SeekBar billsSeekBar;
        private EditText billsTotal;
        private SeekBar foodSeekBar;
        private EditText foodTotal;
        private SeekBar housingSeekBar;
        private EditText housingTotal;
        private SeekBar transitSeekBar;
        private EditText transitTotal;
        private SeekBar otherSeekBar;
        private EditText otherTotal;

        private TextView savingsPerSalaryLabel;
        private EditText savingsPerSalary;
        private EditText payoffTime;
        private EditText finalSavings;

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (savedInstanceState == null)
            {
                salary = 0;
                salaryTypeIndex = 1; // 1 = Monthly
            }
            else
            {
                salary = savedInstanceState.getDouble(SALARY);
                salaryTypeIndex = savedInstanceState.getInt(SALARY_TYPE_INDEX);
            }
            View rootView = inflater.inflate(R.layout.fragment_budget, container, false);

            initSalary(rootView);
            initDebt(rootView);
            initDebtPayoff(rootView);
            initExpenses(rootView);
            initResults(rootView);


            return rootView;
        }

        private void initSalary(View rootView) {
            salarySpinner = (Spinner)rootView.findViewById(R.id.salarySpinner);
            salarySpinner.setSelection(salaryTypeIndex);

            salaryEditText = (EditText) rootView.findViewById(R.id.salaryEditText);
            salaryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    for(ExpenseIO eio : expenseIOs)
                        eio.setMax((int)Math.round(extractDouble(salaryEditText.getText())));
                    updateResults();
                    return false; // false means I don't want to take control from the OS
                }
            });

            salarySpinner = (Spinner) rootView.findViewById(R.id.salarySpinner);

            ArrayAdapter<SalaryType> salarySpinnerArrayAdapter =
                    new ArrayAdapter<SalaryType>(
                            getActivity(),
                            android.R.layout.simple_spinner_item,
                            SalaryType.values()
                    ); //selected item will look like a spinner set from XML
            salarySpinnerArrayAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            salarySpinner.setAdapter(salarySpinnerArrayAdapter);

            salarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    currentSalary = SalaryType.values()[position];
                    updateResults();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    currentSalary = SalaryType.MONTHLY;
                }
            });
            salarySpinner.setSelection(salaryTypeIndex);
        }

        private void initDebt(View rootView) {
            debtEditText = (EditText) rootView.findViewById(R.id.debtEditText);
            debtEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    updateResults();
                    return false; // false means I don't want to take control from the OS
                }
            });
        }

        private void initDebtPayoff(View rootView) {
            debtPayoffPercentTextView = (TextView) rootView.findViewById(
                    R.id.salaryDebtPayoffPercentTextView);

            debtPayoffSeekBar = (SeekBar) rootView.findViewById(R.id.salaryDebtPayoffSeekBar);
            Convenience.bindSeekBarInOut(
                    debtPayoffSeekBar,
                    debtPayoffPercentTextView,
                    new DecimalFormat("##@'%'"),
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            updateResults();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            updateResults();
                        }
                    }
            );

        }

        private void initExpenses(View rootView) {
            initExpenseIO(
                billsSeekBar = (SeekBar) rootView.findViewById(R.id.billsExpensesSeekBar),
                billsTotal  = (EditText) rootView.findViewById(R.id.billsExpensesTotal),
                Expense.BILLS);
            initExpenseIO(
                foodSeekBar = (SeekBar) rootView.findViewById(R.id.foodExpensesSeekBar),
                foodTotal  = (EditText) rootView.findViewById(R.id.foodExpensesTotal),
                Expense.FOOD);
            initExpenseIO(
                housingSeekBar = (SeekBar) rootView.findViewById(R.id.housingExpensesSeekBar),
                housingTotal  = (EditText) rootView.findViewById(R.id.housingExpensesTotal),
                Expense.HOUSING);
            initExpenseIO(
                transitSeekBar = (SeekBar) rootView.findViewById(R.id.transitExpensesSeekBar),
                transitTotal  = (EditText) rootView.findViewById(R.id.transitExpensesTotal),
                Expense.TRANSIT);
            initExpenseIO(
                otherSeekBar = (SeekBar) rootView.findViewById(R.id.otherExpensesSeekBar),
                otherTotal  = (EditText) rootView.findViewById(R.id.otherExpensesTotal),
                Expense.OTHER);
        }
            private void initExpenseIO(SeekBar in, EditText out, Expense type) {
                expenseIOs.add(new ExpenseIO(in, out, type));
            }

        private void initResults(View rootView) {
            savingsPerSalaryLabel = (TextView) rootView.findViewById(R.id.savingsPerCycleLabel);
            savingsPerSalary = (EditText) rootView.findViewById(R.id.savingsPerCycle);
            payoffTime = (EditText) rootView.findViewById(R.id.timeToPayExpenses);
            finalSavings = (EditText) rootView.findViewById(R.id.savingsOverall);
        }



        public void updateResults()
        {
            double expense = 0;
            for(ExpenseIO eio : expenseIOs)
                expense += eio.getExpenseAmount();

            DebtCalculator debtCalculator = new DebtCalculator(
                    currentSalary,
                    extractDouble(debtEditText.getText()),
                    extractDouble(salaryEditText.getText()),
                    expense,
                    debtPayoffSeekBar.getProgress() * .01
            );

            debtCalculator.budgetCalculation(1);
            String salaryNoun = currentSalary.toNoun();
            savingsPerSalaryLabel.setText("Savings per " + salaryNoun);
            savingsPerSalary.setText(MONEY_FORMAT.format(debtCalculator.money_saved));
            payoffTime.setText(
                Double.isInfinite(debtCalculator.time_to_pay_off_debt)
                || Double.isNaN(debtCalculator.time_to_pay_off_debt)
                    ? "Never"
                    :
                        new DecimalFormat("##.##")
                            .format(debtCalculator.time_to_pay_off_debt) + " " + salaryNoun + "s"
            );
            finalSavings.setText(MONEY_FORMAT.format(debtCalculator.amount_saved_once_debt_payoff));
        }

        @Override
        public void onSaveInstanceState(Bundle outState)
        {
            super.onSaveInstanceState(outState);

            outState.putDouble(SALARY, salary);
            outState.putInt(SALARY_TYPE_INDEX, salaryTypeIndex);
        }

        private class ExpenseIO
        {
            private Expense representative;
            private SeekBar in;
            private EditText out;

            public ExpenseIO(SeekBar initIn, EditText initOut, final Expense initRepresentative)
            {
                in = initIn;
                out = initOut;
                representative = initRepresentative;
                Convenience.bindSeekBarInOut(
                        initIn,
                        initOut,
                        MONEY_FORMAT,
                        new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                setExpenseAmount(progress);
                                updateResults();
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                updateResults();
                            }
                        });
            }

            public double getExpenseAmount() {
                return representative.getExpenseAmount();
            }

            public void setExpenseAmount(double newExpenseAmount) {
                representative.setExpenseAmount(newExpenseAmount);
            }

            public void setMax(int newMax) {
                in.setMax(newMax);
            }
        }
    }
}
