/* Ben Leggiero's mod of the Deitel Tip Calculator into an Expense Tracker */
package org.bh.expensetracker;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Main extends FragmentActivity implements ActionBar.TabListener {

    ViewPager viewPager;
    ActionBar actionBar;
    Fragment budgetFragment, tipFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast loadNotification = Toast.makeText(this, "Loading " + getString(R.string.app_name) + "...", Toast.LENGTH_SHORT);
        loadNotification.setGravity(Gravity.CENTER, 0, 0);
        loadNotification.show();

        setContentView(R.layout.activity_main);

        budgetFragment = new BudgetCalculator.PlaceholderFragment();
        tipFragment = new TipCalculator.PlaceholderFragment();

        viewPager = (ViewPager) findViewById(R.id.mainContainer);
        viewPager.setAdapter(
            new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public android.support.v4.app.Fragment getItem(int position) {
                    switch (position) {
                        default:
                        case 0:
                            return budgetFragment;
                        case 1:
                            return tipFragment;
                    }
                }

                @Override
                public int getCount() {
                    return 2;
                }
            }
        );

        actionBar = getActionBar();
        if (actionBar == null)
        {
            Toast.makeText(
                this,
                "Could not start Expense Tracker without Action Bar",
                Toast.LENGTH_LONG
            ).show();
            System.exit(58);
        }
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // When the tab is selected, switch to the
                // corresponding page in the ViewPager.
                viewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction ft){}

            public void onTabReselected(ActionBar.Tab tab,FragmentTransaction ft){}
        };
        actionBar.addTab(
                actionBar.newTab()
                        .setText(getString(R.string.budgetingTabText))
                        .setTabListener(tabListener));
        actionBar.addTab(
                actionBar.newTab()
                        .setText(getString(R.string.tippingTabText))
                        .setTabListener(tabListener)
        );

        viewPager.setOnPageChangeListener(
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    // When swiping between pages, select the
                    // corresponding tab.
                    getActionBar().setSelectedNavigationItem(position);
                }
            }
        );



        loadNotification.cancel();
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
        switch (id)
        {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Sorry, Expense Tracker has no settings", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
