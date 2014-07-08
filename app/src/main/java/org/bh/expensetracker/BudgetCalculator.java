package org.bh.expensetracker;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        private double salary;       // Bill as entered by the user
        private int salaryTypeIndex; // Custom tip % as set by user

        private Spinner salarySpinner;

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

            {
                salarySpinner = (Spinner)rootView.findViewById(R.id.salarySpinner);
                salarySpinner.setSelection(salaryTypeIndex);
            }


            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState)
        {
            super.onSaveInstanceState(outState);

            outState.putDouble(SALARY, salary);
            outState.putInt(SALARY_TYPE_INDEX, salaryTypeIndex);
        }
    }
}
