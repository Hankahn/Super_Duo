package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilities {

    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static String getLeague(int leagueNum, Context context) {
        switch (leagueNum) {
            case SERIE_A:
                return context.getString(R.string.seriaa);
            case PREMIER_LEGAUE:
                return context.getString(R.string.premierLeague);
            case CHAMPIONS_LEAGUE:
                return context.getString(R.string.championsLeague);
            case PRIMERA_DIVISION:
                return context.getString(R.string.primeraDivison);
            case BUNDESLIGA:
                return context.getString(R.string.bundesliga);
            default:
                return context.getString(R.string.teamUnknown);
        }
    }

    public static String getMatchDay(int match_day, int league_num, Context context) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return context.getString(R.string.matchDay6);
            } else if (match_day == 7 || match_day == 8) {
                return context.getString(R.string.matchDayFirstKnockoutRound);
            } else if (match_day == 9 || match_day == 10) {
                return context.getString(R.string.matchDayQuarterFinal);
            } else if (match_day == 11 || match_day == 12) {
                return context.getString(R.string.matchDaySemiFinal);
            } else {
                return context.getString(R.string.matchDayFinal);
            }
        } else {
            return context.getString(R.string.matchDayDefault) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals, int awaygoals, Context context) {
        if (home_goals < 0 || awaygoals < 0) {
            return context.getString(R.string.emptyScore);
        } else {
            return String.format(context.getString(R.string.scoreTemplate),
                    String.valueOf(home_goals), String.valueOf(awaygoals));
        }
    }

    public static int getTeamCrestByTeamName(String teamName, Context context) {
        if (teamName == null) {
            return R.drawable.no_icon;
        }

        if (teamName.equals(context.getString(R.string.teamArsenalLondonFc))) {
            return R.drawable.arsenal;
        } else if (teamName.equals(context.getString(R.string.teamAstonVilla))) {
            return R.drawable.aston_villa;
        } else if(teamName.equals(context.getString(R.string.teamChelsea))) {
            return R.drawable.chelsea;
        } else if(teamName.equals(context.getString(R.string.teamCrystalPalace))) {
            return R.drawable.crystal_palace_fc;
        } else if (teamName.equals(context.getString(R.string.teamEvertonFc))) {
            return R.drawable.everton_fc_logo1;
        } else if (teamName.equals(context.getString(R.string.teamHullCity))) {
            return R.drawable.hull_city_afc_hd_logo;
        } else if (teamName.equals(context.getString(R.string.teamLeicesterCity))) {
            return R.drawable.leicester_city_fc_hd_logo;
        } else if (teamName.equals(context.getString(R.string.teamManchesterUnitedFc))) {
            return R.drawable.manchester_united;
        } else if (teamName.equals(context.getString(R.string.teamRealMadrid))) {
            return R.mipmap.real_madrid;
        } else if (teamName.equals(context.getString(R.string.teamStokeCityFc))) {
            return R.drawable.stoke_city;
        } else if (teamName.equals(context.getString(R.string.teamSunderlandAfc))) {
            return R.drawable.sunderland;
        } else if (teamName.equals(context.getString(R.string.teamSwanseaCity))) {
            return R.drawable.swansea_city_afc;
        } else if (teamName.equals(context.getString(R.string.teamTottenhemHotspurFc))) {
            return R.drawable.tottenham_hotspur;
        } else if (teamName.equals(context.getString(R.string.teamWestBromwichAlbion))) {
            return R.drawable.west_bromwich_albion_hd_logo;
        } else if (teamName.equals(context.getString(R.string.teamWestHamUnitedFc))) {
            return R.drawable.west_ham;
        } else {
            return R.drawable.no_icon;
        }
    }

}
