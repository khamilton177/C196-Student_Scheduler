package com.thecodebarista.c196_studentscheduler.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.thecodebarista.c196_studentscheduler.entities.Assessment;

@Dao
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);
}
