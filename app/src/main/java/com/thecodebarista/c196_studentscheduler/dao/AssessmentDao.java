package com.thecodebarista.c196_studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY id ASC")
    List<Term> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE type = :type ORDER BY id ASC")
    List<Term> getAssessmentsByType(Assessment.AssessmentType type);

    @Query("SELECT * FROM assessments WHERE course_id = :courseID ORDER BY id ASC")
    List<Term> getAllCourseAssessments(int courseID);
}
