package org.bh.expensetracker;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.bh.expensetracker.util.SalaryType;

import java.text.DecimalFormat;

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

        private EditText salaryEditText;
        private Spinner salarySpinner;
        private SeekBar debtPayoffSeekBar;
        private TextView debtPayoffPercentTextView;

        private SalaryType currentSalary;

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
            salaryEditText = (EditText) rootView.findViewById(R.id.salaryEditText);
            {
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        currentSalary = SalaryType.MONTHLY;
                    }
                });
                salarySpinner.setSelection(salaryTypeIndex);


                /*final SpinnerAdapter originalAdapter = salarySpinner.getAdapter();
                salarySpinner.setAdapter(new SpinnerAdapter() {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null)
                            convertView = new TextView(getActivity());
                        SalaryType[] salaryTypes = SalaryType.values();
                        if (position > salaryTypes.length)
                            return null;
                        if (convertView instanceof TextView)
                            ((TextView)convertView).setText(salaryTypes[position].toString());
                        return convertView;
                    }

                    @Override
                    public void registerDataSetObserver(DataSetObserver observer)
                    {if(originalAdapter!=null)originalAdapter.registerDataSetObserver(observer);}

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver observer)
                    {if(originalAdapter!=null)originalAdapter.unregisterDataSetObserver(observer);}

                    @Override
                    public int getCount() {
                        return SalaryType.values().length;
                    }

                    @Override
                    public Object getItem(int position) {
                        return SalaryType.values()[position];
                    }

                    @Override
                    public long getItemId(int position) {
                        return SalaryType.values()[position].ordinal();
                    }

                    @Override
                    public boolean hasStableIds() {
                        return true;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent)
                    {return originalAdapter!=null?originalAdapter.getView(position, convertView,
                        parent):null;}

                    @Override
                    public int getItemViewType(int position)
                    {return originalAdapter!=null?originalAdapter.getItemViewType(position):
                        IGNORE_ITEM_VIEW_TYPE;}

                    @Override
                    public int getViewTypeCount()
                    {return originalAdapter!=null?originalAdapter.getViewTypeCount():1;}

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }
                });*/
            }
            debtPayoffPercentTextView = (TextView) rootView.findViewById(
                    R.id.salaryDebtPayoffPercentTextView);
            {
                debtPayoffSeekBar = (SeekBar) rootView.findViewById(R.id.salaryDebtPayoffSeekBar);
                Convenience.bindSeekBarInOut(
                        debtPayoffSeekBar,
                        debtPayoffPercentTextView,
                        new DecimalFormat("##@'%'"),
                        new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        }
                );
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
