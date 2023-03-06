package com.thecodebarista.c196_studentscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Pojo representing the database table instructors.
 */
@Entity (tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instructorID;
    @NonNull
    private int courseID;
    private String name;
    private String phoneNumber;
    private String email;

}
