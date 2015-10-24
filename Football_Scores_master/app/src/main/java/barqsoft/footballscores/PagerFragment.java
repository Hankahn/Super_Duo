package barqsoft.footballscores;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yehya khaled on 2/27/2015.
 */
public class PagerFragment extends Fragment {

    public static final int NUM_PAGES = 5;
    public ViewPager mPagerHandler;
    private myPageAdapter mPagerAdapter;
    private MainScreenFragment[] viewFragments = new MainScreenFragment[5];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        mPagerHandler = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerAdapter = new myPageAdapter(getChildFragmentManager());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration config = getResources().getConfiguration();
            int layoutDirection = config.getLayoutDirection();

            if (layoutDirection == ViewPager.LAYOUT_DIRECTION_RTL) {
                for (int i = 4, j = 0; j < NUM_PAGES; i--, j++) {
                    Date fragmentdate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
                    SimpleDateFormat mformat =
                            new SimpleDateFormat(getActivity().getString(R.string.dateFormat));
                    viewFragments[j] = new MainScreenFragment();
                    viewFragments[j].setFragmentDate(mformat.format(fragmentdate));
                    Log.d("BLAH", mformat.format(fragmentdate));
                }
            } else {
                for (int i = 0; i < NUM_PAGES; i++) {
                    Date fragmentdate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
                    SimpleDateFormat mformat =
                            new SimpleDateFormat(getActivity().getString(R.string.dateFormat));
                    viewFragments[i] = new MainScreenFragment();
                    viewFragments[i].setFragmentDate(mformat.format(fragmentdate));
                }
            }
        } else {
            for (int i = 0; i < NUM_PAGES; i++) {
                Date fragmentdate = new Date(System.currentTimeMillis() + ((i - 2) * 86400000));
                SimpleDateFormat mformat = new SimpleDateFormat(getActivity().getString(R.string.dateFormat));
                viewFragments[i] = new MainScreenFragment();
                viewFragments[i].setFragmentDate(mformat.format(fragmentdate));
            }
        }

        mPagerHandler.setAdapter(mPagerAdapter);
        mPagerHandler.setCurrentItem(MainActivity.current_fragment);

        return rootView;
    }

    private class myPageAdapter extends FragmentStatePagerAdapter {

        @Override
        public Fragment getItem(int i) {
            return viewFragments[i];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public myPageAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            int layoutDirection = 0;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Configuration configuration = getResources().getConfiguration();

                layoutDirection = configuration.getLayoutDirection();
            }

            if(layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                return getDayName(getActivity(),
                        System.currentTimeMillis() + ((position - 2) * 86400000));
            } else {
                return getDayName(getActivity(),
                        System.currentTimeMillis() - ((position - 2) * 86400000));
            }
        }

        public String getDayName(Context context, long dateInMillis) {
            // If the date is today, return the localized version of "Today" instead of the actual
            // day name.

            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);

            if (julianDay == currentJulianDay) {
                return context.getString(R.string.today);
            } else if (julianDay == currentJulianDay + 1) {
                return context.getString(R.string.tomorrow);
            } else if (julianDay == currentJulianDay - 1) {
                return context.getString(R.string.yesterday);
            } else {
                Time time = new Time();

                time.setToNow();
                // Otherwise, the format is just the day of the week (e.g "Wednesday".
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

                return dayFormat.format(dateInMillis);
            }
        }

    }

}
