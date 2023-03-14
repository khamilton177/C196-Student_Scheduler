package com.thecodebarista.c196_studentscheduler.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Pojo representing the database table assessments.
 */
@Entity (tableName = "assessments",
        foreignKeys = @ForeignKey(entity = Course.class,
                        parentColumns = "id",
                        childColumns = "course_id"),
        indices = {@Index(value = "title", unique = true)})
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int assessmentID;

    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseID;

    @NonNull
    private AssessmentType type;

    @NonNull
    @ColumnInfo(name = "title")
    private String assessmentTitle;

    @ColumnInfo(name = "start_date")
    private String startDt;

    @ColumnInfo(name = "end_date")
    private String endDt;

    /**
     * Default empty Constructor.
     */
    public Assessment() {
    }

    /**
     * All Course persisted member Constructor.
     * @param assessmentID Primary Key Assessment ID
     * @param courseID Foreign Key Course ID
     * @param type Assessment Type
     * @param assessmentTitle Assessment Title
     * @param startDt Assessment Start Date
     * @param endDt Assessment End Date
     */
    public Assessment(int assessmentID, int courseID, AssessmentType type, @NonNull String assessmentTitle, String startDt, String endDt) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.type = type;
        this.assessmentTitle = assessmentTitle;
        this.startDt = startDt;
        this.endDt = endDt;
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

    @NonNull
    public AssessmentType getType() {
        return type;
    }

    public void setType(@NonNull AssessmentType type) {
        this.type = type;
    }

    @NonNull
    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(@NonNull String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
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

    /**
     * Inner enum Class for type field.
     */
    public enum AssessmentType {
        OBJECTIVE, PERFORMANCE;
    }
}
