package com.example.selfcareapp;

import android.content.Context;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

//class for handling business logic for sessions and goals
public class GoalHelper {
    private GoalDbHelper dbHelper;

    //constructor
    public GoalHelper(Context context){
        dbHelper = new GoalDbHelper(context);
    }


    //create a new goal
    public GoalModel createGoal(GoalModel.GoalType type, int targetMinutes){
        GoalModel goal = new GoalModel(type, targetMinutes);
        long id = dbHelper.addGoal(goal);
        goal.setId(id);
        return goal;
    }

    //record a breathing session
    public void recordSession(int durationMinutes, String exerciseType){
        SessionRecord session = new SessionRecord(durationMinutes, exerciseType);
        dbHelper.addSession(session);
    }

    // Get progress for daily goal
    public GoalProgress getDailyProgress(LocalDate date) {
        List<GoalModel> activeGoals = dbHelper.getActiveGoals();
        GoalModel dailyGoal = null;

        for (GoalModel goal : activeGoals) {
            if (goal.getType() == GoalModel.GoalType.DAILY) {
                dailyGoal = goal;
                break;
            }
        }

        if (dailyGoal == null) {
            return null;
        }

        GoalProgress progress = new GoalProgress(dailyGoal, date);
        List<SessionRecord> sessions = dbHelper.getSessionsInRange(date, date);

        for (SessionRecord session : sessions) {
            progress.addMinutes(session.getDurationMinutes());
        }

        return progress;
    }

    // Get progress for weekly goal
    public GoalProgress getWeeklyProgress(LocalDate date) {
        List<GoalModel> activeGoals = dbHelper.getActiveGoals();
        GoalModel weeklyGoal = null;

        for (GoalModel goal : activeGoals) {
            if (goal.getType() == GoalModel.GoalType.WEEKLY) {
                weeklyGoal = goal;
                break;
            }
        }

        if (weeklyGoal == null) {
            return null;
        }

        GoalProgress progress = new GoalProgress(weeklyGoal, date);

        // Calculate start and end of week
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6); // Saturday

        List<SessionRecord> sessions = dbHelper.getSessionsInRange(startOfWeek, endOfWeek);

        for (SessionRecord session : sessions) {
            progress.addMinutes(session.getDurationMinutes());
        }

        return progress;
    }
}
