package com.thecodebarista.c196_studentscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Pojo representing the database table assessments.
 */
@Entity (tableName = "assessments",
        foreignKeys = @ForeignKey(entity = Course.class,
                        parentColumns = "id",
                        childColumns = "course_id"))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int assessmentID;

    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseID;

    private AssessmentType type;

    /**
     * Default empty Constructor
     */
    public Assessment() {
    }

    /**
     * All Course persisted member Constructor
     * @param assessmentID Primary Key Assessment ID
     * @param courseID Foreign Key Course ID
     * @param type Assessment Type
     */
    public Assessment(int assessmentID, int courseID, AssessmentType type) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.type = type;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public AssessmentType getType() {
        return type;
    }

    public void setType(AssessmentType type) {
        this.type = type;
    }

    /**
     * Inner enum Class for type field.
     */
    public enum AssessmentType {
        OBJECTIVE, PERFORMANCE;
    }

}
