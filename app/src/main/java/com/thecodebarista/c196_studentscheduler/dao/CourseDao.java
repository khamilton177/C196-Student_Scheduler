package com.thecodebarista.c196_studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY id ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE term_id = :termID ORDER BY id ASC")
    List<Course> getAllTermCourses(int termID);

    @Query("SELECT * FROM courses WHERE status = :courseStatus ORDER BY id ASC")
    List<Course> getCoursesByStatus(Course.CourseStatus courseStatus);

    @Query("SELECT id FROM courses ORDER BY id DESC LIMIT 1")
    int getLastCourseInsert();
}
