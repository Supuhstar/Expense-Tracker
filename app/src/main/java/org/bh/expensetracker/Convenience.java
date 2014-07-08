package org.bh.expensetracker;

import android.widget.SeekBar;
import android.widget.TextView;

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
    public static void bindPercentageInOut(SeekBar input, final TextView output)
    {
        input.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        output.setText(seekBar.getProgress() + "%");
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar){}
                    @Override public void onStopTrackingTouch(SeekBar seekBar){}
                }
            )
        ;
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
