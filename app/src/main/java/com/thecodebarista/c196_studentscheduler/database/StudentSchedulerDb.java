package com.thecodebarista.c196_studentscheduler.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thecodebarista.c196_studentscheduler.dao.AssessmentDao;
import com.thecodebarista.c196_studentscheduler.dao.CourseDao;
import com.thecodebarista.c196_studentscheduler.dao.InstructorDao;
import com.thecodebarista.c196_studentscheduler.dao.TermDao;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 4, exportSchema = false)
public abstract class StudentSchedulerDb extends RoomDatabase {
    private static volatile StudentSchedulerDb DB_INSTANCE;
    private static final String DATABASE_NAME = "student_scheduler.db";

    public static StudentSchedulerDb getDatabase(final Context context){
        if (DB_INSTANCE == null) {
            DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentSchedulerDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return DB_INSTANCE;
    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract InstructorDao instructorDao();
}
