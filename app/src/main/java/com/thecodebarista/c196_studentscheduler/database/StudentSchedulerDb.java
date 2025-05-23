package com.thecodebarista.c196_studentscheduler.database;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.UI.MainActivity;
import com.thecodebarista.c196_studentscheduler.dao.AssessmentDao;
import com.thecodebarista.c196_studentscheduler.dao.CourseDao;
import com.thecodebarista.c196_studentscheduler.dao.InstructorDao;
import com.thecodebarista.c196_studentscheduler.dao.TermDao;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 22, exportSchema = false)
public abstract class StudentSchedulerDb extends RoomDatabase {
    private static volatile StudentSchedulerDb DB_INSTANCE;
    private static final String DATABASE_NAME = "student_scheduler.db";


    public static StudentSchedulerDb getDatabase(final Context context){
        final Context appContext = context.getApplicationContext();
 /*
       RoomDatabase.Callback seedInstructor = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
                    Resources resource = appContext.getResources();
                    System.out.println("DOING INSTRUCTOR CALLBACK");
                });
            }
        };
*/
        if (DB_INSTANCE == null) {
            DB_INSTANCE = Room.databaseBuilder(appContext, StudentSchedulerDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    //.addCallback(seedInstructor)
                    .build();
        }
        return DB_INSTANCE;

    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract InstructorDao instructorDao();
}
