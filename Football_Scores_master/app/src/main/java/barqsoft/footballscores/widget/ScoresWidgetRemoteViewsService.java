package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.lang.annotation.Target;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresProvider;
import barqsoft.footballscores.Utilities;

/**
 * Created by Shawn on 10/22/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ScoresWidgetRemoteViewsService extends RemoteViewsService {

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScoresRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class ScoresRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor mData = null;
    private Context mContext;
    private int mAppWidgetId;

    private final String[] SCORES_COLUMNS = {
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.TIME_COL
    };

    private final int INDEX_HOME = 0;
    private final int INDEX_AWAY = 1;
    private final int INDEX_HOME_GOALS = 2;
    private final int INDEX_AWAY_GOALS = 3;
    private final int INDEX_TIME = 4;

    public ScoresRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (mData != null) {
            mData.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        Uri scoresByDateUri = DatabaseContract.scores_table.buildScoreWithDate();
        String todaysDate[] = new String[1];
        SimpleDateFormat dateFormat = new SimpleDateFormat(mContext.getString(R.string.dateFormat));
        todaysDate[0] = dateFormat.format(new Date());

        mData = mContext.getContentResolver().query(scoresByDateUri, SCORES_COLUMNS, null,
                todaysDate, DatabaseContract.scores_table.TIME_COL + " ASC");

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (mData != null) {
            mData.close();
            mData = null;
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mData == null || !mData.moveToPosition(position)) {
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_scores_list_item);

        views.setTextViewText(R.id.home_name, mData.getString(INDEX_HOME));
        views.setTextViewText(R.id.away_name, mData.getString(INDEX_AWAY));
        views.setTextViewText(R.id.date_textview, mData.getString(INDEX_TIME));
        views.setTextViewText(R.id.score_textview,
                Utilities.getScores(mData.getInt(INDEX_HOME_GOALS), mData.getInt(INDEX_AWAY_GOALS),
                        mContext));
        views.setImageViewResource(R.id.home_crest, Utilities.getTeamCrestByTeamName(
                mData.getString(INDEX_HOME), mContext));
        views.setImageViewResource(R.id.away_crest, Utilities.getTeamCrestByTeamName(
                mData.getString(INDEX_AWAY), mContext));

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(mContext.getString(R.string.dateTimeFormat));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            views.setContentDescription(R.id.home_crest, mData.getString(INDEX_HOME));
            views.setContentDescription(R.id.away_crest, mData.getString(INDEX_AWAY));
        }
        /*views.setTextViewText(R.id.widget_description, description);
        views.setTextViewText(R.id.widget_high_temperature, formattedMaxTemperature);
        views.setTextViewText(R.id.widget_low_temperature, formattedMinTemperature);*/

        final Intent fillInIntent = new Intent();
        /*String locationSetting =
                Utility.getPreferredLocation(DetailWidgetRemoteViewsService.this);
        Uri weatherUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(
                locationSetting,
                dateInMillis);*/
        /*fillInIntent.setData(weatherUri);*/
        //views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
