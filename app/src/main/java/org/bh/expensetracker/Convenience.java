package org.bh.expensetracker;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Made for ExpenseTracker by and copyrighted to Blue Husky Programming, ©2014 GPLv3.<hr/>
 *
 * @author Kyli Rouge of Blue Husky Programming
 * @since 2014-07-07
 * @version 1.0.0
 */
 public class Convenience {

    /**
     * Binds the given input and output, so that whenever the input changes, its value is displayed
     * in the output with a percent sign next to it.
     *
     * @param input the input to draw from
     * @param output the output to write to
     * @param nestedListener a nested listener, in case you want more functionality
     */
    public static void bindSeekBarInOut(
            final SeekBar input,
            final TextView output,
            final DecimalFormat outputFormat,
            final SeekBar.OnSeekBarChangeListener nestedListener)
    {
        input.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        output.setText(outputFormat.format(seekBar.getProgress()));
                        if (nestedListener != null)
                            nestedListener.onProgressChanged(seekBar, progress, fromUser);
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar) {
                        if (nestedListener != null)
                            nestedListener.onStartTrackingTouch(seekBar);
                    }
                    @Override public void onStopTrackingTouch(SeekBar seekBar) {
                        if (nestedListener != null)
                            nestedListener.onStopTrackingTouch(seekBar);
                    }
                }
            )
        ;
        if (output instanceof EditText)
        {
            ((EditText) output).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    input.setProgress((int)Math.round(extractDouble(v.getText()) * input.getMax()));
                    return false;
                }
            });
        }
    }

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
     * @version 1.1.0
     *      - 1.1.0 (2014-07-08) - Ben Leggiero replaced throwing an exception with returning 0
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
            return 0;
    }
}
