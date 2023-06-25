package com.thecodebarista.c196_studentscheduler.UI;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.InputQueue;

import androidx.appcompat.app.AlertDialog;

import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.security.auth.callback.Callback;

public interface DegreePlanner {
    public static final String COURSE_START_DATE_NOTIFY = " course starts ";
    public static final String COURSE_END_DATE_NOTIFY = " course is ending ";
    public static final String ASSESSMENT_START_DATE_NOTIFY = " assessment starts ";
    public static final String ASSESSMENT_END_DATE_NOTIFY = " assessment is ending ";
    public String dateFormat = "MM/dd/yyyy";
    public SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.US);

    default Long CreateTriggerDate(String dpDate) {
        Date detailsDate = null;
        try
        {
            detailsDate = dateFormatter.parse(dpDate);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        Long trigger = detailsDate.getTime();
        return trigger;
    }

    default void CreatePendingIntent(Context packageContext, Long trigger, String title, String msg, String dateValue) {
        Intent intent = new Intent(packageContext, DegreePlannerReceiver.class);
        intent.putExtra("notifyDate", title + msg + dateValue);
        PendingIntent pendBroadcast = PendingIntent.getBroadcast(packageContext, ++MainActivity.alarmNumber, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) packageContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendBroadcast);
    }

    default Intent ShareMessage(String title, String msg) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent, null);
        return shareIntent;
    }

    boolean finishCallback();

    default void ConfirmDeleteDialog(Context context, StudentSchedulerRepo repo, Object obj) {
        int msg = -1;
        if (obj instanceof Term) {
            msg = R.string.confDelTerm;
        } else if (obj instanceof Course) {
            msg = R.string.confDelCourse;
        } else {
            msg = R.string.confDelAssessment;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (obj instanceof Term) {
                            repo.delete((Term)obj);
                        } else if (obj instanceof Course) {
                            repo.delete((Course)obj);
                        } else {
                            repo.delete((Assessment)obj);
                        }
                        finishCallback();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog confDelItem = alertBuilder.create();
        confDelItem.show();
    }

    public default void seedTerms(Calendar calendar, StudentSchedulerRepo repo) {
        String today = dateFormatter.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 28);
        String today3 = dateFormatter.format(calendar.getTime());
        int termID = 0;
        for (int i = 1; i < 25; ++i) {
            String termTitle = String.format("Term Seed %d", i);
            Term term = new Term(termID, termTitle, today, today3);
            repo.insert(term);
        }
        finishCallback();
    }

}
