package com.thecodebarista.c196_studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

@Dao
public interface InstructorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM instructors ORDER BY id ASC")
    List<Instructor> getAllInstructors();

/*    @Query("SELECT * FROM instructors WHERE course_id = :courseID ORDER BY id ASC")
    List<Instructor> getInstructorByCourse(int courseID);*/

}
