package com.thecodebarista.c196_studentscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


/**
 * Pojo representing the database table instructors.
 */
@Entity (tableName = "instructors",
        foreignKeys = @ForeignKey(entity = Course.class,
                parentColumns = "id",
                childColumns = "course_id"))
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int instructorID;

    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseID;

    private String name;
    private String phoneNumber;
    private String email;

    /**
     * Default empty Constructor
     */
    public Instructor() {
    }

    /**
     *
     * @param instructorID Primary Key Instructor ID
     * @param courseID Foreign Key Course ID
     * @param name Instructor name
     * @param phoneNumber Instructor phone number
     * @param email Instructor email address
     */
    public Instructor(int instructorID, int courseID, String name, String phoneNumber, String email) {
        this.instructorID = instructorID;
        this.courseID = courseID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
