package barqsoft.footballscores;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import barqsoft.footballscores.DatabaseContract.scores_table;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Scores.db";
    private static final int DATABASE_VERSION = 2;

    public ScoresDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CreateScoresTable = String.format(
                "CREATE TABLE %s (\n" +
                        "            %s INTEGER PRIMARY KEY,\n" +
                        "            %s TEXT NOT NULL,\n" +
                        "            %s INTEGER NOT NULL,\n" +
                        "            %s TEXT NOT NULL,\n" +
                        "            %s TEXT NOT NULL,\n" +
                        "            %s INTEGER NOT NULL,\n" +
                        "            %s TEXT NOT NULL,\n" +
                        "            %s TEXT NOT NULL,\n" +
                        "            %s INTEGER NOT NULL,\n" +
                        "            %s INTEGER NOT NULL,\n" +
                        "            UNIQUE (%s) ON CONFLICT REPLACE);",
                DatabaseContract.SCORES_TABLE,
                scores_table._ID,
                scores_table.DATE_COL,
                scores_table.TIME_COL,
                scores_table.HOME_COL,
                scores_table.AWAY_COL,
                scores_table.LEAGUE_COL,
                scores_table.HOME_GOALS_COL,
                scores_table.AWAY_GOALS_COL,
                scores_table.MATCH_ID,
                scores_table.MATCH_DAY,
                scores_table.MATCH_ID);

        db.execSQL(CreateScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Remove old values when upgrading.
        db.execSQL(String.format(Resources.getSystem().getString(R.string.dropScoresTableSql),
                DatabaseContract.SCORES_TABLE));
    }

}
