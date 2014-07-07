/* Ben Leggiero's mod of the Deitel Tip Calculator into an Expense Tracker */
package org.bh.tipcalculator2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TipCalculator extends Activity {
    // constants used when saving/restoring state
    private static final String BILL_TOTAL     = "BILL_TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        /*if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private double currentBillTotal;      // Bill as entered by the user
        private int currentCustomPercent;     // Custom tip % as set by user
        private EditText billEditText;        // Bill input widget
        private EditText tip10EditText;       // 10% tip output
        private EditText tip15EditText;       // 15% tip output
        private EditText tip20EditText;       // 20% tip output
        private EditText tipCustomEditText;   // ##% tip output
        private EditText total10EditText;     // 10% total output
        private EditText total15EditText;     // 15% total output
        private EditText total20EditText;     // 20% total output
        private EditText totalCustomEditText; // ##% total output
        private TextView customTipTextView;   // Custom tip % output

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            if (savedInstanceState == null)
            {
                currentBillTotal = 0;
                currentCustomPercent = 18;
            }
            else
            {
                currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
                currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
            }
            View rootView = inflater.inflate(R.layout.fragment_tip_calculator, container, false);

            tip10EditText =       (EditText) rootView.findViewById(R.id.tip10EditText);
            tip15EditText =       (EditText) rootView.findViewById(R.id.tip15EditText);
            tip20EditText =       (EditText) rootView.findViewById(R.id.tip20EditText);
            tipCustomEditText =   (EditText) rootView.findViewById(R.id.tipCustomEditText);
            total10EditText =     (EditText) rootView.findViewById(R.id.total10EditText);
            total15EditText =     (EditText) rootView.findViewById(R.id.total15EditText);
            total20EditText =     (EditText) rootView.findViewById(R.id.total20EditText);
            totalCustomEditText = (EditText) rootView.findViewById(R.id.totalCustomEditText);
            customTipTextView =   (TextView) rootView.findViewById(R.id.customTipTextView);

            billEditText = (EditText) rootView.findViewById(R.id.billEditText);
            //System.out.print(billEditText);
            billEditText.addTextChangedListener(billEditTextWatcher);

            SeekBar customSeekBar = (SeekBar) rootView.findViewById(R.id.customSeekBar);
            customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
            return rootView;
        }

        /**
         * Calls {@link #updateStandard()} and then {@link #updateCustom()}.
         *
         * @see #updateStandard()
         * @see #updateCustom()
         */
        public void updateAll()
        {
            updateStandard();
            updateCustom();
        }

        public void updateStandard()
        {
            calculateTipAndTotal(tip10EditText, total10EditText, .1);
            calculateTipAndTotal(tip15EditText, total15EditText, .15);
            calculateTipAndTotal(tip20EditText, total20EditText, .2);
        }

        public void updateCustom()
        {
            customTipTextView.setText(currentCustomPercent + "%");
            calculateTipAndTotal(tipCustomEditText, totalCustomEditText, currentCustomPercent * .01);
        }

        public void calculateTipAndTotal(EditText tipEditText, EditText totalEditText, double tip)
        {
            tipEditText  .setText(getString(R.string.currencySymbol) + String.format("%.02f", currentBillTotal * tip));
            totalEditText.setText(getString(R.string.currencySymbol) + String.format("%.02f", currentBillTotal * (tip + 1)));
        }

        @Override
        public void onSaveInstanceState(Bundle outState)
        {
            super.onSaveInstanceState(outState);

            outState.putDouble(BILL_TOTAL, currentBillTotal);
            outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
        }

        private SeekBar.OnSeekBarChangeListener customSeekBarListener =
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        currentCustomPercent = seekBar.getProgress();
                        updateCustom();
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar){}
                    @Override public void onStopTrackingTouch(SeekBar seekBar){}
                }
            ;

        private TextWatcher billEditTextWatcher =
                new TextWatcher()
                {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try
                        {
                            currentBillTotal = extractDouble(s);
                        }
                        catch (NumberFormatException ex) // if we still didn't get a number
                        {
                            currentBillTotal = 0;
                        }

                        updateAll();
                    }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    @Override public void afterTextChanged(Editable editable){}
                }
            ;

        /**
         * Attempts to extract the first double found. This ideally works with percents, currency
         * notation, and other formats where the double is surrounded by delimiters and text, but is
         * not interrupted by it.<br/>
         *<br/>
         * A double may look like any one of these, where {@code #} represents any number of digits 0
         * through 9:
         *
         * <ul>
         *     <li>{@code #}</li>
         *     <li>{@code .#}</li>
         *     <li>{@code #.#}</li>
         * </ul>
         *
         * @param possibleDouble a character sequence that might contain a double
         *
         * @return the extracted double
         *
         * @throws java.lang.NumberFormatException if a double could not be found
         */
        public static double extractDouble(CharSequence possibleDouble)
        {
            /**
             * Matches at least one digit, optionally preceded by a radix point, optionally preceded by
             * any number of digits
             */
            Matcher matcher = Pattern.compile("([0-9]*\\.)?[0-9]+").matcher(possibleDouble);
            if (matcher.find())
                return Double.parseDouble(matcher.group());
            else
                throw new NumberFormatException(
                    "Double could not be extracted from \"" + possibleDouble + "\"");
        }
    }
}
