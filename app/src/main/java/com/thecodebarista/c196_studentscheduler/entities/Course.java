package com.thecodebarista.c196_studentscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * POJO representing the database table courses.
 */
@Entity (tableName = "courses",
        foreignKeys = {@ForeignKey(entity = Term.class,
                        parentColumns = "id",
                        childColumns = "term_id"),
                        @ForeignKey(entity = Instructor.class,
                            parentColumns = "id",
                            childColumns = "instructor_id")})
public class Course {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int courseID;

    @NonNull
    @ColumnInfo(name = "term_id")
    private int termID;

    private String title;

    @ColumnInfo(name = "start_date")
    private String startDt;

    @ColumnInfo(name = "end_date")
    private String endDt;

    @NonNull
    private CourseStatus status;

    private String notes;

    @NonNull
    @ColumnInfo(name = "instructor_id")
    private int instructorID;

    /**
     * Default empty Constructor
     */
    public Course() {
    }

    /**
     * All Course persisted member Constructor
     * @param courseID Primary Key Course ID
     * @param termID Foreign Key Term ID
     * @param title Course Title
     * @param startDt Course Start Date
     * @param endDt Course End Date
     * @param status Course Status
     * @param notes Course Optional Note
     * @param instructorID Foreign Key Instructor ID
     */
    public Course(int courseID, int termID, String title, String startDt,
                  String endDt, @NonNull CourseStatus status, String notes, int instructorID) {
        this.courseID = courseID;
        this.termID = termID;
        this.title = title;
        this.startDt = startDt;
        this.endDt = endDt;
        this.status = status;
        this.notes = notes;
        this.instructorID = instructorID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    @NonNull
    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(@NonNull CourseStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    /**
     * Inner enum Class hold ENUM values used to populate the Course 'status' field without creating a status table.
     */
    public enum CourseStatus {
        WIP("In Progress"),
        COMP("Completed"),
        DROP("Dropped"),
        PLAN("Plan to Take");

        private String status;

        CourseStatus(String value) {
            this.status = value;
        }

        public String getStatus() {
            return status;
        }
    }
}
